package com.ws1718.ismla.client;

import java.util.List;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.ws1718.ismla.shared.ClientTabooWordSummary;
import com.ws1718.ismla.shared.LanguageCodes;

public class TokenTabooFinderUiBinder extends Composite {

	/**
	 * The message displayed to the user when the server cannot be reached or
	 * returns an error.
	 */
	private static final String SERVER_ERROR = "An error occurred while "
			+ "attempting to contact the server. Please check your network " + "connection and try again.";

	private static TokenTabooFinderUiBinderUiBinder uiBinder = GWT.create(TokenTabooFinderUiBinderUiBinder.class);
	
	private static final String LINK_WIKTIONARY = "https://en.wiktionary.org/wiki/";
	private static final String LINK_GOOGLE = "https://translate.google.com/?hl=de#auto/en/";
	
	/**
	 * distance evaluations
	 */
	private static final String CRITICAL = "inappropriate";
	private static final String CONCERNING = "concerning";
	private static final String RELEVANT = "medium";
	private static final String FINE = "fine";
	private static final String PERFECT = "appropriate";
	
	/**
	 * colors
	 */
	private static final String CRITICAL_COLOR_CLASS = "footer-red";
	private static final String CONCERNING_COLOR_CLASS = "footer-orange";
	private static final String RELEVANT_COLOR_CLASS = "footer-yellow";
	private static final String FINE_COLOR_CLASS = "footer-limegreen";
	private static final String PERFECT_COLOR_CLASS = "footer-darkgreen";

	/**
	 * Create a remote service proxy to talk to the server-side Greeting
	 * service.
	 */
	private final GreetingServiceAsync greetingService = GWT.create(GreetingService.class);
	private final MyResources resources = GWT.create(MyResources.class);

	interface TokenTabooFinderUiBinderUiBinder extends UiBinder<Widget, TokenTabooFinderUiBinder> {
	}

	public TokenTabooFinderUiBinder() {
		initWidget(uiBinder.createAndBindUi(this));
		sendRequest();
		
		cardContainer.setStyleName("row justify-content-center");
	}
	
	@UiField
	HTMLPanel applicationHeading;
	@UiField
	Image loader;
	@UiField
	HTMLPanel applicationBody;
	@UiField
	TextBox inputBox;
	
	
	@UiField
	HTMLPanel cardContainer;

	private String getText() {
		return inputBox.getText();
	}

	private void sendRequest() {
		// handle user request
		inputBox.addKeyUpHandler(new KeyUpHandler() {
			@Override
			public void onKeyUp(KeyUpEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					
					
					loader.setResource(resources.ajaxLoader());
					loader.setVisible(true);
					cardContainer.clear();
					applicationBody.setVisible(false);

					final String input = getText();
					greetingService.greetServer(input, new AsyncCallback<List<ClientTabooWordSummary>>() {

						@Override
						public void onSuccess(List<ClientTabooWordSummary> result) {
							
							loader.setVisible(false);

							for (int i = 0; i < result.size(); i++) {
								
								StringBuilder sbWiktionaryLink = new StringBuilder();
								sbWiktionaryLink.append(LINK_WIKTIONARY);
								
								StringBuilder sbGoogleLink = new StringBuilder();
								sbGoogleLink.append(LINK_GOOGLE);
								
								final ClientTabooWordSummary t = result.get(i);
								final LanguageCodes l = t.getLanguage();
								String tabooWord = t.getTabooWord();
								sbWiktionaryLink.append(tabooWord);
								sbGoogleLink.append(tabooWord);
								final float distance = t.getDistanceToInput();
								final String evaluated = distanceEvaluation(distance); 
								final String colorClass = distanceColorClass(distance);
								String translation = "not available";
								if(t.getTranslation() != null && t.getTranslation().length() > 0){
									translation = t.getTranslation();
								}
								
								LanguageCard lc = new LanguageCard();
								lc.getCardWrapper().setStyleName("col-lg-2 col-md-4 col-sm-12");
								lc.getTabooWord().getElement().setInnerHTML(tabooWord);
								lc.getFooter().setStyleName(colorClass);
								lc.getStatus().getElement().setInnerHTML(evaluated);
								lc.getLanguage().getElement().setInnerHTML(LanguageCodes.fullLanguageName(l));
								lc.getTranslation().getElement().setInnerHTML(translation);
								lc.getWiktionaryLink().setHref(sbWiktionaryLink.toString());
								lc.getGoogleLink().setHref(sbGoogleLink.toString());
								
								
								
								

								switch (l) {

								case DEU:
									lc.getImg().setResource(resources.german());
									break;
									
								case SPA:
									lc.getImg().setResource(resources.spanish());
									break;
									
								case FAS:
									lc.getImg().setResource(resources.persian());
									break;
									
								case FRA:
									lc.getImg().setResource(resources.french());
									break;
									
								case HIN:
									lc.getImg().setResource(resources.hindi());
									break;
									
								case ITA:
									lc.getImg().setResource(resources.italian());
									break;
									
								case JPN:		
									lc.getImg().setResource(resources.japanese());
									break;
									
								case KOR:									
									lc.getImg().setResource(resources.korean());
									break;
									
								case NLD:	
									lc.getImg().setResource(resources.dutch());
									break;
									
								case POL:
									lc.getImg().setResource(resources.polish());
									break;
									
								case POR:
									lc.getImg().setResource(resources.portuguese());
									break;
									
								case SWE:
									lc.getImg().setResource(resources.swedish());
									break;
									
								case TH:			
									lc.getImg().setResource(resources.thai());
									break;
									
								case TUR:	
									lc.getImg().setResource(resources.turkish());
									break;
									
								case CMN:	
									lc.getImg().setResource(resources.chinese());
									break;
									
								case ARA:
									lc.getImg().setResource(resources.arabic());
									break;
									
								case RUS:
									lc.getImg().setResource(resources.russian());
									break;

								default:
									break;
								}
								
								
								cardContainer.add(lc);
								
								if((i != 0) && ((i+1) % 4 == 0)){
									HTMLPanel h = new HTMLPanel("");
									h.addStyleName("w-100 d-none d-lg-block margin");
									cardContainer.add(h);
								}
							}
							
							
							applicationBody.setVisible(true);
							

						}

						@Override
						public void onFailure(Throwable caught) {
							// TODO Auto-generated method stub
							Window.alert(SERVER_ERROR);
						}
					});
				}
			}
		});

	}
	
	private String distanceEvaluation(float distance){
		
		if(distance <= 0.25){
			return CRITICAL;
		}else if(distance <= 0.4){
			return CONCERNING;
		}else if(distance <= 0.6){
			return RELEVANT;
		}else if(distance <= 0.8){
			return FINE;
		}else{
			return PERFECT;
		}
	}
	
	
	private String distanceColorClass(float distance){
		
		if(distance <= 0.25){
			return CRITICAL_COLOR_CLASS;
		}else if(distance <= 0.4){
			return CONCERNING_COLOR_CLASS;
		}else if(distance <= 0.6){
			return RELEVANT_COLOR_CLASS;
		}else if(distance <= 0.8){
			return FINE_COLOR_CLASS;
		}else{
			return PERFECT_COLOR_CLASS;
		}
	}

}
