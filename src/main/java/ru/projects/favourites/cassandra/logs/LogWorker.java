package ru.projects.favourites.cassandra.logs;

import java.io.File;

import javax.validation.constraints.NotNull;

public interface LogWorker {

	void saveToDB(@NotNull File file);
}
