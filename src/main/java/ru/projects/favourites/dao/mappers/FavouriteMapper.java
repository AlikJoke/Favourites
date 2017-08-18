package ru.projects.favourites.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import ru.projects.favourites.domain.Favourite;

@Component("favouriteMapper")
public class FavouriteMapper extends DomainMapper<Favourite> {

	public static final String NAME_FIELD = "name";
	public static final String LINK_FIELD = "link";
	public static final String ADDING_DT_FIELD = "addingDT";
	public static final String USERNAME_FIELD = "username";
	public static final String ORDER_FIELD = "order_fv";
	public static final String COUNTER_FIELD = "counter";

	@Override
	public Favourite mapRow(ResultSet arg0, int arg1) throws SQLException {
		Favourite fav = new Favourite(arg0.getString(DomainMapper.UID_FIELD), arg0.getString(USERNAME_FIELD),
				getLocalDateTimeValue(arg0.getTimestamp(ADDING_DT_FIELD)),
				getLocalDateTimeValue(arg0.getTimestamp(DomainMapper.DELETING_DT_FIELD)));
		fav.setName(arg0.getString(NAME_FIELD));
		fav.setLink(arg0.getString(LINK_FIELD));
		fav.setCounter(arg0.getLong(COUNTER_FIELD));
		fav.setOrder(arg0.getInt(ORDER_FIELD));

		return fav;
	}

	@Override
	public Favourite extractData(ResultSet rs) throws SQLException, DataAccessException {
		return mapRow(rs, 0);
	}

}
