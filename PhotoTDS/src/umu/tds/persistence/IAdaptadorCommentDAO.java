package umu.tds.persistence;

import java.util.List;

import umu.tds.model.Comment;



public interface IAdaptadorCommentDAO {
	public void createComment(Comment u);
	public Comment readComment(int codigo);
	public void updateComentario(Comment u);
	public void deleteComentario(Comment u);
	public List<Comment> readAllComment();
}
