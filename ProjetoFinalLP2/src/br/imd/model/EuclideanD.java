package br.imd.model;

/**Classe abstrata de medição de distância euclidiana*/
public class EuclideanD extends Distance {

	/**Método euclidiano para a medição de distância de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Distância euclidiana entre os vetores
	 * */
	@Override
	public double getDistance(float[] p1, float[] p2) {
		double sum = 0;
		
		for(int i = 0; i < p2.length; i++) {
			sum += Math.pow((p1[i] - p2[i]), 2);
		}
		
		return Math.sqrt(sum);
	}

}
