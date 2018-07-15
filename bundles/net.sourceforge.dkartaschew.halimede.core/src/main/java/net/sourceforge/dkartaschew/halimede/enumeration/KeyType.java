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

package net.sourceforge.dkartaschew.halimede.enumeration;

import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.jcajce.provider.asymmetric.dstu.BCDSTU4145PublicKey;
import org.bouncycastle.jce.interfaces.GOST3410PublicKey;
import org.bouncycastle.jce.spec.ECNamedCurveSpec;

import net.sourceforge.dkartaschew.halimede.data.KeyPairFactory;
import net.sourceforge.dkartaschew.halimede.exceptions.UnknownKeyTypeException;

/**
 * The type of private/public key pair type.
 */
public enum KeyType {
	/**
	 * RSA 512bit key
	 */
	RSA_512("RSA 512", "RSA", 512, null),
	/**
	 * RSA 1024bit key
	 */
	RSA_1024("RSA 1024", "RSA", 1024, null),
	/**
	 * RSA 2048bit key
	 */
	RSA_2048("RSA 2048", "RSA", 2048, null),
	/**
	 * RSA 4096bit key
	 */
	RSA_4096("RSA 4096", "RSA", 4096, null),
	/**
	 * RSA 8192bit key
	 */
	RSA_8192("RSA 8192", "RSA", 8192, null),
	/**
	 * RSA 16384bit key
	 */
	RSA_16384("RSA 16384", "RSA", 16384, null),

	/**
	 * DSA 1024bit key
	 */
	DSA_512("DSA 512", "DSA", 512, null),
	/**
	 * DSA 1024bit key
	 */
	DSA_1024("DSA 1024", "DSA", 1024, null),
	/**
	 * DSA 4096bit key
	 */
	DSA_2048("DSA 2048", "DSA", 2048, null),
	/**
	 * DSA 3072bit key
	 */
	DSA_3072("DSA 3072", "DSA", 3072, null),
	///**
	// * DSA 4096bit key - not implemented.
	// */
	//DSA_4096("DSA 4096", "DSA", 4096, null),

