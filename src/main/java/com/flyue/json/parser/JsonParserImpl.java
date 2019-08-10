package com.flyue.json.parser;

import com.flyue.json.type.JsonParserContext;
import com.flyue.json.type.JsonType;
import com.flyue.json.type.JsonValue;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Author: Liu Yuefei
 * @Date: Created in 2019/8/3 20:53
 * @Description:
 */
public class JsonParserImpl implements JsonParser {
    public int parser(JsonValue value, JsonParserContext context) {
        value.setType(JsonType.LEPT_NULL);
        removeWhiteSpace(context);
        return parseItemValue(context, value);

    }

    private int parseItemValue(JsonParserContext context, JsonValue value) {
        StringBuilder json = context.getJson();
        if (json.length() == 0) {
            System.err.println("待解析的字符串为空");
            return JsonParser.LEPT_PARSE_EXPECT_VALUE;
        } else {
            char indexChar = json.charAt(0);
            if (indexChar == 'n') {
                return parseNullValue(context, value);
            } else if (indexChar == 'f') {
                return parseFalseValue(context, value);
            } else if (indexChar == 't') {
                return parseTrueValue(context, value);
            } else if (indexChar == '\"') {
                return parseStringValue(context, value);
            } else if (indexChar == '[') {
                return parseArrayValue(context, value);
            } else if (indexChar == '{') {
                return parserObjectValue(context, value);
            } else {
                return parseNumberValue(context, value);
            }
        }
    }

    private int parserObjectValue(JsonParserContext context, JsonValue value) {
        StringBuilder json = context.getJson();
        if (json.length() < 2) {
            System.err.println("解析object时出错,错误的结尾");
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }
        value.setType(JsonType.LEPT_OBJECT);
        Map<String, JsonValue> map = new LinkedHashMap<>();
        do {
            JsonValue key = new JsonValue();
            context.getJson().deleteCharAt(0);
            removeWhiteSpace(context);
            parseStringValue(context, key);
            removeWhiteSpace(context);
            if (context.getJson().charAt(0) != ':') {
                System.out.println("解析object key结束符时失败");
                System.out.println("期待的字符是 :, 但实际是:" + context.getJson().substring(0, context.getJson().length() > 5 ? 5 : context.getJson().length()));
                return JsonParser.LEPT_PARSE_INVALID_VALUE;
            }
            context.getJson().deleteCharAt(0);
            JsonValue keyValue = new JsonValue();
            parser(keyValue, context);
            map.put(key.getStr(), keyValue);
            removeWhiteSpace(context);
        } while (context.getJson().charAt(0) == ',');
        if (context.getJson().charAt(0) == '}') {
            value.setMap(map);
            context.getJson().deleteCharAt(0);
            return JsonParser.LEPT_PARSE_OK;
        } else {
            System.out.println("解析object结束符时失败");
            System.out.println("期待的字符是}, 但实际是:" + context.getJson().substring(0, context.getJson().length() > 5 ? 5 : context.getJson().length()));
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }
    }

    private int parseArrayValue(JsonParserContext context, JsonValue value) {
        StringBuilder json = context.getJson();
        if (json.length() < 2) {
            System.out.println("解析array出错");
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }

        value.setType(JsonType.LEPT_ARRAY);
        List<JsonValue> arr = new LinkedList<>();
        do {
            JsonValue item = new JsonValue();
            context.getJson().deleteCharAt(0);
            parser(item, context);
            arr.add(item);
            removeWhiteSpace(context);
        } while (context.getJson().charAt(0) == ',');
        if (context.getJson().charAt(0) == ']') {
            value.setArray(arr);
            context.getJson().deleteCharAt(0);
            return JsonParser.LEPT_PARSE_OK;
        } else {
            System.out.println("解析数组结束符时失败");
            System.out.println("期待的字符是], 但实际是:" + context.getJson().substring(0, context.getJson().length() > 5 ? 5 : context.getJson().length()));
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }
    }

    private int parseStringValue(JsonParserContext context, JsonValue value) {
        StringBuilder json = context.getJson();
        if (json.length() < 2) {
            System.err.println("解析string时出错");
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }
        int endIndex = context.getJson().indexOf("\"", 1);
        value.setType(JsonType.LEPT_STRING);
        value.setStr(context.getJson().substring(1, endIndex));
        context.getJson().delete(0, endIndex + 1);
        return JsonParser.LEPT_PARSE_OK;
    }

    private int parseNumberValue(JsonParserContext context, JsonValue value) {
        StringBuilder json = context.getJson();
        int count = 0;
        for (int i = 0; i < json.length(); i++) {
            char item = json.charAt(i);
            if ((item - '0' >= 0 && item - '0' <= 9) || item == '.' || item == '-') {
                count++;
            } else {
                break;
            }
        }
        String strNumber = context.getJson().substring(0, count);
        value.setType(JsonType.LEPT_NUMBER);
        try {
            if (strNumber.indexOf('.') == -1) {
                long longValue = Long.valueOf(strNumber);
                if (longValue < Integer.MAX_VALUE) {
                    value.setNumber((int) longValue);
                } else {
                    value.setNumber(longValue);
                }
            } else {
                double numberValue = Double.valueOf(strNumber);
                value.setNumber(numberValue);
            }
            context.getJson().delete(0, count);
            return JsonParser.LEPT_PARSE_OK;
        } catch (Exception e) {
            System.err.println("解析number时出错");
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }

    }

    private int parseFalseValue(JsonParserContext context, JsonValue value) {
        if (context.getJson().length() < 5) {
            System.err.println("解析false时失败");
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }
        if (!"false".equals(context.getJson().substring(0, 5))) {
            System.err.println("解析false时失败");
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }
        value.setType(JsonType.LEPT_FALSE);
        context.getJson().delete(0, 5);
        return JsonParser.LEPT_PARSE_OK;
    }

    private int parseTrueValue(JsonParserContext context, JsonValue value) {
        if (context.getJson().length() < 4) {
            System.err.println("解析true时失败");
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }
        if (!"true".equals(context.getJson().substring(0, 4))) {
            System.err.println("解析true时失败");
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }
        value.setType(JsonType.LEPT_TRUE);
        context.getJson().delete(0, 4);
        return JsonParser.LEPT_PARSE_OK;
    }

    private int parseNullValue(JsonParserContext context, JsonValue value) {
        if (context.getJson().length() < 4) {
            System.err.println("解析null值出错");
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }
        if (!context.getJson().toString().startsWith("null")) {
            System.err.println("解析null值出错");
            return JsonParser.LEPT_PARSE_INVALID_VALUE;
        }
        value.setType(JsonType.LEPT_NULL);
        context.getJson().delete(0, 4);
        return JsonParser.LEPT_PARSE_OK;
    }


    public static final void removeWhiteSpace(JsonParserContext context) {
        StringBuilder json = context.getJson();
        int len = json.length();
        int st = 0;
        while ((st < len) && (json.charAt(st) == ' ' || json.charAt(st) == '\t' || json.charAt(st) == '\r' || json.charAt(st) == '\n')) {
            st++;
        }
        json.delete(0, st);
    }

    @Override
    public JsonValue parse(String jsonStr) {
        JsonParserContext context = new JsonParserContext(jsonStr);
        JsonValue value = new JsonValue();
        this.parser(value, context);
        return value;
    }
}
