package ru.projects.favourites.cassandra.logs;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import ru.projects.favourites.cassandra.repository.ExtCassandraRepository;

@Service
public class LogWorkerImpl implements LogWorker {

	@Autowired
	private LogParser parser;

	@Value("${logging.path}")
	private String logsPath;

	@Autowired
	private ExtCassandraRepository repository;

	@Override
	public void saveToDB(File file) {
		parser.parse(file).forEach(log -> repository.save(log));
	}

	@Scheduled(initialDelay = 1000, fixedDelay = 10000)
	private void detectLogs() {
		File file = new File(logsPath);
		saveToDB(file);
	}

}
