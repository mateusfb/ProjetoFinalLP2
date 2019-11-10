package br.imd.model;

/**Classe abstrata de medição de distância de Chebyshev*/
public class ChebyshevD extends Distance {

	/**Método de chebyshev para a medição de distância de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Distância de chebyshev entre os vetores
	 * */
	@Override
	public double getDistance(float[] p1, float[] p2) {
		double ds = 0;
		
		for(int i = 0; i < p2.length; i++) {
			ds = Math.max(ds, Math.abs(p1[i]-p2[i]));
		} 
		
		return ds;
	}

}
