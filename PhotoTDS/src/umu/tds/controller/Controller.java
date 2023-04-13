package umu.tds.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import javax.swing.DefaultListModel;

import umu.tds.model.Photo;
import umu.tds.model.Publication;
import umu.tds.model.PublicationRepository;
import umu.tds.model.User;
import umu.tds.model.UserRepository;
import umu.tds.persistence.AdaptadorUserTDS;
import umu.tds.persistence.DAOException;
import umu.tds.persistence.DAOFactory;
import umu.tds.persistence.IAdaptadorPublicationDAO;
import umu.tds.persistence.IAdaptadorUserDAO;
import umu.tds.view.Constantes;
import umu.tds.view.StartWindow;

public class Controller {
	private static Controller unicaInstancia;

	private IAdaptadorUserDAO adaptadorUser;
	private IAdaptadorPublicationDAO adaptadorPublication;

	private UserRepository userRepo;
	private PublicationRepository publRepo;

	private Controller() {
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
		else if (user.get().getPassword().equals(password))
			return true;
		else
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
		List<User> allUsers = userRepo.getUser();
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

	public boolean updateUser(User user, String fullname, String username, String description, String profilePicPath) {
		user.setFullName(fullname);
		user.setUsername(username);
		user.setDescription(description);
		user.setProfilePic(profilePicPath);
		adaptadorUser.updateUser(user);
		return true;
	}

	public boolean updateUserSensibleInfo(User user, String email, String password) {
		user.setEmail(email);
		user.setPassword(password);
		adaptadorUser.updateUser(user);
		return true;
	}

	public boolean createPhoto(String user, String titulo, String descripcion, String path) {
		User usuario = this.getUser(user);
		Photo p = usuario.createPhoto(titulo, descripcion, path);
		this.publRepo.createPublication(p);
		this.adaptadorUser.updateUser(usuario);
		return true;
	}

	public Optional<Publication> getPublication(String titulo) {
		return this.publRepo.getPublication(titulo);
	}
	
	public List<Publication> getAllPublications(){
		return new LinkedList<>(publRepo.getAllPublications());
	}
}
