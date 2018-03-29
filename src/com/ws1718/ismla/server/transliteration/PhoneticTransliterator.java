package com.ws1718.ismla.server.transliteration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.northeuralex.util.convert.GeneralTransliterator;

import com.ws1718.ismla.shared.LanguageCodes;
import com.ws1718.ismla.shared.TabooWordSummary;

public class PhoneticTransliterator {

	/**
	 * maps the languages to their corresponding phonetic representation of the
	 * input
	 * 
	 * @param input
	 * @return a map
	 */
	public static Map<LanguageCodes, String> getPhoneticRepresentationForAllLanguages(String input,
			List<SourceTransliteration> transliterations) {
		Map<LanguageCodes, String> rval = new HashMap<>();

		// get phonetic representations from input
		rval.put(LanguageCodes.DEU, GeneralTransliterator.orthToIPA(LanguageCodes.DEU.code(), input));
		rval.put(LanguageCodes.SPA, GeneralTransliterator.orthToIPA(LanguageCodes.SPA.code(), input));
		rval.put(LanguageCodes.HIN, GeneralTransliterator.prncToIPA(LanguageCodes.HIN.code(), input));
		rval.put(LanguageCodes.ITA, GeneralTransliterator.orthToIPA(LanguageCodes.ITA.code(), input));
		rval.put(LanguageCodes.JPN, GeneralTransliterator.prncToIPA(LanguageCodes.JPN.code(), input));
		rval.put(LanguageCodes.KOR, GeneralTransliterator.orthToIPA(LanguageCodes.KOR.code(), input));
		rval.put(LanguageCodes.NLD, GeneralTransliterator.orthToIPA(LanguageCodes.NLD.code(), input));
		rval.put(LanguageCodes.POL, GeneralTransliterator.orthToIPA(LanguageCodes.POL.code(), input));
		rval.put(LanguageCodes.POR, GeneralTransliterator.orthToIPA(LanguageCodes.POR.code(), input));
		rval.put(LanguageCodes.SWE, GeneralTransliterator.orthToIPA(LanguageCodes.SWE.code(), input));
		rval.put(LanguageCodes.TUR, GeneralTransliterator.orthToIPA(LanguageCodes.TUR.code(), input));
		rval.put(LanguageCodes.CMN, GeneralTransliterator.prncToIPA(LanguageCodes.CMN.code(), input));

		// get phonetic representations from transliterated input
		for (SourceTransliteration st : transliterations) {
			LanguageCodes c = st.getLanguageCode();
			final String transliteratedInput = st.getTranslit();

			if (c.equals(LanguageCodes.RUS)) {
				rval.put(c, GeneralTransliterator.orthToIPA(c.code(), transliteratedInput));
			} else if (c.equals(LanguageCodes.ARA)) {
				rval.put(c, GeneralTransliterator.prncToIPA(c.code(), transliteratedInput));
			} else if (c.equals(LanguageCodes.FAS)){
				rval.put(c, GeneralTransliterator.prncToIPA(c.code(), transliteratedInput));
			}
		}

		return rval;
	}

	/**
	 * transforms the input token to phonetic representation of the given
	 * language
	 * 
	 * @param input
	 * @param c
	 *            language code
	 * @return list
	 */
	public static String getPhoneticRepresentation(String input, LanguageCodes c) {
		String rval = "";

		if (c.equals(LanguageCodes.DEU) || c.equals(LanguageCodes.SPA) || c.equals(LanguageCodes.ITA)
				|| c.equals(LanguageCodes.KOR) || c.equals(LanguageCodes.NLD) || c.equals(LanguageCodes.POL)
				|| c.equals(LanguageCodes.POR) || c.equals(LanguageCodes.SWE) || c.equals(LanguageCodes.TUR)
				|| c.equals(LanguageCodes.ARA) || c.equals(LanguageCodes.RUS)) {

			rval = GeneralTransliterator.orthToIPA(c.code(), input);

		} else if (c.equals(LanguageCodes.FAS) || c.equals(LanguageCodes.HIN) || c.equals(LanguageCodes.JPN)
				|| c.equals(LanguageCodes.CMN)) {

			rval = GeneralTransliterator.prncToIPA(c.code(), input);

		}

		return rval;
	}

