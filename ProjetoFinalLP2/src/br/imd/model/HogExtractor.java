package br.imd.model;

import java.io.File;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

/**Classe de extratores de atributos de imagens*/
public class HogExtractor {

	private String imgLocation; //Caminho para a imagem a ser operada
	
	/**Construtor de HogExtractor
	 * @param imgLocation String - Caminho da imagem 
	 * */
	public HogExtractor(String imgLocation) {
		this.imgLocation = imgLocation;
	}	

	/**
	 * @return String - Caminho da imagem
	 */
	public String getImgLocation() {
		return imgLocation;
	}

	/**
	 * @param label String - Caminho da imagem
	 */
	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}

	/**Método extrator dos atributos de uma imagem
	 * @return float[] - Vetor com os atributos extraídos da imagem*/
	public float[] extract() {
		HOGDescriptor hog = new HOGDescriptor();
		Mat img = new Mat();
		MatOfFloat features = new MatOfFloat();
		img = Imgcodecs.imread(imgLocation, Imgcodecs.IMREAD_GRAYSCALE);
		Imgproc.resize(img, img, new Size(64, 128), 0.5, 0.5, Imgproc.INTER_LINEAR);
		hog.compute(img, features);
		float[] arrayOfFeatures = features.toArray();
		
		//Salvando a imagem tratada
		String saveLocation = new File("").getAbsolutePath() + "\\src\\br\\imd\\resources\\modified.png";
    	Imgcodecs.imwrite(saveLocation, img);
		
		return arrayOfFeatures;
	}
}
