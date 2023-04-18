package umu.tds.persistence;

import java.util.List;

import umu.tds.model.Comment;



public interface IAdaptadorCommentDAO {
	public void createComment(Comment u);
	public Comment readComment(int codigo);
	public void updateComment(Comment u);
	public void deleteComment(Comment u);
	public List<Comment> readAllComments();
}
