package ru.projects.favourites.dao.mappers;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import ru.projects.favourites.domain.User;

@Component("userMapper")
public class UserMapper extends DomainMapper<User> {

	private static final String EMAIL_FIELD = "email";
	private static final String REG_DT_FIELD = "regDate";
	private static final String PASSWORD_FIELD = "password";

	@Override
	public User mapRow(ResultSet arg0, int arg1) throws SQLException {
		User user = new User(arg0.getString(DomainMapper.UID_FIELD),
				getLocalDateTimeValue(arg0.getTimestamp(DomainMapper.DELETING_DT_FIELD)),
				arg0.getString(PASSWORD_FIELD));
		user.setEmail(arg0.getString(EMAIL_FIELD));
		user.setRegDate(arg0.getDate(REG_DT_FIELD).toLocalDate());

		return user;
	}

}
