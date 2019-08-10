package com.flyue.json.type;

import java.util.*;

/**
 * @Author: Liu Yuefei
 * @Date: Created in 2019/8/3 20:43
 * @Description:
 */
public class JsonValue {


    private double decimal;

    private long number;

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

    public double getDecimal() {
        return decimal;
    }

    public void setDecimal(double decimal) {
        this.decimal = decimal;
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

    @Override
    public String toString() {
        if (JsonType.LEPT_NULL.equals(this.type)) {
            return "null";
        } else if (JsonType.LEPT_TRUE.equals(this.type)) {
            return "true";
        } else if (JsonType.LEPT_STRING.equals(this.type)) {
            return "\"" + this.getStr() + "\"";
        } else if (JsonType.LEPT_NUMBER.equals(this.type)) {
            return String.valueOf(this.decimal);
        } else if (JsonType.LEPT_ARRAY.equals(this.type)) {
            return this.array.toString();
        } else if (JsonType.LEPT_LONG.equals(this.type)) {
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

    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }
}
