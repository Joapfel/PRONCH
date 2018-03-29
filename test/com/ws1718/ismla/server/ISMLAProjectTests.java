package com.ws1718.ismla.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ws1718.ismla.server.distance.WeightedMeasures;
import com.ws1718.ismla.server.transliteration.LanguageTransliterator;
import com.ws1718.ismla.server.transliteration.PhoneticTransliterator;
import com.ws1718.ismla.server.transliteration.SourceTransliteration;
import com.ws1718.ismla.shared.LanguageCodes;
import com.ws1718.ismla.shared.TabooWordSummary;

import junit.framework.TestCase;










/**
 * 
 * 
 * not used
 * 
 * 
 * 
 * @author johannes
 *
 */

public class ISMLAProjectTests extends TestCase {

	/**********************************************
	 * 
	 * VARIABLES
	 * 
	 * 
	 * 
	 * 
	 */
	// transliteration source
	private static final String TRANSLIT_LOCATION = "transcription/";
	private static final String ARABIC_TRANSLIT = TRANSLIT_LOCATION + "Arabic_chat.txt";
	private static final String RUSSIAN_TRANSLIT = TRANSLIT_LOCATION + "Russian_translit.txt";

	// tabu lists source
	private static final String TABU_LOCATION = "tabuLists/";
	private static final String PREFIX_TABU = TABU_LOCATION + "tabu_";
	private static final String SUFFIX_TABU = ".txt";

	// ipa list resources
	private static final String IPA_LOCATION = "IPA/";
	private static final String IPA_SIMPLE = IPA_LOCATION + "IPA_Simple.txt";

	/*************************************************
	 * 
	 * TEST UNITS
	 * 
	 * 
	 * 
	 * 
	 */

	public void testGermanTabooWords() {
		LanguageCodes language = LanguageCodes.DEU;
		
	    final Map<String, String> ipaSimple = getMapForColumns(IPA_SIMPLE, 0, 1);
		Map<LanguageCodes, List<TabooWordSummary>> phonologicalRepresentationOfTabuWords = getPhonologicalRepresentationOfTabuWords();
		phonologicalRepresentationOfTabuWords = PhoneticTransliterator.getSimplePhonologicalRepresentationOfTabuWords(ipaSimple, phonologicalRepresentationOfTabuWords);
		
		
		List<String> lines = getLinesFromFile(PREFIX_TABU + language + SUFFIX_TABU);

		List<TabooWordSummary> languageSpecificPhonTabooWords = phonologicalRepresentationOfTabuWords.get(language);
		
		for(String input : lines){
			float distance = Float.MAX_VALUE;
			String phonInput = PhoneticTransliterator.getPhoneticRepresentation(input, language);
			Map<LanguageCodes, String> phonologicalRepresentationsOfInput = new HashMap<>();
			phonologicalRepresentationsOfInput.put(language, phonInput);
			HashMap<LanguageCodes, String> simplePhonInput = (HashMap<LanguageCodes, String>) PhoneticTransliterator.getSimplePhonologicalRepresentationOfInput(ipaSimple, phonologicalRepresentationsOfInput); 
			String simpleInput = simplePhonInput.get(language);
			
			for(TabooWordSummary tbs : languageSpecificPhonTabooWords){
				String simpleTaboo = tbs.getSimplefiedPhonologicalRepresentationOfTabooWord();

				float dist = WeightedMeasures.weightedLevenshteinWithLCS(simpleInput, simpleTaboo, 1, 1);
				if(dist < distance){
					distance = dist;
				}
			}
			
			assertEquals(distance, (float)0);
		}
	}
	
	
	
