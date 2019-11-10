package br.imd.model;

/**Classe abstrata de medi��o de dist�ncia de Manhattan*/
public class ManhattanD extends Distance {

	/**M�todo de manhattan para a medi��o de dist�ncia de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Dist�ncia de manhattan entre os vetores
	 * */
	@Override
	public double getDistance(float[] p1, float[] p2) {
		double ds = 0;
		
		for(int i = 0; i < p2.length; i++) {
			ds += Math.abs(p1[i] - p2[i]);
		}
		
		return ds;
	}

}
