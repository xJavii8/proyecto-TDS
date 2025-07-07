package umu.tds.model;

import java.util.Date;

public class Comment {
	private int codigo;
	private String text;
	private String author;
	private Date publishedDate;

	// MÉTODO CONSTRUCTOR
	public Comment(int code, String text, String author, Date publishedDate) {
		super();
		this.codigo = code;
		this.text = text;
		this.author = author;
		this.publishedDate = publishedDate;
	}

	public Comment(String text, String author, Date publishedDate) {
		super();
		this.text = text;
		this.author = author;
		this.publishedDate = publishedDate;
	}

	// GETTERS AND SETTERS
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int code) {
		this.codigo = code;
	}

	public String getText() {
		return text;
	}

	public String getAuthor() {
		return author;
	}

	public Date getPublishedDate() {
		return publishedDate;
	}

}
