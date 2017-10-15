package pl.vrp.configuration;

import pl.vrp.enums.ExecuteType;
import pl.vrp.enums.FileType;

public class ConfigurationHelper {

	public static ExecuteType getExecuteType(FileType fileType){
    	if (fileType.equals(FileType.RANDOMIZE)){
    		return ExecuteType.RANDOMIZE;
    	} else if( ( fileType.equals(FileType.POSITION_DIR)) || (fileType.equals(FileType.DISTANCE_DIR)) ) {
    		return ExecuteType.DIR_EXECUTE;
    	} else {
    		return ExecuteType.FILE_EXECUTE;
    	}
	}
}
