package com.ws1718.ismla.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class LanguageCard extends Composite{

	private static LanguageCardUiBinder uiBinder = GWT.create(LanguageCardUiBinder.class);

	interface LanguageCardUiBinder extends UiBinder<Widget, LanguageCard> {
	}
	private final MyResources resources = GWT.create(MyResources.class);

	public LanguageCard() {
		initWidget(uiBinder.createAndBindUi(this));	
	}
	
	@UiField
	HTMLPanel cardWrapper;
	@UiField
	Image img;
	@UiField 
	HTMLPanel language;
	@UiField
	HTMLPanel tabooWord;
	@UiField
	HTMLPanel translation;
	@UiField
	Anchor linkW;
	@UiField
	Anchor linkG;
	@UiField
	HTMLPanel footer;
	@UiField
	HTMLPanel status;

	public Image getImg() {
		return img;
	}

	public HTMLPanel getTabooWord() {
		return tabooWord;
	}

	public HTMLPanel getTranslation() {
		return translation;
	}
	
	public Anchor getWiktionaryLink() {
		return linkW;
	}
	
	public Anchor getGoogleLink() {
		return linkG;
	}

	public HTMLPanel getFooter() {
		return footer;
	}

	public HTMLPanel getStatus() {
		return status;
	}

	public HTMLPanel getLanguage() {
		return language;
	}

	public HTMLPanel getCardWrapper() {
		return cardWrapper;
	}
	
	
	
	
	
	

}
