package umu.tds.model;

import java.util.Date;

public class Comment {
	private int code;
	private String text;
	private String author;
	private Date publishedDate;
	
	//MÉTODO CONSTRUCTOR
	public Comment(int code, String text, String author, Date publishedDate) {
		super();
		this.code = code;
		this.text = text;
		this.author = author;
		this.publishedDate = publishedDate;
	}

	//GETS AND SETS
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
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
