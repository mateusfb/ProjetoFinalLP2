package br.imd.model;

/**Classe abstrata de medição de distância*/
public abstract class Distance {
	public Distance() {};
	
	/**Método abstrato para a medição de distância de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Distância de chebyshev entre os vetores
	 * */
	public abstract double getDistance(float[] p1, float[] p2);
}
