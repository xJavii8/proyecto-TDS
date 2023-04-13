package umu.tds.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;

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

		boolean userExist = false;
		Matcher emailMatch = StartWindow.EMAIL_PAT.matcher(username);
		if (emailMatch.matches())
			userExist = userRepo.userExistEmail(username);
		else
			userExist = userRepo.userExist(username);

		if (userExist == false)
			return false;

		User user;

		if (emailMatch.matches())
			user = userRepo.getUserFromEmail(username);
		else
			user = userRepo.getUser(username);

		if (user.getPassword().equals(password))
			return true;
		else
			return false;
	}

	public String getProfilePicPath(String username) {
		String profilePic;
		Matcher emailMatch = StartWindow.EMAIL_PAT.matcher(username);
		if (emailMatch.matches())
			profilePic = userRepo.getUserFromEmail(username).getProfilePic();
		else
			profilePic = userRepo.getUser(username).getProfilePic();

		return profilePic;
	}

	public boolean isPremium(String username) {
		return userRepo.getUser(username).isPremium();
	}

	public boolean setPremium(String username) {
		User user = userRepo.getUser(username);
		user.setPremium(true);
		adaptadorUser.updateUser(user);
		return true;
	}

	public boolean createExcel(String username, String path) {
		User user = userRepo.getUser(username);
		if (!user.isPremium())
			return false;

		ExcelGen.genExcel(user, user.getFollowers(), path);

		return true;
	}

	public boolean createPDF(String username, String path) {
		User user = userRepo.getUser(username);
		if (!user.isPremium())
			return false;

		PDFGen.genPDF(user, user.getFollowers(), path);

		return true;
	}

	public int getNumFollowers(String username) {
		User user = userRepo.getUser(username);
		return user.getFollowers().size();
	}

	public int getNumUsersFollowing(String username) {
		User user = userRepo.getUser(username);
		return user.getUsersFollowing().size();
	}

	public int getNumPublications(String username) {
		User user = userRepo.getUser(username);
		return user.getPublications().size();
	}

	public User getUser(String username) {
		return userRepo.getUser(username);
	}

	public boolean userIsFollower(String selfUsername, String usernameSearched) {
		User userSearched = userRepo.getUser(usernameSearched);
		for (User user : userSearched.getFollowers()) {
			if (user.getUsername().equals(selfUsername))
				return true;
		}
		return false;
	}

	public boolean follow(String selfUsername, String usernameSearched) {
		User selfUser = userRepo.getUser(selfUsername);
		User searchedUser = userRepo.getUser(usernameSearched);

		selfUser.addUserFollowing(searchedUser);
		searchedUser.addUserFollower(selfUser);

		adaptadorUser.updateUser(selfUser);
		adaptadorUser.updateUser(searchedUser);
		return true;
	}

	public boolean unfollow(String selfUsername, String usernameSearched) {
		User selfUser = userRepo.getUser(selfUsername);
		User searchedUser = userRepo.getUser(usernameSearched);

		selfUser.removeUserFollowing(searchedUser);
		searchedUser.removeUserFollower(selfUser);
		adaptadorUser.updateUser(selfUser);
		adaptadorUser.updateUser(searchedUser);
		return true;
	}

	public DefaultListModel<User> search(String selfUsername, String searchString) {
		User selfUser = userRepo.getUser(selfUsername);
		DefaultListModel<User> matchingUsers = new DefaultListModel<>();
		List<User> allUsers = userRepo.getUser();
		Matcher emailMatch = StartWindow.EMAIL_PAT.matcher(searchString);
		Matcher fullnameMatch = StartWindow.FULLNAME_PAT.matcher(searchString);
		if (emailMatch.matches()) {
			for (User u : allUsers) {
				if (u.getEmail().equals(searchString)) {
					matchingUsers.addElement(u);
				}
			}
		} else if (fullnameMatch.matches()) {
			for (User u : allUsers) {
				if (u.getFullName().startsWith(searchString) || u.getFullName().equals(searchString)) {
					matchingUsers.addElement(u);
				}
			}
		} else {
			for (User u : allUsers) {
				if (u.getUsername().startsWith(searchString) || u.getUsername().equals(searchString)) {
					matchingUsers.addElement(u);
				}
			}
		}

		if (matchingUsers.contains(selfUser))
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
}
