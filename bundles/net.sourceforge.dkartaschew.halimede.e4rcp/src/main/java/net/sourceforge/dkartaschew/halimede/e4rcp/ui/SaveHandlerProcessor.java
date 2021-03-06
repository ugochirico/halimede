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

package net.sourceforge.dkartaschew.halimede.e4rcp.ui;

import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.basic.MWindow;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.lifecycle.PostContextCreate;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.e4.ui.workbench.modeling.ISaveHandler;
import org.eclipse.e4.ui.workbench.modeling.IWindowCloseHandler;
import org.eclipse.swt.widgets.Display;

import net.sourceforge.dkartaschew.halimede.e4rcp.Activator;

import org.eclipse.e4.ui.internal.workbench.E4Workbench;
import org.eclipse.e4.ui.internal.workbench.swt.PartRenderingEngine;

/**
 * Register a ISaveHandler and IWindowCloseHandler for the application.
 */
public class SaveHandlerProcessor {

	@PostContextCreate
	public void execute(final IEventBroker eventBroker, final IEclipseContext context, final Display display) {

		/*
		 * Set Application theme based on Display properties.
		 */
		String cssURI;
		if (Display.isSystemDarkTheme() || true) {
			cssURI = "platform:/plugin/" + Activator.PLUGIN_ID + "/css/moonrise-ui-standalone_WIN.css";
		} else {
			cssURI = "platform:/plugin/" + Activator.PLUGIN_ID + "/css/light.css";
		}
		context.set(E4Workbench.CSS_URI_ARG, cssURI);
		PartRenderingEngine.initializeStyling(display, context);

		eventBroker.subscribe(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE, event -> {
			/**
			 * Register the save handler
			 */
			final MApplication app = context.get(MApplication.class);
			final EModelService modelService = context.get(EModelService.class);
			final MWindow window = (MWindow) modelService.find("net.sourceforge.dkartaschew.halimede.e4rcp.window", app);
			window.getContext().set(ISaveHandler.class, new HalimedeSaveHandler());

			/*
			 * Register window close handler.
			 */
			window.getContext().set(IWindowCloseHandler.class, new HalimedeCloseHandler(context));

		});
	}

}
