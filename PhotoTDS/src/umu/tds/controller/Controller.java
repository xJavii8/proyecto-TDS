package umu.tds.controller;

import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import umu.tds.fotos.CargadorFotos;
import umu.tds.fotos.Foto;
import umu.tds.fotos.Fotos;
import umu.tds.fotos.MapperFotosXMLtoJava;
import umu.tds.model.Album;
import umu.tds.model.Comment;
import umu.tds.model.Hashtag;
import umu.tds.model.Notification;
import umu.tds.model.Photo;
import umu.tds.model.Publication;
import umu.tds.model.PublicationRepository;
import umu.tds.model.User;
import umu.tds.model.UserRepository;
import umu.tds.persistence.DAOException;
import umu.tds.persistence.DAOFactory;
import umu.tds.persistence.IAdaptadorHashtagDAO;
import umu.tds.persistence.IAdaptadorNotificationDAO;
import umu.tds.persistence.IAdaptadorPublicationDAO;
import umu.tds.persistence.IAdaptadorUserDAO;
import umu.tds.view.Constantes;
import umu.tds.view.Utilities;

public class Controller implements PropertyChangeListener {
	private static Controller unicaInstancia;

	private IAdaptadorUserDAO adaptadorUser;
	private IAdaptadorPublicationDAO adaptadorPublication;
	private IAdaptadorHashtagDAO adaptadorHashtag;
	private IAdaptadorNotificationDAO adaptadorNotification;

	private UserRepository userRepo;
	private PublicationRepository publRepo;

	private Optional<User> actualUser;

	private Controller() {
		CargadorFotos.getInstancia().addListener(this);
		inicializarAdaptadores();
		inicializarRepos();
	}

	public static Controller getInstancia() {
		if (unicaInstancia == null) {
			unicaInstancia = new Controller();
		}

		return unicaInstancia;
	}

	private void inicializarAdaptadores() {
		DAOFactory factoria = null;

		try {
			factoria = DAOFactory.getInstancia(DAOFactory.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}

		adaptadorUser = factoria.getUserDAO();
		adaptadorPublication = factoria.getPublicationDAO();
		adaptadorHashtag = factoria.getHashtagDAO();
		adaptadorNotification = factoria.getNotificationDAO();
	}

	private void inicializarRepos() {
		userRepo = UserRepository.getInstancia();
		publRepo = PublicationRepository.getInstancia();
	}

	public boolean createUser(String email, String fullname, String username, String password, Date birthday,
			String profilePic, String description) {
		boolean userExist = userRepo.userExist(username);
		boolean emailExist = userRepo.userExistEmail(email);

		if (userExist == true || emailExist == true)
			return false;

		User user = new User(username, email, password, fullname, birthday, profilePic, description, new Date());
		userRepo.addUser(user);

		return true;
	}
	
	public boolean removeUser(String nickname) {
		User usuario = this.getUser(nickname);
		if (usuario == null) {
			return false;
		}
		this.userRepo.removeUser(usuario);
		return true;
	}
	
	
	
	

	public boolean login(String username, String password) {
		Matcher emailMatch = Constantes.EMAIL_PAT.matcher(username);

		Optional<User> user;

		if (emailMatch.matches())
			user = userRepo.getUserFromEmail(username);
		else
			user = userRepo.getUser(username);
		if (user.isEmpty())
			return false;
		else if (user.get().getPassword().equals(password)) {
			this.actualUser = user;
			return true;
		} else
			return false;
	}

	public void actualizarLastLogin(String username) {
		Optional<User> user;
		String fecha;

		fecha = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());
		user = userRepo.getUser(username);
		user.get().setLastLogin(Utilities.stringToDateHours(fecha));
		this.adaptadorUser.updateUser(user.get());
	}

	public boolean setPremium(String username) {
		Optional<User> userO = userRepo.getUser(username);
		if (userO.isEmpty())
			return false;

		User user = userO.get();
		user.setPremium(true);
		adaptadorUser.updateUser(user);
		return true;
	}

