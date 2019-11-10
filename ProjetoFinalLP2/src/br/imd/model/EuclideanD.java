package br.imd.model;

/**Classe abstrata de medi��o de dist�ncia euclidiana*/
public class EuclideanD extends Distance {

	/**M�todo euclidiano para a medi��o de dist�ncia de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Dist�ncia euclidiana entre os vetores
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
