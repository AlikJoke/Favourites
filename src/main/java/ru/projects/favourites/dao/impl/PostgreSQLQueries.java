package ru.projects.favourites.dao.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.projects.favourites.dao.Queries;
import ru.projects.favourites.domain.DomainObject;
import ru.projects.favourites.domain.Favourite;
import ru.projects.favourites.domain.User;

@Component("postgresqlQueries")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PostgreSQLQueries implements Queries {

	@Override
	public String getDeleteQuery(DomainObject domain) {
		return Patterns.replacePlaceholders(domain, Patterns.DELETE_QUERY);
	}

	@Override
	public String getFindQuery(DomainObject domain) {
		return Patterns.replacePlaceholders(domain, Patterns.FIND_QUERY);
	}

	@Override
	public String getUpdateQuery(DomainObject domainObject, String columnName) {
		return Patterns.replaceUpdatePlaceholders(domainObject, columnName, Patterns.UPDATE_QUERY);
	}

	@Override
	public FavouriteQueries getFavouriteQueries() {
		return new FavouriteQueries() {

			@Override
			public String getInsertFavouriteQuery(Favourite favourite) {
				return Patterns.INSERT_FAVOURITE_QUERY;
			}

			@Override
			public String getFindAllFavouritesQuery() {
				return Patterns.FIND_FAVOURITES_QUERY;
			}

		};
	}

	@Override
	public UserQueries getUserQueries() {
		return new UserQueries() {

			@Override
			public String getInsertUserQuery(User favourite) {
				return Patterns.INSERT_USER_QUERY;
			}

			@Override
			public String getFindAllUsersQuery() {
				return Patterns.FIND_USERS_QUERY;
			}

		};
	}

	final static class Patterns {

		static final String TABLE_NAME_PLACEHOLDER = "<tableName>";
		static final String COLUMN_NAME_PLACEHOLDER = "<columnName>";

		static final String DELETE_QUERY = "DELETE FROM <tableName> WHERE uid = ?";
		static final String FIND_QUERY = "SELECT * FROM <tableName> WHERE uid = ?";
		static final String INSERT_FAVOURITE_QUERY = "INSERT INTO favourite (uid, name, link, addingDT, deletingDT, order, counter, username) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		static final String INSERT_USER_QUERY = "INSERT INTO user (username, email, regDT, password, deletingDT) "
				+ "VALUES (?, ?, ?, ?, ?)";
		static final String UPDATE_QUERY = "UPDATE <tableName> SET <columnName> = ?";
		static final String FIND_FAVOURITES_QUERY = "SELECT * FROM favourite WHERE username = ?";
		static final String FIND_USERS_QUERY = "SELECT * FROM user";
		static final String CHECK_TABLE = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ?)";
		static final String CREATE_FV_TABLE = "CREATE TABLE FAVOURITE (UID varchar(64) NOT NULL, NAME varchar(256) NOT NULL, "
				+ "LINK varchar(512) NOT NULL, ADDING_DT timestamp NOT NULL, DELETING_DT timestamp NULL, "
				+ "username varchar(64) NOT NULL references USER(UID), PRIMARY KEY (UID));";
		static final String CREATE_USER_TABLE = "CREATE TABLE USER (UID varchar(64) NOT NULL, EMAIL varchar(128) NOT NULL, "
				+ "PASSWORD varchar(64) NOT NULL, REG_DATE date NOT NULL, DELETING_DT timestamp NULL, "
				+ "PRIMARY KEY (UID));";
		static final String CREATE_INDEX_TABLE = "CREATE INDEX idx_<columnName> ON <tableName> (<columnName>);";

		static String replaceDDLPlaceholders(String tableName, String columnName, String sourceQuery) {
			return sourceQuery.replace(TABLE_NAME_PLACEHOLDER, tableName).replace(COLUMN_NAME_PLACEHOLDER, columnName);
		}

		static String replacePlaceholders(DomainObject domain, String sourceQuery) {
			return sourceQuery.replace(TABLE_NAME_PLACEHOLDER, domain.getEntityName());
		}

		static String replaceDeleteByIdPlaceholders(String entityType, String sourceQuery) {
			return sourceQuery.replace(TABLE_NAME_PLACEHOLDER, entityType);
		}

		static String replaceUpdatePlaceholders(DomainObject domain, String columnName, String sourceQuery) {
			return sourceQuery.replace(TABLE_NAME_PLACEHOLDER, domain.getEntityName()).replace(COLUMN_NAME_PLACEHOLDER,
					columnName);
		}
	}

	@Override
	public String getDeleteQuery(String entityType) {
		return Patterns.replaceDeleteByIdPlaceholders(entityType, Patterns.DELETE_QUERY);
	}

	@Override
	public SchemaBuilder getSchemaBuilder() {
		return new SchemaBuilder() {

			@Override
			public String checkTableExistsQuery() {
				return Patterns.CHECK_TABLE;
			}

			@Override
			public String getQueryToCreateTableFavourites() {
				return Patterns.CREATE_FV_TABLE;
			}

			@Override
			public String getQueryToCreateTableUsers() {
				return Patterns.CREATE_USER_TABLE;
			}

			@Override
			public String getQueryToCreateIndex(String tableName, String columnName) {
				return Patterns.replaceDDLPlaceholders(tableName, columnName, Patterns.CREATE_INDEX_TABLE);
			}

		};
	}

}
