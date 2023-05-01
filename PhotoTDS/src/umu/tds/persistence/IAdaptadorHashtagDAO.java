package umu.tds.persistence;

import java.util.List;

import umu.tds.model.Hashtag;

public interface IAdaptadorHashtagDAO {
	public void createHashtag(Hashtag hashtag);

	public Hashtag readHashtag(int hashtagCode);

	public void updateHashtag(Hashtag hashtag);

	public void deleteHashtag(Hashtag hashtag);

	public List<Hashtag> readAllHashtags();
}
