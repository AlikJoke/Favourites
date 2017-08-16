package ru.projects.favourites.dao.impl;

import org.springframework.stereotype.Component;

import ru.projects.favourites.dao.Queries;
import ru.projects.favourites.domain.EntityType;

/**
 * Имплементация сервиса получения запросов к БД для СУБД MySQL.
 * 
 * @author Alimurad A. Ramazanov
 * @since 15.08.2017
 * @version 1.0.2
 *
 */
@Component("mysqlQueries")
public class MySQLQueries implements Queries {

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

	@Override
	public String getDeleteQuery(String entityType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SchemaBuilder getSchemaBuilder() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDeleteQuery(EntityType entityType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getFindQuery(EntityType entityType) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUpdateQuery(EntityType entityType, String columnName) {
		// TODO Auto-generated method stub
		return null;
	}

}
