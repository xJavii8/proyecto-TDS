package umu.tds.model;

public class Hashtag {
	private int code;
	private static final int MAX_HASH_LENGTH = 15;
	private String name;

	// MÉTODO CONSTRUCTOR
	public Hashtag(int code, String name) {
		super();
		this.code = code;
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

}
