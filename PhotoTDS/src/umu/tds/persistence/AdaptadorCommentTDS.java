package umu.tds.persistence;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import umu.tds.model.Comment;
import umu.tds.view.Utilities;

public class AdaptadorCommentTDS implements IAdaptadorCommentDAO {
	private static ServicioPersistencia serverPersistencia;
	private static AdaptadorCommentTDS unicaInstancia = null;

	public static AdaptadorCommentTDS getUnicaInstancia() {
		if (unicaInstancia == null) {
			return new AdaptadorCommentTDS();
		} else
			return unicaInstancia;
	}

	private AdaptadorCommentTDS() {
		serverPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	public void createComment(Comment c) {
		Entidad eComment = null;
		System.out.println("Hola");
		try {
			eComment = serverPersistencia.recuperarEntidad(c.getCodigo());
		} catch (NullPointerException e) {
		}

		if (eComment != null) {
			return;
		}

		eComment = new Entidad();
		eComment.setNombre("comentario");
		eComment.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("text", c.getText()),
				new Propiedad("publishedDate", Utilities.dateToString(c.getPublishedDate())),
				new Propiedad("author", c.getAuthor()))));

		eComment = serverPersistencia.registrarEntidad(eComment);
		c.setCodigo(eComment.getId());
		System.out.println("Comentario Creado con id: " + Integer.toString(eComment.getId()));

	}

	public Comment readComment(int codigo) {
		if (PoolDAO.getUnicaInstancia().contiene(codigo)) {
			return (Comment) PoolDAO.getUnicaInstancia().getObjeto(codigo);
		}

		Entidad eCommment;

		String text;
		String publishedDate;
		String author;

		eCommment = serverPersistencia.recuperarEntidad(codigo);

		text = serverPersistencia.recuperarPropiedadEntidad(eCommment, "text");
		publishedDate = serverPersistencia.recuperarPropiedadEntidad(eCommment, "publishedDate");
		author = serverPersistencia.recuperarPropiedadEntidad(eCommment, "author");

		Comment c = new Comment(text, author, Utilities.stringToDate(publishedDate));
		c.setCodigo(codigo);

		PoolDAO.getUnicaInstancia().addObjeto(codigo, c);

		return c;
	}

	public void updateComment(Comment c) {
		Entidad eComment = serverPersistencia.recuperarEntidad(c.getCodigo());

		for (Propiedad prop : eComment.getPropiedades()) {
			if (prop.getNombre().equals("text")) {
				prop.setValor(c.getText());
			} else if (prop.getNombre().equals("publishedDate")) {
				prop.setValor(Utilities.dateToString(c.getPublishedDate()));
			} else if (prop.getNombre().equals("author")) {
				prop.setValor(c.getAuthor());
			}
			serverPersistencia.modificarPropiedad(prop);
		}
	}

	public void deleteComment(Comment c) {
		Entidad eCommment = serverPersistencia.recuperarEntidad(c.getCodigo());

		serverPersistencia.borrarEntidad(eCommment);
	}

	public List<Comment> readAllComments() {
		List<Entidad> eComment = serverPersistencia.recuperarEntidades("comentario");
		List<Comment> comentarios = new LinkedList<>();

		for (Entidad eCom : eComment) {
			comentarios.add(readComment(eCom.getId()));
		}

		return comentarios;
	}

	@Override
	public void updateComentario(Comment u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteComentario(Comment u) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Comment> readAllComment() {
		// TODO Auto-generated method stub
		return null;
	}

}
