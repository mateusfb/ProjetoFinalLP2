package br.imd.model;

import java.io.File;

import org.opencv.core.Mat;
import org.opencv.core.MatOfFloat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

public class HogExtractor {

	private String imgLocation;
	
	public HogExtractor(String imgLocation) {
		this.imgLocation = imgLocation;
	}	

	/**
	 * @return the imgLocation
	 */
	public String getImgLocation() {
		return imgLocation;
	}

	/**
	 * @param imgLocation the imgLocation to set
	 */
	public void setImgLocation(String imgLocation) {
		this.imgLocation = imgLocation;
	}

	public float[] extract() {
		HOGDescriptor hog = new HOGDescriptor();
		Mat img = new Mat();
		MatOfFloat features = new MatOfFloat();
		img = Imgcodecs.imread(imgLocation, Imgcodecs.IMREAD_GRAYSCALE);
		Imgproc.resize(img, img, new Size(64, 128), 0.5, 0.5, Imgproc.INTER_LINEAR);
		hog.compute(img, features);
		float[] arrayOfFeatures = features.toArray();
		
		String saveLocation = new File("").getAbsolutePath() + "\\src\\br\\imd\\resources\\modified.png";
    	Imgcodecs.imwrite(saveLocation, img);
		
		return arrayOfFeatures;
	}
}
