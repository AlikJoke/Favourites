package ru.projects.favourites.cassandra.logs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;

@Service
public class LogParserImpl implements LogParser {

	private final static String LOG_EXTENSION = ".log";
	private final static String DT_PATTERN = "^(\\d{4}-\\d{2}-\\d{2}.+)";

	@Override
	public List<LogItem> parse(File file) throws RuntimeException {
		checkFile(file);

		List<LogItem> logs = Lists.newArrayList();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			Pattern pattern = Pattern.compile(DT_PATTERN);
			try (final BufferedReader tmp = reader) {
				tmp.lines().forEach(line -> {
					if (pattern.matcher(line).matches())
						logs.add(tokenizeAndBuild(line));
				});
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return logs;
	}

	@Override
	public List<LogItem> parse(String filePath) throws RuntimeException {
		File file = new File(filePath);

		List<LogItem> logs = Lists.newArrayList();
		getFiles(file).stream().filter(f -> f.getAbsolutePath().endsWith(LOG_EXTENSION))
				.forEach(f -> logs.addAll(parse(f)));

		return logs;
	}

	private List<File> getFiles(@NotNull File file) {
		checkFile(file);
		final List<File> files;
		if (file.isDirectory())
			files = getFilesFromDirectory(file);
		else
			files = Arrays.asList(file);

		return files;
	}

	private List<File> getFilesFromDirectory(@NotNull File directory) {
		checkFile(directory);
		if (!directory.isDirectory())
			throw new IllegalStateException();

		List<File> files = Lists.newArrayList();
		Stream.of(directory.listFiles()).forEach(file -> {
			if (file.isDirectory()) {
				files.addAll(getFilesFromDirectory(file));
			} else {
				if (file.getAbsolutePath().endsWith(".log"))
					files.add(file);
			}
		});

		return files;
	}

	private void checkFile(@NotNull File file) {
		if (!file.canRead())
			throw new SecurityException("Can't read directory: no access");

		if (!file.exists())
			throw new IllegalStateException(String.format("Can't find file with the filepath = '%s'", file.getPath()));
	}

	private LogItem tokenizeAndBuild(String line) {
		StringTokenizer tokenizer = new StringTokenizer(line);
		LogItem log = new LogItem();

		while (tokenizer.hasMoreTokens()) {
			if (log.getFormattedTimestamp() == null) {
				final Timestamp timestamp = Timestamp.valueOf(tokenizer.nextElement() + " " + tokenizer.nextToken());
				log.setFormattedTimestamp(timestamp.toLocalDateTime());
				log.setTimestamp(timestamp.getTime());
				continue;
			}

			if (log.getLevel() == null) {
				log.setLevel(LogLevel.value(tokenizer.nextToken()));
				continue;
			}

			if (log.getThread() == null) {
				log.setThread(tokenizer.nextToken("]") + "]");
				continue;
			}

			if (log.getClassName() == null) {
				log.setClassName(tokenizer.nextToken(": "));
				continue;
			}

			String text = Optional.fromNullable(log.getText()).or("");
			text = text + tokenizer.nextToken();
			log.setText(text);
		}

		return log;
	}
}
