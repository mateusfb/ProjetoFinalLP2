package br.imd.model;

import java.util.Arrays;

public class DatasetOp {

	private Dataset dataset;
	private String distance;
	private static final int K = 11;
	
	public DatasetOp(Dataset dataset){
		this.dataset = dataset;
		this.distance = "Euclidiana";
	}
	
	public void setDistance(String distance) {
		this.distance = distance;
	}
	
	public double euclideanD(float[] p1, float[] p2) {
		double sum = 0;
		
		for(int i = 0; i < p2.length; i++) {
			sum += Math.pow((p1[i] - p2[i]), 2);
		}
		
		return Math.sqrt(sum);
	}
	
	public double manhattanD(float[] p1, float[] p2) {
		double ds = 0;
		
		for(int i = 0; i < p2.length; i++) {
			ds += Math.abs(p1[i] - p2[i]);
		}
		
		return ds;
	}
	
	public double chebyshevD(float[] p1, float[] p2) {
		double ds = 0;
		
		for(int i = 0; i < p2.length; i++) {
			ds = Math.max(ds, Math.abs(p1[i]-p2[i]));
		} 
		
		return ds;
	}
	
	public boolean isPerson(float[] imgAtributes) {
		int[] minIndex = new int[K];
		double[] distances = new double[100];
		double maxDistance = 0;
		int personCount = 0;
		
		for(int i = 0; i < 100; i++) {
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
				maxDistance = distances[i];
			}
		}
		
		System.out.println(maxDistance);
		System.out.println(Arrays.toString(distances));
		
		for(int i = 0; i < K; i++) {
			minIndex[i] = FindMinimumIndex(distances);
			distances[minIndex[i]] = maxDistance;
			
			System.out.println(dataset.getDataset().get(minIndex[i]).getLabel());
			
			if(dataset.getDataset().get(minIndex[i]).getLabel().equals("person")) {
				personCount++;
			}
		}
		
		System.out.println(Arrays.toString(minIndex));
		
		if(personCount > Math.floor(K/2)) {
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
