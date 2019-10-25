package br.imd.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Dataset {

	private ArrayList<ImageInstance> dataset;
	private String datasetLocation;
	
	public Dataset(String datasetLocation) {
		this.dataset = new ArrayList<ImageInstance>();
		this.datasetLocation = datasetLocation;
	}

	/**
	 * @return the dataset
	 */
	public ArrayList<ImageInstance> getDataset() {
		return dataset;
	}

	/**
	 * @param dataset the dataset to set
	 */
	public void setDataset(ArrayList<ImageInstance> dataset) {
		this.dataset = dataset;
	}

	/**
	 * @return the datasetLocation
	 */
	public String getDatasetLocation() {
		return datasetLocation;
	}

	/**
	 * @param datasetLocation the datasetLocation to set
	 */
	public void setDatasetLocation(String datasetLocation) {
		this.datasetLocation = datasetLocation;
	}
	
	public void readData() {
		FileReader file = null;
		String row;
		String[] data;
		float[] atributes = new float[1000];
		
		try {
			file = new FileReader(datasetLocation);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
			
		BufferedReader dataReader = new BufferedReader(file);
			
		try {
			dataReader.readLine();
				
			while((row = dataReader.readLine()) != null) {
				data = row.split(",");
					
				for(int i = 0; i < 1000; i++) {
					atributes[i] = Float.parseFloat(data[i]);
				}
					
				dataset.add(new ImageInstance(data[1000], atributes));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
