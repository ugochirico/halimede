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

import org.junit.Test;

import net.sourceforge.dkartaschew.halimede.data.render.HTMLOutputRenderer;
import net.sourceforge.dkartaschew.halimede.data.render.ICertificateOutputRenderer;
import net.sourceforge.dkartaschew.halimede.data.render.TextOutputRenderer;

public class TestSystemInformation {

	@Test
	public void testSystemInfomrationTxt() {
		SystemInformation info = new SystemInformation();
		ICertificateOutputRenderer renderer = new TextOutputRenderer(System.out);
		info.render(renderer);
	}

	@Test
	public void testSystemInfomrationHTML() {
		SystemInformation info = new SystemInformation();
		ICertificateOutputRenderer renderer = new HTMLOutputRenderer(System.out, "System Information");
		info.render(renderer);
	}
}