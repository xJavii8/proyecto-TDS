package umu.tds.controller;

import umu.tds.persistence.IAdaptadorUserDAO;

public class Controller {
	private static Controller unicaInstancia;
	
	private IAdaptadorUserDAO adaptadorUser;
	
	private Controller() {
		
	}
	
	private static Controller getInstancia() {
		if(unicaInstancia == null) {
			unicaInstancia = new Controller();
		}
		
		return unicaInstancia;
	}
}
