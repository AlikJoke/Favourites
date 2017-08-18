package ru.projects.favourites.dao;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import ru.projects.favourites.domain.EntityType;
import ru.projects.favourites.domain.Favourite;
import ru.projects.favourites.domain.User;

/**
 * Сервис построения SQL-запросов для различных операций с сущностями (DML) и
 * построения схемы (DDL).
 * 
 * @author Alimurad A. Ramazanov
 * @since 15.08.2017
 * @version 1.0.1* 
 *
 */
public interface Queries {

	@NotNull
	@NotEmpty
	String getDeleteQuery(@NotNull EntityType entityType);

	@NotNull
	@NotEmpty
	String getDeleteQuery(@NotNull String entityType);

	@NotNull
	@NotEmpty
	String getFindQuery(@NotNull EntityType entityType);

	@NotNull
	@NotEmpty
	String getUpdateQuery(@NotNull EntityType entityType, @NotNull @NotEmpty String columnName);

	@NotNull
	FavouriteQueries getFavouriteQueries();

	public interface FavouriteQueries {

		@NotNull
		@NotEmpty
		String getInsertFavouriteQuery(@NotNull Favourite favourite);

		@NotNull
		@NotEmpty
		String getFindAllFavouritesQuery();
	}

	@NotNull
	UserQueries getUserQueries();

	public interface UserQueries {

		@NotNull
		@NotEmpty
		String getInsertUserQuery(@NotNull User favourite);

		@NotNull
		@NotEmpty
		String getFindAllUsersQuery();
		
		@NotNull
		@NotEmpty
		String getFindUsersByLoggedDT();
	}

	@NotNull
	SchemaBuilder getSchemaBuilder();

	public interface SchemaBuilder {

		@NotNull
		@NotEmpty
		String checkTableExistsQuery();

		@NotNull
		@NotEmpty
		String getQueryToCreateTableFavourites();

		@NotNull
		@NotEmpty
		String getQueryToCreateTableUsers();

		@NotNull
		@NotEmpty
		String getQueryToCreateIndex(@NotNull String tableName, @NotNull String columnName);
	}
}
