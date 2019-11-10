package br.imd.model;

import java.util.Arrays;

/**Classe com os m�todos operadores do dataset*/
public class DatasetOp {

	private Dataset operated; //Dataset a ser operado
	private String distance; //M�todo de medi��o de dist�ncia a ser utilizado
	private int k; //N�mero de objetos mais pr�ximos a comparar
	
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
	 * @param distance String - M�todo de medi��o de dist�ncia
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
	
	/**M�todo que detecta se uma imagem possui pessoa ou n�o, atrav�s do knn
	 * @param imgAtributes float[] - Vetor de atributos da imagem a ser analisada
	 * @return boolean - true se � pessoa, ou false caso contr�rio
	 * */
	public boolean isPerson(float[] imgAtributes) {
		int[] minIndex = new int[k]; //Vetor de �ndices dos k pontos mais pr�ximos � imagem
		double[] distances = new double[operated.getDataset().size()]; //Vetor de todas as distancias calculadas entre a imagem e o dataset
		double maxDistance = 0; //Maior dist�ncia encontrada
		int personCount = 0; //Contador para as ocorr�ncias de pessoa
		int notPersonCount = 0; //Contador para n�o ocorr�ncia de pessoa
		Distance method = null;
		
		System.out.println("Valor de K: " + k);
		System.out.println("M�todo de medi��o de dist�ncia: " + distance);
		
		for(int i = 0; i < operated.getDataset().size(); i++) { //Calculando e armazenando todas as dist�ncias
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
				maxDistance = distances[i]; //Armazenando a maior dist�ncia
			}
		}
		
		System.out.println("Dist�ncias: " + Arrays.toString(distances));
		System.out.println("-R�tulos mais pr�ximos-");
		
		for(int i = 0; i < k; i++) { 
			minIndex[i] = FindMinimumIndex(distances); //Armazenando os �ndices das menores dist�ncias
			distances[minIndex[i]] = maxDistance; //Trocando o valor da menor distancia para a da maior, para n�o armazenar o mesmo valor mais de uma vez
			
			System.out.println(operated.getDataset().get(minIndex[i]).getLabel());
			
			if(operated.getDataset().get(minIndex[i]).getLabel().equals("person")) { //Contando as ocorr�ncias de pessoa
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
