package ru.projects.favourites.cassandra.repository;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.constraints.NotNull;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

import ru.projects.favourites.cassandra.logs.LogItem;
import ru.projects.favourites.cassandra.logs.LogLevel;

@Repository
public interface ExtCassandraRepository extends CassandraRepository<LogItem> {

	@Query("select * from logs where class_name = ?0 order by unformatted_timestamp ASC")
	List<LogItem> findByClassname(@NotNull String classname);

	@Query("select * from logs where formatted_timestamp < ?0 order by unformatted_timestamp ASC ALLOW FILTERING ")
	List<LogItem> findByDT(@NotNull LocalDateTime dt);

	@Query("select * from logs where formatted_level = ?0 order by unformatted_timestamp ASC")
	List<LogItem> findByLevel(@NotNull LogLevel level);

	@Query("select * from logs where formatted_level = ?0 and class_name = ?1 order by unformatted_timestamp ASC")
	List<LogItem> findByLevelAndClassname(@NotNull LogLevel level, @NotNull String classname);

	@Query("select * from logs where formatted_level = ?0 and class_name = ?1 and formatted_timestamp < ?2 order by unformatted_timestamp ASC ALLOW FILTERING ")
	List<LogItem> findByLevelAndClassnameAndTimestamp(@NotNull LogLevel level, @NotNull String classname,
			@NotNull LocalDateTime dt);

	@Query("select * from logs where text like '%?0%' order by unformatted_timestamp ASC ALLOW FILTERING ")
	List<LogItem> search(@NotNull String text);
}
