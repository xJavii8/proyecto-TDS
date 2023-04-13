package umu.tds.controller;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

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

	public boolean login(String username, String password, boolean isEmail) {

		boolean userExist = false;
		if (isEmail)
			userExist = userRepo.userExistEmail(username);
		else
			userExist = userRepo.userExist(username);

		if (userExist == false)
			return false;

		User user;

		if (isEmail)
			user = userRepo.getUserFromEmail(username);
		else
			user = userRepo.getUser(username);

		if (user.getPassword().equals(password))
			return true;
		else
			return false;
	}

	public String getProfilePicPath(String username, boolean isEmail) {
		String profilePic;

		if (isEmail)
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

		userRepo.updateUser(selfUser);
		userRepo.updateUser(searchedUser);
		return true;
	}

	public boolean unfollow(String selfUsername, String usernameSearched) {
		User selfUser = userRepo.getUser(selfUsername);
		User searchedUser = userRepo.getUser(usernameSearched);

		selfUser.removeUserFollowing(searchedUser);
		searchedUser.removeUserFollower(selfUser);
		userRepo.updateUser(selfUser);
		userRepo.updateUser(searchedUser);
		return true;
	}

	public DefaultListModel<User> searchByUsername(String selfUsername, String username) {
		User selfUser = userRepo.getUser(selfUsername);
		DefaultListModel<User> matchingUsers = new DefaultListModel<>();
		List<User> allUsers = userRepo.getUser();
		for (User u : allUsers) {
			if (u.getUsername().startsWith(username) || u.getUsername().equals(username)) {
				matchingUsers.addElement(u);
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
		userRepo.updateUser(user);
		return true;
	}

	public boolean updateUserSensibleInfo(User user, String email, String password) {
		user.setEmail(email);
		user.setPassword(password);
		userRepo.updateUser(user);
		return true;
	}

	public boolean createPhoto(String user, String titulo, String descripcion, String path) {
		User usuario = this.getUser(user);
		Photo p = usuario.createPhoto(titulo, descripcion, path);
		System.out.println("Photo creada");
		this.publRepo.createPublication(p);
		System.out.println("Publicación creada");
		this.userRepo.updateUser(usuario);
		System.out.println("Usuario actualizado");
		return true;
	}

	public Optional<Publication> getPublication(String titulo) {
		return this.publRepo.getPublication(titulo);
	}
}
