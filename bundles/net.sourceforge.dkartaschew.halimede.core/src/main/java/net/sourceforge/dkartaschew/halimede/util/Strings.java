/*-
 * Halimede Certificate Manager Plugin for Eclipse 
 * Copyright (C) 2017-2018 Darran Kartaschew 
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

import java.lang.reflect.Field;
import java.math.BigInteger;
import java.security.PublicKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;

import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.jce.interfaces.GOST3410PublicKey;
import org.bouncycastle.jce.provider.JCEECPublicKey;
import org.bouncycastle.pqc.jcajce.provider.rainbow.BCRainbowPublicKey;
import org.bouncycastle.pqc.jcajce.provider.sphincs.BCSphincs256PublicKey;
import org.bouncycastle.pqc.jcajce.provider.xmss.BCXMSSMTPublicKey;
import org.bouncycastle.pqc.jcajce.provider.xmss.BCXMSSPublicKey;
import org.bouncycastle.util.encoders.Hex;

public class Strings {

	/**
	 * Default wrapping for pretty print.
	 */
	public final static int WRAP = 32;

	/**
	 * Encode the given data as a String.
	 * 
	 * @param data The data to encode
	 * @return The data as hex encoded string
	 */
	public static String toHexString(byte[] data) {
		return Hex.toHexString(data);
	}

	/**
	 * Encode the given data as a String with the given separator between each byte value
	 * 
	 * @param data The data to encode
	 * @param separator The separator to use (NULL will be transformed to "")
	 * @param wrap The number of bytes per line. (zero or negative is no wrap).
	 * @return The data as hex encoded string
	 */
	public static String toHexString(byte[] data, String separator, int wrap) {
		if (wrap <= 0) {
			wrap = Integer.MAX_VALUE;
		}
		if (separator == null) {
			separator = "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			sb.append(String.format("%02x", Byte.toUnsignedInt(data[i])));
			if (((i + 1) % wrap == 0) && (i + 1 != data.length)) {
				sb.append(System.lineSeparator());
			} else if (i + 1 != data.length) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * Encode the given data as a String with the given separator between each byte value
	 * 
	 * @param data The data to encode
	 * @param separator The separator to use (NULL will be transformed to "")
	 * @param wrap The number of bytes per line. (zero or negative is no wrap).
	 * @param wrapPrefix The prefix to apply for each wrapped line. (NULL will be transformed to "")
	 * @return The data as hex encoded string
	 */
	public static String toHexString(byte[] data, String separator, int wrap, String wrapPrefix) {
		if (wrap <= 0) {
			wrap = Integer.MAX_VALUE;
		}
		if (separator == null) {
			separator = "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			sb.append(String.format("%02x", Byte.toUnsignedInt(data[i])));
			if (((i + 1) % wrap == 0) && (i + 1 != data.length)) {
				sb.append(System.lineSeparator());
				if (wrapPrefix != null) {
					sb.append(wrapPrefix);
				}
			} else if (i + 1 != data.length) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * Encode the given data as a String with the given separator between each short value
	 * 
	 * @param data The data to encode
	 * @param separator The separator to use (NULL will be transformed to "")
	 * @param wrap The number of bytes per line. (zero or negative is no wrap).
	 * @param wrapPrefix The prefix to apply for each wrapped line. (NULL will be transformed to "")
	 * @return The data as hex encoded string
	 */
	private static String toHexString(short[] data, String separator, int wrap, String wrapPrefix) {
		if (wrap <= 0) {
			wrap = Integer.MAX_VALUE;
		}
		if (separator == null) {
			separator = "";
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < data.length; i++) {
			sb.append(String.format("%04x", toUnsignedInt(data[i])));
			if (((i + 1) % wrap == 0) && (i + 1 != data.length)) {
				sb.append(System.lineSeparator());
				if (wrapPrefix != null) {
					sb.append(wrapPrefix);
				}
			} else if (i + 1 != data.length) {
				sb.append(separator);
			}
		}
		return sb.toString();
	}

	/**
	 * Encode the given data as a String with the given separator between each short value
	 * 
	 * @param data The data to encode
	 * @param separator The separator to use (NULL will be transformed to "")
	 * @param wrap The number of bytes per line. (zero or negative is no wrap).
	 * @param wrapPrefix The prefix to apply for each wrapped line. (NULL will be transformed to "")
	 * @return The data as hex encoded string
	 */
	private static String toHexString(short[][] data, String separator, int wrap, String wrapPrefix) {
		if (wrap <= 0) {
			wrap = Integer.MAX_VALUE;
		}
		if (separator == null) {
			separator = "";
		}
		StringBuilder sb = new StringBuilder();
		for (int j = 0; j < data.length; j++) {
			short[] line = data[j];
			if (j != 0) {
				sb.append(System.lineSeparator());
				if (wrapPrefix != null) {
					sb.append(wrapPrefix);
				}
			}
			for (int i = 0; i < line.length; i++) {
				sb.append(String.format("%04x", toUnsignedInt(line[i])));
				if (((i + 1) % wrap == 0) && (i + 1 != line.length)) {
					sb.append(System.lineSeparator());
					if (wrapPrefix != null) {
						sb.append(wrapPrefix);
					}
				} else if (i + 1 != line.length) {
					sb.append(separator);
				}
			}
		}
		return sb.toString();
	}
	
    /**
     * Converts the argument to an {@code int} by an unsigned
     * conversion.  In an unsigned conversion to an {@code int}, the
     * high-order 24 bits of the {@code int} are zero and the
     * low-order 8 bits are equal to the bits of the {@code byte} argument.
     *
     * Consequently, zero and positive {@code byte} values are mapped
     * to a numerically equal {@code int} value and negative {@code
     * byte} values are mapped to an {@code int} value equal to the
     * input plus 2<sup>8</sup>.
     *
     * @param  x the value to convert to an unsigned {@code int}
     * @return the argument converted to {@code int} by an unsigned
     *         conversion
     * @since 1.8
     */
    public static int toUnsignedInt(short x) {
        return ((int) x) & 0xff;
    }
	
	/**
	 * Pretty format details of public key for display.
	 * 
	 * @param pkey The public key
	 * @return A pretty print version of the public key details (data only).
	 */
	public static String prettyPrint(PublicKey pkey) {
		if (pkey instanceof RSAPublicKey) {
			RSAPublicKey rsapkey = (RSAPublicKey) pkey;
			StringBuilder sb = new StringBuilder();
			sb.append("Modulus: ");
			sb.append(toHexString(rsapkey.getModulus().toByteArray(), " ", WRAP, "         "));
			sb.append(System.lineSeparator());
			sb.append("Exponent: ");
			sb.append(rsapkey.getPublicExponent().toString());
			sb.append(" (0x");
			sb.append(rsapkey.getPublicExponent().toString(16));
			sb.append(")");
			return sb.toString();
		}
		if (pkey instanceof DSAPublicKey) {
			DSAPublicKey dsapkey = (DSAPublicKey) pkey;
			StringBuilder sb = new StringBuilder();
			sb.append("Y: ");
			sb.append(toHexString(dsapkey.getY().toByteArray(), " ", WRAP, "   "));
			return sb.toString();
		}
		if (pkey instanceof JCEECPublicKey) {
			final JCEECPublicKey ecpriv = (JCEECPublicKey) pkey;
			StringBuilder sb = new StringBuilder();
			sb.append("X: ");
			sb.append(toHexString(ecpriv.getQ().getAffineXCoord().toBigInteger().toByteArray(), " ", WRAP, "   "));
			sb.append(System.lineSeparator());
			sb.append("Y: ");
			sb.append(toHexString(ecpriv.getQ().getAffineYCoord().toBigInteger().toByteArray(), " ", WRAP, "   "));
			return sb.toString();
		}
		if (pkey instanceof ECPublicKey) {
			final ECPublicKey ecpriv = (ECPublicKey) pkey;
			StringBuilder sb = new StringBuilder();
			sb.append("X: ");
			sb.append(toHexString(ecpriv.getW().getAffineX().toByteArray(), " ", WRAP, "   "));
			sb.append(System.lineSeparator());
			sb.append("Y: ");
			sb.append(toHexString(ecpriv.getW().getAffineY().toByteArray(), " ", WRAP, "   "));
			return sb.toString();
		}
		if (pkey instanceof GOST3410PublicKey) {
			GOST3410PublicKey gost = (GOST3410PublicKey) pkey;
			StringBuilder sb = new StringBuilder();
			sb.append("Y: ");
			sb.append(toHexString(gost.getY().toByteArray(), " ", WRAP, "   "));
			return sb.toString();
		}
		if (pkey instanceof BCRainbowPublicKey) {
			BCRainbowPublicKey rainbow = (BCRainbowPublicKey) pkey;
			// docLength, coeffquadratic, coeffsingular, coeffscalar
			StringBuilder sb = new StringBuilder();
			sb.append("DocLength: ");
			sb.append(rainbow.getDocLength());
			sb.append(System.lineSeparator());
			sb.append("coeffquadratic: ");
			sb.append(toHexString(rainbow.getCoeffQuadratic(), " ", WRAP / 2, "   "));
			sb.append(System.lineSeparator());
			sb.append("coeffsingular: ");
			sb.append(toHexString(rainbow.getCoeffSingular(), " ", WRAP / 2, "   "));
			sb.append(System.lineSeparator());
			sb.append("coeffscalar: ");
			sb.append(toHexString(rainbow.getCoeffScalar(), " ", WRAP / 2, "   "));
			return sb.toString();
		}
		if (pkey instanceof BCSphincs256PublicKey) {
			BCSphincs256PublicKey sphincs = (BCSphincs256PublicKey)pkey;
			StringBuilder sb = new StringBuilder();
			try {
				/*
				 * NASTY, but to determine the key, when need the digest.
				 */
				Field f = pkey.getClass().getDeclaredField("treeDigest");
				f.setAccessible(true);
				ASN1ObjectIdentifier digest = (ASN1ObjectIdentifier) f.get(pkey);
				if(digest.equals(NISTObjectIdentifiers.id_sha512_256)){
					sb.append("Tree Digest: SHA512-256");
				}
				if(digest.equals(NISTObjectIdentifiers.id_sha3_256)){
					sb.append("Tree Digest: SHA3-256");
				}
			} catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e) {
				//throw new RuntimeException("BC SPHINCS-256 modified", e);
				// Ignore here...
			}
			
			sb.append("Key Data: ");
			sb.append(toHexString(sphincs.getKeyData(), " ", WRAP, "   "));
			return sb.toString();
		}
		if (pkey instanceof BCXMSSPublicKey) {
			BCXMSSPublicKey xmss = (BCXMSSPublicKey)pkey;
			StringBuilder sb = new StringBuilder();
			sb.append("Digest: ");
			sb.append(xmss.getTreeDigest());
			sb.append(System.lineSeparator());
			sb.append("Height: ");
			sb.append(xmss.getHeight());
			// TODO: Get Root and PublicSeed
			return sb.toString();
		}
		if (pkey instanceof BCXMSSMTPublicKey) {
			BCXMSSMTPublicKey xmssmt = (BCXMSSMTPublicKey)pkey;
			StringBuilder sb = new StringBuilder();
			sb.append("Digest: ");
			sb.append(xmssmt.getTreeDigest());
			sb.append(System.lineSeparator());
			sb.append("Height: ");
			sb.append(xmssmt.getHeight());
			sb.append(System.lineSeparator());
			sb.append("Layers: ");
			sb.append(xmssmt.getLayers());
			// TODO: Get Root and PublicSeed
			return sb.toString();
		}
		return "";
	}

	/**
	 * Trim the string to the given length...
	 * <p>
	 * This will automatically truncate to line separator...
	 * 
	 * @param value The value to trim. (NULL will return "");
	 * @param wrap The max characters. Must be positive.
	 * @return The trimmed string.
	 * @throws IllegalArgumentException If the wrap value is not a positive value.
	 */
	public static String trim(String value, int wrap) throws IllegalArgumentException {
		if (value == null) {
			return "";
		}
		if (wrap < 0) {
			throw new IllegalArgumentException("Wrap " + wrap + " value must be positive");
		}
		value = value.trim();
		if (value.length() > wrap) {
			value = value.substring(0, wrap) + "...";
		}
		if (value.indexOf(System.lineSeparator()) > 0) {
			value = value.substring(0, value.indexOf(System.lineSeparator())) + "...";
		}
		return value;
	}

	/**
	 * Create a string that contains both hex and decimal.
	 * 
	 * @param s The value to encode
	 * @return The resulting string.
	 */
	public static String asDualValue(BigInteger s) {
		if (s == null) {
			return "0x0 (0)";
		}
		return "0x" + s.toString(16) + " (" + s.toString() + ")";
	}
}
