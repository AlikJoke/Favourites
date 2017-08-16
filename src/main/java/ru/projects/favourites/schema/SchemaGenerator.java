package ru.projects.favourites.schema;

import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.projects.favourites.dao.Queries;

@Component
public class SchemaGenerator {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private Queries queries;

	private final static String FV_TABLE_NAME = "FAVOURITE";
	private final static String[] FV_INDEXED_COLUMNS = new String[] { "NAME", "DELETING_DT", "ORDER", "COUNTER",
			"USERNAME" };

	private final static String USER_TABLE_NAME = "USER";
	private final static String[] USER_INDEXED_COLUMNS = new String[] { "EMAIL", "DELETING_DT" };

	@PostConstruct
	public void buildSchema() {
		this.generateSchemaIfNotExists();
	}
	
	public void generateSchemaIfNotExists() {
		Boolean userExists = jdbcTemplate.queryForObject(queries.getSchemaBuilder().checkTableExistsQuery(),
				Boolean.class, new Object[] { USER_TABLE_NAME });
		if (!userExists) {
			jdbcTemplate.execute(queries.getSchemaBuilder().getQueryToCreateTableUsers());
			Stream.of(USER_INDEXED_COLUMNS).forEach(columnName -> jdbcTemplate
					.execute(queries.getSchemaBuilder().getQueryToCreateIndex(USER_TABLE_NAME, columnName)));
		}
		
		Boolean fvExists = jdbcTemplate.queryForObject(queries.getSchemaBuilder().checkTableExistsQuery(),
				Boolean.class, new Object[] { FV_TABLE_NAME });
		if (!fvExists) {
			jdbcTemplate.execute(queries.getSchemaBuilder().getQueryToCreateTableFavourites());
			Stream.of(FV_INDEXED_COLUMNS).forEach(columnName -> jdbcTemplate
					.execute(queries.getSchemaBuilder().getQueryToCreateIndex(FV_TABLE_NAME, columnName)));
		}
	}
}
