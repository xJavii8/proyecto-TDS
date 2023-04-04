package umu.tds.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class User {
	// ATRIBUTOS
	private int codigo;
	private String name;
	private String email;
	private String fullName;
	private Date birthDay;
	private boolean isPremium;
	private List<User> usersFollowing;
	private List<User> usersFollowed;
	private List<Publication> publications;

	// MÉTODO CONSTRUCTOR
	public User(String name, String email, String fullName, Date birthDay, boolean isPremium) {
		super();
		this.name = name;
		this.email = email;
		this.fullName = fullName;
		this.birthDay = birthDay;
		this.isPremium = isPremium;

		this.usersFollowing = new LinkedList<User>();
		this.usersFollowed = new LinkedList<User>();
		this.publications = new LinkedList<Publication>();
	}

	
	
	//GETS AND SETS
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public List<User> getUsersFollowing() {
		return new LinkedList<User>(this.usersFollowing);
	}

	public void setUsersFollowing(List<User> usersFollowing) {
		this.usersFollowing = usersFollowing;
	}

	public List<User> getUsersFollowed() {
		return new LinkedList<User>(this.usersFollowed);
	}

	public void setUsersFollowed(List<User> usersFollowed) {
		this.usersFollowed = usersFollowed;
	}

	public List<Publication> getPublications() {
		return new LinkedList<Publication>(this.publications);
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}
	
	
	

}
