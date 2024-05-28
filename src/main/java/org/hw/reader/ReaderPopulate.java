package org.hw.reader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import lombok.Data;
import org.hw.settings.Settings;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class ReaderPopulate {
    public List<Map<String, String>> getValuesToFill(String nameTable) {
        List<Map<String, String>> values = new ArrayList<>();

        String filePath = Settings.getInstance().getString(Settings.FILE_NAME_POPULATE_DB_JSON);
        FileReader fileReader = null;
        try {
            fileReader = new FileReader(filePath);
        } catch (IOException e) {
            e.printStackTrace();
            return values;
        }

        JsonObject jsonObject = JsonParser.parseReader(fileReader).getAsJsonObject();
        Gson gson = new Gson();

        Type mapType = new TypeToken<Map<String, List<Map<String, String>>>>() {
        }.getType();
        Map<String, List<Map<String, String>>> data = gson.fromJson(jsonObject, mapType);

        return data.get(nameTable);
    }
}