	/**
	 * SEC curve secp112r1
	 */
	EC_secp112r1("EC SEC secp112r1", "ECDSA", 112, "secp112r1"),
	/**
	 * SEC curve secp112r2
	 */
	EC_secp112r2("EC SEC secp112r2", "ECDSA", 112, "secp112r2"),
	/**
	 * SEC curve secp128r1
	 */
	EC_secp128r1("EC SEC secp128r1", "ECDSA", 128, "secp128r1"),
	/**
	 * SEC curve secp128r2
	 */
	EC_secp128r2("EC SEC secp128r2", "ECDSA", 128, "secp128r2"),
	/**
	 * SEC curve secp160k1
	 */
	EC_secp160k1("EC SEC secp160k1", "ECDSA", 160, "secp160k1"),
	/**
	 * SEC curve secp160r1
	 */
	EC_secp160r1("EC SEC secp160r1", "ECDSA", 160, "secp160r1"),
	/**
	 * SEC curve secp160r2
	 */
	EC_secp160r2("EC SEC secp160r2", "ECDSA", 160, "secp160r2"),
	/**
	 * SEC curve secp192k1
	 */
	EC_secp192k1("EC SEC secp192k1", "ECDSA", 192, "secp192k1"),
	/**
	 * SEC curve secp192r1
	 */
	EC_secp192r1("EC SEC secp192r1", "ECDSA", 192, "secp192r1"),
	/**
	 * SEC curve secp224k1
	 */
	EC_secp224k1("EC SEC secp224k1", "ECDSA", 224, "secp224k1"),
	/**
	 * SEC curve secp224r1
	 */
	EC_secp224r1("EC SEC secp224r1", "ECDSA", 224, "secp224r1"),
	/**
	 * SEC curve secp256k1
	 */
	EC_secp256k1("EC SEC secp256k1", "ECDSA", 256, "secp256k1"),
	/**
	 * SEC curve secp256r1
	 */
	EC_secp256r1("EC SEC secp256r1", "ECDSA", 256, "secp256r1"),
	/**
	 * SEC curve secp384r1
	 */
	EC_secp384r1("EC SEC secp384r1", "ECDSA", 384, "secp384r1"),
	/**
	 * SEC curve secp521r1
	 */
	EC_secp521r1("EC SEC secp521r1", "ECDSA", 521, "secp521r1"),
	/**
	 * SEC curve sect113r1
	 */
	EC_sect113r1("EC SEC sect113r1", "ECDSA", 113, "sect113r1"),
	/**
	 * SEC curve sect113r2
	 */
	EC_sect113r2("EC SEC sect113r2", "ECDSA", 113, "sect113r2"),
	/**
	 * SEC curve sect131r1
	 */
	EC_sect131r1("EC SEC sect131r1", "ECDSA", 131, "sect131r1"),
	/**
	 * SEC curve sect131r2
	 */
	EC_sect131r2("EC SEC sect131r2", "ECDSA", 131, "sect131r2"),
	/**
	 * SEC curve sect163k1
	 */
	EC_sect163k1("EC SEC sect163k1", "ECDSA", 163, "sect163k1"),
	/**
	 * SEC curve sect163r1
	 */
	EC_sect163r1("EC SEC sect163r1", "ECDSA", 163, "sect163r1"),
	/**
	 * SEC curve sect163r2
	 */
	EC_sect163r2("EC SEC sect163r2", "ECDSA", 163, "sect163r2"),
	/**
	 * SEC curve sect193r1
	 */
	EC_sect193r1("EC SEC sect193r1", "ECDSA", 193, "sect193r1"),
	/**
	 * SEC curve sect193r2
	 */
	EC_sect193r2("EC SEC sect193r2", "ECDSA", 193, "sect193r2"),
	/**
	 * SEC curve sect233k1
	 */
	EC_sect233k1("EC SEC sect233k1", "ECDSA", 233, "sect233k1"),
	/**
	 * SEC curve sect233r1
	 */
	EC_sect233r1("EC SEC sect233r1", "ECDSA", 233, "sect233r1"),
	/**
	 * SEC curve sect239k1
	 */
	EC_sect239k1("EC SEC sect239k1", "ECDSA", 239, "sect239k1"),
	/**
	 * SEC curve sect283k1
	 */
	EC_sect283k1("EC SEC sect283k1", "ECDSA", 283, "sect283k1"),
	/**
	 * SEC curve sect283r1
	 */
	EC_sect283r1("EC SEC sect283r1", "ECDSA", 283, "sect283r1"),
	/**
	 * SEC curve sect409k1
	 */
	EC_sect409k1("EC SEC sect409k1", "ECDSA", 409, "sect409k1"),
	/**
	 * SEC curve sect409r1
	 */
	EC_sect409r1("EC SEC sect409r1", "ECDSA", 409, "sect409r1"),
	/**
	 * SEC curve sect571k1
	 */
	EC_sect571k1("EC SEC sect571k1", "ECDSA", 571, "sect571k1"),
	/**
	 * SEC curve sect571r1
	 */
	EC_sect571r1("EC SEC sect571r1", "ECDSA", 571, "sect571r1"),
	/**
	 * NIST curve B-163
	 */
	EC_B163("EC NIST B-163", "ECDSA", 163, "B-163"),
	/**
	 * NIST curve B-233
	 */
	EC_B233("EC NIST B-233", "ECDSA", 233, "B-233"),
	/**
	 * NIST curve B-283
	 */
	EC_B283("EC NIST B-283", "ECDSA", 283, "B-283"),
	/**
	 * NIST curve B-409
	 */
	EC_B409("EC NIST B-409", "ECDSA", 409, "B-409"),
	/**
	 * NIST curve B-571
	 */
	EC_B571("EC NIST B-571", "ECDSA", 571, "B-571"),
	/**
	 * NIST curve K-163
	 */
	EC_K163("EC NIST K-163", "ECDSA", 163, "K-163"),
	/**
	 * NIST curve K-233
	 */
	EC_K233("EC NIST K-233", "ECDSA", 233, "K-233"),
	/**
	 * NIST curve K-283
	 */
	EC_K283("EC NIST K-283", "ECDSA", 283, "K-283"),
	/**
	 * NIST curve K-409
	 */
	EC_K409("EC NIST K-409", "ECDSA", 409, "K-409"),
	/**
	 * NIST curve K-571
	 */
	EC_K571("EC NIST K-571", "ECDSA", 571, "K-571"),
	/**
	 * NIST curve P-192
	 */
	EC_P192("EC NIST P-192", "ECDSA", 192, "P-192"),
	/**
	 * NIST curve P-224
	 */
	EC_P224("EC NIST P-224", "ECDSA", 224, "P-224"),
	/**
	 * NIST curve P-256
	 */
	EC_P256("EC NIST P-256", "ECDSA", 256, "P-256"),
	/**
	 * NIST curve P-384
	 */
	EC_P384("EC NIST P-384", "ECDSA", 384, "P-384"),
	/**
	 * NIST curve P-521
	 */
	EC_P521("EC NIST P-521", "ECDSA", 521, "P-521"),
	/**
	 * ANSI X9.62 curve c2pnb163v1
	 */
	EC_c2pnb163v1("EC ANSI X9.62 c2pnb163v1", "ECDSA", 163, "c2pnb163v1"),
	/**
	 * ANSI X9.62 curve c2pnb163v2
	 */
	EC_c2pnb163v2("EC ANSI X9.62 c2pnb163v2", "ECDSA", 163, "c2pnb163v2"),
	/**
	 * ANSI X9.62 curve c2pnb163v3
	 */
	EC_c2pnb163v3("EC ANSI X9.62 c2pnb163v3", "ECDSA", 163, "c2pnb163v3"),
	/**
	 * ANSI X9.62 curve c2pnb176w1
	 */
	EC_c2pnb176w1("EC ANSI X9.62 c2pnb176w1", "ECDSA", 176, "c2pnb176w1"),
	/**
	 * ANSI X9.62 curve c2pnb208w1
	 */
	EC_c2pnb208w1("EC ANSI X9.62 c2pnb208w1", "ECDSA", 208, "c2pnb208w1"),
	/**
	 * ANSI X9.62 curve c2pnb272w1
	 */
	EC_c2pnb272w1("EC ANSI X9.62 c2pnb272w1", "ECDSA", 272, "c2pnb272w1"),
	/**
	 * ANSI X9.62 curve c2pnb304w1
	 */
	EC_c2pnb304w1("EC ANSI X9.62 c2pnb304w1", "ECDSA", 304, "c2pnb304w1"),
	/**
	 * ANSI X9.62 curve c2pnb368w1
	 */
	EC_c2pnb368w1("EC ANSI X9.62 c2pnb368w1", "ECDSA", 368, "c2pnb368w1"),
	/**
	 * ANSI X9.62 curve c2tnb191v1
	 */
	EC_c2tnb191v1("EC ANSI X9.62 c2tnb191v1", "ECDSA", 191, "c2tnb191v1"),
	/**
	 * ANSI X9.62 curve c2tnb191v2
	 */
	EC_c2tnb191v2("EC ANSI X9.62 c2tnb191v2", "ECDSA", 191, "c2tnb191v2"),
	/**
	 * ANSI X9.62 curve c2tnb191v3
	 */
	EC_c2tnb191v3("EC ANSI X9.62 c2tnb191v3", "ECDSA", 191, "c2tnb191v3"),
	/**
	 * ANSI X9.62 curve c2tnb239v1
	 */
	EC_c2tnb239v1("EC ANSI X9.62 c2tnb239v1", "ECDSA", 239, "c2tnb239v1"),
	/**
	 * ANSI X9.62 curve c2tnb239v2
	 */
	EC_c2tnb239v2("EC ANSI X9.62 c2tnb239v2", "ECDSA", 239, "c2tnb239v2"),
	/**
	 * ANSI X9.62 curve c2tnb239v3
	 */
	EC_c2tnb239v3("EC ANSI X9.62 c2tnb239v3", "ECDSA", 239, "c2tnb239v3"),
	/**
	 * ANSI X9.62 curve c2tnb359v1
	 */
	EC_c2tnb359v1("EC ANSI X9.62 c2tnb359v1", "ECDSA", 359, "c2tnb359v1"),
	/**
	 * ANSI X9.62 curve c2tnb431r1
	 */
	EC_c2tnb431r1("EC ANSI X9.62 c2tnb431r1", "ECDSA", 431, "c2tnb431r1"),
	/**
	 * ANSI X9.62 curve prime192v1
	 */
	EC_prime192v1("EC ANSI X9.62 prime192v1", "ECDSA", 192, "prime192v1"),
	/**
	 * ANSI X9.62 curve prime192v2
	 */
	EC_prime192v2("EC ANSI X9.62 prime192v2", "ECDSA", 192, "prime192v2"),
	/**
	 * ANSI X9.62 curve prime192v3
	 */
	EC_prime192v3("EC ANSI X9.62 prime192v3", "ECDSA", 192, "prime192v3"),
	/**
	 * ANSI X9.62 curve prime239v1
	 */
	EC_prime239v1("EC ANSI X9.62 prime239v1", "ECDSA", 239, "prime239v1"),
	/**
	 * ANSI X9.62 curve prime239v2
	 */
	EC_prime239v2("EC ANSI X9.62 prime239v2", "ECDSA", 239, "prime239v2"),
	/**
	 * ANSI X9.62 curve prime239v3
	 */
	EC_prime239v3("EC ANSI X9.62 prime239v3", "ECDSA", 239, "prime239v3"),
	/**
	 * ANSI X9.62 curve prime256v1
	 */
	EC_prime256v1("EC ANSI X9.62 prime256v1", "ECDSA", 256, "prime256v1"),
	/**
	 * TeleTrusT curve brainpoolP160r1
	 */
	EC_brainpoolP160r1("EC TeleTrusT brainpoolP160r1", "ECDSA", 160, "brainpoolP160r1"),
	/**
	 * TeleTrusT curve brainpoolP160t1
	 */
	EC_brainpoolP160t1("EC TeleTrusT brainpoolP160t1", "ECDSA", 160, "brainpoolP160t1"),
	/**
	 * TeleTrusT curve brainpoolP192r1
	 */
	EC_brainpoolP192r1("EC TeleTrusT brainpoolP192r1", "ECDSA", 192, "brainpoolP192r1"),
	/**
	 * TeleTrusT curve brainpoolP192t1
	 */
	EC_brainpoolP192t1("EC TeleTrusT brainpoolP192t1", "ECDSA", 192, "brainpoolP192t1"),
	/**
	 * TeleTrusT curve brainpoolP224r1
	 */
	EC_brainpoolP224r1("EC TeleTrusT brainpoolP224r1", "ECDSA", 224, "brainpoolP224r1"),
	/**
	 * TeleTrusT curve brainpoolP224t1
	 */
	EC_brainpoolP224t1("EC TeleTrusT brainpoolP224t1", "ECDSA", 224, "brainpoolP224t1"),
	/**
	 * TeleTrusT curve brainpoolP256r1
	 */
	EC_brainpoolP256r1("EC TeleTrusT brainpoolP256r1", "ECDSA", 256, "brainpoolP256r1"),
	/**
	 * TeleTrusT curve brainpoolP256t1
	 */
	EC_brainpoolP256t1("EC TeleTrusT brainpoolP256t1", "ECDSA", 256, "brainpoolP256t1"),
	/**
	 * TeleTrusT curve brainpoolP320r1
	 */
	EC_brainpoolP320r1("EC TeleTrusT brainpoolP320r1", "ECDSA", 320, "brainpoolP320r1"),
	/**
	 * TeleTrusT curve brainpoolP320t1
	 */
	EC_brainpoolP320t1("EC TeleTrusT brainpoolP320t1", "ECDSA", 320, "brainpoolP320t1"),
	/**
	 * TeleTrusT curve brainpoolP384r1
	 */
	EC_brainpoolP384r1("EC TeleTrusT brainpoolP384r1", "ECDSA", 384, "brainpoolP384r1"),
	/**
	 * TeleTrusT curve brainpoolP384t1
	 */
	EC_brainpoolP384t1("EC TeleTrusT brainpoolP384t1", "ECDSA", 384, "brainpoolP384t1"),
	/**
	 * TeleTrusT curve brainpoolP512r1
	 */
	EC_brainpoolP512r1("EC TeleTrusT brainpoolP512r1", "ECDSA", 512, "brainpoolP512r1"),
	/**
	 * TeleTrusT curve brainpoolP512t1
	 */
	EC_brainpoolP512t1("EC TeleTrusT brainpoolP512t1", "ECDSA", 512, "brainpoolP512t1"),
	/**
	 * ANSI curve FRP256v1
	 */
	EC_FRP256v1("EC ANSI FRP256v1", "ECDSA", 256, "FRP256v1"),
	/**
	 * GM curve sm2p256v1
	 */
	EC_sm2p256v1("EC GM sm2p256v1", "ECDSA", 256, "sm2p256v1"),
	/**
	 * GM curve wapip192v1
	 */
	EC_wapip192v1("EC GM wapip192v1", "ECDSA", 192, "wapip192v1"),

