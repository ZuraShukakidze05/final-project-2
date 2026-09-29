package ge.tbc.testautomation.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ge.tbc.testautomation.constants.Constants;

public class JsonUtils {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static JsonNode parse(String json) {
        try {
            return MAPPER.readTree(json);
        } catch (Exception e) {
            throw new RuntimeException(Constants.FAILED_MESSAGE + json, e);
        }
    }

    public static boolean bodyContains(String json, String value) {
        if (json == null) return false;
        try {
            return MAPPER.readTree(json).toString().contains(value);
        } catch (Exception e) {
            return false;
        }
    }

    public static JsonNode findList(JsonNode root) {
        if (root.isArray()) return root;
        for (JsonNode child : root) {
            if (child.isArray()) return child;
        }
        return null;
    }
}