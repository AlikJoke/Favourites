package ru.projects.favourites.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import ru.projects.favourites.dao.DomainOperations;
import ru.projects.favourites.dao.Queries;
import ru.projects.favourites.dao.mappers.DomainMapper;
import ru.projects.favourites.domain.DomainObject;
import ru.projects.favourites.domain.Favourite;

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
		//
	}

	@Override
	public void update(DomainObject domain, String field) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean remove(DomainObject entity) {
		// TODO Auto-generated method stub
		return false;
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

}
