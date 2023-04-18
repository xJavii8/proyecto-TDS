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
import umu.tds.model.Album;
import umu.tds.model.Comment;
import umu.tds.model.Photo;
import umu.tds.model.Publication;
import umu.tds.model.User;
import umu.tds.view.Utilities;

public class AdaptadorPublicationTDS implements IAdaptadorPublicationDAO {
	private static ServicioPersistencia serverPersistencia;
	private static AdaptadorPublicationTDS unicaInstancia = null;
	private static AdaptadorCommentTDS adaptadorComment = null;

	public static AdaptadorPublicationTDS getUnicaInstancia() {
		if (unicaInstancia == null) {
			return new AdaptadorPublicationTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorPublicationTDS() {
		serverPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		adaptadorComment = AdaptadorCommentTDS.getUnicaInstancia();
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
		// del usuario, cuando est� el resto de adaptadores)
		ePublication = new Entidad();
		if (publication instanceof Photo) {
			ePublication.setNombre("photo");
			ePublication.setPropiedades(
					new ArrayList<Propiedad>(Arrays.asList(new Propiedad("title", publication.getTitle()),
							new Propiedad("datePublication", Utilities.dateToString(publication.getDatePublication())),
							new Propiedad("description", publication.getDescription()),
							new Propiedad("likes", String.valueOf(publication.getLikes())),
							new Propiedad("path", ((Photo) publication).getPath()),
							new Propiedad("user", publication.getUser()),
							new Propiedad("comentarios", obtenerCodigosComentarios(publication.getComments()))
					// new Propiedad("hashtags", );
					)));
		} else if (publication instanceof Album) {
			((Album) publication).getPhotos().stream().forEach(f -> getUnicaInstancia().createPublication(publication));
			ePublication.setNombre("album");
			ePublication.setPropiedades(
					new ArrayList<Propiedad>(Arrays.asList(new Propiedad("title", publication.getTitle()),
							new Propiedad("datePublication", Utilities.dateToString(publication.getDatePublication())),
							new Propiedad("description", publication.getDescription()),
							new Propiedad("likes", String.valueOf(publication.getLikes())),
							new Propiedad("photos", obtenerCodigosPhotos(((Album) publication).getPhotos())),
							new Propiedad("user", publication.getUser()),
							new Propiedad("comentarios", obtenerCodigosComentarios(publication.getComments()))
					// new Propiedad("hashtags", );
					)));
		}

		// registrar entidad cliente
		ePublication = serverPersistencia.registrarEntidad(ePublication);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		publication.setCodigo(ePublication.getId());
	}

	public Publication readPublication(int publicationCode) {
		// Si la entidad esta en el pool, la devuelve directamente
		if (PoolDAO.getUnicaInstancia().contiene(publicationCode)) {
			Object ob = PoolDAO.getUnicaInstancia().getObjeto(publicationCode);
			if (ob instanceof Photo) {
				return (Photo) ob;
			} else if (ob instanceof Album) {
				return (Album) ob;
			}
		}

		Entidad ePublication;
		ePublication = serverPersistencia.recuperarEntidad(publicationCode);
		if (ePublication.getNombre().equals("photo"))
			return readPhoto(publicationCode);
		else if (ePublication.getNombre().equals("album"))
			return readAlbum(publicationCode);
		else
			return null;
	}

	private Photo readPhoto(int codigo) {
		Entidad ePublication;

		String title;
		String datePublication;
		String description;
		String likes;
		String user;
		String path;

		String comments;
		// String hashtags

		// recuperar entidad
		ePublication = serverPersistencia.recuperarEntidad(codigo);
		title = serverPersistencia.recuperarPropiedadEntidad(ePublication, "title");
		datePublication = serverPersistencia.recuperarPropiedadEntidad(ePublication, "datePublication");
		description = serverPersistencia.recuperarPropiedadEntidad(ePublication, "description");
		likes = serverPersistencia.recuperarPropiedadEntidad(ePublication, "likes");
		path = serverPersistencia.recuperarPropiedadEntidad(ePublication, "path");
		user = serverPersistencia.recuperarPropiedadEntidad(ePublication, "user");
		comments = serverPersistencia.recuperarPropiedadEntidad(ePublication, "comentarios");

		// Hay que pasar la string a fecha
		Photo p = new Photo(title, Utilities.stringToDate(datePublication), description, Integer.parseInt(likes), path,
				user, obtenerComentariosDesdeCodigos(comments));
		p.setCodigo(codigo);

		PoolDAO.getUnicaInstancia().addObjeto(codigo, p);

		return p;

	}

	private Album readAlbum(int codigo) {
		Entidad ePublication;

		String title;
		String datePublication;
		String description;
		String likes;
		String user;
		String path;

		String photos;

		String comments;
		// String hashtags

		// recuperar entidad
		ePublication = serverPersistencia.recuperarEntidad(codigo);

		// recuperar propiedades que no son objetos
		title = serverPersistencia.recuperarPropiedadEntidad(ePublication, "title");
		datePublication = serverPersistencia.recuperarPropiedadEntidad(ePublication, "datePublication");
		description = serverPersistencia.recuperarPropiedadEntidad(ePublication, "description");
		likes = serverPersistencia.recuperarPropiedadEntidad(ePublication, "likes");
		path = serverPersistencia.recuperarPropiedadEntidad(ePublication, "path");
		photos = serverPersistencia.recuperarPropiedadEntidad(ePublication, "photos");
		user = serverPersistencia.recuperarPropiedadEntidad(ePublication, "user");

		Album p = new Album(title, Utilities.stringToDate(datePublication), description, Integer.parseInt(likes), user);
		p.setCodigo(codigo);

		PoolDAO.getUnicaInstancia().addObjeto(codigo, p);

		return p;

	}

	public void updatePublication(Publication publication) {
		Entidad ePublicacion = serverPersistencia.recuperarEntidad(publication.getCodigo());

		if (ePublicacion.getNombre().equals("photo")) {
			updatePhoto((Photo) publication);
		} else if (ePublicacion.getNombre().equals("album")) {
			updateAlbum((Album) publication);
		} else {
			System.err.println("La publicación ni es una foto ni un album");
		}
	}

	private void updatePhoto(Photo p) {
		Entidad ePhoto = serverPersistencia.recuperarEntidad(p.getCodigo());

		for (Propiedad pro : ePhoto.getPropiedades()) {
			if (pro.getNombre().equals("tittle")) {
				pro.setValor(String.valueOf(p.getTitle()));
			} else if (pro.getNombre().equals("datePublication")) {
				pro.setValor(Utilities.dateToString(p.getDatePublication()));
			} else if (pro.getNombre().equals("description")) {
				pro.setValor(String.valueOf(p.getDescription()));
			} else if (pro.getNombre().equals("likes")) {
				pro.setValor(String.valueOf(p.getLikes()));
			} else if (pro.getNombre().equals("user")) {
				pro.setValor(p.getUser());
			} else if (pro.getNombre().equals("path")) {
				pro.setValor(String.valueOf(p.getPath()));
			} else if (pro.getNombre().equals("comentarios")) {
				p.getComments().stream().forEach(c -> adaptadorComment.createComment(c));
				pro.setValor(obtenerCodigosComentarios(p.getComments()));
			}
			serverPersistencia.modificarPropiedad(pro);
		}
	}

	private void updateAlbum(Album a) {
		Entidad eAlbum = serverPersistencia.recuperarEntidad(a.getCodigo());

		for (Propiedad pro : eAlbum.getPropiedades()) {
			if (pro.getNombre().equals("tittle")) {
				pro.setValor(String.valueOf(a.getTitle()));
			} else if (pro.getNombre().equals("datePublication")) {
				pro.setValor(Utilities.dateToString(a.getDatePublication()));
			} else if (pro.getNombre().equals("description")) {
				pro.setValor(String.valueOf(a.getDescription()));
			} else if (pro.getNombre().equals("likes")) {
				pro.setValor(String.valueOf(a.getLikes()));
			} else if (pro.getNombre().equals("user")) {
				pro.setValor(a.getUser());
			} else if (pro.getNombre().equals("fotos")) {
				pro.setValor(obtenerCodigosPhotos(a.getPhotos()));
			}
			serverPersistencia.modificarPropiedad(pro);
		}
	}

	public void deletePublication(Publication publication) {
		Entidad ePublication = serverPersistencia.recuperarEntidad(publication.getCodigo());
		serverPersistencia.borrarEntidad(ePublication);
	}

	public List<Publication> readAllPublications() {
		List<Entidad> ePhotos = serverPersistencia.recuperarEntidades("photo");
		List<Entidad> eAlbums = serverPersistencia.recuperarEntidades("album");
		List<Entidad> ePublications = new LinkedList<Entidad>(ePhotos);
		ePublications.addAll(eAlbums);

		List<Publication> publications = new LinkedList<Publication>();

		for (Entidad ePublication : ePublications) {
			publications.add(readPublication(ePublication.getId()));
		}
		return publications;
	}

	// FUNCIONES PRIVADAS
	private String obtenerCodigosPhotos(List<Photo> listaFotos) {
		String aux = "";
		for (Photo p : listaFotos) {
			aux += p.getCodigo() + " ";
		}
		return aux.trim();
	}

	private String obtenerCodigosComentarios(List<Comment> listaComentarios) {
		String aux = "";
		for (Comment c : listaComentarios) {
			aux += c.getCodigo() + " ";
		}
		return aux.trim();
	}
	
	private List<Comment> obtenerComentariosDesdeCodigos(String comentarios) {
		List<Comment> commentList = new LinkedList<Comment>();
		StringTokenizer strTok = new StringTokenizer(comentarios, " ");
		while (strTok.hasMoreTokens()) {
			commentList.add(adaptadorComment.readComment(Integer.valueOf((String) strTok.nextElement())));
		}
		return commentList;
	}

}
