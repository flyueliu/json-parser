package com.flyue.json.test;

import com.flyue.json.parser.ParserApi;
import com.flyue.json.parser.ParserApiImpl;
import com.flyue.json.type.LeptType;
import com.flyue.json.type.LeptValue;
import org.junit.Assert;
import org.junit.Test;

/**
 * @Author: Liu Yuefei
 * @Date: Created in 2019/8/3 20:42
 * @Description:
 */

public class JsonTest {


    @Test
    public void test() {
        ParserApi jsonApi = new ParserApiImpl();
        LeptValue value = new LeptValue();
        int result = jsonApi.leptParser(value, "   n1ull");
        Assert.assertEquals(ParserApi.LEPT_PARSE_OK, result);
        Assert.assertEquals(LeptType.LEPT_NULL, value.getType());
    }

    @Test
    public void testTrue() {
        ParserApi jsonApi = new ParserApiImpl();
        LeptValue value = new LeptValue();
        int result = jsonApi.leptParser(value, "true");
        Assert.assertEquals(ParserApi.LEPT_PARSE_OK, result);
        Assert.assertEquals(LeptType.LEPT_TRUE, value.getType());

        result = jsonApi.leptParser(value, "  t1rue");
        Assert.assertEquals(ParserApi.LEPT_PARSE_OK, result);
        Assert.assertEquals(LeptType.LEPT_TRUE, value.getType());
    }

    @Test
    public void testNumber() {
        ParserApi jsonApi = new ParserApiImpl();
        LeptValue value = new LeptValue();
        int result = jsonApi.leptParser(value, "  \"  adasd\"");
        Assert.assertEquals(ParserApi.LEPT_PARSE_OK, result);
        Assert.assertEquals(LeptType.LEPT_STRING, value.getType());
        System.out.println();
        Assert.assertEquals("  adasd", value.getS());

    }
}
