package umu.tds.persistence;

import java.util.List;

import beans.Entidad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorUserTDS implements IAdaptadorUserDAO {
	private static ServicioPersistencia serverPersistencia;
	private static AdaptadorUserTDS unicaInstancia = null;

	public static AdaptadorUserTDS getUnicaInstancia() {
		if (unicaInstancia == null) {
			return new AdaptadorUserTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorUserTDS() {
		serverPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public void createUser(User user) {
		Entidad eUser = null;
		
		try {
			eUser = serverPersistencia.recuperarEntidad(user.getCodigo());
		} catch (NullPointerException e) {}
		
		if(eUser != null) return;
		
		// Registrar primero atributos que son objetos
	}

	public void readUser(int userCode) {

	}

	public void updateUser(User user) {

	}

	public void deleteUser(User user) {

	}

	public List<User> readAllUsers() {

	}
}
