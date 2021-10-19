package CSTool.model;

public class DBEventEntry extends FileEvent {

	private static final long serialVersionUID = -1777492703556033170L;
	
	private boolean alert;
	private int duration;

	public DBEventEntry(String id) {
		super(id);
		
	}

	public boolean isAlert() {
		return alert;
	}

	public void setAlert(boolean alert) {
		this.alert = alert;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

}
