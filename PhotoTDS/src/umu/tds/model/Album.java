package umu.tds.model;

import java.util.Date;
import java.util.List;

public class Album extends Publication {

	private static final int MAX_NUM_PHOTOS = 16;
	private List<Photo> photos;
	
	public Album(String title, Date datePublication, String description, int likes) {
		super(title, datePublication, description, likes);
		// TODO Auto-generated constructor stub
	}

}
