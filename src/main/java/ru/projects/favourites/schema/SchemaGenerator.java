package ru.projects.favourites.schema;

import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.projects.favourites.dao.Queries;
import ru.projects.favourites.domain.EntityType;

/**
 * Класс, выполняющий генерацию схемы БД приложения.
 * 
 * @see JdbcTemplate
 * 
 * @author Alimurad A. Ramazanov
 * @since 16.08.2017
 * @version 1.0.1
 *
 */
@Component
public class SchemaGenerator {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Queries queries;

	private final static String[] FV_INDEXED_COLUMNS = new String[] { "NAME", "DELETING_DT", "ORDER_FV", "COUNTER",
			"USERNAME" };

	private final static String[] USER_INDEXED_COLUMNS = new String[] { "EMAIL", "DELETING_DT", "LAST_LOGGED" };

	@PostConstruct
	public void buildSchema() {
		this.generateSchemaIfNotExists();
	}

	private void generateSchemaIfNotExists() {
		Boolean userExists = jdbcTemplate.queryForObject(queries.getSchemaBuilder().checkTableExistsQuery(),
				Boolean.class, new Object[] { EntityType.USER.getName() });
		if (!userExists) {
			jdbcTemplate.execute(queries.getSchemaBuilder().getQueryToCreateTableUsers());
			Stream.of(USER_INDEXED_COLUMNS).forEach(columnName -> jdbcTemplate
					.execute(queries.getSchemaBuilder().getQueryToCreateIndex(EntityType.USER.getName(), columnName)));
		}

		Boolean fvExists = jdbcTemplate.queryForObject(queries.getSchemaBuilder().checkTableExistsQuery(),
				Boolean.class, new Object[] { EntityType.FAVOURITE.getName() });
		if (!fvExists) {
			jdbcTemplate.execute(queries.getSchemaBuilder().getQueryToCreateTableFavourites());
			Stream.of(FV_INDEXED_COLUMNS).forEach(columnName -> jdbcTemplate.execute(
					queries.getSchemaBuilder().getQueryToCreateIndex(EntityType.FAVOURITE.getName(), columnName)));

		}
	}
}
