package selenium.functions;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;

public class SeleniumFunctions {

    private static final Logger log = Logger.getLogger(SeleniumFunctions.class);
    public static String FileName = "";
    public static String PagesFilePath = "src/test/resources/Pages/";

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static JsonNode readJson() throws IOException {
        File file = new File(PagesFilePath + FileName);

        if (file.exists()) {
            return objectMapper.readTree(file);
        } else {
            log.error("No existe el archivo: " + FileName);
            throw new IllegalStateException("No existe el archivo: " + FileName);
        }
    }

    public static JsonNode readEntity(String element) throws IOException {
        JsonNode jsonObject = readJson();

        if (jsonObject.has(element)) {
            JsonNode entity = jsonObject.get(element);
            log.info(entity.toString());
            return entity;
        } else {
            log.error("El elemento no existe en el archivo JSON: " + element);
            throw new IllegalStateException("El elemento no existe en el archivo JSON: " + element);
        }
    }
}
