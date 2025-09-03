package utils;

import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class JasonReader {
    private final String test_data_path = "src/test/java/test-data/";
    String jsonReader;
    String jsonFileName;

    public JasonReader(String jsonFileName){
        this.jsonFileName = jsonFileName;
        try {
            JSONObject data = (JSONObject) new JSONParser().parse(new FileReader(test_data_path + jsonFileName + ".json"));
            jsonReader = data.toJSONString();
        } catch (Exception e){
            System.out.println("Error reading json file");
        }
    }


    public String getJsonData(String jsonPath)
    {
        try {
            return JsonPath.read(jsonReader, jsonPath);
        } catch (Exception e) {
            System.out.println("Error reading json file");
            return "";
        }
    }
}
