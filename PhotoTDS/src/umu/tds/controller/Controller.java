package umu.tds.controller;

import java.util.Date;

import umu.tds.model.User;
import umu.tds.model.UserRepository;
import umu.tds.persistence.AdaptadorUserTDS;
import umu.tds.persistence.DAOException;
import umu.tds.persistence.DAOFactory;
import umu.tds.persistence.IAdaptadorUserDAO;

public class Controller {
	private static Controller unicaInstancia;

	private IAdaptadorUserDAO adaptadorUser;

	private UserRepository userRepo;

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
	}

	private void inicializarRepos() {
		userRepo = UserRepository.getInstancia();
	}

	public boolean createUser(String email, String fullname, String username, String password, Date birthday,
			String description) {
		boolean userExist = userRepo.userExist(username);

		if (userExist == true)
			return false;
		
		User user = new User(username, email, password, fullname, birthday, userExist);
		userRepo.addUser(user);

		return true;
	}

}
