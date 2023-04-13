package umu.tds.model;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import umu.tds.persistence.DAOException;
import umu.tds.persistence.DAOFactory;
import umu.tds.persistence.IAdaptadorUserDAO;

public class UserRepository {
	private Map<String, User> users;
	private static UserRepository uniqueInstance;

	private DAOFactory dao;
	private IAdaptadorUserDAO userAdapter;

	private UserRepository() {
		try {
			dao = DAOFactory.getInstancia(DAOFactory.DAO_TDS);
			userAdapter = dao.getUserDAO();
			users = new HashMap<String, User>();
			this.uploadRepository();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	// Comprobamos si el usuario existe por su username
	public boolean userExist(String username) {
		User user = users.get(username);
		if (user != null)
			return true;
		return false;
	}

	public User getUser(String username) {
		User user = users.get(username);
		if (user != null)
			return user;
		return null;
	}

	public boolean userExistEmail(String email) {
		for (User u : users.values()) {
			if (u.getEmail().equals(email))
				return true;
		}
		return false;
	}

	public User getUserFromEmail(String email) {
		for (User u : users.values()) {
			if (u.getEmail().equals(email))
				return u;
		}
		return null;
	}

	// Obtener la unica instancia de la clase ---> SINGLETONE
	public static UserRepository getInstancia() {
		if (uniqueInstance == null) {
			uniqueInstance = new UserRepository();
		}
		return uniqueInstance;
	}

	// Devolver todos los usuarios
	public List<User> getUser() {
		ArrayList<User> lista = new ArrayList<User>();
		for (User u : users.values())
			lista.add(u);
		return lista;
	}

	// Obtener uusario
	public User getUser(int codigo) {
		for (User u : users.values()) {
			if (u.getCodigo() == codigo)
				return u;
		}
		return null;
	}

	// A�adimos el usuario tanto a la lista de usuarios como al adaptador
	public void addUser(User user) {
		userAdapter.createUser(user);
		users.put(user.getUsername(), user);
	}

	// Actualizamos el repositorio
	private void uploadRepository() {
		List<User> userDB = userAdapter.readAllUsers();
		for (User u : userDB) {
			users.put(u.getUsername(), u);
		}

	}
}
