package messages;

public class ToastrMessage {
	int index;
	String type;
	String message;
	String raumId;

	public ToastrMessage(int index, String type, String message, String raumId) {
		this.index = index;
		this.type = type;
		this.message = message;
		this.raumId = raumId;
	}

	public long getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
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
