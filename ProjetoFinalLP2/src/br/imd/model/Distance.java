package br.imd.model;

/**Classe abstrata de medi��o de dist�ncia*/
public abstract class Distance {
	public Distance() {};
	
	/**M�todo abstrato para a medi��o de dist�ncia de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Dist�ncia de chebyshev entre os vetores
	 * */
	public abstract double getDistance(float[] p1, float[] p2);
}
