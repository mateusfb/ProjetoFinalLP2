package br.imd.model;

/**Classe com os m�todos operadores do dataset*/
public class DatasetOp {

	private Dataset dataset; //Dataset a ser operado
	private String distance; //M�todo de medi��o de dist�ncia a ser utilizado
	private int k; //N�mero de objetos mais pr�ximos a comparar
	
	/**Construtor de DatasetOp
	 * @param dataset Dataset - Dataset a ser operado
	 * */
	public DatasetOp(Dataset dataset){
		this.dataset = dataset;
		this.distance = "Euclidiana";
		this.k = 5;
	}
	
	/**
	 * @param distance String - M�todo de medi��o de dist�ncia
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	/**
	 * @return int - Valor de k
	 */
	public int getK() {
		return this.k;
	}
	
	/**
	 * @param k int - Valor de k
	 */
	public void setK(int k) {
		this.k = k;
	}
	
	/**M�todo euclidiano para a medi��o de dist�ncia de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Dist�ncia euclidiana entre os vetores
	 * */
	public double euclideanD(float[] p1, float[] p2) {
		double sum = 0;
		
		for(int i = 0; i < p2.length; i++) {
			sum += Math.pow((p1[i] - p2[i]), 2);
		}
		
		return Math.sqrt(sum);
	}
	
	/**M�todo de manhattan para a medi��o de dist�ncia de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Dist�ncia de manhattan entre os vetores
	 * */
	public double manhattanD(float[] p1, float[] p2) {
		double ds = 0;
		
		for(int i = 0; i < p2.length; i++) {
			ds += Math.abs(p1[i] - p2[i]);
		}
		
		return ds;
	}
	
	/**M�todo de chebyshev para a medi��o de dist�ncia de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Dist�ncia de chebyshev entre os vetores
	 * */
	public double chebyshevD(float[] p1, float[] p2) {
		double ds = 0;
		
		for(int i = 0; i < p2.length; i++) {
			ds = Math.max(ds, Math.abs(p1[i]-p2[i]));
		} 
		
		return ds;
	}
	
	/**M�todo que detecta se uma imagem � de uma pessoa ou n�o
	 * @param imgAtributes float[] - Vetor de atributos da imagem a ser analisada
	 * @return boolean - true se � pessoa, ou false caso contr�rio
	 * */
	public boolean isPerson(float[] imgAtributes) {
		int[] minIndex = new int[k]; //Vetor de �ndices dos k pontos mais pr�ximos � imagem
		double[] distances = new double[100]; //Vetor de todas as distancias calculadas entre a imagem e o dataset
		double maxDistance = 0; //Maior dist�ncia encontrada
		int personCount = 0; //Contador para as ocorr�ncias de pessoa
		
		for(int i = 0; i < 100; i++) { //Calculando e armazenando todas as dist�ncias
			switch(this.distance){
				case "Euclidiana":
					distances[i] = euclideanD(imgAtributes, dataset.getDataset().get(i).getAtributes());
					break;
				case "Manhattan":
					distances[i] = manhattanD(imgAtributes, dataset.getDataset().get(i).getAtributes());
					break;
				case "Chebyshev":
					distances[i] = chebyshevD(imgAtributes, dataset.getDataset().get(i).getAtributes());
					break;
			}
			if(distances[i] > maxDistance) {
				maxDistance = distances[i]; //Armazenando a maior dist�ncia
			}
		}
		
		for(int i = 0; i < k; i++) { 
			minIndex[i] = FindMinimumIndex(distances); //Armazenando os �ndices das menores dist�ncias
			distances[minIndex[i]] = maxDistance; //Trocando o valor da menor distancia para a da maior, para n�o armazenar o mesmo valor mais de uma vez
			
			if(dataset.getDataset().get(minIndex[i]).getLabel().equals("person")) { //Contando as ocorr�ncias de pessoa
				personCount++;
			}
		}
		
		if(personCount > Math.floor(k/2)) {
			return true;
		}
		
		return false;
	}
	
	/**M�todo para encontrar o menor valor de um vetor e retornar o seu �ndice
	 * @param array double - Vetor de valores a ser operado
	 * @return int - �ndice do menor valor do vetor*/
	public int FindMinimumIndex(double[] array) {
		double min = array[0]; 
		int minIndex = 0;
		
		for(int i = 1; i < array.length; i++) {
			if(array[i] < min) {
				min = array[i];
				minIndex = i;
			}
		}
		
		return minIndex;
	}
}
