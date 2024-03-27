package me.lemonflux.fluxflips.utils;

import me.lemonflux.fluxflips.Reference;
import net.minecraft.client.*;
import org.apache.commons.io.*;
import me.lemonflux.fluxflips.modules.*;
import java.util.*;
import java.io.*;
import java.net.URL;

import com.google.gson.*;

public class Config
{
    private static File configFile;
    private static File licenseFile;
    private static String jsonL;
    private static JsonObject json;
	private static String apiUrl;
	private static File creditsFile;
    public static final Gson gson;
    
    public static void init() throws IOException {
        // Config File
        Config.configFile = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "//" + Utils.getPrefix("raw") + "//config.json");
        if (Config.configFile.exists() && !Config.configFile.isDirectory()) {
            final InputStream is = new FileInputStream(Config.configFile);
            final String jsonTxt = IOUtils.toString(is, "UTF-8");
            Config.json = new JsonParser().parse(jsonTxt).getAsJsonObject();
        }
        else {
            Config.configFile.getParentFile().mkdirs();
            Config.configFile.createNewFile();
            try (final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Config.configFile), "utf-8"))) {
                Config.json = new JsonObject();
                writer.write(Config.json.toString());
                writer.close();
            }
        }
        try (final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Config.configFile), "utf-8"))) {
            for (final Module m : Modules.modules) {
                if (!Config.json.has(m.getInternalID())) {
                    Config.json.add(m.getInternalID(), m.getDefaultValue());
                }
            }
            if (!Config.json.has("apikey")) {
                Config.json.add("apikey", Config.gson.toJsonTree((Object)""));
            }
            writer.write(Config.json.toString());
            writer.close();
        }
        // License File
    	Config.licenseFile = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "//" + Utils.getPrefix("raw") + "//license.key");
        if (Config.licenseFile.exists() && !Config.licenseFile.isDirectory()) {
            final InputStream is2 = new FileInputStream(Config.licenseFile);
            final String jsonTxt2 = IOUtils.toString(is2, "UTF-8");
            Config.jsonL = jsonTxt2;
            is2.close();
        }
        else {
            Config.licenseFile.getParentFile().mkdirs();
            Config.licenseFile.createNewFile();
            try (final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Config.licenseFile), "utf-8"))) {
                Config.jsonL = new String();
                writer.write(Utils.getPrefix("raw").toLowerCase() + "-example-key");
                writer.close();
            }
        }
        // Credits File
        Config.creditsFile = new File(Minecraft.getMinecraft().mcDataDir.getAbsolutePath() + "//" + Utils.getPrefix("raw") + "//credits.txt");
        if (Config.creditsFile.exists() && !Config.creditsFile.isDirectory()) {
        	try (final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Config.creditsFile), "utf-8"))) {
                writer.write(Utils.getPrefix("raw") + " " + Utils.getPrefix("version") + " made with <3 by " + Utils.getPrefix("dev"));
                writer.close();
            }
        }
        else {
            Config.creditsFile.getParentFile().mkdirs();
            Config.creditsFile.createNewFile();
            try (final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Config.creditsFile), "utf-8"))) {
            	writer.write(Utils.getPrefix("raw") + " " + Utils.getPrefix("version") + " made with <3 by " + Utils.getPrefix("developer"));
                writer.close();
            }
        }
        Config.apiUrl = Utils.getAPIUrl(getLicenseKey(), Reference.PREMIUM);
    }
    public static String api() {
    	return Config.apiUrl;
    }
    public static String getLicenseKey() {
    	InputStream is = null;
    	try {
    		is = new FileInputStream(Config.licenseFile);
    		//System.out.println(Config.licenseFile);
    	} catch(FileNotFoundException e) {
    		e.printStackTrace();
    	}
    	String jsonTxt = null;
    	try {
    		jsonTxt = IOUtils.toString(is, "UTF-8");
    		is.close();
    	} catch (IOException e2) {
    		e2.printStackTrace();
    	}
    	//System.out.println("HAD TO GET LICENSE KEY");
    	//System.out.println(jsonTxt.replaceAll("\n", ""));
    	return jsonTxt.replaceAll("\n", "");
    }
    public static JsonObject getConfig() {
        InputStream is = null;
        try {
            is = new FileInputStream(Config.configFile);
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String jsonTxt = null;
        try {
            jsonTxt = IOUtils.toString(is, "UTF-8");
        }
        catch (IOException e2) {
            e2.printStackTrace();
        }
        return new JsonParser().parse(jsonTxt).getAsJsonObject();
    }
    
    public static void write(final String string, final JsonElement jsonTree) {
        final JsonObject config = getConfig();
        config.add(string, jsonTree);
        try (final Writer writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(Config.configFile), "utf-8"))) {
            writer.write(config.toString());
            writer.close();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        catch (FileNotFoundException e2) {
            e2.printStackTrace();
        }
        catch (IOException e3) {
            e3.printStackTrace();
        }
    }
    
    static {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }
}
