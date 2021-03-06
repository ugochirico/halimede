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

package net.sourceforge.dkartaschew.halimede;

import java.net.URL;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.resource.LocalResourceManager;
import org.eclipse.jface.resource.ResourceManager;
import org.eclipse.swt.graphics.RGB;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

/**
 * General Plugin defaults/consts.
 * @version 1.0
 */
public class PluginDefaults {

	/**
	 * Application title
	 */
	public static final String APPLICATION_NAME = "Halimede CA";
	/**
	 * Full application name.
	 */
	public static final String APPLICATION_FULLNAME = "Halimede Certificate Authority";
	/**
	 * Application website.
	 */
	public static final String APPLICATION_WEBSITE = "https://halimede.sourceforge.io/";
	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "net.sourceforge.dkartaschew.halimede.core";

	/**
	 * ID of the preferred stack element to add the part to dynamically.
	 */
	public static final String EDITOR = "org.eclipse.e4.primaryDataStack";

	/**
	 * Default command param for the editor to add new views to.
	 */
	public static final String COMMAND_PARAM = "net.sourceforge.dkartaschew.halimede.handler.show.commandparameter.editorid";
	
	/**
	 * Default image for Application Icon at size 64px
	 */
	public static final String IMG_APPLICATION = "icons/application-icon.png";
	/**
	 * Default image for Certificate at size 16px
	 */
	public static final String IMG_CERTIFICATE = "icons/application-certificate.png";

	/**
	 * Default image for Folder at size 16px
	 */
	public static final String IMG_FOLDER = "icons/folder.png";
	
	/**
	 * Default image for Lock Overlay at 16px.
	 */
	public static final String IMG_LOCK_OVERLAY = "icons/deadlock_ovr.png";
	
	/**
	 * Default image for List add at size 16px
	 */
	public static final String IMG_LIST_ADD = "icons/list-add.png";
	/**
	 * Default image for List add at size 16px
	 */
	public static final String IMG_LIST_ADD_DARK = "icons/list-add-dark.png";
	
	/**
	 * Default image for List remove at size 16px
	 */
	public static final String IMG_LIST_REMOVE = "icons/list-remove.png";
	/**
	 * Default image for List remove at size 16px
	 */
	public static final String IMG_LIST_REMOVE_DARK = "icons/list-remove-dark.png";
	/**
	 * Default image for List edit at size 16px
	 */
	public static final String IMG_LIST_EDIT = "icons/list-edit.png";
	/**
	 * Default image for List edit at size 16px
	 */
	public static final String IMG_LIST_EDIT_DARK = "icons/list-edit-dark.png";
	/**
	 * Default image for System Information at size 16px
	 */
	public static final String IMG_SYSTEM_INFORMATION = "icons/dialog-question.png";
	/**
	 * Default image for System Information at size 16px
	 */
	public static final String IMG_SYSTEM_INFORMATION_DARK = "icons/dialog-question-dark.png";
	
	/**
	 * The node with the preferences for the base paths.
	 */
	public static final String PREFERENCES_NODE_PATHS = "paths";
	
	/**
	 * Minimum length of the password for warnings.
	 */
	public static final int MIN_PASSWORD_LENGTH = 6;

	/**
	 * The length for header labels in parts.
	 */
	public static final int PART_HEADER_LENGTH = 60;
	
	/**
	 * The colour to use on the header (Certificate Details view) when a light theme is in use.
	 * <p>
	 * Note: Non-final to allow consumers to modify to suite application theme
	 */
	public static final RGB HEADER_COLOUR_LIGHT = new RGB(26, 82, 118); // Midnight Blue

	/**
	 * The colour to use on the header (Certificate Details view) when a dark theme is in use.
	 * <p>
	 * Note: Non-final to allow consumers to modify to suite application theme
	 */
	public static final RGB HEADER_COLOUR_DARK = new RGB(133, 193, 233); 
	
	/**
	 * Local resource manager for any images.
	 */
	private static volatile ResourceManager resourceManager = null;
	/**
	 * Access lock for resource manager.
	 */
	private final static Object resourceManagerLock = new Object();
	
	/**
	 * Dispose the resource manager.
	 */
	public static void dispose() {
		// garbage collect system resources
		if (resourceManager != null) {
			synchronized (resourceManagerLock) {
				if (resourceManager != null)
					resourceManager.dispose();
				resourceManager = null;
			}
		}
	}

	/**
	 * Get the local resource manager
	 * 
	 * @return The local resource manager.
	 */
	public static ResourceManager getResourceManager() {
		if (resourceManager == null) {
			synchronized (resourceManagerLock) {
				if (resourceManager == null)
					resourceManager = new LocalResourceManager(JFaceResources.getResources());
			}
		}
		return resourceManager;
	}

	/**
	 * Get the image descriptor for the image path
	 * 
	 * @param image the image path
	 * @return An image descriptor
	 */
	public static ImageDescriptor createImageDescriptor(String image) {
		Bundle bundle = FrameworkUtil.getBundle(PluginDefaults.class);
		URL url = FileLocator.find(bundle, new Path(image), null);
		return ImageDescriptor.createFromURL(url);
	}
	
	/**
	 * Get the image descriptor for the image path
	 * 
	 * @param bundle The bundle containing the image.
	 * @param image the image path
	 * @return An image descriptor
	 */
	public static ImageDescriptor createImageDescriptor(Bundle bundle, String image) {
		if (bundle == null) {
			return createImageDescriptor(image);
		}
		URL url = FileLocator.find(bundle, new Path(image), null);
		return ImageDescriptor.createFromURL(url);
	}
	
}
