package com.flyue.json.parser;

import com.flyue.json.type.LeptContext;
import com.flyue.json.type.LeptType;
import com.flyue.json.type.LeptValue;
import org.junit.Assert;

/**
 * @Author: Liu Yuefei
 * @Date: Created in 2019/8/3 20:53
 * @Description:
 */
public class ParserApiImpl implements ParserApi {
    @Override
    public int leptParser(LeptValue value, String json) {
        LeptContext context = new LeptContext();
        context.setJson(json);
        value.setType(LeptType.LEPT_NULL);
        leptParseWhitespace(context);
        return leptParserValue(context, value);

    }

    private int leptParserValue(LeptContext context, LeptValue value) {
        String json = context.getJson();
        if (json.isEmpty()) {
            System.err.println("待解析的字符串为空");
            return ParserApi.LEPT_PARSE_EXPECT_VALUE;
        } else if (json.toCharArray()[0] == 'n') {
            return leptParserNull(context, value);
        } else if (json.toCharArray()[0] == 'f') {
            return leptParserFalse(context, value);
        } else if (json.toCharArray()[0] == 't') {
            return leptParserTrue(context, value);
        } else if (json.toCharArray()[0] == '\"') {
            return leptParserString(context, value);
        } else {
            return leptParserNumber(context, value);
        }
    }

    private int leptParserString(LeptContext context, LeptValue value) {
        char[] chars = context.getJson().toCharArray();
        if (chars.length < 2) {
            System.err.println("解析string时出错");
            return ParserApi.LEPT_PARSE_INVALID_VALUE;
        }
        int count = 0;
        for (int i = 1; i < chars.length; i++) {
            if (chars[i] != '\"') {
                count++;
            }
        }
        value.setType(LeptType.LEPT_STRING);
        value.setS(context.getJson().substring(1, count + 1));
        context.setJson(context.getJson().substring(count + 2));
        return ParserApi.LEPT_PARSE_OK;
    }

    private int leptParserNumber(LeptContext context, LeptValue value) {
        char[] chars = context.getJson().toCharArray();
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if ((chars[i] - '0' >= 0 && chars[i] - '0' <= '9') || chars[i] == '.' || chars[i] == '-') {
                count++;
            }
        }
        try {
            double numberValue = Double.valueOf(context.getJson().substring(0, count));
            value.setType(LeptType.LEPT_NUMBER);
            value.setN(numberValue);
            context.setJson(context.getJson().substring(count));
            return ParserApi.LEPT_PARSE_OK;
        } catch (Exception e) {
            System.err.println("解析number时出错");
            return ParserApi.LEPT_PARSE_INVALID_VALUE;
        }
    }

    private int leptParserFalse(LeptContext context, LeptValue value) {
        if (context.getJson().length() < 5) {
            System.err.println("解析false时失败");
            return ParserApi.LEPT_PARSE_INVALID_VALUE;
        }
        if (!"false".equals(context.getJson().substring(0, 5))) {
            System.err.println("解析false时失败");
            return ParserApi.LEPT_PARSE_INVALID_VALUE;
        }
        value.setType(LeptType.LEPT_FALSE);
        context.setJson(context.getJson().substring(5));
        return ParserApi.LEPT_PARSE_OK;
    }

    private int leptParserTrue(LeptContext context, LeptValue value) {
        if (context.getJson().length() < 4) {
            System.err.println("解析true时失败");
            return ParserApi.LEPT_PARSE_INVALID_VALUE;
        }
        if (!"true".equals(context.getJson().substring(0, 4))) {
            System.err.println("解析true时失败");
            return ParserApi.LEPT_PARSE_INVALID_VALUE;
        }
        value.setType(LeptType.LEPT_TRUE);
        context.setJson(context.getJson().substring(4));
        return ParserApi.LEPT_PARSE_OK;
    }

    private int leptParserNull(LeptContext context, LeptValue value) {
        if (context.getJson().length() < 4) {
            System.err.println("解析null值出错");
            return ParserApi.LEPT_PARSE_INVALID_VALUE;
        }
        if (!context.getJson().startsWith("null")) {
            System.err.println("解析null值出错");
            return ParserApi.LEPT_PARSE_INVALID_VALUE;
        }
        value.setType(LeptType.LEPT_NULL);
        context.setJson(context.getJson().substring(4));
        return ParserApi.LEPT_PARSE_OK;
    }


    public static final void leptParseWhitespace(LeptContext context) {
        String json = context.getJson();
        char[] value = json.toCharArray();
        int len = value.length;
        int st = 0;
        char[] val = value;    /* avoid getfield opcode */
        while ((st < len) && (val[st] == ' ' || val[st] == '\t' || val[st] == '\r')) {
            st++;
        }
        context.setJson(json.substring(st));
    }
}
