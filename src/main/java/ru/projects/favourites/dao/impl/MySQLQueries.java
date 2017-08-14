package ru.projects.favourites.dao.impl;

import org.springframework.stereotype.Component;

import ru.projects.favourites.dao.Queries;
import ru.projects.favourites.domain.DomainObject;

@Component("mysqlQueries")
public class MySQLQueries implements Queries {

	@Override
	public String getDeleteQuery(DomainObject domain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFindQuery(DomainObject domain) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUpdateQuery(DomainObject favourite, String columnName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public FavouriteQueries getFavouriteQueries() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserQueries getUserQueries() {
		// TODO Auto-generated method stub
		return null;
	}

}
