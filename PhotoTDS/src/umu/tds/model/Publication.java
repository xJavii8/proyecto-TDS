package umu.tds.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class Publication {
	private int code;
	private String title;
	private Date datePublication;
	private String description;
	private int likes;
	private List<HashTag> hashtags;
	private List<Comment> comments;

	// MÉTODO CONSTRUCTOR
	public Publication(String title, Date datePublication, String description, int likes) {
		super();
		this.title = title;
		this.datePublication = datePublication;
		this.description = description;
		this.likes = likes;
		this.hashtags = new LinkedList<HashTag>();
		this.comments = new LinkedList<Comment>();
	}

	// GETS AND SETS
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getDatePublication() {
		return datePublication;
	}

	public void setDatePublication(Date datePublication) {
		this.datePublication = datePublication;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes = likes;
	}

	public List<HashTag> getHashtags() {
		return new LinkedList<HashTag>(this.hashtags);
	}

	public void setHashtags(List<HashTag> hashtags) {
		this.hashtags = hashtags;
	}

	public List<Comment> getComments() {
		return new LinkedList<Comment>(this.comments);
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

}