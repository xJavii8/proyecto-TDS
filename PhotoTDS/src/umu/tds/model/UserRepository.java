package umu.tds.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import umu.tds.persistence.DAOException;
import umu.tds.persistence.DAOFactory;
import umu.tds.persistence.IAdaptadorUserDAO;

public enum UserRepository {
	INSTANCE;
	
	private Map<String, User> users;
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
		return Optional.ofNullable(users.get(username)).isPresent();
	}

	public Optional<User> getUser(String username) {
		return Optional.ofNullable(users.get(username));
	}

	public boolean userExistEmail(String email) {
		return users.values().stream().anyMatch(u -> u.getEmail().equals(email));
	}

	public Optional<User> getUserFromEmail(String email) {
		return users.values().stream().filter(u -> u.getEmail().equals(email)).findFirst();
	}

	// Devolver todos los usuarios
	public List<User> getAllUsers() {
		ArrayList<User> lista = new ArrayList<User>();
		for (User u : users.values())
			lista.add(u);
		return lista;
	}

	// Obtener uusario
	public Optional<User> getUser(int codigo) {
		return users.values().stream().filter(u -> u.getCodigo() == codigo).findFirst();
	}

	// A�adimos el usuario tanto a la lista de usuarios como al adaptador
	public void addUser(User user) {
		userAdapter.createUser(user);
		users.put(user.getUsername(), user);
	}

	// Borramos el usuario tanto en el adaptador como en la lista
	public void removeUser(User user) {
		userAdapter.deleteUser(user);
		users.remove(user.getUsername());
	}

	public void editUser(String oldUsername, User newUser) {
		users.remove(oldUsername);
		users.put(newUser.getUsername(), newUser);
		userAdapter.updateUser(newUser);
	}

	// Actualizamos el repositorio
	private void uploadRepository() {
		List<User> userDB = userAdapter.readAllUsers();
		for (User u : userDB) {
			users.put(u.getUsername(), u);
		}

	}
}
