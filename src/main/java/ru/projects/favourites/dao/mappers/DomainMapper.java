package ru.projects.favourites.dao.mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.validation.constraints.Null;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

public abstract class DomainMapper<T> implements RowMapper<T>, ResultSetExtractor<T> {

	public static final String UID_FIELD = "uid";
	public static final String DELETING_DT_FIELD = "deletingDT";

	@Null
	protected final LocalDateTime getLocalDateTimeValue(@Null Timestamp value) {
		return value == null ? null : value.toLocalDateTime();
	}
}
