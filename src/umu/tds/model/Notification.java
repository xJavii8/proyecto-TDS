package umu.tds.model;

import java.util.Date;

public class Notification {
	private int codigo;
	private Date date;
	private Publication pub;

	// MÉTODO CONSTRUCTOR
	public Notification(Date date, Publication pub) {
		super();
		this.pub = pub;
		this.date = date;
	}

	// GETTERS AND SETTERS
	public int getCodigo() {
		return codigo;
	}

	public void setCode(int code) {
		this.codigo = code;
	}

	public Date getDate() {
		return date;
	}

	public Publication getPublication() {
		return pub;
	}

}
