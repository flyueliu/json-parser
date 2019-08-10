package com.flyue.json.type;

import java.util.*;

/**
 * @Author: Liu Yuefei
 * @Date: Created in 2019/8/3 20:43
 * @Description:
 */
public class JsonValue {


    private Number number;

    private String str;

    private List<JsonValue> array;

    private Map<String, JsonValue> map;

    private JsonType type;

    public JsonType getType() {
        return type;
    }

    public void setType(JsonType type) {
        this.type = type;
    }


    public String getStr() {
        return str;
    }

    public void setStr(String str) {
        this.str = str;
    }

    public List<JsonValue> getArray() {
        return array;
    }

    public void setArray(List<JsonValue> array) {
        this.array = array;
    }

    public Object convert() {
        if (this.isBasicValue()) {
            return this.getBasicValue();
        } else if (this.type.equals(JsonType.LEPT_ARRAY)) {
            return this.convertToList();
        } else {
            return this.convertToMap();
        }

    }

    public Map<String, Object> convertToMap() {
        if (this.type.equals(JsonType.LEPT_OBJECT)) {
            Map<String, Object> outputMap = new LinkedHashMap<>();
            for (Map.Entry<String, JsonValue> item : this.map.entrySet()) {
                String key = item.getKey();
                JsonValue value = item.getValue();
                if (value.type.equals(JsonType.LEPT_NULL)) {
                    outputMap.put(key, null);
                } else if (value.type.equals(JsonType.LEPT_TRUE)) {
                    outputMap.put(key, true);
                } else if (value.type.equals(JsonType.LEPT_FALSE)) {
                    outputMap.put(key, false);
                } else if (value.type.equals(JsonType.LEPT_STRING)) {
                    outputMap.put(key, value.str);
                } else if (value.type.equals(JsonType.LEPT_NUMBER)) {
                    outputMap.put(key, value.number);
                } else if (value.type.equals(JsonType.LEPT_ARRAY)) {
                    outputMap.put(key, value.convertToList());
                } else {
                    outputMap.put(key, value.convertToMap());
                }
            }
            return outputMap;
        }
        throw new RuntimeException("not map type");
    }

    private static JsonType[] basicType = new JsonType[]{JsonType.LEPT_STRING,
            JsonType.LEPT_NULL,
            JsonType.LEPT_FALSE,
            JsonType.LEPT_NUMBER,
            JsonType.LEPT_TRUE};

    private boolean isBasicValue() {
        for (JsonType type : basicType) {
            if (this.type.equals(type)) {
                return true;
            }
        }
        return false;
    }

    private Object getBasicValue() {
        if (this.type.equals(JsonType.LEPT_NULL)) {
            return null;
        } else if (this.type.equals(JsonType.LEPT_TRUE)) {
            return true;
        } else if (this.type.equals(JsonType.LEPT_FALSE)) {
            return false;
        } else if (this.type.equals(JsonType.LEPT_STRING)) {
            return this.str;
        } else if (this.type.equals(JsonType.LEPT_NUMBER)) {
            return this.number;
        }
        throw new RuntimeException("is not basic type");
    }

    public List<Object> convertToList() {
        List<Object> outputList = new ArrayList<>();
        for (JsonValue item : this.array) {
            if (item.isBasicValue()) {
                outputList.add(item.getBasicValue());
            } else if (item.type.equals(JsonType.LEPT_ARRAY)) {
                outputList.add(item.convertToList());
            } else {
                outputList.add(item.convertToMap());
            }
        }
        return outputList;
    }

    @Override
    public String toString() {
        if (JsonType.LEPT_NULL.equals(this.type)) {
            return "null";
        } else if (JsonType.LEPT_TRUE.equals(this.type)) {
            return "true";
        } else if (JsonType.LEPT_STRING.equals(this.type)) {
            return "\"" + this.getStr() + "\"";
        } else if (JsonType.LEPT_ARRAY.equals(this.type)) {
            return this.array.toString();
        } else if (JsonType.LEPT_NUMBER.equals(this.type)) {
            return String.valueOf(this.number);
        } else if (JsonType.LEPT_OBJECT.equals(this.type)) {
            StringBuilder builder = new StringBuilder();
            builder.append("{");
            Iterator<Map.Entry<String, JsonValue>> iterator = this.map.entrySet().iterator();
            do {
                if (iterator.hasNext()) {
                    Map.Entry<String, JsonValue> item = iterator.next();
                    builder.append(String.format("\"%s\":%s", item.getKey(), item.getValue()));
                }
                if (iterator.hasNext()) {
                    builder.append(",");
                }
            } while (iterator.hasNext());
            builder.append("}");
            return builder.toString();
        }
        return "un implement";
    }

    public Map<String, JsonValue> getMap() {
        return map;
    }

    public void setMap(Map<String, JsonValue> map) {
        this.map = map;
    }

    public Number getNumber() {
        return number;
    }

    public void setNumber(Number number) {
        this.number = number;
    }
}
