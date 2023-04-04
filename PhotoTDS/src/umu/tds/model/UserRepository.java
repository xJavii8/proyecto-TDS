package umu.tds.model;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import umu.tds.persistence.DAOException;
import umu.tds.persistence.DAOFactory;
import umu.tds.persistence.IAdaptadorUserDAO;
import umu.tds.persistence.DAOFactory;


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

	

	// Obtener la única instancia de la clase ---> SINGLETONE
	public static UserRepository getUniqueInstance() {
		if (uniqueInstance == null) {
			uniqueInstance = new UserRepository();
		}
		return uniqueInstance;
	}

	// Devolver todos los clientes
	public List<User> getClientes() {
		ArrayList<User> lista = new ArrayList<User>();
		for (User c : users.values())
			lista.add(c);
		return lista;
	}

	// Obtener cliente
	public User getUser(int codigo) {
		for (User c : users.values()) {
			if (c.getCodigo() == codigo)
				return c;
		}
		return null;
	}

	private void uploadRepository() {
		List<User> userDB = userAdapter.readAllUsers();
		for (User u : userDB) {
			users.put(u.getFullName(), u);
		}
		
	}
}
