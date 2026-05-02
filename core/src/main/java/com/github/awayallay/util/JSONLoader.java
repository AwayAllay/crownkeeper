package com.github.awayallay.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

/**Loads usable JsonValues from given JSON files.*/
public class JSONLoader {

    private final JsonReader reader;


    public JSONLoader() {
        reader = new JsonReader();
    }

    /**Loads the values from the given JSON file.
     * @param filePath Path to the JSON file
     * @return  JsonValues of the JSON file.*/
    public JsonValue loadJSON(String filePath) {

        FileHandle file = Gdx.files.internal(filePath);
        String jsonString =  file.readString();

        return reader.parse(jsonString);
    }


    /**Parses the given JSONString into usable JSON values.
     * @param JSONString the Pre-read String from a JSON-file.
     * @return JsonValues of the String, null otherwise.*/
    public JsonValue getJSONFromString(String JSONString) {

        if (JSONString == null || JSONString.isEmpty()) {
            System.out.println("No JSONSTring provided");
            return null;
        }

        return reader.parse(JSONString);
    }


}
