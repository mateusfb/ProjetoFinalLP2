package br.imd.model;

/**Classe com os métodos operadores do dataset*/
public class DatasetOp {

	private Dataset dataset; //Dataset a ser operado
	private String distance; //Método de medição de distância a ser utilizado
	private int k; //Número de objetos mais próximos a comparar
	
	/**Construtor de DatasetOp
	 * @param dataset Dataset - Dataset a ser operado
	 * */
	public DatasetOp(Dataset dataset){
		this.dataset = dataset;
		this.distance = "Euclidiana";
		this.k = 5;
	}
	
	/**
	 * @param distance String - Método de medição de distância
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
	
	/**Método euclidiano para a medição de distância de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Distância euclidiana entre os vetores
	 * */
	public double euclideanD(float[] p1, float[] p2) {
		double sum = 0;
		
		for(int i = 0; i < p2.length; i++) {
			sum += Math.pow((p1[i] - p2[i]), 2);
		}
		
		return Math.sqrt(sum);
	}
	
	/**Método de manhattan para a medição de distância de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Distância de manhattan entre os vetores
	 * */
	public double manhattanD(float[] p1, float[] p2) {
		double ds = 0;
		
		for(int i = 0; i < p2.length; i++) {
			ds += Math.abs(p1[i] - p2[i]);
		}
		
		return ds;
	}
	
	/**Método de chebyshev para a medição de distância de dois vetores
	 * @param p1 float[] - Primeiro vetor de valores
	 * @param p2 float[] - Segundo vetor de valores
	 * @return double - Distância de chebyshev entre os vetores
	 * */
	public double chebyshevD(float[] p1, float[] p2) {
		double ds = 0;
		
		for(int i = 0; i < p2.length; i++) {
			ds = Math.max(ds, Math.abs(p1[i]-p2[i]));
		} 
		
		return ds;
	}
	
	/**Método que detecta se uma imagem é de uma pessoa ou não
	 * @param imgAtributes float[] - Vetor de atributos da imagem a ser analisada
	 * @return boolean - true se é pessoa, ou false caso contrário
	 * */
	public boolean isPerson(float[] imgAtributes) {
		int[] minIndex = new int[k]; //Vetor de índices dos k pontos mais próximos à imagem
		double[] distances = new double[100]; //Vetor de todas as distancias calculadas entre a imagem e o dataset
		double maxDistance = 0; //Maior distância encontrada
		int personCount = 0; //Contador para as ocorrências de pessoa
		
		for(int i = 0; i < 100; i++) { //Calculando e armazenando todas as distâncias
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
				maxDistance = distances[i]; //Armazenando a maior distância
			}
		}
		
		for(int i = 0; i < k; i++) { 
			minIndex[i] = FindMinimumIndex(distances); //Armazenando os índices das menores distâncias
			distances[minIndex[i]] = maxDistance; //Trocando o valor da menor distancia para a da maior, para não armazenar o mesmo valor mais de uma vez
			
			if(dataset.getDataset().get(minIndex[i]).getLabel().equals("person")) { //Contando as ocorrências de pessoa
				personCount++;
			}
		}
		
		if(personCount > Math.floor(k/2)) {
			return true;
		}
		
		return false;
	}
	
	/**Método para encontrar o menor valor de um vetor e retornar o seu índice
	 * @param array double - Vetor de valores a ser operado
	 * @return int - Índice do menor valor do vetor*/
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
