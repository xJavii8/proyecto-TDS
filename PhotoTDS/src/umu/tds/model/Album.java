package umu.tds.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import umu.tds.view.Constantes;

public class Album extends Publication {

	private List<Photo> photos;

	public Album(String title, Date datePublication, String description, String user, List<Hashtag> hashtags,
			List<Publication> pubs) {
		super(title, datePublication, description, user, hashtags);
		List<Photo> photos = new LinkedList<>();
		for(Publication p : pubs) {
			if(p instanceof Photo) {
				photos.add((Photo) p);
			}
		}
		this.setPhotos(photos);
	}
	
	public Album(String title, Date datePublication, String description, int likes, String user, List<Hashtag> hashtags,
			List<Photo> p) {
		super(title, datePublication, description, user, hashtags);
		this.setPhotos(p);
	}

	public List<Photo> getPhotos() {
		return new LinkedList<>(photos);
	}
	
	public void setPhotos(List<Photo> photos) {
		this.photos = photos;
	}
	
	public boolean removePhoto(Photo p) {
		this.photos.remove(p);
		return true;
	}

}
