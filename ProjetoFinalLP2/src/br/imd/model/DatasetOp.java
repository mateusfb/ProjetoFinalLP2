package br.imd.model;

import java.util.Arrays;

/**Classe com os métodos operadores do dataset*/
public class DatasetOp {

	private Dataset operated; //Dataset a ser operado
	private String distance; //Método de medição de distância a ser utilizado
	private int k; //Número de objetos mais próximos a comparar
	
	/**Construtor de DatasetOp
	 * @param dataset Dataset - Dataset a ser operado
	 * */
	public DatasetOp(Dataset operated){
		this.operated = operated;
		this.distance = "Euclidiana";
		this.k = 5;
	}
	
	/**
	 * @return Dataset - Dataset operado
	 */
	public Dataset getOperated() {
		return operated;
	}
	
	/**
	 * @param distance String - Método de medição de distância
	 */
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	/**
	 * @param k int - Valor de k
	 */
	public void setK(int k) {
		this.k = k;
	}
	
	/**Método que detecta se uma imagem possui pessoa ou não, através do knn
	 * @param imgAtributes float[] - Vetor de atributos da imagem a ser analisada
	 * @return boolean - true se é pessoa, ou false caso contrário
	 * */
	public boolean isPerson(float[] imgAtributes) {
		int[] minIndex = new int[k]; //Vetor de índices dos k pontos mais próximos à imagem
		double[] distances = new double[operated.getDataset().size()]; //Vetor de todas as distancias calculadas entre a imagem e o dataset
		double maxDistance = 0; //Maior distância encontrada
		int personCount = 0; //Contador para as ocorrências de pessoa
		int notPersonCount = 0; //Contador para não ocorrência de pessoa
		Distance method = null;
		
		System.out.println("Valor de K: " + k);
		System.out.println("Método de medição de distância: " + distance);
		
		for(int i = 0; i < operated.getDataset().size(); i++) { //Calculando e armazenando todas as distâncias
			switch(this.distance){
				case "Euclidiana":
					method = new EuclideanD();
					break;
				case "Manhattan":
					method = new ManhattanD();
					break;
				case "Chebyshev":
					method = new ChebyshevD();
					break;
			}
			
			distances[i] = method.getDistance(imgAtributes, operated.getDataset().get(i).getAtributes());
			
			if(distances[i] > maxDistance) {
				maxDistance = distances[i]; //Armazenando a maior distância
			}
		}
		
		System.out.println("Distâncias: " + Arrays.toString(distances));
		System.out.println("-Rótulos mais próximos-");
		
		for(int i = 0; i < k; i++) { 
			minIndex[i] = FindMinimumIndex(distances); //Armazenando os índices das menores distâncias
			distances[minIndex[i]] = maxDistance; //Trocando o valor da menor distancia para a da maior, para não armazenar o mesmo valor mais de uma vez
			
			System.out.println(operated.getDataset().get(minIndex[i]).getLabel());
			
			if(operated.getDataset().get(minIndex[i]).getLabel().equals("person")) { //Contando as ocorrências de pessoa
				personCount++;
			} else {
				notPersonCount++;
			}
		}
		
		System.out.println("-----------------------");
		
		if(personCount > notPersonCount) {
			return true;
		} else if(personCount == notPersonCount) {
			if(operated.getDataset().get(minIndex[0]).getLabel().equals("person")) {
				return true;
			}
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
