package ru.projects.favourites.cassandra.logs;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.cassandra.mapping.Column;
import org.springframework.data.cassandra.mapping.PrimaryKey;
import org.springframework.data.cassandra.mapping.Table;

@Table("logs")
public class LogItem {

	@PrimaryKey
	private UUID uid;

	@Column("level")
	private LogLevel level;

	@Column("formatted_timestamp")
	private LocalDateTime formattedTimestamp;

	@Column("unformatted_timestamp")
	private long timestamp;

	@Column("thread_info")
	private String thread;

	@Column("class_name")
	private String className;

	@Column("text")
	private String text;

	public LogItem() {
		this.uid = UUID.randomUUID();
	}

	public UUID getUID() {
		return uid;
	}

	public void setLogLevel(LogLevel level) {
		this.level = level;
	}

	public LogLevel getLevel() {
		return level;
	}

	public void setLevel(LogLevel level) {
		this.level = level;
	}

	public LocalDateTime getFormattedTimestamp() {
		return formattedTimestamp;
	}

	public void setFormattedTimestamp(LocalDateTime formattedTimestamp) {
		this.formattedTimestamp = formattedTimestamp;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getThread() {
		return thread;
	}

	public void setThread(String thread) {
		this.thread = thread;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
