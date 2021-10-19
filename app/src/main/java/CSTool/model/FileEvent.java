package CSTool.model;

import java.io.Serializable;
import java.util.Objects;

public class FileEvent implements Serializable{
	
	private static final long serialVersionUID = 6547375154140520270L;
	
	private String id;
	private Status state;
	private String type;
	private String host;
	private long timestamp;
	
	public FileEvent(String id, Status state) {
		setState(state);
		setId(id);
	}
	
	public FileEvent(String id) {
		setId(id);
	}
	
	public enum Status {
		STARTED, FINISHED
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Status getState() {
		return state;
	}

	public void setState(Status state) {
		this.state = state;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public int hashCode() {
		return Objects.hash(host, id, type);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileEvent other = (FileEvent) obj;
		return Objects.equals(host, other.host) && Objects.equals(id, other.id) && Objects.equals(type, other.type);
	}

}
