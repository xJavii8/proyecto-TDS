package umu.tds.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.model.Hashtag;

public class AdaptadorHashtagTDS implements IAdaptadorHashtagDAO {
	
	private static ServicioPersistencia serverPersistencia;
	private static AdaptadorHashtagTDS unicaInstancia = null;

	public static AdaptadorHashtagTDS getUnicaInstancia() {
		if (unicaInstancia == null) {
			return new AdaptadorHashtagTDS();
		} else
			return unicaInstancia;
	}
	
	private AdaptadorHashtagTDS() {
		serverPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	@Override
	public void createHashtag(Hashtag hashtag) {
		Entidad eHashtag = null;
		try {
			eHashtag = serverPersistencia.recuperarEntidad(hashtag.getCode());
		} catch (NullPointerException e) {
		}

		if (eHashtag != null) {
			return;
		}

		eHashtag = new Entidad();
		eHashtag.setNombre("hashtag");
		eHashtag.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("name", hashtag.getName()))));
		eHashtag = serverPersistencia.registrarEntidad(eHashtag);
		hashtag.setCode(eHashtag.getId());
	}

	@Override
	public Hashtag readHashtag(int hashtagCode) {
		if (PoolDAO.getUnicaInstancia().contiene(hashtagCode)) {
			return (Hashtag) PoolDAO.getUnicaInstancia().getObjeto(hashtagCode);
		}

		Entidad eHashtag;

		String name;

		eHashtag = serverPersistencia.recuperarEntidad(hashtagCode);

		name = serverPersistencia.recuperarPropiedadEntidad(eHashtag, "name");

		Hashtag h = new Hashtag(name);
		h.setCode(hashtagCode);

		PoolDAO.getUnicaInstancia().addObjeto(hashtagCode, h);

		return h;
	}

	@Override
	public void updateHashtag(Hashtag hashtag) {
		Entidad eHashtag = serverPersistencia.recuperarEntidad(hashtag.getCode());

		for (Propiedad prop : eHashtag.getPropiedades()) {
			if (prop.getNombre().equals("name")) {
				prop.setValor(hashtag.getName());
			}
			serverPersistencia.modificarPropiedad(prop);
		}
	}

	@Override
	public void deleteHashtag(Hashtag hashtag) {
		Entidad eHashtag = serverPersistencia.recuperarEntidad(hashtag.getCode());
		serverPersistencia.borrarEntidad(eHashtag);
	}

	@Override
	public List<Hashtag> readAllHashtags() {
		List<Entidad> eHashtags = serverPersistencia.recuperarEntidades("hashtag");
		List<Hashtag> hashtags = new LinkedList<>();

		for (Entidad eHashtag : eHashtags) {
			hashtags.add(readHashtag(eHashtag.getId()));
		}

		return hashtags;
	}

}
