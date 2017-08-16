package ru.projects.favourites.dao;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import ru.projects.favourites.domain.DomainObject;
import ru.projects.favourites.domain.Favourite;
import ru.projects.favourites.domain.User;

public interface Queries {

	@NotNull
	@NotEmpty
	String getDeleteQuery(@NotNull DomainObject domain);
	
	@NotNull
	@NotEmpty
	String getDeleteQuery(@NotNull String entityType);

	@NotNull
	@NotEmpty
	String getFindQuery(@NotNull DomainObject domain);

	@NotNull
	@NotEmpty
	String getUpdateQuery(@NotNull DomainObject favourite, @NotNull @NotEmpty String columnName);

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
	}
}
