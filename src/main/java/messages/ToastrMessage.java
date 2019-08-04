package messages;

import java.time.LocalDateTime;

public class ToastrMessage {
	String type;
	String message;
	String raumId;
	LocalDateTime createdAt;
	boolean onlyLogging;
    String origin;
    String target;
    String toastrType;

	public ToastrMessage(String type, String message, String raumId, LocalDateTime createdAt, boolean onlyLogging, String toastrType) {
		this.type = type;
		this.message = message;
		this.raumId = raumId;
		this.createdAt = createdAt;
		this.onlyLogging = onlyLogging;
		this.toastrType = toastrType;
	}
	
	
	
	public String getToastrType() {
		return toastrType;
	}

	public void setToastrType(String toastrType) {
		this.toastrType = toastrType;
	}

	public boolean isOnlyLogging() {
		return onlyLogging;
	}

	public void setOnlyLogging(boolean onlyLogging) {
		this.onlyLogging = onlyLogging;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getRaumId() {
		return raumId;
	}

	public void setRaumId(String raumId) {
		this.raumId = raumId;
	}

}