	public DefaultListModel<Photo> getTop10LikedPhotos(String username) {
		Optional<User> userO = userRepo.getUser(username);
		if (userO.isEmpty())
			return null;

		User user = userO.get();
		List<Photo> top10PhotosList = user.getPublications().stream()
				.filter(publication -> publication instanceof Photo).map(publication -> (Photo) publication)
				.sorted(Comparator.comparing(Photo::getLikes).reversed()).limit(Constantes.TOP_LIKED_PHOTOS_PREMIUM)
				.collect(Collectors.toList());
		DefaultListModel<Photo> top10PhotosDLM = new DefaultListModel<>();
		top10PhotosList.forEach(top10PhotosDLM::addElement);
		return top10PhotosDLM;
	}

	public boolean createExcel(String username, String path) {
		Optional<User> userO = userRepo.getUser(username);
		if (userO.isEmpty())
			return false;

		User user = userO.get();
		if (!user.isPremium())
			return false;

		ExcelGen.genExcel(user, user.getFollowers(), path);

		return true;
	}

	public boolean createPDF(String username, String path) {
		Optional<User> userO = userRepo.getUser(username);
		if (userO.isEmpty())
			return false;

		User user = userO.get();
		if (!user.isPremium())
			return false;

		PDFGen.genPDF(user, user.getFollowers(), path);

		return true;
	}

	public User getUser(String username) {
		Optional<User> userO;
		Matcher emailMatch = Constantes.EMAIL_PAT.matcher(username);

		if (emailMatch.matches())
			userO = userRepo.getUserFromEmail(username);
		else
			userO = userRepo.getUser(username);
		if (userO.isEmpty())
			return null;

		return userO.get();
	}

	public boolean userIsFollower(String selfUsername, String usernameSearched) {
		User userSearched = userRepo.getUser(usernameSearched).get();
		return userSearched.getFollowers().stream().anyMatch(user -> user.getUsername().equals(selfUsername));
	}

	public boolean follow(String selfUsername, String usernameSearched) {
		User selfUser = userRepo.getUser(selfUsername).get();
		User searchedUser = userRepo.getUser(usernameSearched).get();

		selfUser.addUserFollowing(searchedUser);
		searchedUser.addUserFollower(selfUser);

		adaptadorUser.updateUser(selfUser);
		adaptadorUser.updateUser(searchedUser);
		return true;
	}

	public boolean unfollow(String selfUsername, String usernameSearched) {
		User selfUser = userRepo.getUser(selfUsername).get();
		User searchedUser = userRepo.getUser(usernameSearched).get();

		selfUser.removeUserFollowing(searchedUser);
		searchedUser.removeUserFollower(selfUser);
		adaptadorUser.updateUser(selfUser);
		adaptadorUser.updateUser(searchedUser);
		return true;
	}

	public DefaultListModel<User> search(String selfUsername, String searchString) {
		User selfUser = userRepo.getUser(selfUsername).get();
		DefaultListModel<User> matchingUsers = new DefaultListModel<>();
		List<User> allUsers = userRepo.getAllUsers();
		Matcher emailMatch = Constantes.EMAIL_PAT.matcher(searchString);
		Matcher fullnameMatch = Constantes.FULLNAME_PAT.matcher(searchString);
		if (emailMatch.matches()) {
			matchingUsers.addAll(
					allUsers.stream().filter(u -> u.getEmail().equals(searchString)).collect(Collectors.toList()));
		} else if (fullnameMatch.matches()) {
			matchingUsers.addAll(allUsers.stream()
					.filter(u -> u.getFullName().startsWith(searchString) || u.getFullName().equals(searchString))
					.collect(Collectors.toList()));
		} else {
			matchingUsers.addAll(allUsers.stream()
					.filter(u -> u.getUsername().startsWith(searchString) || u.getUsername().equals(searchString))
					.collect(Collectors.toList()));
		}

		matchingUsers.removeElement(selfUser);
		return matchingUsers;
	}

