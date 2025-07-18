package umu.tds.model;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import umu.tds.persistence.DAOException;
import umu.tds.persistence.DAOFactory;
import umu.tds.persistence.IAdaptadorPublicationDAO;

public enum PublicationRepository {
	INSTANCE;
	
	private Map<String, Publication> publications;
	private DAOFactory dao;
	private IAdaptadorPublicationDAO publicationAdapter;

	private PublicationRepository() {
		try {
			dao = DAOFactory.getInstancia(DAOFactory.DAO_TDS);
			publicationAdapter = dao.getPublicationDAO();
			publications = new HashMap<String, Publication>();
			this.uploadRepository();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}


	/*
	 * Si obtenemos la publicación devuelve el objeto, si no la encuentra devuelve
	 * nulo
	 */
	public Optional<Publication> getPublication(String titulo) {
		return Optional.ofNullable(publications.get(titulo));
	}

	// Añadimos la publicación tanta a la lista de publicaciones como al adaptador
	public void createPublication(Publication p) {
		this.publications.put(p.getTitle(), p);
		this.publicationAdapter.createPublication(p);
	}

	public void updatePublication(Publication p) {
		this.publicationAdapter.updatePublication(p);
		this.publications.remove(p.getTitle());
		this.publications.put(p.getTitle(), p);
	}

	public void removePublication(Publication p) {
		this.publicationAdapter.deletePublication(p);
		this.publications.remove(p.getTitle());
	}

	public Set<Publication> getAllPublications() {
		return new LinkedHashSet<>(this.publications.values());
	}

	public List<Publication> getAllPublicationsUser(String u) {
		return this.getAllPublications().stream().filter(p -> p.getUser().equals(u)).collect(Collectors.toList());
	}

	// Recargar repositorio
	private void uploadRepository() {
		List<Publication> publicationDB = publicationAdapter.readAllPublications();
		for (Publication p : publicationDB) {
			publications.put(p.getTitle(), p);
		}
	}
}
