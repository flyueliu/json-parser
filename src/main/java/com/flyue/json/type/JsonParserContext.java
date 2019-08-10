package com.flyue.json.type;

import java.util.Objects;

/**
 * @Author: Liu Yuefei
 * @Date: Created in 2019/8/3 21:05
 * @Description: 解析上下文
 */
public class JsonParserContext {

    private StringBuilder json;

    public JsonParserContext(String json) {
        this.json = new StringBuilder(json);
    }

    public StringBuilder getJson() {
        return json;
    }

    public void setJson(StringBuilder json) {
        this.json = json;
    }
}
