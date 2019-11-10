package br.imd.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**Classe para armazenar informações de um dataset*/
public class Dataset {

	private ArrayList<ImageInstance> dataset; //Vetor com as instâncias de imagem
	private String datasetLocation; //Caminho para o dataset
	
	/**Construtor de Dataset
	 * @param datasetLocation String - Caminho para o dataset
	 * */
	public Dataset() {
		this.dataset = new ArrayList<ImageInstance>();
		this.datasetLocation = null;
	}

	/**
	 * @return ArrayList<ImageInstance> - Vetor de instâncias de imagem do dataset
	 */
	public ArrayList<ImageInstance> getDataset() {
		return dataset;
	}

	/**
	 * @param datasetLocation String - Caminho para o dataset
	 */
	public void setDatasetLocation(String datasetLocation) {
		this.datasetLocation = datasetLocation;
		try {
			this.readData();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**Método para ler e armazenar os dados do dataset*/
	public void readData() throws IOException{
		FileReader file = null;
		String row;
		String[] data;
		
		file = new FileReader(datasetLocation);
		
		BufferedReader dataReader = new BufferedReader(file);
		
		//Lendo a primeira linha do dataset
		dataReader.readLine();
		
		while((row = dataReader.readLine()) != null) { //Armazenando cada linha na variavel row
			data = row.split(","); //Armazenando cada substring da linha no vetor data
			float[] atributes = new float[1000]; 
			
			for(int i = 0; i < 1000; i++) {
				atributes[i] = Float.parseFloat(data[i]); //Armazenando todos os atributos de imagem no vetor atributes
			}
			
			dataset.add(new ImageInstance(data[1000], atributes)); //Instanciando uma nova imagem e adicionando ao vetor dataset
			
		}
		
		dataReader.close();
	}
}
