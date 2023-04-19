package umu.tds.model;

public class Hashtag {
	private int code;
	private String name;

	// MÉTODO CONSTRUCTOR
	public Hashtag(String name) {
		super();
		this.name = name;
	}

	// GETTERS AND SETTERS
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