	public void testItalienTabooWords() {
		LanguageCodes language = LanguageCodes.ITA;
		
	    final Map<String, String> ipaSimple = getMapForColumns(IPA_SIMPLE, 0, 1);
		Map<LanguageCodes, List<TabooWordSummary>> phonologicalRepresentationOfTabuWords = getPhonologicalRepresentationOfTabuWords();
		phonologicalRepresentationOfTabuWords = PhoneticTransliterator.getSimplePhonologicalRepresentationOfTabuWords(ipaSimple, phonologicalRepresentationOfTabuWords);
		
		
		List<String> lines = getLinesFromFile(PREFIX_TABU + language + SUFFIX_TABU);

		List<TabooWordSummary> languageSpecificPhonTabooWords = phonologicalRepresentationOfTabuWords.get(language);
		
		for(String input : lines){
			float distance = Float.MAX_VALUE;
			String phonInput = PhoneticTransliterator.getPhoneticRepresentation(input, language);
			Map<LanguageCodes, String> phonologicalRepresentationsOfInput = new HashMap<>();
			phonologicalRepresentationsOfInput.put(language, phonInput);
			HashMap<LanguageCodes, String> simplePhonInput = (HashMap<LanguageCodes, String>) PhoneticTransliterator.getSimplePhonologicalRepresentationOfInput(ipaSimple, phonologicalRepresentationsOfInput); 
			String simpleInput = simplePhonInput.get(language);
			
			for(TabooWordSummary tbs : languageSpecificPhonTabooWords){
				String simpleTaboo = tbs.getSimplefiedPhonologicalRepresentationOfTabooWord();

				float dist = WeightedMeasures.weightedLevenshteinWithLCS(simpleInput, simpleTaboo, 1, 1);
				if(dist < distance){
					distance = dist;
				}
			}
			
			assertEquals(distance, (float)0);
		}
	}
	
	
	
	public void testDutchTabooWords() {
		LanguageCodes language = LanguageCodes.NLD;
		
	    final Map<String, String> ipaSimple = getMapForColumns(IPA_SIMPLE, 0, 1);
		Map<LanguageCodes, List<TabooWordSummary>> phonologicalRepresentationOfTabuWords = getPhonologicalRepresentationOfTabuWords();
		phonologicalRepresentationOfTabuWords = PhoneticTransliterator.getSimplePhonologicalRepresentationOfTabuWords(ipaSimple, phonologicalRepresentationOfTabuWords);
		
		
		List<String> lines = getLinesFromFile(PREFIX_TABU + language + SUFFIX_TABU);

		List<TabooWordSummary> languageSpecificPhonTabooWords = phonologicalRepresentationOfTabuWords.get(language);
		
		for(String input : lines){
			float distance = Float.MAX_VALUE;
			String phonInput = PhoneticTransliterator.getPhoneticRepresentation(input, language);
			Map<LanguageCodes, String> phonologicalRepresentationsOfInput = new HashMap<>();
			phonologicalRepresentationsOfInput.put(language, phonInput);
			HashMap<LanguageCodes, String> simplePhonInput = (HashMap<LanguageCodes, String>) PhoneticTransliterator.getSimplePhonologicalRepresentationOfInput(ipaSimple, phonologicalRepresentationsOfInput); 
			String simpleInput = simplePhonInput.get(language);
			
			for(TabooWordSummary tbs : languageSpecificPhonTabooWords){
				String simpleTaboo = tbs.getSimplefiedPhonologicalRepresentationOfTabooWord();

				float dist = WeightedMeasures.weightedLevenshteinWithLCS(simpleInput, simpleTaboo, 1, 1);
				if(dist < distance){
					distance = dist;
				}
			}
			
			assertEquals(distance, (float)0);
		}
	}
	
	
	
