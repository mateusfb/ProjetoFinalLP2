package br.imd.model;

/**Classe para inst�ncias de imagem, onde ser�o contidos os atributos e r�tulos das mesmas.*/
public class ImageInstance {
	
	private String label; //R�tulo da imagem
	private float[] atributes; //Vetor com os atributos da imagem

	/**Construtor de ImageInstance
	 * @param label String - R�tulo da imagem
	 * @param atributes float[] - Atributos da imagem
	 * */
	public ImageInstance(String label, float[] atributes) {
		super();
		this.label = label;
		this.atributes = atributes;
	}

	/**
	 * @return String - R�tulo da imagem
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label String - R�tulo da imagem
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * @return float[] - Atributos da imagem
	 */
	public float[] getAtributes() {
		return atributes;
	}

	/**
	 * @param atributes float[] - Atributos da imagem
	 */
	public void setAtributes(float[] atributes) {
		this.atributes = atributes;
	}
	
	
}