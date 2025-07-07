package umu.tds.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.junit.Test;

import umu.tds.controller.Controller;
import umu.tds.model.User;

public class PhotoTDSTests {

	/**
	 * Verifica que un usuario pueda seguir y dejar de seguir a otro usuario
	 */
	@Test
	public void testFollowUnfollow() {
		// Se establecen los datos de los usuarios
		String nickname_Ignacio = "ignacio";
		String nickname_Javi = "javi";
		String ignacioContra = "ignacio";
		String javiContra = "javi";
		LocalDate localDate1 = LocalDate.of(2002, Month.DECEMBER, 4);
		Date fecha1 = Date.from(localDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());
		LocalDate localDate2 = LocalDate.of(2002, Month.DECEMBER, 21);
		Date fecha2 = Date.from(localDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());

		// Creamos a los dos usuarios y los metemos a la base de datos
		assertTrue(Controller.INSTANCE.createUser("ignacio@gmail.com", "Ignacio", nickname_Ignacio, ignacioContra,
				fecha1, null, "hola"));
		assertTrue(Controller.INSTANCE.createUser("javi@gmail.com", "Javi", nickname_Javi, javiContra, fecha2,
				null, "adios"));

		// Logueamos al primer usuario
		assertTrue(Controller.INSTANCE.login(nickname_Ignacio, ignacioContra));

		// Obtenemos el segundo usuario
		Optional<User> userOpt = Optional.ofNullable(Controller.INSTANCE.getUser(nickname_Javi));
		assertTrue(userOpt.isPresent());
		User javi = userOpt.get();

		// Miramos los seguidores antes de seguir
		int followersBeforeSeguir = javi.getFollowers().size();

		// El primer usuario sigue al segundo
		assertTrue(Controller.INSTANCE.follow(nickname_Ignacio, nickname_Javi));

		// Miramos los seguidores depues de seguir
		int followersAfterSeguir = javi.getFollowers().size();

		// Obtenemos al primer usuario
		Optional<User> userOpt2 = Optional.ofNullable(Controller.INSTANCE.getUser(nickname_Ignacio));
		assertTrue(userOpt2.isPresent());
		User ignacio = userOpt2.get();

		// Comprobamos que el segundo usuario es seguido por el primero
		assertTrue(javi.getFollowers().contains(ignacio));

		// Comprobamos si le ha seguido
		assertNotEquals(followersBeforeSeguir, followersAfterSeguir);

		// Miramos los seguidores antes de dejar de seguir
		int followersBeforeDejarDeSeguir = javi.getFollowers().size();
		assertTrue(Controller.INSTANCE.unfollow(nickname_Ignacio, nickname_Javi));
		// Miramos los seguidores depués de dejar de seguir
		int followersAfterDejarDeSeguir = javi.getFollowers().size();
		assertNotEquals(followersBeforeDejarDeSeguir, followersAfterDejarDeSeguir);

		// Borramos los dos usuarios
		assertTrue(Controller.INSTANCE.removeUser(nickname_Ignacio));
		assertTrue(Controller.INSTANCE.removeUser(nickname_Javi));

	}

	/**
	 * Verifica que un usuario pueda agregar y eliminar una foto de su perfil.
	 */
	@Test
	public void addDeletePhoto() {
		// Se establecen los datos del usuario
		String nickname_Ignacio = "ignacio";
		String ignacioContra = "ignacio";
		LocalDate localDate1 = LocalDate.of(2002, Month.DECEMBER, 4);
		Date fecha1 = Date.from(localDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());

		// Se crea el usuario y se inicia sesión
		assertTrue(Controller.INSTANCE.createUser("ignacio@gmail.com", "Ignacio", nickname_Ignacio, ignacioContra,
				fecha1, null, "hola"));
		assertTrue(Controller.INSTANCE.login(nickname_Ignacio, ignacioContra));

		// Se obtiene el usuario creado
		Optional<User> userOpt = Optional.ofNullable(Controller.INSTANCE.getUser(nickname_Ignacio));
		User userIgnacio = userOpt.get();

		// Se agrega una foto al perfil del usuario
		assertTrue(Controller.INSTANCE.createPhoto(nickname_Ignacio, "test1", "Descripción del text1",
				"url-test1.png"));

		// Se verifica que la foto se agrega y se borra correctamente
		int publBefore = userIgnacio.getPublications().size();
		assertTrue(Controller.INSTANCE.deletePhoto(Controller.INSTANCE.getPublication("test1").get()));
		int publAfter = userIgnacio.getPublications().size();
		assertNotEquals(publBefore, publAfter);

		// Se elimina el usuario
		assertTrue(Controller.INSTANCE.removeUser(nickname_Ignacio));

	}

	/**
	 * Verifica si un usuario puede encontrar a otro usuario en la aplicación.
	 */
	@Test
	public void userFinder() {
		// Los datos de los usuarios
		String user = "ignacio";
		String userToFind = "javi";
		String ignacioContra = "ignacio";
		String javiContra = "javi";
		LocalDate localDate1 = LocalDate.of(2002, Month.DECEMBER, 4);
		Date fecha1 = Date.from(localDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());
		LocalDate localDate2 = LocalDate.of(2002, Month.DECEMBER, 21);
		Date fecha2 = Date.from(localDate1.atStartOfDay(ZoneId.systemDefault()).toInstant());

		// Se crean los usuarios en la aplicación
		assertTrue(Controller.INSTANCE.createUser("ignacio@gmail.com", "Ignacio", user, ignacioContra, fecha1,
				null, "hola"));
		assertTrue(Controller.INSTANCE.createUser("javi@gmail.com", "Javi", userToFind, javiContra, fecha2, null,
				"adios"));

		// Se verifica que el primer usuario puede encontrar al segundo usuario
		assertTrue(Controller.INSTANCE.login(user, ignacioContra));
		User u = Controller.INSTANCE.search(user, userToFind).get(0);
		assertEquals(u.getUsername(), userToFind);

		// Se eliminan ambos usuarios de la aplicación
		assertTrue(Controller.INSTANCE.removeUser(user));
		assertTrue(Controller.INSTANCE.removeUser(userToFind));

	}
}