package umu.tds.persistence;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.sl.draw.geom.PolarAdjustHandle;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.model.Notification;
import umu.tds.model.Publication;
import umu.tds.view.Utilities;


public class AdaptadorNotificationTDS implements IAdaptadorNotificationDAO {

	private static ServicioPersistencia serverPersistencia;
	private static AdaptadorNotificationTDS unicaInstancia = null;
	private static AdaptadorPublicationTDS adaptadorPublication = null;

	public static AdaptadorNotificationTDS getUnicaInstancia() {
		if (unicaInstancia == null) {
			return new AdaptadorNotificationTDS();
		} else
			return unicaInstancia;
	}
	
	private AdaptadorNotificationTDS() {
		serverPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		adaptadorPublication = AdaptadorPublicationTDS.getUnicaInstancia();
	}
	
	@Override
	public void createNotification(Notification n) {
		Entidad eNotification = null;
		
		try {
			eNotification = serverPersistencia.recuperarEntidad(n.getCodigo());
		} catch (NullPointerException e) {
		}

		if (eNotification != null) {
			return;
		}
		
		// registramos primero los atributos que son objetos
		adaptadorPublication.createPublication(n.getPublication());
		
		// creamos entidad notification
		eNotification = new Entidad();
		eNotification.setNombre("notification");
		System.out.println("El valor codigo publicacion notificacion es: " + n.getPublication().getCodigo());
		eNotification.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("date", new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(n.getDate())),
							  new Propiedad("publication", String.valueOf(n.getPublication().getCodigo())))));
		
		// registrar entidad notification
		eNotification = serverPersistencia.registrarEntidad(eNotification);
		n.setCode(eNotification.getId());
	}

	@Override
	public Notification readNotification(int notificationCode) {
		
		if (PoolDAO.getUnicaInstancia().contiene(notificationCode)) {
			return (Notification) PoolDAO.getUnicaInstancia().getObjeto(notificationCode);
		}
		
		Entidad eNotification;
		
		String date;
		Publication publication;
		
		eNotification = serverPersistencia.recuperarEntidad(notificationCode);
		date = serverPersistencia.recuperarPropiedadEntidad(eNotification, "date");
		System.out.println("El date guardado es: "+ date);
		
		publication = adaptadorPublication.readPublication(
				Integer.parseInt(serverPersistencia.recuperarPropiedadEntidad(eNotification, "publication")));
		
		System.out.println(Utilities.stringToDateHours(date));
		Notification n = new Notification(Utilities.stringToDateHours(date), publication);
		n.setCode(notificationCode);
		
		PoolDAO.getUnicaInstancia().addObjeto(notificationCode, n);
		
		return n;
	}

	@Override
	public void deleteNotification(Notification n) {
		Entidad eNotification = serverPersistencia.recuperarEntidad(n.getCodigo());
		serverPersistencia.borrarEntidad(eNotification);
		
	}

	@Override
	public List<Notification> readAllNotifications() {
		List<Entidad> eNotifications = serverPersistencia.recuperarEntidades("notification");
		List<Notification> notifications = new LinkedList<>();
		
		for (Entidad eNotification : eNotifications) {
			notifications.add(readNotification(eNotification.getId()));
		}
		
		return notifications;
	}

}