	public void testPolishTabooWords() {
		LanguageCodes language = LanguageCodes.NLD;
		
	    final Map<String, String> ipaSimple = getMapForColumns(IPA_SIMPLE, 0, 1);
		Map<LanguageCodes, List<TabooWordSummary>> phonologicalRepresentationOfTabuWords = getPhonologicalRepresentationOfTabuWords();
		phonologicalRepresentationOfTabuWords = PhoneticTransliterator.getSimplePhonologicalRepresentationOfTabuWords(ipaSimple, phonologicalRepresentationOfTabuWords);
		
		
		List<String> lines = getLinesFromFile(PREFIX_TABU + language + SUFFIX_TABU);

		List<TabooWordSummary> languageSpecificPhonTabooWords = phonologicalRepresentationOfTabuWords.get(language);
		
		for(String input : lines){
			float distance = Float.MAX_VALUE;
			String phonInput = PhoneticTransliterator.getPhoneticRepresentation(input, language);
			Map<LanguageCodes, String> phonologicalRepresentationsOfInput = new HashMap<>();
			phonologicalRepresentationsOfInput.put(language, phonInput);
			HashMap<LanguageCodes, String> simplePhonInput = (HashMap<LanguageCodes, String>) PhoneticTransliterator.getSimplePhonologicalRepresentationOfInput(ipaSimple, phonologicalRepresentationsOfInput); 
			String simpleInput = simplePhonInput.get(language);
			
			for(TabooWordSummary tbs : languageSpecificPhonTabooWords){
				String simpleTaboo = tbs.getSimplefiedPhonologicalRepresentationOfTabooWord();

				float dist = WeightedMeasures.weightedLevenshteinWithLCS(simpleInput, simpleTaboo, 1, 1);
				if(dist < distance){
					distance = dist;
				}
			}
			
			assertEquals(distance, (float)0);
		}
	}
	
	
	
	public void testPortugueseTabooWords() {
		LanguageCodes language = LanguageCodes.POR;
		
	    final Map<String, String> ipaSimple = getMapForColumns(IPA_SIMPLE, 0, 1);
		Map<LanguageCodes, List<TabooWordSummary>> phonologicalRepresentationOfTabuWords = getPhonologicalRepresentationOfTabuWords();
		phonologicalRepresentationOfTabuWords = PhoneticTransliterator.getSimplePhonologicalRepresentationOfTabuWords(ipaSimple, phonologicalRepresentationOfTabuWords);
		
		
		List<String> lines = getLinesFromFile(PREFIX_TABU + language + SUFFIX_TABU);

		List<TabooWordSummary> languageSpecificPhonTabooWords = phonologicalRepresentationOfTabuWords.get(language);
		
		for(String input : lines){
			float distance = Float.MAX_VALUE;
			String phonInput = PhoneticTransliterator.getPhoneticRepresentation(input, language);
			Map<LanguageCodes, String> phonologicalRepresentationsOfInput = new HashMap<>();
			phonologicalRepresentationsOfInput.put(language, phonInput);
			HashMap<LanguageCodes, String> simplePhonInput = (HashMap<LanguageCodes, String>) PhoneticTransliterator.getSimplePhonologicalRepresentationOfInput(ipaSimple, phonologicalRepresentationsOfInput); 
			String simpleInput = simplePhonInput.get(language);
			
			for(TabooWordSummary tbs : languageSpecificPhonTabooWords){
				String simpleTaboo = tbs.getSimplefiedPhonologicalRepresentationOfTabooWord();

				float dist = WeightedMeasures.weightedLevenshteinWithLCS(simpleInput, simpleTaboo, 1, 1);
				if(dist < distance){
					distance = dist;
				}
			}
			
			assertEquals(distance, (float)0);
		}
	}
	
	
	
