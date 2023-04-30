package umu.tds.view;

import java.awt.Color;
import java.util.regex.Pattern;

public class Constantes {
	public static final int INITIAL_WIDTH = 450;
	public static final int INITIAL_HEIGHT = 299;

	public static final int HTML_PHOTO_SELECTED_SIZE = 75;

	public static final int MIN_PASSWORD_LENGTH = 8;

	public static final int MAX_DESCRIP_REGISTER_LENGTH = 200;
	public static final int MAX_TITLE_PUB_LENGTH = 15;
	public static final int MAX_DESCRIP_PUB_LENGTH = 60;

	public static final String VALID_EMAIL_REGEX = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
	public static final String VALID_FULLNAME_REGEX = "^[A-ZÁÉÍÓÚ][a-záéíóú\\s]*$";
	public static final String VALID_HASHTAG_REGEX = "(#(\\w+))";
	public static final Pattern EMAIL_PAT = Pattern.compile(VALID_EMAIL_REGEX);
	public static final Pattern FULLNAME_PAT = Pattern.compile(VALID_FULLNAME_REGEX);
	public static final Pattern HASHTAG_PAT = Pattern.compile(VALID_HASHTAG_REGEX);

	public static final Color LIGHT_BARS = new Color(230, 230, 230, 230);
	public static final Color DARK_BARS = new Color(75, 77, 78);

	public static final int PREMIUM_PRICE = 10;

	public static final int SELF_USER_PIC_SIZE = 32;
	public static final int PROFILE_PIC_SIZE = 128;
	public static final int PUBLICATION_PIC_SIZE = 240;
	public static final int PROFILE_PUBLICATION_PIC_SIZE = 96;

	public static final int MIN_AGE_PREMIUM_DISCOUNT = 18;
	public static final int MAX_AGE_PREMIUM_DISCOUNT = 25;
	public static final double DISCOUNT_PER_AGE = 0.025;

	public static final int MIN_LIKES_PREMIUM_DISCOUNT = 5;
	public static final int LIKES_DISCOUNT = 3;
	
	public static final int TOP_LIKED_PHOTOS_PREMIUM = 10;
	
	public static final int MAX_HASH_LENGTH = 15;
	
	public static final int DEFAULT_LIKES_PUBLICATION = 0;
	public static final int ALBUM_MAX_NUM_PHOTOS = 16;
	public static final int ALBUM_MINI_PHOTOS = 9;
	public static final int MAX_TITLE_ALBUM_LIST = 13;

}
