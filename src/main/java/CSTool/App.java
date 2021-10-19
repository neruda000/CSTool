/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package CSTool;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import CSTool.data.Parser;
import CSTool.data.SaveEntry;
import CSTool.model.FileEvent;
import CSTool.model.DBEventEntry;

public class App {
	private static final Logger LOGGER = 
			LoggerFactory.getLogger(App.class);
	
    public String welcome() {
        return "Analyze the log...\nPlease insert log path: ";
    }

    public static void main(String[] args) {
        System.out.println(new App().welcome());
        Scanner scanner =  new Scanner(System.in);
        String path = scanner.next();
        
        File file = checkFileExists(path);
        if (!file.isFile()) {
        	LOGGER.error("File not found "+path);
        	return;
        }
        
        LOGGER.info("File found! "+path);
        
        try {
        	Parser parser = new Parser(file);
        	
        	Set<String> indexes = new HashSet<String>();
        	
        	Vector<FileEvent> eventi = parser.getEventi();
        	
        	Map<FileEvent, FileEvent> matches = new HashMap<FileEvent, FileEvent>();
        	
        	/**
        	 * Event is equal if id, host, type are equals
        	 */
        	for (int i = 0; i < eventi.size(); i++) {
        		FileEvent event = eventi.get(i);
        		
        		if (!indexes.contains(event.getId())) {
        			indexes.add(event.getId());
        			matches.put(event, null);
        		} else {
        			matches.put(event, event);
        		}
        	}
        	
        	findMatchesAndSave(matches);
        	
		} catch (Exception e) {
			LOGGER.error("Error parsing file "+path+"\nMessage: "+e.getMessage());
		}
        finally {
        	SaveEntry.shutDown();
        }
        
        
        
    }
    
    private static File checkFileExists(String path) {
    	File file = null;
    	try {
    		file = new File(path);
    		if (file.isFile()) {
    			return file;
    		}
    		
    	}catch(Exception fnf) {
    		System.out.println("error");
    	}
    	
    	return file;
    }
    
    private static void findMatchesAndSave(Map<FileEvent, FileEvent> matches) {
    	
    	matches.keySet().forEach(e -> 	{
    										long start = e.getTimestamp();
    										long end = start;
    										if(matches.get(e) != null) {
    											 end = ((FileEvent)matches.get(e)).getTimestamp();
    										}
    										Long duration = end - start;
    										
    										saveEventEntry(e, duration.intValue());
  
    									}
    							);
    }

	private static void saveEventEntry(FileEvent e, int duration) {
		DBEventEntry entry = new DBEventEntry(e.getId());
		entry.setHost(e.getHost());
		entry.setType(e.getType());
		entry.setDuration(duration);
		entry.setAlert(duration > 4);
		
		try {
			SaveEntry.save(entry);
		} catch (SQLException e1) {
			LOGGER.error("ERROR persisting entry: "+entry.getId()+" Message: "+e1.getMessage(), e1);
		}
		
	}
}
