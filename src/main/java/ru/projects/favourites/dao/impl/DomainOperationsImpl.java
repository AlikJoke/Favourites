package ru.projects.favourites.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ru.projects.favourites.dao.DomainOperations;
import ru.projects.favourites.dao.Queries;
import ru.projects.favourites.dao.mappers.DomainMapper;
import ru.projects.favourites.domain.DomainObject;
import ru.projects.favourites.domain.Favourite;
import ru.projects.favourites.domain.User;

@Service
public class DomainOperationsImpl implements DomainOperations<DomainObject> {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "userMapper")
	private DomainMapper<User> userMapper;

	@Resource(name = "favouriteMapper")
	private DomainMapper<Favourite> favouriteMapper;

	@Autowired
	private Queries queries;

	@Override
	public void save(DomainObject domain) {
		if (domain instanceof Favourite) {
			Favourite fv = (Favourite) domain;
			jdbcTemplate.update(queries.getFavouriteQueries().getInsertFavouriteQuery(fv), ps -> {
				ps.setString(1, fv.getUID());
				ps.setString(2, fv.getName());
				ps.setString(3, fv.getLink());
				ps.setTimestamp(4, Timestamp.valueOf(fv.getAddingDT()));
				ps.setTimestamp(5, fv.getDeletingDT() == null ? null : Timestamp.valueOf(fv.getDeletingDT()));
				ps.setInt(6, fv.getOrder());
				ps.setLong(7, fv.getCounter());
			});
		} else if (domain instanceof User) {
			User user = (User) domain;
			jdbcTemplate.update(queries.getUserQueries().getInsertUserQuery(user), ps -> {
				ps.setString(1, user.getUID());
				ps.setString(2, user.getEmail());
				ps.setDate(3, java.sql.Date.valueOf(user.getRegDate()));
				ps.setString(4, user.getPassword());
				ps.setTimestamp(5, user.getDeletingDT() == null ? null : Timestamp.valueOf(user.getDeletingDT()));
			});
		}
	}

	@Override
	public void update(DomainObject domain, String field, Object fieldValue) {
		jdbcTemplate.update(queries.getUpdateQuery(domain, field), ps -> paramTypeSetter(ps, fieldValue, 1));

	}

	@Override
	public boolean remove(DomainObject entity) {
		return jdbcTemplate.update(queries.getDeleteQuery(entity), ps -> ps.setString(1, entity.getUID())) > 0;
	}

	@Override
	public boolean remove(String entityType, String key) {
		return jdbcTemplate.update(queries.getDeleteQuery(entityType), ps -> ps.setString(1, key)) > 0;
	}

	@Override
	public List<? extends DomainObject> findAll(String username) {
		return jdbcTemplate.query(queries.getFavouriteQueries().getFindAllFavouritesQuery(), new Object[] { username },
				favouriteMapper);
	}

	@Override
	public DomainObject findById(boolean isFavourite, String uid) {
		// TODO
		return null;
	}

	private void paramTypeSetter(PreparedStatement ps, Object value, int position) {
		try {
			if (value instanceof String)
				ps.setString(position, (String) value);
			else if (value instanceof Timestamp)
				ps.setTimestamp(position, (Timestamp) value);
			else if (value instanceof LocalDateTime)
				ps.setTimestamp(position, Timestamp.valueOf((LocalDateTime) value));
			else if (value instanceof Date)
				ps.setDate(position, (Date) value);
			else if (value instanceof LocalDate)
				ps.setDate(position, Date.valueOf((LocalDate) value));
			else if (value instanceof Integer)
				ps.setInt(position, (Integer) value);
			else if (value instanceof Long)
				ps.setLong(position, (Long) value);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
