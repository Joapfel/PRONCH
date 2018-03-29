package com.ws1718.ismla.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.ws1718.ismla.client.GreetingService;
import com.ws1718.ismla.server.distance.SimilarityMeasures;
import com.ws1718.ismla.server.distance.WeightedMeasures;
import com.ws1718.ismla.server.transliteration.LanguageTransliterator;
import com.ws1718.ismla.server.transliteration.PhoneticTransliterator;
import com.ws1718.ismla.server.transliteration.SourceTransliteration;
import com.ws1718.ismla.shared.ClientTabooWordSummary;
import com.ws1718.ismla.shared.LanguageCodes;
import com.ws1718.ismla.shared.TabooWordSummary;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class GreetingServiceImpl extends RemoteServiceServlet implements GreetingService {

	// transliteration source
	private static final String TRANSLIT_LOCATION = "/transcription/";
	private static final String ARABIC_TRANSLIT = TRANSLIT_LOCATION + "Arabic_chat.txt";
	private static final String RUSSIAN_TRANSLIT = TRANSLIT_LOCATION + "Russian_translit.txt";
	private static final String PERSIAN_TRANSLIT = TRANSLIT_LOCATION + "Persian_chat.txt";
	private static final String THAI_TRANSLIT = TRANSLIT_LOCATION + "Thai_royal_transcription.txt";

	// tabu lists source
	private static final String TABU_LOCATION = "/tabuLists/";
	private static final String PREFIX_TABU = TABU_LOCATION + "tabu_";
	private static final String SUFFIX_TABU = ".txt";

	// ipa list resources
	private static final String IPA_LOCATION = "/IPA/";
	private static final String IPA_SIMPLE = IPA_LOCATION + "IPA_Simple.txt";

	public List<ClientTabooWordSummary> greetServer(String input) throws IllegalArgumentException {

		List<ClientTabooWordSummary> rval = new ArrayList<>();

		/*
		 * equivalence classes of IPA
		 */
		final Map<String, String> ipaSimple = getMapForColumns(IPA_SIMPLE, 0, 1);

		/*
		 * input transformation to phonetic representation
		 */
		Map<LanguageCodes, String> phonologicalRepresentationsOfInput = getPhonologicalRepresentationsOfInput(
				input);
		
		final List<String> thaiTranslit = getLinesFromFile(THAI_TRANSLIT);
		final String thaiInputTransliterated = LanguageTransliterator.transcribeRoyalThaiTranscriptionToPhon(input, thaiTranslit);
		phonologicalRepresentationsOfInput.put(LanguageCodes.TH, thaiInputTransliterated);
		
		// create
		Map<LanguageCodes, String> simplePhonologicalRepresentationOfInput = PhoneticTransliterator
				.getSimplePhonologicalRepresentationOfInput(ipaSimple, phonologicalRepresentationsOfInput);

		/*
		 * tabu word transformation to phonetic representation
		 */
		Map<LanguageCodes, List<TabooWordSummary>> phonologicalRepresentationOfTabuWords = getPhonologicalRepresentationOfTabuWords();
		
		
		
		// add simple phon
		phonologicalRepresentationOfTabuWords = PhoneticTransliterator
				.getSimplePhonologicalRepresentationOfTabuWords(ipaSimple, phonologicalRepresentationOfTabuWords);

		// go through simple representations of input
		for (LanguageCodes c : simplePhonologicalRepresentationOfInput.keySet()) {

			String simplePhonInput = simplePhonologicalRepresentationOfInput.get(c);

			float lowestDistance = 1000;
			String closestOriginalWord = "";
			
			// go through simple representations of taboo words
			for (LanguageCodes ct : phonologicalRepresentationOfTabuWords.keySet()) {
				if (c.equals(ct)) {

					List<TabooWordSummary> phonTabuWords = phonologicalRepresentationOfTabuWords.get(ct);

//					 reduce search space via LCS
					List<TabooWordSummary> longestCommonSubstrings = new ArrayList<>();
					int longestCommonSubstringLenght = 0;
					for (TabooWordSummary phonTabooWord : phonTabuWords) {
						String simplePhonTabooWord = phonTabooWord.getSimplefiedPhonologicalRepresentationOfTabooWord();
						String lcs = SimilarityMeasures.longestCommonSubstring(simplePhonInput, simplePhonTabooWord);

						if (lcs.length() == longestCommonSubstringLenght) {
							longestCommonSubstrings.add(phonTabooWord);

						} else if (lcs.length() > longestCommonSubstringLenght) {
							longestCommonSubstringLenght = lcs.length();

							longestCommonSubstrings = new ArrayList<>();
							longestCommonSubstrings.add(phonTabooWord);
						}
					}

					phonTabuWords = longestCommonSubstrings;

					// calculate the distance
					for (TabooWordSummary phonTabuWord : phonTabuWords) {
						String simplePhonTabooWord = phonTabuWord.getSimplefiedPhonologicalRepresentationOfTabooWord();
						float distance = WeightedMeasures.weightedLevenshteinWithLCS(simplePhonInput,
								simplePhonTabooWord, 2, 1);

						if (distance < lowestDistance) {

							lowestDistance = distance;
							closestOriginalWord = phonTabuWord.getTabooWord();
						}
						
					}

				}
			}
			
			
			rval.add(new ClientTabooWordSummary(c, closestOriginalWord, lowestDistance));
			
			Map<String, String> cmnTranslations = getMapForColumns(PREFIX_TABU + LanguageCodes.CMN + SUFFIX_TABU, 1, 2);
			Map<String, String> araTranslations = getMapForColumns(PREFIX_TABU + LanguageCodes.ARA + SUFFIX_TABU, 1, 2);
			Map<String, String> fasTranslations = getMapForColumns(PREFIX_TABU + LanguageCodes.FAS + SUFFIX_TABU, 1, 2);
			Map<String, String> jpnTranslations = getMapForColumns(PREFIX_TABU + LanguageCodes.JPN + SUFFIX_TABU, 1, 2);
			Map<String, String> thTranslations = getMapForColumns(PREFIX_TABU + LanguageCodes.TH + SUFFIX_TABU, 0, 2);
			
			for(ClientTabooWordSummary t : rval){
				LanguageCodes l = t.getLanguage();
				
				switch (l) {
				case CMN:
					t.setTranslation(cmnTranslations.get(t.getTabooWord()));
					break;
					
				case ARA:
					t.setTranslation(araTranslations.get(t.getTabooWord()));
					break;
					
				case FAS:
					t.setTranslation(fasTranslations.get(t.getTabooWord()));
					break;
					
				case JPN:
					t.setTranslation(jpnTranslations.get(t.getTabooWord()));
					break;
				
				case TH:
					t.setTranslation(thTranslations.get(t.getTabooWord()));
					break;

				default:
					break;
				}
			}
			

		}
		
		rval.sort((o1, o2) -> Float.compare(o1.getDistanceToInput(), o2.getDistanceToInput()));

		return rval;
	}

	/**
	 * creates a mapping of all languages to the corresponding phonological
	 * representation of the input
	 * 
	 * @param input
	 *            a word written in latin characters
	 * @return a map
	 */
	private Map<LanguageCodes, String> getPhonologicalRepresentationsOfInput(String input) {

		final List<String> arabicChat = getLinesFromFile(ARABIC_TRANSLIT);
		final String arabicInputTransliterated = LanguageTransliterator.transcribeArabicToOfficialTranscription(input,
				arabicChat);

		final List<String> russianTranslit = getLinesFromFile(RUSSIAN_TRANSLIT);
		final String russianInputTransliterated = LanguageTransliterator.transcribeToRussian(input, russianTranslit);
		
		final List<String> persianChat = getLinesFromFile(PERSIAN_TRANSLIT);
		final String persianInputTransliterated = LanguageTransliterator.transcribeInternetPersian(input, persianChat);
		
		

		List<SourceTransliteration> transliterations = new ArrayList<>();
		transliterations.add(new SourceTransliteration(russianInputTransliterated, LanguageCodes.RUS));
		transliterations.add(new SourceTransliteration(arabicInputTransliterated, LanguageCodes.ARA));
		transliterations.add(new SourceTransliteration(persianInputTransliterated, LanguageCodes.FAS));

		return PhoneticTransliterator.getPhoneticRepresentationForAllLanguages(input, transliterations);
	}

	/**
	 * creates a mapping of all languages to the phonetic representation of the
	 * corresponding tabu words
	 * 
	 * @return a map
	 */
	private Map<LanguageCodes, List<TabooWordSummary>> getPhonologicalRepresentationOfTabuWords() {
		Map<LanguageCodes, List<TabooWordSummary>> rval = new HashMap<LanguageCodes, List<TabooWordSummary>>();

		for (LanguageCodes c : LanguageCodes.values()) {
			if (c.equals(LanguageCodes.UNKNOWN) || c.equals(LanguageCodes.EN) || c.equals(LanguageCodes.FRA)
					|| c.equals(LanguageCodes.TH) || c.equals(LanguageCodes.CMN) || c.equals(LanguageCodes.JPN)
					|| c.equals(LanguageCodes.FAS) || c.equals(LanguageCodes.ARA))
				continue;

			final List<String> tabuList = getLinesFromFile(PREFIX_TABU + c + SUFFIX_TABU);

			List<TabooWordSummary> summary = new ArrayList<>();
			for (String tabooWord : tabuList) {
				String phonologicalRepresentationOfTabooWord = PhoneticTransliterator
						.getPhoneticRepresentation(tabooWord, c);
				summary.add(new TabooWordSummary(c, tabooWord, phonologicalRepresentationOfTabooWord));
			}

			// fill the |language -> tabu words summary| map
			rval.put(c, summary);
		}

		/*
		 * persisch
		 */
		LanguageCodes fas = LanguageCodes.FAS;
		rval.put(fas, phonologicalRepresentationForToTranscribeLanguage(fas, 1));

		/*
		 * japanese
		 */
		LanguageCodes jpn = LanguageCodes.JPN;
		rval.put(jpn, phonologicalRepresentationForToTranscribeLanguage(jpn, 1));

		/*
		 * thai
		 */
		LanguageCodes th = LanguageCodes.TH;
		List<String> originalThaiTabooWords = getColumnForLanguage(th, 0);
		List<String> phonologicalRepresentationOfThaiTabooWords = getColumnForLanguage(th, 1);
		List<TabooWordSummary> summary = new ArrayList<>();
		for (int i = 0; i < originalThaiTabooWords.size(); i++) {
			summary.add(new TabooWordSummary(th, originalThaiTabooWords.get(i),
					phonologicalRepresentationOfThaiTabooWords.get(i)));
		}
		rval.put(th, summary);

		/*
		 * arabic
		 */
		LanguageCodes ara = LanguageCodes.ARA;
		rval.put(ara, phonologicalRepresentationForToTranscribeLanguage(ara, 1));

		/*
		 * chinese
		 */
		LanguageCodes cmn = LanguageCodes.CMN;
		rval.put(cmn, phonologicalRepresentationForToTranscribeLanguage(cmn, 1));

		return rval;
	}

	/**
	 * creates a list of phonological representations for languages which have
	 * to be transcribed via external resources
	 * 
	 * @param c
	 *            the specific language
	 * @param col
	 *            the files column where the respective information can be found
	 * @return a list
	 */
	private List<TabooWordSummary> phonologicalRepresentationForToTranscribeLanguage(LanguageCodes c, int col) {
		List<TabooWordSummary> rval = new ArrayList<>();

		List<String> tabuListTranscribed = getColumnForLanguage(c, col);

		for (String tabooWord : tabuListTranscribed) {
			String phonologicalRepresentationOfTabooWord = PhoneticTransliterator.getPhoneticRepresentation(tabooWord,
					c);
			rval.add(new TabooWordSummary(c, tabooWord, phonologicalRepresentationOfTabooWord));
		}

		return rval;
	}

	/**
	 * extracts a specific column from a file for a specific language
	 * 
	 * @param c
	 *            language
	 * @param col
	 *            column of the file
	 * @return list of tokens
	 */
	private List<String> getColumnForLanguage(LanguageCodes c, int col) {

		final List<String> tabuList = getLinesFromFile(PREFIX_TABU + c + SUFFIX_TABU);
		List<String> tabuListTranscribed = new ArrayList<>();
		for (String s : tabuList) {
			String[] cols = s.split("\t");
			if (cols.length > 2) {
				tabuListTranscribed.add(cols[col]);
			}
		}

		return tabuListTranscribed;
	}

	/**
	 * reades a file and creates a map based on the given columns where the
	 * elements of the one column will be the keys in the map and the elements
	 * of the other column will be the values in the map
	 * 
	 * @param path
	 *            to the file
	 * @param keyColumn
	 *            the column which elements will be the keys
	 * @param valueColumn
	 *            the column which elements will be the values
	 * @return
	 */
	private Map<String, String> getMapForColumns(String path, int keyColumn, int valueColumn) {
		Map<String, String> rval = new HashMap<>();

		final List<String> lines = getLinesFromFile(path);
		for (String line : lines) {
			String[] cols = line.split("\t");
			int numberOfColumns = cols.length - 1;
			if (numberOfColumns >= keyColumn && numberOfColumns >= valueColumn) {
				rval.put(cols[keyColumn], cols[valueColumn]);
			}
		}

		return rval;
	}

	/***
	 * 
	 * START: reading util
	 * 
	 * 
	 */

	/**
	 * 
	 * creates the input stream to read files from the resource directory
	 * 
	 * @param path
	 * @return
	 */
	private InputStream getInputStream(String path) {
		return getServletContext().getResourceAsStream("/WEB-INF/resources/" + path);
	}

	/**
	 * creates a buffered reader for reading a file from the resource directory
	 * 
	 * @param path
	 * @return
	 */
	private BufferedReader getBufferedReader(String path) {
		return new BufferedReader(new InputStreamReader(getInputStream(path)));
	}

	/**
	 * creates a list of lines for a file from the resource directory
	 * 
	 * @param path
	 * @return
	 */
	private List<String> getLinesFromFile(String path) {
		List<String> rval = new ArrayList<String>();

		BufferedReader reader = getBufferedReader(path);
		String line;
		try {
			while ((line = reader.readLine()) != null) {
				if (line.trim().length() > 0) {
					rval.add(line);
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return rval;
	}

	/***
	 * 
	 * END: reading util
	 * 
	 * 
	 */

}
