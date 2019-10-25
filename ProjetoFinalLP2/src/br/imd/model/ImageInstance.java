package br.imd.model;

public class ImageInstance {
	
	private String label;
	private float[] atributes;

	public ImageInstance(String label, float[] atributes) {
		super();
		this.label = label;
		this.atributes = atributes;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return the atributes
	 */
	public float[] getAtributes() {
		return atributes;
	}

	/**
	 * @param atributes the atributes to set
	 */
	public void setAtributes(float[] atributes) {
		this.atributes = atributes;
	}
	
	
}