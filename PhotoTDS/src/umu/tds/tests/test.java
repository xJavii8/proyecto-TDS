package umu.tds.tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Optional;

import org.junit.Test;
import umu.tds.controller.*;
import umu.tds.model.*;


public class test {
    
 
  @Test
  public void testFollowUnfollow() {
	  
	  String nickname_Ignacio = "ignacio";
	  String nickname_Javi = "javi";
	  
	  String ignacioContra = "ignacio";
	  String javiContra = "javijavi";
	  
	  //Creamos a los dos usuarios y los metemos a la base de datos
	  Controller.getInstancia().createUser("ignacio@gmail.com", "Ignacio" , nickname_Ignacio, ignacioContra, new Date(2002, 12, 4), null , "hola");
	  Controller.getInstancia().createUser("javi@gmail.com", "Javi" , nickname_Javi, javiContra, new Date(2002, 12, 21), null , "adios");
	  
	  Controller.getInstancia().unfollow(nickname_Ignacio, nickname_Javi);

	  assertTrue(Controller.getInstancia().login(nickname_Ignacio, ignacioContra));
	  
	  
	  Optional<User> userOpt = Optional.ofNullable(Controller.getInstancia().getUser(nickname_Javi));
	  assertTrue(userOpt.isPresent());
	  
	  User javi = userOpt.get();
	  
	  int followersBeforeSeguir = javi.getFollowers().size();
	  
	  assertTrue(Controller.getInstancia().follow(nickname_Ignacio, nickname_Javi));
	  
	  int followersAfterSeguir = javi.getFollowers().size();
	  
	  Optional<User> userOpt2 = Optional.ofNullable(Controller.getInstancia().getUser(nickname_Ignacio));
	  
	  assertTrue(userOpt2.isPresent());
	  
	  User ignacio = userOpt2.get();
	  assertTrue(javi.getFollowers().contains(ignacio));
	  
	  assertNotEquals(followersBeforeSeguir, followersAfterSeguir);
	  
	  int followersBeforeDejarDeSeguir = javi.getFollowers().size();
	  assertTrue(Controller.getInstancia().unfollow(nickname_Ignacio, nickname_Javi));
	  int followersAfterDejarDeSeguir = javi.getFollowers().size();
	  assertNotEquals(followersBeforeDejarDeSeguir, followersAfterDejarDeSeguir);
	  
  }
  
  
 
}