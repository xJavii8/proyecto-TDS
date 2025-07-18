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
	private List<Album> albums;
	private List<Notification> notifications;
	private Date lastLogin;

	// MÉTODO CONSTRUCTOR
	public User(String username, String email, String password, String fullName, Date birthDay, String profilePic,
			String description, boolean isPremium, Date lastLogin) {
		super();
		this.username = username;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.birthDay = birthDay;
		this.profilePic = profilePic;
		this.description = description;
		this.isPremium = isPremium;
		this.lastLogin = lastLogin;

		this.usersFollowing = new LinkedList<User>();
		this.followers = new LinkedList<User>();
		this.publications = new LinkedList<Publication>();
		this.likedPublications = new LinkedList<Publication>();
		this.albums = new LinkedList<Album>();
		this.notifications = new LinkedList<Notification>();
	}

	public User(String username, String email, String password, String fullName, Date birthDay, String profilePic,
			String description, Date lastLogin) {
		this(username, email, password, fullName, birthDay, profilePic, description, false, lastLogin);
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
		int descuento = 0;
		for (Discount d : descuentos) {
			if (d.validDiscountForUser(this))
				descuento += d.getPremiumDiscount(Constantes.PREMIUM_PRICE, this);
		}
		return (Constantes.PREMIUM_PRICE - descuento);
	}

	public Photo createPhoto(String titulo, String descripcion, String path) {
		Photo p = new Photo(titulo, new Date(), descripcion, path, this.getUsername());
		this.publications.add(p);
		return p;
	}

	public List<Album> deletePhotoInAlbums(Photo p) {
		List<Album> updatedAlbums = new LinkedList<>();
		for (Album a : albums) {
			List<Photo> photos = a.getPhotos();
			if (photos.removeIf(ph -> ph.getCodigo() == p.getCodigo())) {
				removeLike(p);
				updatedAlbums.add(a);
				a.setPhotos(photos);
			}

		}
		return updatedAlbums;
	}

	public boolean deletePhoto(Publication p) {
		this.publications.removeIf(ph -> ph.getCodigo() == p.getCodigo());
		return true;
	}

	public boolean addLike(Publication p) {
		if (!likedPublications.contains(p)) {
			this.likedPublications.add(p);
			return true;
		}
		return false;
	}

	public boolean removeLike(Publication p) {
		if (likedPublications.contains(p)) {
			this.likedPublications.remove(p);
			return true;
		}
		return false;
	}

	public Album createAlbum(String titulo, String descripcion, List<Publication> pubs, List<Hashtag> hashtags,
			String iconAlbum) {
		Album a = new Album(titulo, new Date(), descripcion, this.getUsername(), hashtags, pubs, iconAlbum);
		this.albums.add(a);
		return a;
	}

	public boolean deleteAlbum(Album a) {
		removeLike(a);
		albums.remove(a);
		return true;
	}

	public boolean addNotification(Notification n) {
		if (!notifications.contains(n)) {
			notifications.add(n);
			return true;
		}
		return false;

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

	public List<Album> getAlbums() {
		return albums;
	}

	public void setAlbums(List<Album> albums) {
		this.albums = albums;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

}
