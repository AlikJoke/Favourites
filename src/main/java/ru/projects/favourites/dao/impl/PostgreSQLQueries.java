package ru.projects.favourites.dao.impl;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.projects.favourites.dao.Queries;
import ru.projects.favourites.domain.EntityType;
import ru.projects.favourites.domain.Favourite;
import ru.projects.favourites.domain.User;

/**
 * Имплементация сервиса получения запросов к БД для СУБД PostgreSQL.
 * 
 * @author Alimurad A. Ramazanov
 * @since 15.08.2017
 * @version 1.0.2
 *
 */
@Component("postgresqlQueries")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class PostgreSQLQueries implements Queries {

	@Override
	public String getDeleteQuery(EntityType entityType) {
		return Patterns.replacePlaceholders(entityType, Patterns.DELETE_QUERY);
	}

	@Override
	public String getFindQuery(EntityType entityType) {
		return Patterns.replacePlaceholders(entityType, Patterns.FIND_QUERY);
	}

	@Override
	public String getUpdateQuery(EntityType entityType, String columnName) {
		return Patterns.replaceUpdatePlaceholders(entityType, columnName, Patterns.UPDATE_QUERY);
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
		static final String INSERT_FAVOURITE_QUERY = "INSERT INTO favourite (uid, name, link, addingDT, deletingDT, order_fv, counter, username) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		static final String INSERT_USER_QUERY = "INSERT INTO user_t (username, email, regDT, password, deletingDT, lastLogged) "
				+ "VALUES (?, ?, ?, ?, ?, ?);";
		static final String UPDATE_QUERY = "UPDATE <tableName> SET <columnName> = ?";
		static final String FIND_FAVOURITES_QUERY = "SELECT * FROM favourite WHERE username = ?";
		static final String FIND_USERS_QUERY = "SELECT * FROM user";
		static final String CHECK_TABLE = "SELECT EXISTS (SELECT 1 FROM information_schema.tables WHERE table_name = ?)";
		static final String CREATE_FV_TABLE = "CREATE TABLE IF NOT EXISTS FAVOURITE (UID varchar(64) NOT NULL, NAME varchar(256) NOT NULL, "
				+ "LINK varchar(512) NOT NULL, ADDING_DT timestamp NOT NULL, DELETING_DT timestamp NULL, ORDER_FV int NOT NULL, COUNTER int NOT NULL, "
				+ "username varchar(64) NOT NULL references USER_T(UID), PRIMARY KEY (UID));";
		static final String CREATE_USER_TABLE = "CREATE TABLE IF NOT EXISTS USER_T (UID varchar(64) NOT NULL, EMAIL varchar(128) NOT NULL, "
				+ "PASSWORD varchar(64) NOT NULL, REG_DATE date NOT NULL, DELETING_DT timestamp NULL, LAST_LOGGED timestamp NOT NULL, "
				+ "PRIMARY KEY (UID));";
		static final String CREATE_INDEX_TABLE = "CREATE INDEX idx_<columnName> ON <tableName> (<columnName>);";

		static String replaceDDLPlaceholders(String tableName, String columnName, String sourceQuery) {
			return sourceQuery.replace(TABLE_NAME_PLACEHOLDER, tableName).replace(COLUMN_NAME_PLACEHOLDER, columnName);
		}

		static String replacePlaceholders(EntityType entityType, String sourceQuery) {
			return sourceQuery.replace(TABLE_NAME_PLACEHOLDER, entityType.getName());
		}

		static String replaceDeleteByIdPlaceholders(String entityType, String sourceQuery) {
			return sourceQuery.replace(TABLE_NAME_PLACEHOLDER, entityType);
		}

		static String replaceUpdatePlaceholders(EntityType entityType, String columnName, String sourceQuery) {
			return sourceQuery.replace(TABLE_NAME_PLACEHOLDER, entityType.getName()).replace(COLUMN_NAME_PLACEHOLDER,
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
