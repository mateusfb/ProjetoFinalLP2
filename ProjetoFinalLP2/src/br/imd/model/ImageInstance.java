package br.imd.model;

/**Classe para instâncias de imagem, onde serão contidos os atributos e rótulos das mesmas.*/
public class ImageInstance {
	
	private String label; //Rótulo da imagem
	private float[] atributes; //Vetor com os atributos da imagem

	/**Construtor de ImageInstance
	 * @param label String - Rótulo da imagem
	 * @param atributes float[] - Atributos da imagem
	 * */
	public ImageInstance(String label, float[] atributes) {
		super();
		this.label = label;
		this.atributes = atributes;
	}

	/**
	 * @return String - Rótulo da imagem
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return float[] - Atributos da imagem
	 */
	public float[] getAtributes() {
		return atributes;
	}
}