	/**
	 * GOST 3410-94 (Pro-A)
	 */
	GOST_3410_94_A("GostR34.10-94 CryptoPro-A", "GOST3410", 1024,
			CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_A.getId()),
	/**
	 * GOST 3410-94 (Pro-B)
	 */
	GOST_3410_94_B("GostR34.10-94 CryptoPro-B", "GOST3410", 1024,
			CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_B.getId()),
	// /**
	// * GOST 3410-94 (Pro-C) - Not Implemented
	// */
	// GOST_3410_94_C("GostR3410-94 CryptoPro-C", "GOST3410", 1024,
	// CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_C.getId()),
	// /**
	// * GOST 3410-94 (Pro-D) - Not Implemented
	// */
	// GOST_3410_94_D("GostR3410-94 CryptoPro-D", "GOST3410", 1024,
	// CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_D.getId()),

	/**
	 * GOST 3410-94 (Pro-D)
	 */
	GOST_3410_94_XA("GostR34.10-94 CryptoPro-XchA", "GOST3410", 1024,
			CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_XchA.getId()),

	/**
	 * ECGOST 3410-2001 (Pro-A)
	 */
	GOST_3410_2001_A("GostR34.10-2001 CryptoPro-A", "ECGOST3410", 239, "GostR3410-2001-CryptoPro-A"),
	/**
	 * ECGOST 3410-2001 (Pro-B)
	 */
	GOST_3410_2001_B("GostR34.10-2001 CryptoPro-B", "ECGOST3410", 239, "GostR3410-2001-CryptoPro-B"),
	/**
	 * ECGOST 3410-2001 (Pro-C)
	 */
	GOST_3410_2001_C("GostR34.10-2001 CryptoPro-C", "ECGOST3410", 239, "GostR3410-2001-CryptoPro-C"),
	/**
	 * ECGOST 3410-2001 (Pro-XA)
	 */
	GOST_3410_2001_XA("GostR34.10-2001 CryptoPro-XchA", "ECGOST3410", 239, "GostR3410-2001-CryptoPro-XchA"),
	/**
	 * ECGOST 3410-2001 (Pro-XB)
	 */
	GOST_3410_2001_XB("GostR34.10-2001 CryptoPro-XchB", "ECGOST3410", 239, "GostR3410-2001-CryptoPro-XchB"),
	/**
	 * ECGOST 3410-2012 (256-Pro-A)
	 */
	GOST_3410_2012_256_A("GostR34.10-2012-256 SetA", "ECGOST3410-2012", 256, "Tc26-Gost-3410-12-256-paramSetA"),
	/**
	 * ECGOST 3410-2012 (512-Pro-A)
	 */
	GOST_3410_2012_512_A("GostR34.10-2012-512 SetA", "ECGOST3410-2012", 512, "Tc26-Gost-3410-12-512-paramSetA"),
	/**
	 * ECGOST 3410-2012 (512-Pro-B)
	 */
	GOST_3410_2012_512_B("GostR34.10-2012-512 SetB", "ECGOST3410-2012", 512, "Tc26-Gost-3410-12-512-paramSetB"),
	/**
	 * ECGOST 3410-2012 (512-Pro-C)
	 */
	GOST_3410_2012_512_C("GostR34.10-2012-512 SetC", "ECGOST3410-2012", 512, "Tc26-Gost-3410-12-512-paramSetC"),
	/**
	 * DSTU 4145
	 */
	DSTU4145_0("DSTU 4145-2002-0", "DSTU4145", 163, "1.2.804.2.1.1.1.1.3.1.1.2.0"),
	/**
	 * DSTU 4145
	 */
	DSTU4145_1("DSTU 4145-2002-1", "DSTU4145", 167, "1.2.804.2.1.1.1.1.3.1.1.2.1"),
	/**
	 * DSTU 4145
	 */
	DSTU4145_2("DSTU 4145-2002-2", "DSTU4145", 173, "1.2.804.2.1.1.1.1.3.1.1.2.2"),
	/**
	 * DSTU 4145
	 */
	DSTU4145_3("DSTU 4145-2002-3", "DSTU4145", 179, "1.2.804.2.1.1.1.1.3.1.1.2.3"),
	/**
	 * DSTU 4145
	 */
	DSTU4145_4("DSTU 4145-2002-4", "DSTU4145", 191, "1.2.804.2.1.1.1.1.3.1.1.2.4"),
	/**
	 * DSTU 4145
	 */
	DSTU4145_5("DSTU 4145-2002-5", "DSTU4145", 233, "1.2.804.2.1.1.1.1.3.1.1.2.5"),
	/**
	 * DSTU 4145
	 */
	DSTU4145_6("DSTU 4145-2002-6", "DSTU4145", 257, "1.2.804.2.1.1.1.1.3.1.1.2.6"),
	/**
	 * DSTU 4145
	 */
	DSTU4145_7("DSTU 4145-2002-7", "DSTU4145", 307, "1.2.804.2.1.1.1.1.3.1.1.2.7"),
	/**
	 * DSTU 4145
	 */
	DSTU4145_8("DSTU 4145-2002-8", "DSTU4145", 367, "1.2.804.2.1.1.1.1.3.1.1.2.8"),
	/**
	 * DSTU 4145
	 */
	DSTU4145_9("DSTU 4145-2002-9", "DSTU4145", 431, "1.2.804.2.1.1.1.1.3.1.1.2.9");

