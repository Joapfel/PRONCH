package com.ws1718.ismla.server.transliteration;

import java.util.HashMap;
import java.util.List;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class LanguageTransliterator extends RemoteServiceServlet {

	/**
	 * transcription from latin to russian
	 * 
	 * @param input
	 *            latin token
	 * @param fileLines
	 *            is a list of read in lines from a resource file
	 * @return russian token
	 */
	public static String transcribeToRussian(String input, List<String> fileLines) {

		HashMap<String, String> latinToRussianCharacters = new HashMap<>();

		for (String line : fileLines) {

			String[] cols = line.split("\t");
			String russianCharacter = cols[0];
			String russianTranslit = cols[1];

			String[] russianTranslitSymbols = russianTranslit.split("\\s+");

			for (String phon : russianTranslitSymbols) {
				latinToRussianCharacters.put(phon, russianCharacter);
			}

		}

		// apply transformation
		StringBuilder sb = new StringBuilder();
		// iter over characters of input word
		for (int i = 0; i < input.length(); i++) {
			int end = input.length();

			String c = input.substring(i, end);

			for (int k = c.length() - 1; k >= 0; k--) {
				String subCheck = c.substring(0, k + 1);

				if (latinToRussianCharacters.containsKey(subCheck)) {
					sb.append(latinToRussianCharacters.get(subCheck));

					i += k;
					break;
				}
			}
		}

		return sb.toString();
	}

	/**
	 * latin transcription to phonetic version of arabic token
	 * 
	 * @param input
	 *            latin token
	 * @param fileLines
	 *            is a list of read in lines from a resource file
	 * @return phonetic token
	 */
	public static String transcribeArabicToOfficialTranscription(String input, List<String> fileLines) {

		HashMap<String, String> characterMapping = new HashMap<>();

		for (String line : fileLines) {
			if (line != null && line.length() > 0) {

				String[] cols = line.split("\t");
				characterMapping.put(cols[0], cols[1]);

			}
		}

		// apply transformation
		StringBuilder sb = new StringBuilder();
		// iter over characters of input word
		for (int i = 0; i < input.length(); i++) {
			int end = input.length();

			String c = input.substring(i, end);

			for (int k = c.length() - 1; k >= 0; k--) {
				String subCheck = c.substring(0, k + 1);

				if (characterMapping.containsKey(subCheck)) {
					sb.append(characterMapping.get(subCheck));

					i += k;
					break;
				}
			}
		}

		return sb.toString();

	}

	/**
	 * internet persian (in latin characters) transcribed to a processable
	 * transcription
	 * 
	 * @param input
	 * @param fileLines
	 * @return
	 */
	public static String transcribeInternetPersian(String input, List<String> fileLines) {

		HashMap<String, String> characterMapping = new HashMap<>();

		for (String line : fileLines) {
			if (line != null && line.length() > 0) {
				String[] cols = line.split("\t");
				characterMapping.put(cols[0], cols[1]);
			}
		}

		// apply transformation
		StringBuilder sb = new StringBuilder();
		// iter over characters of input word
		for (int i = 0; i < input.length(); i++) {
			int end = input.length();

			String c = input.substring(i, end);

			for (int k = c.length() - 1; k >= 0; k--) {
				String subCheck = c.substring(0, k + 1);

				if (characterMapping.containsKey(subCheck)) {
					sb.append(characterMapping.get(subCheck));

					i += k;
					break;
				}
			}
		}

		return sb.toString();
	}

	
	/**
	 * transcribes thai written with latin characters to the phonological representation
	 * presuposes that the royal thai general system of transcription was used for the input representation
	 * @param input
	 * @param fileLines
	 * @return
	 */
	public static String transcribeRoyalThaiTranscriptionToPhon(String input, List<String> fileLines) {

		HashMap<String, String> characterMapping = new HashMap<>();

		for (String line : fileLines) {
			if (line != null && line.length() > 0) {
				String[] cols = line.split("\t");
				characterMapping.put(cols[0], cols[1]);
			}
		}

		// apply transformation
		StringBuilder sb = new StringBuilder();
		// iter over characters of input word
		for (int i = 0; i < input.length(); i++) {
			int end = input.length();

			String c = input.substring(i, end);

			for (int k = c.length() - 1; k >= 0; k--) {
				String subCheck = c.substring(0, k + 1);

				if (characterMapping.containsKey(subCheck)) {
					sb.append(characterMapping.get(subCheck));

					i += k;
					break;
				}
			}
		}

		return sb.toString();
	}

}
