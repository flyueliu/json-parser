package com.flyue.json.parser;

import com.flyue.json.type.JsonParserContext;
import com.flyue.json.type.JsonValue;

/**
 * @Author: Liu Yuefei
 * @Date: Created in 2019/8/3 20:43
 * @Description:
 */
public interface JsonParser {

    public static final int LEPT_PARSE_OK = 0;
    // 若一个 JSON 只含有空白，传回 LEPT_PARSE_EXPECT_VALUE
    public static final int LEPT_PARSE_EXPECT_VALUE = 1;
    //若一个值之后，在空白之后还有其他字符，传回 LEPT_PARSE_ROOT_NOT_SINGULAR
    public static final int LEPT_PARSE_INVALID_VALUE = 2;
    // 若值不是那三种字面值，传回 LEPT_PARSE_INVALID_VALUE
    public static final int LEPT_PARSE_ROOT_NOT_SINGULAR = 3;

    JsonValue parse(String jsonStr);

    int parser(JsonValue value, JsonParserContext context);

}
