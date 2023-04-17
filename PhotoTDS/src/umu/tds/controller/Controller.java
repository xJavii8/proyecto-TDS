package umu.tds.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;

import umu.tds.model.Comment;
import umu.tds.model.Photo;
import umu.tds.model.Publication;
import umu.tds.model.PublicationRepository;
import umu.tds.model.User;
import umu.tds.model.UserRepository;
import umu.tds.persistence.DAOException;
import umu.tds.persistence.DAOFactory;
import umu.tds.persistence.IAdaptadorPublicationDAO;
import umu.tds.persistence.IAdaptadorUserDAO;
import umu.tds.view.Constantes;

import umu.tds.fotos.CargadorFotos;
import umu.tds.fotos.MapperFotosXMLtoJava;
import umu.tds.fotos.Fotos;
import umu.tds.fotos.Foto;


public class Controller implements PropertyChangeListener {
	private static Controller unicaInstancia;

	private IAdaptadorUserDAO adaptadorUser;
	private IAdaptadorPublicationDAO adaptadorPublication;

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

		User user = new User(username, email, password, fullname, birthday, profilePic, description);
		userRepo.addUser(user);

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

	public String getProfilePicPath(String username) {
		Matcher emailMatch = Constantes.EMAIL_PAT.matcher(username);
		Optional<User> user;
		if (emailMatch.matches())
			user = userRepo.getUserFromEmail(username);
		else
			user = userRepo.getUser(username);

		if (user.isEmpty())
			return "";
		else
			return user.get().getProfilePic();
	}

	public boolean isPremium(String username) {
		return userRepo.getUser(username).get().isPremium();
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
	
	public List<Publication> getTop10LikedPhotos(String username) {
		Optional<User> userO = userRepo.getUser(username);
		if (userO.isEmpty())
			return null;

		User user = userO.get();
		return user.getPublications().stream()
			    .sorted(Comparator.comparing(Publication::getLikes).reversed())
			    .limit(Constantes.TOP_LIKED_PHOTOS_PREMIUM)
			    .collect(Collectors.toList());
		
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
		Optional<User> userO= userRepo.getUser(username);
		if(userO.isEmpty())
			return false;
		
		User user = userO.get();
		if (!user.isPremium())
			return false;

		PDFGen.genPDF(user, user.getFollowers(), path);

		return true;
	}

	public int getNumFollowers(String username) {
		Optional<User> userO= userRepo.getUser(username);
		if(userO.isEmpty())
			return 0;
		
		User user = userO.get();
		return user.getFollowers().size();
	}

	public int getNumUsersFollowing(String username) {
		Optional<User> userO= userRepo.getUser(username);
		if(userO.isEmpty())
			return 0;
		
		User user = userO.get();
		return user.getUsersFollowing().size();
	}

	public int getNumPublications(String username) {
		Optional<User> userO= userRepo.getUser(username);
		if(userO.isEmpty())
			return 0;
		
		User user = userO.get();
		return user.getPublications().size();
	}

	public User getUser(String username) {
		Optional<User> userO = userRepo.getUser(username);
		if(userO.isEmpty())
			return null;
		
		return userO.get();
	}

	public boolean userIsFollower(String selfUsername, String usernameSearched) {
		User userSearched = userRepo.getUser(usernameSearched).get();
		return userSearched.getFollowers().stream()
	            .anyMatch(user -> user.getUsername().equals(selfUsername));
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
	        matchingUsers.addAll(allUsers.stream()
	                .filter(u -> u.getEmail().equals(searchString))
	                .collect(Collectors.toList()));
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
	
	public DefaultListModel<User> getFollowingUsers(String username) {
		User user = userRepo.getUser(username).get();
		DefaultListModel<User> users = new DefaultListModel<>();
		List<User> followingUsers = user.getUsersFollowing();
		for(User u : followingUsers)
			users.addElement(u);
		return users;
	}
	
	public DefaultListModel<User> getFollowers(String username) {
		User user = userRepo.getUser(username).get();
		DefaultListModel<User> users = new DefaultListModel<>();
		List<User> followers = user.getFollowers();
		for(User u : followers)
			users.addElement(u);
		return users;
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
	
	public boolean removeComment (Publication publ, Comment comment) {
		Optional<Comment> com =  publ.getComments().stream()
		.filter(f -> f.getAuthor().equals(comment.getAuthor()))
		.filter(f -> f.getText().equals(comment.getText()))
		.filter(f -> f.getPublishedDate().equals(comment.getPublishedDate()))
		.findFirst();
		
		if (com.isEmpty()) {
			return false;
		}
		
		return publ.removeComment(comment);
	}
	
	
	public boolean createPhoto(String user, String titulo, String descripcion, String path) {
		User usuario = this.getUser(user);
		Photo p = usuario.createPhoto(titulo, descripcion, path);
		this.publRepo.createPublication(p);
		this.adaptadorUser.updateUser(usuario);
		return true;
	}
	
	public boolean deletePhoto(Publication p) {
		if(this.getPublication(p.getTitle()) == null)
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
	
	public List<Photo> getPhothosProfile (String user){
		Optional<User> userOpt = this.userRepo.getUser(user);
		if (userOpt.isEmpty()) {
			return null;
		}
		User u = userOpt.get();
		List<Publication> publicacionesUs = publRepo.getAllPublicationsUser(u.getUsername());
		List<Photo> photosUser = new LinkedList<>();
		for (Publication p : publicacionesUs) {
			if (p instanceof Photo) {
				photosUser.add((Photo) p);
			}
		}
		return photosUser;
	}
	
	public List<Publication> getAllPublications(){
		return new LinkedList<>(publRepo.getAllPublications());
	}
	
	
	
	public void like(String user, Publication p) {
		p.addLike();
		User u = this.getUser(user);
		u.addLike(p);
		adaptadorPublication.updatePublication(p);
		adaptadorUser.updateUser(u);
	}
	
	public void dislike(String user, Publication p) {
		p.removeLike();
		User u = this.getUser(user);
		u.removeLike(p);
		adaptadorPublication.updatePublication(p);
		adaptadorUser.updateUser(u);
	}
	
	public boolean userLikedPub(String user, Publication p) {
		User u = this.getUser(user);
		List<Publication> allLikedP = u.getLikedPublications();
		for(Publication likedP : allLikedP) {
			if(likedP.equals(p)) {
				return true;
			}
		}
		return false;
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
		for(Foto f : fotos.getFoto()) {
			this.createPhoto(actualUser.get().getUsername(), f.getTitulo(), f.getDescripcion(), f.getPath());
		}
		
	}
}
