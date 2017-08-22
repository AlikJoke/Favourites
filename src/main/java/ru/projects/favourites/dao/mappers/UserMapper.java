package ru.projects.favourites.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import ru.projects.favourites.domain.User;

@Component("userMapper")
public class UserMapper extends DomainMapper<User> {

	public static final String EMAIL_FIELD = "email";
	public static final String REG_DT_FIELD = "regDate";
	public static final String PASSWORD_FIELD = "password";
	public static final String LAST_LOGGED_FIELD = "lastLogged";

	@Override
	public User mapRow(ResultSet arg0, int arg1) throws SQLException {
		User user = null;
		if (arg0.next()) {
			user = new User(arg0.getString(DomainMapper.UID_FIELD),
					getLocalDateTimeValue(arg0.getTimestamp(DomainMapper.DELETING_DT_FIELD)),
					arg0.getString(PASSWORD_FIELD));
			user.setEmail(arg0.getString(EMAIL_FIELD));
			user.setRegDate(arg0.getDate(REG_DT_FIELD).toLocalDate());
			user.setLastLoggedDT(getLocalDateTimeValue(arg0.getTimestamp(LAST_LOGGED_FIELD)));
		}
		return user;
	}

	@Override
	public User extractData(ResultSet rs) throws SQLException, DataAccessException {
		return mapRow(rs, 0);
	}

}
