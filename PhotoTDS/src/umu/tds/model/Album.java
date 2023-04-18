package umu.tds.model;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Predicate;

public class Album extends Publication {

	private static final int MAX_NUM_PHOTOS = 16;
	private List<Photo> photos;

	public Album(String title, Date datePublication, String description, int likes, String user) {
		super(title, datePublication, description, likes, user);
	}

	public boolean addPhoto(Photo pho) {
		Predicate<Photo> added = f -> !this.photos.contains(pho) && this.photos.size() < MAX_NUM_PHOTOS;
		return added.test(pho);
	}

	public boolean checkPhoto(Photo pho) {
		return this.photos.stream().anyMatch(f -> f.equals(pho));
	}

	public List<Photo> getPhotos() {
		return new LinkedList<>(photos);
	}

}