	public DefaultListModel<Publication> searchPublicationsByHashtags(String searchText) {
		DefaultListModel<Publication> pubs = new DefaultListModel<>();
		List<String> validHashtags = new ArrayList<>();

		Matcher matcher = Constantes.HASHTAG_PAT.matcher(searchText);
		while (matcher.find()) {
			validHashtags.add(matcher.group(2));
		}

		if (validHashtags.isEmpty()) {
			return pubs;
		}

		List<Publication> matchingPublications = new LinkedList<>();

		for (Publication p : getAllPublications()) {
			List<String> hashNames = p.getHashtags().stream().map(Hashtag::getName).collect(Collectors.toList());
			if (hashNames.containsAll(validHashtags)) {
				matchingPublications.add(p);
			}
		}

		matchingPublications.stream().forEach(p -> pubs.addElement(p));
		return pubs;
	}

	public DefaultListModel<User> getFollowingUsers(String username) {
		User user = userRepo.getUser(username).get();
		DefaultListModel<User> users = new DefaultListModel<>();
		List<User> followingUsers = user.getUsersFollowing();
		for (User u : followingUsers)
			users.addElement(u);
		return users;
	}

	public DefaultListModel<User> getFollowers(String username) {
		User user = userRepo.getUser(username).get();
		DefaultListModel<User> users = new DefaultListModel<>();
		List<User> followers = user.getFollowers();
		for (User u : followers)
			users.addElement(u);
		return users;
	}

	public DefaultListModel<Comment> getComments(String tituloPub) {
		Publication publ = publRepo.getPublication(tituloPub).get();
		DefaultListModel<Comment> comments = new DefaultListModel<>();
		List<Comment> comentarios = publ.getComments();
		for (Comment c : comentarios) {
			comments.addElement(c);
		}

		return comments;
	}

	public boolean albumExist(String username, String tituloAlbum) {
		User user = userRepo.getUser(username).get();
		for (Album a : user.getAlbums()) {
			if (a.getTitle().equals(tituloAlbum))
				return true;
		}

		return false;

	}

	public boolean updateUser(User user, String fullname, String username, String description, String profilePicPath) {
		String oldUsername = user.getUsername();
		user.setFullName(fullname);
		user.setUsername(username);
		user.setDescription(description);
		user.setProfilePic(profilePicPath);
		userRepo.editUser(oldUsername, user);
		return true;
	}

	public boolean updateUserSensibleInfo(User user, String email, String password) {
		user.setEmail(email);
		user.setPassword(password);
		adaptadorUser.updateUser(user);
		return true;
	}

	public boolean addComment(Publication publ, String comment, String user) {
		publ.addComment(comment, this.userRepo.getUser(user).get().getUsername());
		this.publRepo.updatePublication(publ);
		return true;
	}

	public boolean removeComment(Publication publ, Comment comment) {
		Optional<Comment> com = publ.getComments().stream().filter(f -> f.getAuthor().equals(comment.getAuthor()))
				.filter(f -> f.getText().equals(comment.getText()))
				.filter(f -> f.getPublishedDate().equals(comment.getPublishedDate())).findFirst();

		if (com.isEmpty()) {
			return false;
		}

		publ.removeComment(comment);

		this.publRepo.updatePublication(publ);
		return true;
	}

	public boolean addHashtag(Publication p, String hashName) {
		p.addHashtag(hashName);
		publRepo.updatePublication(p);
		return true;
	}

	public boolean removeHashtag(Publication p, Hashtag hash) {
		p.removeHashtag(hash);
		publRepo.updatePublication(p);
		return true;
	}

	public boolean createPhoto(String user, String titulo, String descripcion, String path) {
		User usuario = this.getUser(user);
		Photo p = usuario.createPhoto(titulo, descripcion, path);
		List<Hashtag> hashtags = new ArrayList<>();
		Matcher matcher = Constantes.HASHTAG_PAT.matcher(descripcion);
		while (matcher.find()) {
			Hashtag h = new Hashtag(matcher.group(2));
			adaptadorHashtag.createHashtag(h);
			hashtags.add(h);
		}

		p.setHashtags(hashtags);
		this.publRepo.createPublication(p);
		this.adaptadorUser.updateUser(usuario);
		this.addNotificacionFollowers(usuario, p);
		return true;
	}

