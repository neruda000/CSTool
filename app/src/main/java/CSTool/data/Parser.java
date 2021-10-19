package CSTool.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

import org.hsqldb.lib.StringUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CSTool.model.FileEvent;
import CSTool.model.FileEvent.Status;

public class Parser {

	private File file;
	private Vector<FileEvent> eventi = new Vector<FileEvent>();
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(Parser.class);
	public Parser(File file) throws IOException {
		this.file = file;
		
		FileReader fr = new FileReader(file);
		
		BufferedReader br = new BufferedReader(fr);
		
		String line;
		while( (line = br.readLine()) != null) {
			if(StringUtil.isEmpty(line)) continue;
			
			JSONObject obj = new JSONObject(line);
			
			
			Status state = Status.valueOf(obj.getString("state"));
			
			FileEvent event = new FileEvent(obj.getString("id"), state);
			event.setHost(obj.getString("host"));
			event.setTimestamp(obj.getLong("timestamp"));
			event.setType(obj.getString("type"));
			
			eventi.add(event);
			LOGGER.info("Added event "+event.getId()+" state "+event.getState().toString());
		}
		
		br.close();
		fr.close();
	}

	public Vector<FileEvent> getEventi() {
		return eventi;
	}
	
	
}
