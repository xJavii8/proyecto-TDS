package umu.tds.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import umu.tds.view.Constantes;

public class Publication {
	private int codigo;
	private String user;
	private String title;
	private Date datePublication;
	private String description;
	private int likes;
	private List<Hashtag> hashtags;
	private List<Comment> comments;

	// M�TODO CONSTRUCTOR
	public Publication(String title, Date datePublication, String description, int likes, String user,
			List<Hashtag> hashtags) {
		super();
		this.title = title;
		this.datePublication = datePublication;
		this.description = description;
		this.likes = likes;
		this.user = user;
		this.hashtags = hashtags;
		this.comments = new LinkedList<Comment>();
	}

	public Publication(String title, Date datePublication, String description, int likes, String user,
			List<Comment> comments, List<Hashtag> hashtags) {
		super();
		this.title = title;
		this.datePublication = datePublication;
		this.description = description;
		this.likes = likes;
		this.user = user;
		this.hashtags = hashtags;
		this.comments = comments;
	}

	public Publication(String title, Date datePublication, String description, String user, List<Hashtag> hashtags) {
		super();
		this.title = title;
		this.datePublication = datePublication;
		this.description = description;
		this.likes = Constantes.DEFAULT_LIKES_PUBLICATION;
		this.user = user;
		this.hashtags = hashtags;
		this.comments = new LinkedList<>();
	}

	public Publication(String title, Date datePublication, String description, String user) {
		super();
		this.title = title;
		this.datePublication = datePublication;
		this.description = description;
		this.user = user;
		this.hashtags = new LinkedList<Hashtag>();
		this.comments = new LinkedList<Comment>();
	}

	public boolean addLike() {
		this.likes += 1;
		return true;
	}

	public boolean removeLike() {
		if(this.likes != 0 || this.likes < 0) 
			this.likes -= 1;
		return true;
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

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public List<Hashtag> getHashtags() {
		return new LinkedList<Hashtag>(this.hashtags);
	}

	public void setHashtags(List<Hashtag> hashtags) {
		this.hashtags = hashtags;
	}

	public Comment addComment(String comment, String user) {
		Comment com = new Comment(comment, user, new Date());
		this.comments.add(com);
		return com;
	}

	public boolean removeComment(Comment comment) {
		if (this.comments.remove(comment)) {
			return true;
		} else {
			return false;
		}
	}

	public Hashtag addHashtag(String hName) {
		Hashtag h = new Hashtag(hName);
		this.hashtags.add(h);
		return h;
	}

	public boolean removeHashtag(Hashtag h) {
		if (this.hashtags.remove(h)) {
			return true;
		} else {
			return false;
		}
	}

	public List<Comment> getComments() {
		return new LinkedList<Comment>(this.comments);
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}