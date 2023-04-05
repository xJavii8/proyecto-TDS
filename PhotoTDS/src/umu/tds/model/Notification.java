package umu.tds.model;

import java.util.Date;

public class Notification {
	private int code;
	private String text;
	private Date date;

	// MÉTODO CONSTRUCTOR
	public Notification(int code, String text, Date date) {
		super();
		this.code = code;
		this.text = text;
		this.date = date;
	}
	
	// GETTERS AND SETTERS
	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getText() {
		return text;
	}

	public Date getDate() {
		return date;
	}

}
