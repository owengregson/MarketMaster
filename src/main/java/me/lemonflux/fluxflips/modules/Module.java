package me.lemonflux.fluxflips.modules;

import com.google.gson.*;

public class Module
{
    private String name;
    private String description;
    private Category category;
    private Type type;
    private String internalID;
    private JsonElement defaultValue;
    private int page;
    
    public Module(final String name, final String description, final Category category, final Type type, final String internalID, final JsonElement defaultValue, final int page) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.type = type;
        this.internalID = internalID;
        this.defaultValue = defaultValue;
        this.page = page;
    }
    
    public int getPage() {
        return this.page;
    }
    
    public JsonElement getDefaultValue() {
        return this.defaultValue;
    }
    
    public Type getType() {
        return this.type;
    }
    
    public String getInternalID() {
        return this.internalID;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Category getCategory() {
        return this.category;
    }
}