	private boolean addNotificacionFollowers(User u, Publication p) {
		Notification n = new Notification(new Date(), p);
		this.adaptadorNotification.createNotification(n);
		List<User> Seguidores = u.getFollowers();
		for (User seguidor : Seguidores) {
			Optional<User> s = userRepo.getUser(seguidor.getUsername());
			if (!s.get().addNotification(n)) {
				return false;
			}
			this.adaptadorUser.updateUser(s.get());
		}
		return true;
	}

	public boolean deletePhoto(Publication p) {
		if (this.getPublication(p.getTitle()) == null)
			return false;

		User usuario = this.getUser(p.getUser());
		usuario.deletePhoto(p);
		this.publRepo.removePublication(p);
		this.adaptadorUser.updateUser(usuario);
		return true;
	}

	public void uploadPhotosXML(String xmlPath) {
		CargadorFotos.getInstancia().setXML(xmlPath);
	}

	public Optional<Publication> getPublication(String titulo) {
		return this.publRepo.getPublication(titulo);
	}

	public DefaultListModel<Publication> getPhotosProfile(String user) {
		Optional<User> userOpt = this.userRepo.getUser(user);
		if (userOpt.isEmpty()) {
			return null;
		}
		User u = userOpt.get();
		List<Publication> publicacionesUs = publRepo.getAllPublicationsUser(u.getUsername());
		DefaultListModel<Publication> photosUser = new DefaultListModel<>();
		for (Publication p : publicacionesUs) {
			if (p instanceof Photo) {
				photosUser.addElement((Photo) p);
			}
		}
		return photosUser;
	}

	public DefaultListModel<Publication> getAlbumsProfile(String user) {
		Optional<User> userOpt = this.userRepo.getUser(user);
		if (userOpt.isEmpty()) {
			return null;
		}
		User u = userOpt.get();
		List<Publication> publicacionesUs = publRepo.getAllPublicationsUser(u.getUsername());
		DefaultListModel<Publication> albumsUser = new DefaultListModel<>();
		for (Publication p : publicacionesUs) {
			if (p instanceof Album) {
				albumsUser.addElement((Album) p);
			}
		}
		return albumsUser;
	}

	public boolean createAlbum(String user, String titulo, String descripcion, List<Publication> publicacionesAlbum) {
		User usuario = this.getUser(user);
		List<Hashtag> hashtags = new ArrayList<>();
		Matcher matcher = Constantes.HASHTAG_PAT.matcher(descripcion);
		while (matcher.find()) {
			Hashtag h = new Hashtag(matcher.group(2));
			adaptadorHashtag.createHashtag(h);
			hashtags.add(h);
		}
		List<File> fotos = new LinkedList<>();
		for (Publication p : publicacionesAlbum) {
			if (p instanceof Photo) {
				Photo ph = (Photo) p;
				fotos.add(new File(ph.getPath()));
			}
		}

		ImageIcon imgIcon = Utilities.getIconAlbum(fotos);
		BufferedImage icon = (BufferedImage) imgIcon.getImage();
		File output = new File("src/umu/tds/photos/albumIcon" + titulo + ".png");
		try {
			ImageIO.write(icon, "png", output);
		} catch (IOException e) {
			System.err.println("Error al guardar la imagen: " + e.getMessage());
		}
		Album a = usuario.createAlbum(titulo, descripcion, publicacionesAlbum, hashtags, output.getPath());
		this.addNotificacionFollowers(usuario, a);
		this.publRepo.createPublication(a);
		this.adaptadorUser.updateUser(usuario);
		return true;
	}

	public List<String> deleteEmptyAlbums(String user) {
		List<Album> albums = this.getUser(user).getAlbums();
		List<String> deletedAlbums = new LinkedList<>();
		for (Album album : albums) {
			if (album.getPhotos().isEmpty()) {
				deletedAlbums.add(album.getTitle());
				deleteAlbum(album);
			}
		}
		return deletedAlbums;
	}