	/**
	 * Plain text user friendly description
	 */
	private final String description;
	/**
	 * Key pair type
	 */
	private final String type;
	/**
	 * The number of bits for RSA/DSA
	 */
	private final int bitLength;
	/**
	 * The curve name for EC
	 */
	private final String curve;

	/**
	 * Create a new key type
	 * 
	 * @param description Plain text user friendly description
	 * @param type Key pair type
	 * @param bitLength The number of bits for RSA/DSA
	 * @param curve The curve name for EC
	 */
	private KeyType(String description, String type, int bitLength, String curve) {
		this.description = description;
		this.type = type;
		this.bitLength = bitLength;
		this.curve = curve;
	}

	/**
	 * Get the bit length for RSA/DSA
	 * 
	 * @return The bit length
	 */
	public int getBitLength() {
		return bitLength;
	}

	/**
	 * Get the key pair type
	 * 
	 * @return The key pair type.
	 */
	public String getType() {
		return type;
	}

	/**
	 * Get the EC Curve name or OID for keying material parameters
	 * 
	 * @return The EC Curve or OID of parameters
	 */
	public String getParameters() {
		return curve;
	}

	/**
	 * Get the user friendly display description
	 * 
	 * @return The description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Get the keytype based on the given description
	 * 
	 * @param value The description obtained from the keytype
	 * @return The key type based on the given description. NULL will returned if no description
	 * @throws NoSuchElementException The description doesn't match a known element.
	 * @throws NullPointerException The description was null.
	 */
	public static Object forDescription(String value) {
		Objects.requireNonNull(value, "Description was null");
		return Arrays.stream(KeyType.values()).filter(i -> i.description.equals(value)).findFirst().get();
	}

