package us.ryanhope.ledservice;

import com.palm.luna.LSException;
import com.palm.luna.service.LunaServiceThread;
import com.palm.luna.service.ServiceMessage;
import com.palm.luna.message.ErrorMessage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import org.json.JSONException;
import org.json.JSONObject;

public class LEDService extends LunaServiceThread {
	
	private class FileIOResult {
		boolean success;
		String result;
		FileIOResult(boolean success) {
			this.success = success;
			this.result = null;
		}
		FileIOResult(boolean success, String result) {
			this.success = success;
			this.result = result;
		}
	}

	private final String LED_PATH_LEFT = 
		"/sys/bus/i2c/devices/3-0032/leds:core_navi_left/brightness";
	private final String LED_PATH_CENTER = 
		"/sys/bus/i2c/devices/3-0032/leds:core_navi_center/brightness";
	private final String LED_PATH_RIGHT = 
		"/sys/bus/i2c/devices/3-0032/leds:core_navi_right/brightness";

	private FileIOResult readLEDBrightness(String sysfsPath) {
		BufferedReader input;
		FileReader fr;
		boolean success = true;
		String value = null;
		try {
			fr = new FileReader(sysfsPath);
			input = new BufferedReader(fr);
			value = input.readLine();
			input.close();
			fr.close();
		} catch (IOException e) {
			success = false;
		}	
		return new FileIOResult(success,value);
	}
	
	private FileIOResult writeLEDBrightness(String sysfsPath, String value) {
		BufferedWriter output;
		boolean success = true;
		try {
			output = new BufferedWriter(new FileWriter(sysfsPath,false));
			output.write(value);
			output.newLine();
			output.close();
		} catch (IOException e) {
			success = false;
		}
		return new FileIOResult(success);
	}
	
    @LunaServiceThread.PublicMethod
    public void setBrightnessLeftLED(ServiceMessage msg)
    throws JSONException, LSException {
        if (msg.getJSONPayload().has("value")) {
            String value = msg.getJSONPayload().getString("value");
            if (Integer.valueOf(value)>=0 && Integer.valueOf(value)<=100) {
            	FileIOResult result =
            		writeLEDBrightness(LED_PATH_LEFT, value);
            	if (result.success) {
            		JSONObject reply = new JSONObject();
            		reply.put("success", result.success);
            		msg.respond(reply.toString());
            		return;
            	}
            }
        }
        msg.respondError(ErrorMessage.ERROR_CODE_INVALID_PARAMETER, "You fail!");
    }
    
    @LunaServiceThread.PublicMethod
    public void setBrightnessCenterLED(ServiceMessage msg)
    throws JSONException, LSException {
        if (msg.getJSONPayload().has("value")) {
            String value = msg.getJSONPayload().getString("value");
            if (Integer.valueOf(value)>=0 && Integer.valueOf(value)<=100) {
            	FileIOResult result =
            		writeLEDBrightness(LED_PATH_CENTER, value);
            	if (result.success) {
            		JSONObject reply = new JSONObject();
            		reply.put("success", result.success);
            		msg.respond(reply.toString());
            		return;
            	}
            }
        }
        msg.respondError(ErrorMessage.ERROR_CODE_INVALID_PARAMETER, "You fail!");
    }
    
    @LunaServiceThread.PublicMethod
    public void setBrightnessRightLED(ServiceMessage msg)
    throws JSONException, LSException {
        if (msg.getJSONPayload().has("value")) {
            String value = msg.getJSONPayload().getString("value");
            if (Integer.valueOf(value)>=0 && Integer.valueOf(value)<=100) {
            	FileIOResult result =
            		writeLEDBrightness(LED_PATH_RIGHT, value);
            	if (result.success) {
            		JSONObject reply = new JSONObject();
            		reply.put("success", result.success);
            		msg.respond(reply.toString());
            		return;
            	}
            }
        }
        msg.respondError(ErrorMessage.ERROR_CODE_INVALID_PARAMETER, "You fail!");
    }
    
    @LunaServiceThread.PublicMethod
    public void getBrightnessLeftLED(ServiceMessage msg)
    throws JSONException, LSException {
    	FileIOResult result = readLEDBrightness(LED_PATH_CENTER);
    	JSONObject reply = new JSONObject();
    	if (result.success) {
    		reply.put("value",result.result);
    		msg.respond(reply.toString());
    	} else {
    		msg.respondError(ErrorMessage.ERROR_CODE_METHOD_EXCEPTION, "You fail!");
    	}
    }

    @LunaServiceThread.PublicMethod
    public void getBrightnessCenterLED(ServiceMessage msg)
    throws JSONException, LSException {
    	FileIOResult result = readLEDBrightness(LED_PATH_CENTER);
    	JSONObject reply = new JSONObject();
    	if (result.success) {
    		reply.put("value",result.result);
    		msg.respond(reply.toString());
    	} else {
    		msg.respondError(ErrorMessage.ERROR_CODE_METHOD_EXCEPTION, "You fail!");
    	}
    }
    
    @LunaServiceThread.PublicMethod
    public void getBrightnessRightLED(ServiceMessage msg)
    throws JSONException, LSException {
    	FileIOResult result = readLEDBrightness(LED_PATH_CENTER);
    	JSONObject reply = new JSONObject();
    	if (result.success) {
    		reply.put("value",result.result);
    		msg.respond(reply.toString());
    	} else {
    		msg.respondError(ErrorMessage.ERROR_CODE_METHOD_EXCEPTION, "You fail!");
    	}
    }
    
}