	public boolean deleteAlbum(Album album) {
		User user = this.getUser(album.getUser());
		for (Photo p : album.getPhotos()) {
			List<Album> updatedAlbums = user.deletePhotoInAlbums(p);
			updatedAlbums.forEach(updatedA -> this.publRepo.updatePublication(updatedA));
			user.deletePhoto(p);
			this.publRepo.removePublication(p);
		}

		user.deleteAlbum(album);
		this.publRepo.removePublication(album);
		this.adaptadorUser.updateUser(user);
		return true;
	}

	public List<Publication> getAllPublications() {
		return new LinkedList<>(publRepo.getAllPublications());
	}

	public DefaultListModel<Photo> getAllPhotos(List<User> users) {
		DefaultListModel<Photo> p = new DefaultListModel<Photo>();
		for (User u : users) {
			for (Publication pub : u.getPublications()) {
				if (pub instanceof Photo) {
					p.addElement((Photo) pub);
				}
			}
		}

		return p;
	}

	public DefaultListModel<Photo> getAllPhotosFromDate(User user, Date fecha) {
		DefaultListModel<Photo> p = new DefaultListModel<Photo>();
		if (fecha == null) {
			return p;
		}

		for (Notification n : user.getNotifications()) {
			if (n.getPublication() instanceof Photo && n.getDate().after(fecha)) {
				p.addElement((Photo) n.getPublication());
			}
		}

		return p;
	}

	public List<Publication> getPublicationByHashtag(Hashtag hashtag) {
		return publRepo.getAllPublications().stream().filter(publication -> publication.getHashtags().contains(hashtag))
				.collect(Collectors.toList());
	}

	public void like(String user, Publication p) {
		p.addLike();
		User u = this.getUser(user);
		u.addLike(p);
		if (p instanceof Album) {
			Album a = (Album) p;
			for (Photo ph : a.getPhotos()) {
				if (!u.getLikedPublications().stream().anyMatch(lp -> lp.getCodigo() == ph.getCodigo())) {
					ph.addLike();
					u.addLike(ph);
					adaptadorPublication.updatePublication(ph);
				}
			}
		}
		adaptadorPublication.updatePublication(p);
		adaptadorUser.updateUser(u);
	}

	public void dislike(String user, Publication p) {
		p.removeLike();
		User u = this.getUser(user);
		u.removeLike(p);
		if (p instanceof Album) {
			Album a = (Album) p;
			for (Photo ph : a.getPhotos()) {
				if (u.getLikedPublications().stream().anyMatch(lp -> lp.getCodigo() == ph.getCodigo())) {
					ph.removeLike();
					u.removeLike(ph);
					adaptadorPublication.updatePublication(ph);
				}
			}
		}
		adaptadorPublication.updatePublication(p);
		adaptadorUser.updateUser(u);
	}

	public boolean userLikedPub(String user, Publication p) {
		User u = this.getUser(user);
		List<Publication> allLikedP = u.getLikedPublications();
		return allLikedP.stream().anyMatch(likedP -> likedP.getCodigo() == p.getCodigo());
	}

	public DefaultListModel<User> getUsersWhoLikedPublication(Publication publication) {
		DefaultListModel<User> usersWhoLikedPublication = new DefaultListModel<>();
		List<User> allUsers = userRepo.getAllUsers();
		for (User user : allUsers) {
			if (user.getLikedPublications().contains(publication)) {
				usersWhoLikedPublication.addElement(user);
			}
		}
		return usersWhoLikedPublication;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		Fotos fotos = MapperFotosXMLtoJava.cargarFotos(evt.getNewValue().toString());
		for (Foto f : fotos.getFoto()) {
			this.createPhoto(actualUser.get().getUsername(), f.getTitulo(), f.getDescripcion(),
					Utilities.guardarImagenRelativa(f.getPath()));
		}

	}
}