	public void testSpanishTabooWords() {
		LanguageCodes language = LanguageCodes.SPA;
		
	    final Map<String, String> ipaSimple = getMapForColumns(IPA_SIMPLE, 0, 1);
		Map<LanguageCodes, List<TabooWordSummary>> phonologicalRepresentationOfTabuWords = getPhonologicalRepresentationOfTabuWords();
		phonologicalRepresentationOfTabuWords = PhoneticTransliterator.getSimplePhonologicalRepresentationOfTabuWords(ipaSimple, phonologicalRepresentationOfTabuWords);
		
		
		List<String> lines = getLinesFromFile(PREFIX_TABU + language + SUFFIX_TABU);

		List<TabooWordSummary> languageSpecificPhonTabooWords = phonologicalRepresentationOfTabuWords.get(language);
		
		for(String input : lines){
			float distance = Float.MAX_VALUE;
			String phonInput = PhoneticTransliterator.getPhoneticRepresentation(input, language);
			Map<LanguageCodes, String> phonologicalRepresentationsOfInput = new HashMap<>();
			phonologicalRepresentationsOfInput.put(language, phonInput);
			HashMap<LanguageCodes, String> simplePhonInput = (HashMap<LanguageCodes, String>) PhoneticTransliterator.getSimplePhonologicalRepresentationOfInput(ipaSimple, phonologicalRepresentationsOfInput); 
			String simpleInput = simplePhonInput.get(language);
			
			for(TabooWordSummary tbs : languageSpecificPhonTabooWords){
				String simpleTaboo = tbs.getSimplefiedPhonologicalRepresentationOfTabooWord();

				float dist = WeightedMeasures.weightedLevenshteinWithLCS(simpleInput, simpleTaboo, 1, 1);
				if(dist < distance){
					distance = dist;
				}
			}
			
			assertEquals(distance, (float)0);
		}
	}
	
	
	
	
	public void testSwedishTabooWords() {
		LanguageCodes language = LanguageCodes.SWE;
		
	    final Map<String, String> ipaSimple = getMapForColumns(IPA_SIMPLE, 0, 1);
		Map<LanguageCodes, List<TabooWordSummary>> phonologicalRepresentationOfTabuWords = getPhonologicalRepresentationOfTabuWords();
		phonologicalRepresentationOfTabuWords = PhoneticTransliterator.getSimplePhonologicalRepresentationOfTabuWords(ipaSimple, phonologicalRepresentationOfTabuWords);
		
		
		List<String> lines = getLinesFromFile(PREFIX_TABU + language + SUFFIX_TABU);

		List<TabooWordSummary> languageSpecificPhonTabooWords = phonologicalRepresentationOfTabuWords.get(language);
		
		for(String input : lines){
			float distance = Float.MAX_VALUE;
			String phonInput = PhoneticTransliterator.getPhoneticRepresentation(input, language);
			Map<LanguageCodes, String> phonologicalRepresentationsOfInput = new HashMap<>();
			phonologicalRepresentationsOfInput.put(language, phonInput);
			HashMap<LanguageCodes, String> simplePhonInput = (HashMap<LanguageCodes, String>) PhoneticTransliterator.getSimplePhonologicalRepresentationOfInput(ipaSimple, phonologicalRepresentationsOfInput); 
			String simpleInput = simplePhonInput.get(language);
			
			for(TabooWordSummary tbs : languageSpecificPhonTabooWords){
				String simpleTaboo = tbs.getSimplefiedPhonologicalRepresentationOfTabooWord();

				float dist = WeightedMeasures.weightedLevenshteinWithLCS(simpleInput, simpleTaboo, 1, 1);
				if(dist < distance){
					distance = dist;
				}
			}
			
			assertEquals(distance, (float)0);
		}
	}
	
	
	
	

	/************************************
	 * 
	 * 
	 * HELPERS
	 * 
	 * THERE ARE NOT TEST UNITS ANYMORE
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 * 
	 */

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
		String arabicInputTransliterated = LanguageTransliterator.transcribeArabicToOfficialTranscription(input,
				arabicChat);

		final List<String> russianTranslit = getLinesFromFile(RUSSIAN_TRANSLIT);
		String russianInputTransliterated = LanguageTransliterator.transcribeToRussian(input, russianTranslit);

		List<SourceTransliteration> transliterations = new ArrayList<>();
		transliterations.add(new SourceTransliteration(russianInputTransliterated, LanguageCodes.RUS));
		transliterations.add(new SourceTransliteration(arabicInputTransliterated, LanguageCodes.ARA));

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

	/**
	 * 
	 * creates the input stream to read files from the resource directory
	 * 
	 * @param path
	 * @return
	 */
	private InputStream getInputStream(String path) {
		return ISMLAProjectTests.class.getResourceAsStream(path);
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

}
