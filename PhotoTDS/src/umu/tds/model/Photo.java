package umu.tds.model;

import java.util.Date;
import java.util.List;

public class Photo extends Publication {
	private String path;

	// Método constructor
	public Photo(String title, Date datePublication, String description, int likes, String path, String user, List<Comment> comentarios, List<Hashtag> hashtags) {
		super(title, datePublication, description, likes, user, comentarios, hashtags);
		this.path = path;
	}
	
	public Photo(String title, Date datePublication, String description, String path, String user) {
		super(title, datePublication, description, user);
		this.path = path;
	}

	//GETTERS AND SETTERS
	public String getPath() {
		return path;
	}

}
