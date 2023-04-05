package umu.tds.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.model.Publication;
import umu.tds.model.User;

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
		} catch (NullPointerException e) {
		}

		if (eUser != null)
			return;

		// Registrar primero atributos que son objetos (esto se hace con los atributos
		// del usuario, cuando esté el resto de adaptadores)

		eUser = new Entidad();
		eUser.setNombre("user");
		eUser.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("email", user.getEmail()), new Propiedad("fullName", user.getFullName()),
						new Propiedad("username", user.getUsername()), new Propiedad("password", user.getPassword()),
						new Propiedad("birthDay", new SimpleDateFormat("dd/MM/yyyy").format(user.getBirthDay())),
						new Propiedad("isPremium", String.valueOf(user.isPremium())),
						new Propiedad("usersFollowing", obtenerCodigosUsuarios(user.getUsersFollowing())),
						new Propiedad("usersFollowed", obtenerCodigosUsuarios(user.getUsersFollowed())),
						new Propiedad("publications", obtenerCodigosPublicaciones(user.getPublications())))));

		// registrar entidad cliente
		eUser = serverPersistencia.registrarEntidad(eUser);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		user.setCodigo(eUser.getId());
	}

	public User readUser(int userCode) {
		// Si la entidad está en el pool, la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(userCode))
			return (User) PoolDAO.getUnicaInstancia().getObjeto(userCode);

		// Si no, la recupera de la base de datos
		Entidad eUser;
		String username;
		String email;
		String password;
		String fullName;
		String birthDayStr;
		String isPremium;
		String usersFollowing;
		String usersFollowed;
		String publications;

		// recuperar entidad
		eUser = serverPersistencia.recuperarEntidad(userCode);

		// recuperar propiedades que no son objetos
		email = serverPersistencia.recuperarPropiedadEntidad(eUser, "email");
		fullName = serverPersistencia.recuperarPropiedadEntidad(eUser, "fullName");
		username = serverPersistencia.recuperarPropiedadEntidad(eUser, "username");
		password = serverPersistencia.recuperarPropiedadEntidad(eUser, "password");
		birthDayStr = serverPersistencia.recuperarPropiedadEntidad(eUser, "birthDay");
		isPremium = serverPersistencia.recuperarPropiedadEntidad(eUser, "isPremium");
		usersFollowing = serverPersistencia.recuperarPropiedadEntidad(eUser, "usersFollowing");
		usersFollowed = serverPersistencia.recuperarPropiedadEntidad(eUser, "usersFollowed");
		publications = serverPersistencia.recuperarPropiedadEntidad(eUser, "publications");
		
		System.out.println("email: " + email);
		System.out.println("fullName: " + fullName);
		System.out.println("username: " + username);
		System.out.println("password: " + password);
		System.out.println("birthDayStr: " + birthDayStr);
		System.out.println("isPremium: " + isPremium);
		System.out.println("usersFollowing: " + usersFollowing);
		System.out.println("usersFollowed: " + usersFollowed);
		System.out.println("publications: " + publications);
		

		Date birthDay = new Date();
		try {
			birthDay = new SimpleDateFormat("dd/MM/yyyy").parse(birthDayStr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		User user = new User(username, email, password, fullName, birthDay, Boolean.parseBoolean(isPremium));
		user.setCodigo(userCode);

		// IMPORTANTE: añadir el usuario al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(userCode, user);
		
		user.setUsersFollowing(obtenerUsersDesdeCodigos(usersFollowing));
		user.setUsersFollowed(obtenerUsersDesdeCodigos(usersFollowed));
		user.setPublications(obtenerPublicationsDesdeCodigos(publications));

		return user;

	}

	public void updateUser(User user) {
		Entidad eUser = serverPersistencia.recuperarEntidad(user.getCodigo());
		for (Propiedad p : eUser.getPropiedades()) {
			if (p.getNombre().equals("email")) {
				p.setValor(user.getEmail());
			} else if (p.getNombre().equals("fullName")) {
				p.setValor(user.getFullName());
			} else if (p.getNombre().equals("username")) {
				p.setValor(user.getUsername());
			} else if (p.getNombre().equals("password")) {
				p.setValor(user.getPassword());
			} else if (p.getNombre().equals("birthDay")) {
				p.setValor(new SimpleDateFormat("dd/MM/yyyy").format(user.getBirthDay()));
			} else if (p.getNombre().equals("isPremium")) {
				p.setValor(String.valueOf(user.isPremium()));
			} else if (p.getNombre().equals("usersFollowing")) {
				p.setValor(obtenerCodigosUsuarios(user.getUsersFollowing()));
			} else if (p.getNombre().equals("usersFollowed")) {
				p.setValor(obtenerCodigosUsuarios(user.getUsersFollowed()));
			} else if (p.getNombre().equals("publications")) {
				p.setValor(obtenerCodigosPublicaciones(user.getPublications()));
			}
			serverPersistencia.modificarPropiedad(p);
		}
	}

	public void deleteUser(User user) {
		Entidad eUser = serverPersistencia.recuperarEntidad(user.getCodigo());
		serverPersistencia.borrarEntidad(eUser);
	}

	public List<User> readAllUsers() {
		List<Entidad> eUsers = serverPersistencia.recuperarEntidades("user");
		List<User> users = new LinkedList<User>();

		for (Entidad eUser : eUsers) {
			users.add(readUser(eUser.getId()));
		}
		return users;
	}

	private String obtenerCodigosUsuarios(List<User> users) {
		String lineas = "";
		for (User user : users) {
			lineas += user.getCodigo() + " ";
		}
		return lineas.trim();
	}

	private String obtenerCodigosPublicaciones(List<Publication> publications) {
		String lineas = "";
		for (Publication publication : publications) {
			lineas += publication.getCodigo() + " ";
		}
		return lineas.trim();
	}

	private List<User> obtenerUsersDesdeCodigos(String users) {
		List<User> userList = new LinkedList<User>();
		StringTokenizer strTok = new StringTokenizer(users, " ");
		AdaptadorUserTDS adaptadorUser = AdaptadorUserTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			userList.add(adaptadorUser.readUser(Integer.valueOf((String) strTok.nextElement())));
		}
		return userList;
	}
	
	private List<Publication> obtenerPublicationsDesdeCodigos(String publications) {
		List<Publication> publicationList = new LinkedList<Publication>();
		StringTokenizer strTok = new StringTokenizer(publications, " ");
		AdaptadorPublicationTDS adaptadorPublication = AdaptadorPublicationTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			publicationList.add(adaptadorPublication.readPublication(Integer.valueOf((String) strTok.nextElement())));
		}
		return publicationList;
	}
}