	/**
	 * Get the index into the values array...
	 * 
	 * @param value The value
	 * @return The index into the values array...
	 */
	public static int getIndex(KeyType value) {
		if (value == null) {
			return 0;
		}
		int index = 0;
		for (KeyType k : values()) {
			if (k == value) {
				return index;
			}
			index++;
		}
		return 0;
	}

	/**
	 * Get the description of the key type based on getName() value of the type
	 * 
	 * @param name The name of the enum
	 * @return The plain description.
	 * @throws NoSuchElementException The name doesn't match a known element.
	 * @throws NullPointerException The name was null.
	 */
	public static String getKeyTypeDescription(String name) {
		Objects.requireNonNull(name, "Name was null");
		return KeyType.valueOf(name).description;
	}

	/**
	 * Attempt to determine the KeyType for the given Public Key.
	 * 
	 * @param publicKey The public Key
	 * @return The KeyType or NULL if unknown?
	 * @throws UnknownKeyTypeException The Public Key doesn't match a defined KeyType.
	 */
	public static KeyType forKey(PublicKey publicKey) throws UnknownKeyTypeException {
		Objects.requireNonNull(publicKey, "Public Key was null");
		String algorithm = publicKey.getAlgorithm();
		int length = KeyPairFactory.getKeyLength(publicKey);
		String curve = null;
		if (publicKey instanceof ECPublicKey) {
			ECPublicKey eckey = (ECPublicKey) publicKey;
			ECParameterSpec spec = eckey.getParams();
			if (spec != null && spec instanceof ECNamedCurveSpec) {
				ECNamedCurveSpec ecspec = (ECNamedCurveSpec) spec;
				curve = ecspec.getName();
			}
		} else if (algorithm.contains("GOST")) {
			if (publicKey instanceof GOST3410PublicKey) {
				GOST3410PublicKey gostKey = (GOST3410PublicKey) publicKey;
				curve = gostKey.getParameters().getPublicKeyParamSetOID();
			}
		} else if (algorithm.contains("DSTU4145")) {
			if (publicKey instanceof BCDSTU4145PublicKey) {
				BCDSTU4145PublicKey dstu = (BCDSTU4145PublicKey) publicKey;
				ECParameterSpec spec = dstu.getParams();
				if (spec != null && spec instanceof ECNamedCurveSpec) {
					ECNamedCurveSpec ecspec = (ECNamedCurveSpec) spec;
					curve = ecspec.getName();
				}
			}
		}
		switch (algorithm) {
		case "ECDSA":
		case "EC":
		case "GOST3410":
		case "ECGOST3410":
		case "ECGOST3410-2012":
		case "DSTU4145":
			// use the curve name...
			for (KeyType t : values()) {
				if (t.curve != null && t.curve.equals(curve)) {
					return t;
				}
			}
			break;

		case "DSA":
		case "RSA":
			for (KeyType t : values()) {
				if (t.type.equals(algorithm) && t.bitLength == length) {
					return t;
				}
			}
		}
		throw new UnknownKeyTypeException("Public Key doesn't match a known KeyType");
	}

	/**
	 * Compare the key type to another key type.
	 * 
	 * @param element The element to compare
	 * @return -1 for less than, 1 for greater than or 0 for equal.
	 */
	public int compare(KeyType element) {
		if (element == null) {
			return -1;
		}
		int rc = type.compareTo(element.type);
		if (rc != 0) {
			return rc;
		}
		return Integer.compare(bitLength, element.bitLength);
	}

}
