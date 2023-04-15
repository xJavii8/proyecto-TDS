package umu.tds.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import umu.tds.view.Constantes;

public class User {
	// ATRIBUTOS
	private int codigo;
	private String username;
	private String email;
	private String password;
	private String fullName;
	private Date birthDay;
	private String profilePic;
	private String description;
	private boolean isPremium;
	private List<User> usersFollowing;
	private List<User> followers;
	private List<Publication> publications;
	private List<Publication> likedPublications;

	// MÉTODO CONSTRUCTOR
	public User(String username, String email, String password, String fullName, Date birthDay, String profilePic,
			String description, boolean isPremium) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.birthDay = birthDay;
		this.profilePic = profilePic;
		this.description = description;
		this.isPremium = isPremium;

		this.usersFollowing = new LinkedList<User>();
		this.followers = new LinkedList<User>();
		this.publications = new LinkedList<Publication>();
		this.likedPublications = new LinkedList<Publication>();
	}

	public User(String username, String email, String password, String fullName, Date birthDay, String profilePic,
			String description) {
		this(username, email, password, fullName, birthDay, profilePic, description, false);
	}

	public boolean addUserFollowing(User u) {
		if (usersFollowing.contains(u))
			return false;
		usersFollowing.add(u);
		return true;
	}

	public boolean removeUserFollowing(User u) {
		if (!usersFollowing.contains(u))
			return false;
		usersFollowing.remove(u);
		return true;
	}

	public boolean addUserFollower(User u) {
		if (followers.contains(u))
			return false;

		followers.add(u);
		return true;
	}

	public boolean removeUserFollower(User u) {
		if (!followers.contains(u))
			return false;

		followers.remove(u);
		return true;
	}

	public int getFinalPrice() {
		List<Discount> descuentos = Discount.getDiscountTypes();
		int precio = Constantes.PREMIUM_PRICE;
		for (Discount d : descuentos) {
			if (d.validDiscountForUser(this))
				precio -= d.getPremiumDiscount(precio, this);
		}
		return precio;
	}

	public Photo createPhoto(String titulo, String descripcion, String path) {
		Photo p = new Photo(titulo, new Date(), descripcion, path, this.getUsername());
		this.publications.add(p);
		return p;
	}

	public boolean addLike(Publication p) {
		this.likedPublications.add(p);
		return true;
	}
	
	public boolean removeLike(Publication p) {
		this.likedPublications.remove(p);
		return true;
	}

	// GETS AND SETS
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfilePic() {
		return profilePic;
	}

	public void setProfilePic(String profilePic) {
		this.profilePic = profilePic;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public List<User> getFollowers() {
		return new LinkedList<User>(this.followers);
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public List<Publication> getLikedPublications() {
		return likedPublications;
	}

	public void setLikedPublications(List<Publication> likedPublications) {
		this.likedPublications = likedPublications;
	}

	public List<Publication> getPublications() {
		return new LinkedList<Publication>(this.publications);
	}

	public void setPublications(List<Publication> publications) {
		this.publications = publications;
	}

}
