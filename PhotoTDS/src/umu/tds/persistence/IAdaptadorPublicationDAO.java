package umu.tds.persistence;

import java.util.List;


import umu.tds.model.Publication;

public interface IAdaptadorPublicationDAO {
	public void createPublication(Publication publication);
	public Publication readPublication(int publicationCode);
	public void updatePublication(Publication publication);
	public void deletePublication(Publication publication);
	public List<Publication> readAllPublications();
}
