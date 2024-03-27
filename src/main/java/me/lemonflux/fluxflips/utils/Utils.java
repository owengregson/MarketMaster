package me.lemonflux.fluxflips.utils;

import com.google.gson.*;

import me.lemonflux.fluxflips.Reference;
import net.minecraft.util.EnumChatFormatting;
import scala.util.parsing.json.JSON;
import scala.util.parsing.json.JSONObject;

import java.io.*;
import java.net.*;
import java.text.*;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

public class Utils
{
    private static String[] suffix;
    private static int MAX_LENGTH;
    
    public static JsonElement getJson(final String jsonUrl) {
        try {
            final URL url = new URL(jsonUrl);
            final URLConnection conn = url.openConnection();
            conn.setRequestProperty("Connection", "close");
            conn.setConnectTimeout(300);
            return new JsonParser().parse((Reader)new InputStreamReader(conn.getInputStream()));
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String getVersion() {
    	return "v1.0";
    }
    public static Map getColors(String scheme) {
    	Map<String, EnumChatFormatting> colors = new HashMap<String, EnumChatFormatting>();
    	if (scheme == "red") {
    		colors.put("dark", EnumChatFormatting.DARK_RED);
        	colors.put("light", EnumChatFormatting.RED);
    	} else if (scheme == "blue") {
    		colors.put("dark", EnumChatFormatting.DARK_BLUE);
        	colors.put("light", EnumChatFormatting.BLUE);
    	} else if (scheme == "aqua") {
    		colors.put("dark", EnumChatFormatting.DARK_AQUA);
        	colors.put("light", EnumChatFormatting.AQUA);
    	} else if (scheme == "yellow") {
    		colors.put("dark", EnumChatFormatting.GOLD);
        	colors.put("light", EnumChatFormatting.YELLOW);
    	} else if (scheme == "green") {
    		colors.put("dark", EnumChatFormatting.DARK_GREEN);
        	colors.put("light", EnumChatFormatting.GREEN);
    	} else if (scheme == "purple") {
    		colors.put("dark", EnumChatFormatting.DARK_PURPLE);
        	colors.put("light", EnumChatFormatting.LIGHT_PURPLE);
    	} else if (scheme == "gray") {
    		colors.put("dark", EnumChatFormatting.GRAY);
        	colors.put("light", EnumChatFormatting.WHITE);
    	}
    	return colors;
    }
	private static String getFirstSegment(String name) {
		// Matches any character until the second uppercase letter is found
		return name.replaceAll("([A-Z][^A-Z]*)([A-Z].*)", "$1");
	}

	private static String getSecondSegment(String name, String firstSegment) {
		// Removes the first segment from the full name
		return name.substring(firstSegment.length());
	}
    public static String getPrefix(String type) {
		final String allName = Reference.NAME;
		final String name1 = getFirstSegment(allName);
		final String name2 = getSecondSegment(allName, name1);
        final String developer = Reference.DEVELOPER;
        
        // ['free', 'premium']
        final String licenseType = Reference.PREMIUM ? "premium" : "free";
        // ['red', 'blue', 'aqua', 'yellow', 'green', 'purple', 'gray']
        final String colorScheme = Reference.colorScheme.toString().toLowerCase();

        switch (type) {
            case "raw":
                return allName;
            case "dev":
                return developer;
            case "firstLets":
                return name1.substring(0, 1).toLowerCase() + name2.substring(0, 1).toLowerCase();
            case "colors":
                return colorScheme;
            case "licType":
                return licenseType;
            default:
                return EnumChatFormatting.BLACK + "[" + getColors(colorScheme).get("dark") + name1 + getColors(colorScheme).get("light") + name2 + EnumChatFormatting.BLACK + "]" + EnumChatFormatting.GRAY + " ";
        }
    }
    public static Integer getIntegerColor() {
    	final String scheme = getPrefix("colors");
    	if (scheme == "red") {
    		return 16733525;
    	} else if (scheme == "blue") {
    		return 5592575;
    	} else if (scheme == "aqua") {
    		return 5636095;
    	} else if (scheme == "yellow") {
    		return 16777045;
    	} else if (scheme == "green") {
    		return 5635925;
    	} else if (scheme == "purple") {
    		return 16733695;
    	} else if (scheme == "gray") {
    		return 16777215;
    	} else {
    		return 16777215;
    	}
    	
    }
    public static String format(final double value) {
        String r;
        for (r = new DecimalFormat("##0E0").format(value), r = r.replaceAll("E[0-9]", Utils.suffix[Character.getNumericValue(r.charAt(r.length() - 1)) / 3]); r.length() > Utils.MAX_LENGTH || r.matches("[0-9]+\\.[a-z]"); r = r.substring(0, r.length() - 2) + r.substring(r.length() - 1)) {}
        return r;
    }
    
    public static String removeColorCodes(final String in) {
        return in.replaceAll("(?i)\\u00A7.", "");
    }
    
    static {
        Utils.suffix = new String[] { "", "k", "m", "b", "t" };
        Utils.MAX_LENGTH = 4;
    }

	public static JsonObject checkLicense(String license, boolean premium) {
        return new JsonParser().parse(sendGetRequest(formatAuthURL(license, premium, ""))).getAsJsonObject();
    }

	private static String formatAuthURL(String license, boolean premium, String request) {
		return ("http://redirectlly.com/auth?key=" + license + "&type=" + (premium ? "premium" : "free") + "&product=" + getPrefix("raw").toLowerCase() + (request.isEmpty() ? "" : "&request=" + request));
	}

	public static String getAPIUrl(String license, boolean premium) {
		final String URL = formatAuthURL(license, premium, "api");
		final String response = sendGetRequest(URL);
		final JsonParser JParser = new JsonParser();
		final JsonObject newJSON = JParser.parse(response).getAsJsonObject();
		final String requestedData = newJSON.get("response").getAsString();
		final String postFilter = requestedData.replace("\n", "").replace("\r", "");
		return postFilter;
	}
	private static String sendGetRequest(final String urlString) {
		String response = "Error!";
		try {
			final HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
			connection.setRequestMethod("GET");
			connection.setReadTimeout(1000);
			connection.setRequestProperty("User-Agent", "Mozilla/5.0");
			connection.connect();
			InputStreamReader isr = new InputStreamReader(connection.getInputStream());
			BufferedReader in = new BufferedReader(isr);
			response = in.lines().collect(Collectors.joining());
			in.close();
		} catch (Exception e) {
			System.out.println("Failed to send GET request: " + e.getMessage());
		}
		int bodyStart = response.indexOf("<font");
		int bodyStartEnd = response.indexOf(">", bodyStart);
		int bodyEnd = response.indexOf("</font");
		response = response.substring(bodyStartEnd + 1, bodyEnd);
		response = response.replace(" ", "");
		return response;
	}
}
