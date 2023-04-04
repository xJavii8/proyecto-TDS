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

public class AdaptadorPublicationTDS implements IAdaptadorPublicationDAO {
	private static ServicioPersistencia serverPersistencia;
	private static AdaptadorPublicationTDS unicaInstancia = null;

	public static AdaptadorPublicationTDS getUnicaInstancia() {
		if (unicaInstancia == null) {
			return new AdaptadorPublicationTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorPublicationTDS() {
		serverPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public void createPublication(Publication publication) {
		Entidad ePublication = null;

		try {
			ePublication = serverPersistencia.recuperarEntidad(publication.getCodigo());
		} catch (NullPointerException e) {
		}

		if (ePublication != null)
			return;

		// Registrar primero atributos que son objetos (esto se hace con los atributos
		// del usuario, cuando esté el resto de adaptadores)

		ePublication = new Entidad();
		ePublication.setNombre("publication");
		ePublication.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("title", publication.getTitle()), 
						new Propiedad("datePublication", new SimpleDateFormat("dd/MM/yyyy").format(publication.getDatePublication())), 
						new Propiedad("description", publication.getDescription()),
						new Propiedad("likes", String.valueOf(publication.getLikes())),
						//new Propiedad("hashtags", obtenerCodigosUsuarios(publication.getUsersFollowing())),
						//new Propiedad("comments", obtenerCodigosUsuarios(publication.getUsersFollowed()))))));

		// registrar entidad cliente
		ePublication = serverPersistencia.registrarEntidad(ePublication);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		publication.setCodigo(ePublication.getId());
	}

	public Publication readPublication(int publicationCode) {
		// Si la entidad está en el pool, la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(publicationCode))
			return (Publication) PoolDAO.getUnicaInstancia().getObjeto(publicationCode);

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
		eUser = serverPersistencia.recuperarEntidad(publicationCode);

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

		Date birthDay = new Date();
		try {
			birthDay = new SimpleDateFormat("dd/MM/yyyy").parse(birthDayStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		User user = new User(username, email, password, fullName, birthDay, Boolean.parseBoolean(isPremium));
		user.setCodigo(publicationCode);

		// IMPORTANTE: añadir el usuario al pool antes de llamar a otros
		// adaptadores
		PoolDAO.getUnicaInstancia().addObjeto(publicationCode, user);
		
		user.setUsersFollowing(obtenerUsersDesdeCodigos(usersFollowing));
		user.setUsersFollowed(obtenerUsersDesdeCodigos(usersFollowed));
		user.setPublications(obtenerPublicationsDesdeCodigos(publications));

		return user;

	}

	public void updatePublication(Publication publication) {
		Entidad ePublication = serverPersistencia.recuperarEntidad(publication.getCodigo());
		for (Propiedad p : ePublication.getPropiedades()) {
			if (p.getNombre().equals("email")) {
				p.setValor(publication.getEmail());
			} else if (p.getNombre().equals("fullName")) {
				p.setValor(publication.getFullName());
			} else if (p.getNombre().equals("username")) {
				p.setValor(publication.getUsername());
			} else if (p.getNombre().equals("password")) {
				p.setValor(publication.getPassword());
			} else if (p.getNombre().equals("birthDay")) {
				p.setValor(new SimpleDateFormat("dd/MM/yyyy").format(publication.getBirthDay()));
			} else if (p.getNombre().equals("isPremium")) {
				p.setValor(String.valueOf(publication.isPremium()));
			} else if (p.getNombre().equals("usersFollowing")) {
				p.setValor(obtenerCodigosUsuarios(publication.getUsersFollowing()));
			} else if (p.getNombre().equals("usersFollowed")) {
				p.setValor(obtenerCodigosUsuarios(publication.getUsersFollowed()));
			} else if (p.getNombre().equals("publications")) {
				p.setValor(obtenerCodigosPublicaciones(publication.getPublications()));
			}
			serverPersistencia.modificarPropiedad(p);
		}
	}

	public void deletePublication(Publication publication) {
		Entidad ePublication = serverPersistencia.recuperarEntidad(publication.getCodigo());
		serverPersistencia.borrarEntidad(ePublication);
	}

	public List<Publication> readAllPublications() {
		List<Entidad> ePublications = serverPersistencia.recuperarEntidades("user");
		List<Publication> publications = new LinkedList<Publication>();

		for (Entidad ePublication : ePublications) {
			publications.add(readPublication(ePublication.getId()));
		}
		return publications;
	}
}
