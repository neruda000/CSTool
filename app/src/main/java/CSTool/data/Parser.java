package CSTool.data;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import org.hsqldb.lib.StringUtil;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CSTool.model.FileEvent;
import CSTool.model.FileEvent.Status;

public class Parser {

	private Vector<FileEvent> eventi = new Vector<FileEvent>();
	
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(Parser.class);
	
	public Parser(FileInputStream file) throws IOException {
		
		Scanner sc = null;
		try {
		    
		    sc = new Scanner(file, "UTF-8");
		    while (sc.hasNextLine()) {
		    	String line = sc.nextLine();
		    	if(StringUtil.isEmpty(line)) continue;
			
				JSONObject obj = new JSONObject(line);
				
				Status state = Status.valueOf(obj.getString("state"));
				
				FileEvent event = new FileEvent(obj.getString("id"), state);
				event.setHost(obj.getString("host"));
				event.setTimestamp(obj.getLong("timestamp"));
				event.setType(obj.getString("type"));
				
				eventi.add(event);
				LOGGER.debug("Added event "+event.getId()+" state "+event.getState().toString());
		}
		}catch(Exception e) {
			LOGGER.error("Error parsing file", e);
		}
		finally {
			file.close();
			sc.close();
		}
	}

	public Vector<FileEvent> getEventi() {
		return eventi;
	}
	
	
}
