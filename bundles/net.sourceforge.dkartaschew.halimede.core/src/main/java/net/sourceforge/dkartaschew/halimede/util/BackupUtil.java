/*-
 * Halimede Certificate Manager Plugin for Eclipse 
 * Copyright (C) 2017-2019 Darran Kartaschew 
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 * 
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the Eclipse
 * Public License, v. 2.0 are satisfied: GNU General Public License, version 2
 * with the GNU Classpath Exception which is
 * available at https://www.gnu.org/software/classpath/license.html.
 */

package net.sourceforge.dkartaschew.halimede.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

import net.sourceforge.dkartaschew.halimede.backup.BackupManifest;
import net.sourceforge.dkartaschew.halimede.backup.BackupManifestEntry;
import net.sourceforge.dkartaschew.halimede.backup.IBackupProgressListener;
import net.sourceforge.dkartaschew.halimede.data.CertificateAuthority;

/**
 * Backup and Restore Utility Functions.
 */
public class BackupUtil {

	public final static String MANIFEST = "manifest.xml";

	/**
	 * Create a backup of the Certificate Authority
	 * 
	 * @param ca The Certificate Authority
	 * @param filename The filename to write to.
	 * @param listener The activity listener. (may be NULL).
	 * @throws IOException If the operation fails.
	 */
	public static void backup(CertificateAuthority ca, Path filename, IBackupProgressListener listener)
			throws IOException {
		Objects.requireNonNull(ca, "Certificate Authority not defined");
		Objects.requireNonNull(filename, "Target filename not defined");
		if (Files.exists(filename)) {
			Files.delete(filename);
		}
		ca.getActivityLogger().log(Level.INFO, "Backup CA to {0}", filename);
		final Path basePath = ca.getBasePath().toAbsolutePath();
		final List<Path> entries = new ArrayList<>();
		Files.walkFileTree(basePath, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
				if (!attrs.isDirectory()) {
					entries.add(file);
				}
				return FileVisitResult.CONTINUE;
			}
		});

		boolean continueActivity = true;
		int currentFileCount = 0;
		final int totalFileCount = entries.size();

		BackupManifest manifest = new BackupManifest();
		manifest.setCreationDate(ZonedDateTime.now());
		manifest.setUuid(ca.getCertificateAuthorityID());
		manifest.setDescription(ca.getDescription());

		try (ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(filename.toFile()))) {
			zip.setComment(ca.getCertificateAuthorityID().toString());
			// Add our entrie
			for (Path file : entries) {

				String entry = file.subpath(basePath.getNameCount(), file.getNameCount()).toString();
				if (entry.startsWith(File.separator)) {
					entry = entry.substring(1);
				}
				// Ensure entry uses '/' as path - 4.4.17.1 of the zip spec.
				entry.replace('\\', '/');
				entry = manifest.getDescription() + "/" + entry;

				if (listener != null) {
					continueActivity = listener.progress(entry, currentFileCount++, totalFileCount);
				}
				if (!continueActivity) {
					break;
				}
				ZipEntry e = new ZipEntry(entry);
				try {
					zip.putNextEntry(e);
					byte[] data = Files.readAllBytes(file);
					zip.write(data, 0, data.length);

					manifest.addEntry(new BackupManifestEntry(entry, //
							data.length, //
							Strings.toHexString(Digest.sha512(data))));
				} finally {
					zip.closeEntry();
				}
			}
			// Add in the manifest.
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			BackupManifest.write(stream, manifest);

			ZipEntry e = new ZipEntry(MANIFEST);
			try {
				zip.putNextEntry(e);
				byte[] data = stream.toByteArray();
				zip.write(data, 0, data.length);
			} finally {
				zip.closeEntry();
			}
		} finally {
			if (listener != null) {
				continueActivity = listener.progress("", totalFileCount, totalFileCount);
			}
			if (!continueActivity) {
				Files.delete(filename);
			}
		}
	}

	/**
	 * Restore the given backup file to the given destination
	 * 
	 * @param filename The filename of the backup file
	 * @param destination The destination location
	 * @param listener The activity listener. (may be NULL).
	 * @throws IOException If reading the backup file fails, or restoring operation fails.
	 */
	public static void restore(Path filename, Path destination, IBackupProgressListener listener) throws IOException {
		Objects.requireNonNull(filename, "Backup filename not defined");
		Objects.requireNonNull(destination, "Destination Location not defined");
		if (!Files.exists(filename) || !Files.isReadable(filename)) {
			throw new IOException("Backup file '" + filename.toString() + "' does not exist or is not readable");
		}
		if (!Files.isDirectory(destination) || !Files.isWritable(destination)) {
			throw new IOException("Destination Location '" + filename.toString()
					+ "' does not exist, is not a Directory or is not writable");
		}
		boolean continueActivity = true;
		int currentFileCount = 0;
		int totalFileCount = 0;
		Path basePath = null;

		// Open the zip container.
		try (ZipFile zip = new ZipFile(filename.toFile())) {
			// Check for manifest
			ZipEntry zipEntry = zip.getEntry(MANIFEST);
			if (zipEntry == null) {
				throw new IOException("File does not appear to be a Halimede Backup file. Missing backup manifest.");
			}
			// Read in the manifest and compare the CA UUID to the zip comment.
			BackupManifest manifest = BackupManifest.read(zip.getInputStream(zipEntry));
			UUID uuid = UUID.fromString(zip.getComment());
			if (!manifest.getUuid().equals(uuid)) {
				throw new IOException("File does not appear to be a Halimede Backup file. "
						+ "Certificate Authority UUID doesn't match backup file UUID.");
			}
			// Start extraction
			basePath = destination.resolve(manifest.getDescription()).toAbsolutePath();
			if (!basePath.startsWith(destination)) {
				throw new IOException("Invalid entry in backup manifest found");
			}
			if (Files.exists(basePath)) {
				throw new IOException("Restore function will overwrite existing files, aborting");
			}
			totalFileCount = manifest.getEntries().size();
			for (BackupManifestEntry e : manifest.getEntries()) {
				// Notification listener.
				if (listener != null) {
					continueActivity = listener.progress(e.getFilename(), currentFileCount++, totalFileCount);
				}
				if (!continueActivity) {
					break;
				}

				/*
				 * Get the target location. Ensure the target location is strictly in the destination folder.
				 */
				Path target = destination.resolve(e.getFilename()).toAbsolutePath();
				if (!target.startsWith(destination)) {
					throw new IOException("Invalid entry in backup file found");
				}
				if (!Files.exists(target.getParent())) {
					Files.createDirectories(target.getParent());
				}
				/*
				 * Extract the contents of file, verify the contents, and then write out...
				 */
				try (FileOutputStream out = new FileOutputStream(target.toFile(), false);
						InputStream in = zip.getInputStream(zip.getEntry(e.getFilename()))) {
					byte[] data = new byte[(int) e.getSize()];
					int read = in.read(data);
					if (read != e.getSize()) {
						throw new IOException("Entry '" + e.getFilename() + "' not read fully?");
					}
					String sha512 = Strings.toHexString(Digest.sha512(data));
					if (!sha512.equalsIgnoreCase(e.getSha512())) {
						throw new IOException("Entry '" + e.getFilename() + "' fails digest verification?");
					}
					out.write(data);
					out.flush();
				}

			}
		} finally {
			if (listener != null) {
				continueActivity = listener.progress("", totalFileCount, totalFileCount);
			}
			if (!continueActivity) {
				// Cleanup...
				if (basePath != null && Files.exists(basePath)) {
					Files.walk(basePath)//
							.sorted(Comparator.reverseOrder())//
							.map(Path::toFile)//
							.forEach(File::delete);
				}
			}
		}
	}

}