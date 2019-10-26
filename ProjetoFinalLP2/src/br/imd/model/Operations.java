package br.imd.model;

import java.util.Arrays;

public class Operations {

	private Dataset dataset;
	
	public Operations(Dataset dataset){
		this.dataset = dataset;
	}
	
	public double euclideanD(float[] p1, float[] p2) {
		double sum = 0;
		
		for(int i = 0; i < 100; i++) {
			sum += Math.pow((p1[i] - p2[i]), 2);
		}
		
		return Math.sqrt(sum);
	}
	
	public boolean isPerson(float[] imgAtributes) {
		int[] minIndex = new int[17];
		double[] distances = new double[100];
		double maxDistance = 0;
		int personCount = 0;
		
		for(int i = 0; i < 100; i++) {
			distances[i] = euclideanD(imgAtributes, dataset.getDataset().get(i).getAtributes());
			if(distances[i] > maxDistance) {
				maxDistance = distances[i];
			}
		}
		
		System.out.println(maxDistance);
		System.out.println(Arrays.toString(distances));
		
		for(int i = 0; i < 17; i++) {
			minIndex[i] = FindMinimumIndex(distances);
			distances[minIndex[i]] = maxDistance;
			
			System.out.println(dataset.getDataset().get(minIndex[i]).getLabel());
			
			if(dataset.getDataset().get(minIndex[i]).getLabel().equals("\"person\"")) {
				personCount++;
			}
		}
		
		System.out.println(Arrays.toString(minIndex));
		
		if(personCount > 8) {
			return true;
		}
		
		return false;
	}
	
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
