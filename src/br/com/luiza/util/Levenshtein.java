package br.com.luiza.util;

public class Levenshtein {
	 
	/**
	 * Algoritmo baseado na definição de Levenshtein, calculando a distância entre 2 Strings
	 * @param a
	 * @param b
	 * @return distance
	 */
    private static int distance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        // i == 0
        int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            // j == 0; nw = lev(i - 1, j)
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
    
    /**
     * Verifica se a distância entre 2 String é aceitável, diante de uma porcentagem determinada
     * @param source
     * @param toCompare
     * @param acceptableDistancePercentage
     * @return É aceitável ou não
     */
    public static boolean isDistanceAcceptable(String source, String toCompare, int acceptableDistancePercentage) {
    	
    	acceptableDistancePercentage = Math.abs(100 - acceptableDistancePercentage);
    	
    	int distance = distance(source, toCompare);
    	int sourceLength = source.length();
    	
    	int acceptable = sourceLength - (sourceLength * acceptableDistancePercentage/100);
    	
    	return distance<acceptable;
    }
}