	/**
	 * transforms each of the input tokens to phonetic representation of the
	 * given language
	 * 
	 * @param inputs
	 *            list of tokens
	 * @param c
	 *            language code
	 * @return list
	 */
	public static List<String> getPhoneticRepresentationForList(List<String> inputs, LanguageCodes c) {
		List<String> rval = new ArrayList<>();

		if (c.equals(LanguageCodes.DEU) || c.equals(LanguageCodes.SPA) || c.equals(LanguageCodes.ITA)
				|| c.equals(LanguageCodes.KOR) || c.equals(LanguageCodes.NLD) || c.equals(LanguageCodes.POL)
				|| c.equals(LanguageCodes.POR) || c.equals(LanguageCodes.SWE) || c.equals(LanguageCodes.TUR)
				|| c.equals(LanguageCodes.ARA) || c.equals(LanguageCodes.RUS)) {

			for (String s : inputs) {
				rval.add(GeneralTransliterator.orthToIPA(c.code(), s));
			}

		} else if (c.equals(LanguageCodes.FAS) || c.equals(LanguageCodes.HIN) || c.equals(LanguageCodes.JPN)
				|| c.equals(LanguageCodes.CMN)) {

			for (String s : inputs) {
				rval.add(GeneralTransliterator.prncToIPA(c.code(), s));
			}

		}

		return rval;
	}

	
	/**
	 * creates a simplified version of the phonological representation via
	 * equivalence classes
	 * 
	 * @param simpleIpaMapping
	 *            mapping from the ipa symbols to the equivalence classes
	 * @param phonologicalRepresentations
	 *            the to simplify input in ipa
	 * @return a map from languages to the corresponding simplified ipa
	 *         representation
	 */
	public static Map<LanguageCodes, String> getSimplePhonologicalRepresentationOfInput(Map<String, String> simpleIpaMapping, Map<LanguageCodes, String> phonologicalRepresentations) {

		Map<LanguageCodes, String> rval = new HashMap<>();

		// transformation of phonological representation to simplified version
		for (LanguageCodes c : phonologicalRepresentations.keySet()) {
			String phonWord = phonologicalRepresentations.get(c);
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < phonWord.length(); i++) {

				if(phonWord.charAt(i) == 't' && i < phonWord.length()-1){
					//potential silibant like ts -> c
					String potentialSibilant = phonWord.substring(i, i+2);
					if(simpleIpaMapping.containsKey(potentialSibilant)){
						sb.append(simpleIpaMapping.get(potentialSibilant));
						i++;
					}else{
						sb.append(simpleIpaMapping.get(phonWord.charAt(i) + ""));
					}
				}else{
					if (simpleIpaMapping.containsKey(phonWord.charAt(i) + "")) {
						sb.append(simpleIpaMapping.get(phonWord.charAt(i) + ""));
					}
				}
				
			}

			rval.put(c, sb.toString());
		}

		return rval;
	}

	
	/**
	 * creates a simplified version of the phonological representation via
	 * equivalence classes
	 * @param simpleIpaMapping mapping from the ipa symbols to the equivalence classes
	 * @param phonologicalRepresentations the to simplify input in ipa
	 * @return a map from languages to the corresponding simplified ipa
	 *         representation
	 */
	public static Map<LanguageCodes, List<TabooWordSummary>> getSimplePhonologicalRepresentationOfTabuWords(Map<String, String> simpleIpaMapping, Map<LanguageCodes, List<TabooWordSummary>> phonologicalRepresentations) {
		Map<LanguageCodes, List<TabooWordSummary>> rval = new HashMap<>();

		// transformation of phonological representation to simplified version
		for (LanguageCodes c : phonologicalRepresentations.keySet()) {
			
			final List<TabooWordSummary> tabuWords = phonologicalRepresentations.get(c);
			
			for (TabooWordSummary tbs : tabuWords) {
				String phonologicalRepresentationOfTabuWord = tbs.getPhonologicalRepresentationOfTabooWord();
				StringBuilder sb = new StringBuilder();
				for (int i = 0; i < phonologicalRepresentationOfTabuWord.length(); i++) {
					
					if(phonologicalRepresentationOfTabuWord.charAt(i) == 't' && i < phonologicalRepresentationOfTabuWord.length()-1){
						String potentialSibilant = phonologicalRepresentationOfTabuWord.substring(i, i+2);
						if(simpleIpaMapping.containsKey(potentialSibilant)){
							sb.append(simpleIpaMapping.get(potentialSibilant));
							i++;
						}else{
							sb.append(simpleIpaMapping.get(phonologicalRepresentationOfTabuWord.charAt(i) + ""));
						}
					}else{
						if (simpleIpaMapping.containsKey(phonologicalRepresentationOfTabuWord.charAt(i) + "")) {
							sb.append(simpleIpaMapping.get(phonologicalRepresentationOfTabuWord.charAt(i) + ""));
						}
					}
					
				}
				tbs.setSimplefiedPhonologicalRepresentationOfTabooWord(sb.toString());
			}
			rval.put(c, tabuWords);
		}

		return rval;
	}

}
