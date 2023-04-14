package umu.tds.view;

import java.awt.Color;
import java.util.regex.Pattern;

public class Constantes {
	// StartWindow
	public static final int INITIAL_WIDTH = 450;
	public static final int INITIAL_HEIGHT = 299;
	
	public static final int PHOTO_SELECTED_SIZE = 75;
	
	public static final int MIN_PASSWORD_LENGTH = 8;
	
	public static final String VALID_EMAIL_REGEX = "^\\w+@[a-zA-Z_]+?\\.[a-zA-Z]{2,3}$";
	public static final String VALID_FULLNAME_REGEX = "^[A-Z][a-z]+(\\s[A-Z][a-z]+)*$";
	public static final Pattern EMAIL_PAT = Pattern.compile(VALID_EMAIL_REGEX);
	public static final Pattern FULLNAME_PAT = Pattern.compile(VALID_FULLNAME_REGEX);
	
	public static final Color LIGHT_BARS = new Color(230, 230, 230, 230);
	public static final Color DARK_BARS = new Color(75, 77, 78);
}
