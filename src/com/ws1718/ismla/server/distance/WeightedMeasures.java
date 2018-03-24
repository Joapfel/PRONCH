package com.ws1718.ismla.server.distance;

public class WeightedMeasures {
	
	
	/**
	 * takes the longest common substring
	 * gets the part of the string before the LCS and the part after the LCS (prefix / suffix)
	 * Levenshtein distance is calculated between both prefixes / suffixes of the respective strings
	 * weights the prefix / suffix distances with the given weights
	 * adds the weighted prefix and suffix together
	 * 
	 * @param s1 (input)
	 * @param s2 (taboo word)
	 * @param prefixWeight
	 * @param suffixWeight
	 * @return calculated distance
	 */
	public static float weightedLevenshteinWithLCS(String s1, String s2, int prefixWeight, int suffixWeight){
		float distance = 0;
		
		Levenshtein dist = new Levenshtein();
		
		String lcs = SimilarityMeasures.longestCommonSubstring(s1, s2);
		String[] beforeAndAfterLCSInput = s1.split(lcs, 2); 
		String[] beforeAndAfterLCSTabooWord = s2.split(lcs, 2);
		
		if(lcs != null && lcs.length() > 0 && !(lcs.equals(s1) && lcs.equals(s2))){
			
			String beforeInput = "";
			if(beforeAndAfterLCSInput.length > 1 || lcs.charAt(0) != s1.charAt(0)){
				beforeInput = beforeAndAfterLCSInput[0];
			}
			
			String beforeTabooWord = "";
			if(beforeAndAfterLCSTabooWord.length > 1 || lcs.charAt(0) != s2.charAt(0)){
				beforeTabooWord = beforeAndAfterLCSTabooWord[0];
			}
			
			String afterInput = "";
			if(beforeAndAfterLCSInput.length > 1){
				afterInput = beforeAndAfterLCSInput[1];
			}else if(beforeAndAfterLCSInput.length > 0 && lcs.charAt(0) == s1.charAt(0)){
				afterInput = beforeAndAfterLCSInput[0];
			}
			
			String afterTabooWord = "";
			if(beforeAndAfterLCSTabooWord.length > 1){
				afterTabooWord = beforeAndAfterLCSTabooWord[1];
			}else if(beforeAndAfterLCSTabooWord.length > 0 && lcs.charAt(0) == s2.charAt(0)){
				afterTabooWord = beforeAndAfterLCSTabooWord[0];
			}
			
			//weighting
			float before = dist.getDistance(beforeInput, beforeTabooWord);
			//punish different lengths
			if(beforeInput.length() != beforeTabooWord.length()){
				float diff = Math.abs(beforeInput.length() - beforeTabooWord.length());
				before += diff*5;
			}
			
			if(lcs.length() >= beforeInput.length()*1.5){
				before = before / lcs.length();
			}
			
			int longerDistBefore = Math.max(beforeInput.length(), beforeTabooWord.length());
			if(before > 0){
				before = before / (longerDistBefore * prefixWeight);
			}			
			float after = dist.getDistance(afterInput, afterTabooWord);
			//punish different lengths
			if(afterInput.length() != afterTabooWord.length()){
				float diff = Math.abs(afterInput.length() - afterTabooWord.length());
				after += diff*5;
			}
			
			if(lcs.length() >= afterInput.length()*1.5){
				after = after / lcs.length();
			}

			int longerDistAfter = Math.max(afterInput.length(), afterTabooWord.length());
			if(after > 0){
				after = after / longerDistAfter;
			}
			
			
			
			distance = (before * prefixWeight) + (after * suffixWeight);
			distance = distance / 2;


		}else{
			distance = dist.getDistance(s1, s2);
		}
		
		
		return distance;
	}
}
