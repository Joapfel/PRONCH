package com.ws1718.ismla.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ISMLAProject implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		
		TokenTabooFinderUiBinder ui = new TokenTabooFinderUiBinder();
		RootPanel.get().add(ui);
	}
}
