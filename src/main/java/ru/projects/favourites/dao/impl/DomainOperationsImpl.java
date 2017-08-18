package ru.projects.favourites.dao.impl;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Objects;
import com.google.common.collect.Maps;

import ru.projects.favourites.dao.DomainOperations;
import ru.projects.favourites.dao.Queries;
import ru.projects.favourites.dao.mappers.DomainMapper;
import ru.projects.favourites.dao.mappers.FavouriteMapper;
import ru.projects.favourites.dao.mappers.UserMapper;
import ru.projects.favourites.domain.DomainObject;
import ru.projects.favourites.domain.EntityType;
import ru.projects.favourites.domain.Favourite;
import ru.projects.favourites.domain.User;

@Service
@Repository
public class DomainOperationsImpl implements DomainOperations {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Resource(name = "userMapper")
	private RowMapper<User> userMapper;

	@Resource(name = "favouriteMapper")
	private RowMapper<Favourite> favouriteMapper;

	@Resource(name = "userMapper")
	private ResultSetExtractor<User> userExtractor;

	@Resource(name = "favouriteMapper")
	private ResultSetExtractor<Favourite> favouriteExtractor;

	@Autowired
	private Queries queries;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
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
				ps.setTimestamp(6, user.getLastLoggedDT() == null ? null : Timestamp.valueOf(user.getLastLoggedDT()));
			});
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void update(EntityType entityType, DomainObject current) {
		if (current.isNew())
			throw new IllegalStateException();

		Map<String, Object> fields = Maps.newHashMap();

		DomainObject old = this.findById(entityType, current.getUID());

		if (!Objects.equal(old.getDeletingDT(), current.getDeletingDT()))
			fields.put(DomainMapper.DELETING_DT_FIELD, current.getDeletingDT());

		switch (entityType) {
		case USER:
			User oldUser = (User) old;
			User currentUser = (User) current;

			if (!Objects.equal(oldUser.getEmail(), currentUser.getEmail()))
				fields.put(UserMapper.EMAIL_FIELD, currentUser.getEmail());

			if (!Objects.equal(oldUser.getLastLoggedDT(), currentUser.getLastLoggedDT()))
				fields.put(UserMapper.LAST_LOGGED_FIELD, currentUser.getLastLoggedDT());

			if (!Objects.equal(oldUser.getPassword(), currentUser.getPassword()))
				fields.put(UserMapper.PASSWORD_FIELD, currentUser.getPassword());

			break;
		case FAVOURITE:
			Favourite oldFv = (Favourite) old;
			Favourite currentFv = (Favourite) current;

			if (!Objects.equal(oldFv.getCounter(), currentFv.getCounter()))
				fields.put(FavouriteMapper.COUNTER_FIELD, currentFv.getCounter());

			if (!Objects.equal(oldFv.getOrder(), currentFv.getOrder()))
				fields.put(FavouriteMapper.ORDER_FIELD, currentFv.getOrder());

			if (!Objects.equal(oldFv.getName(), currentFv.getName()))
				fields.put(FavouriteMapper.NAME_FIELD, currentFv.getName());

			if (!Objects.equal(oldFv.getLink(), currentFv.getLink()))
				fields.put(FavouriteMapper.LINK_FIELD, currentFv.getLink());

			break;
		default:
			throw new IllegalArgumentException();
		}

		fields.entrySet().forEach(entry -> jdbcTemplate.update(queries.getUpdateQuery(entityType, entry.getKey()),
				ps -> paramTypeSetter(ps, entry.getValue(), 1)));

	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean remove(DomainObject entity) {
		return jdbcTemplate.update(queries.getDeleteQuery(EntityType.value(entity.getEntityName())),
				ps -> ps.setString(1, entity.getUID())) > 0;
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public boolean remove(EntityType entityType, String key) {
		return jdbcTemplate.update(queries.getDeleteQuery(entityType.getName()), ps -> ps.setString(1, key)) > 0;
	}

	@Override
	public List<? extends DomainObject> findAll(String username) {
		return jdbcTemplate.query(queries.getFavouriteQueries().getFindAllFavouritesQuery(), new Object[] { username },
				favouriteMapper);
	}

	@SuppressWarnings("unchecked")
	@Override
	public DomainObject findById(EntityType entityType, String uid) {
		if (entityType == EntityType.FAVOURITE)
			return jdbcTemplate.query(queries.getFindQuery(entityType), new Object[] { uid }, favouriteExtractor);
		else
			return jdbcTemplate.query(queries.getFindQuery(entityType), new Object[] { uid }, userExtractor);
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

	@Override
	public List<User> findUsers(LocalDateTime lastLogged) {
		return jdbcTemplate.query(queries.getUserQueries().getFindUsersByLoggedDT(),
				new Object[] { Timestamp.valueOf(lastLogged) }, userMapper);
	}
}
