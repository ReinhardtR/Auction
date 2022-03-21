package shared.transferobjects;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
	private LocalDateTime dateTime;
	private String content;

	public Message(String content, LocalDateTime dateTime) {
		this.dateTime = dateTime;
		this.content = content;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public String getContent() {
		return content;
	}
}
