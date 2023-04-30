package umu.tds.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

import umu.tds.view.Constantes;

public class Album extends Publication {

	private List<Photo> photos;
	private String iconAlbumPath;

	public Album(String title, Date datePublication, String description, String user, List<Hashtag> hashtags,
			List<Publication> pubs, String iconAlbumPath) {
		super(title, datePublication, description, user, hashtags);
		List<Photo> photos = new LinkedList<>();
		for(Publication p : pubs) {
			if(p instanceof Photo) {
				photos.add((Photo) p);
			}
		}
		this.photos = photos;
		this.iconAlbumPath = iconAlbumPath;
	}
	
	public Album(String title, Date datePublication, String description, int likes, String user, List<Hashtag> hashtags,
			List<Photo> p, String iconAlbumPath) {
		super(title, datePublication, description, user, hashtags);
		this.photos = p;
		this.iconAlbumPath = iconAlbumPath;
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

	public String getIconAlbumPath() {
		return iconAlbumPath;
	}

	public void setIconAlbumPath(String iconAlbumPath) {
		this.iconAlbumPath = iconAlbumPath;
	}

}
