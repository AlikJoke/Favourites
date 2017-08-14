package ru.projects.favourites.dao.mappers;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.springframework.jdbc.core.RowMapper;

public abstract class DomainMapper<T> implements RowMapper<T> {

	protected static final String UID_FIELD = "uid";
	protected static final String DELETING_DT_FIELD = "deletingDT";

	protected final LocalDateTime getLocalDateTimeValue(Timestamp value) {
		return value == null ? null : value.toLocalDateTime();
	}
}
