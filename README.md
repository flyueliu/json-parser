### Java实现Json解析器

#### 1. 使用方式
```java
  String s = "{"code":0,"msg":"","data":[{"comic_id":25717,"title":"鬼灭之刃","author":["吾峠呼世晴","集英社"],"vertical_cover":"http://i0.hdslb.com/bfs/manga-static/c937a55af0a114d4f57bfe266aba3e90fac6aaa2.jpg","is_finish":0,"last_ord":171,"last_short_title":"169","styles":[{"id":1013,"name":"冒险"},{"id":998,"name":"奇幻"}],"total":171,"last_rank":1},{"comic_id":25717,"title":"鬼灭之刃","author":["吾峠呼世晴","集英社"],"vertical_cover":"http://i0.hdslb.com/bfs/manga-static/c937a55af0a114d4f57bfe266aba3e90fac6aaa2.jpg","is_finish":0,"last_ord":171,"last_short_title":"169","styles":[{"id":1013,"name":"冒险"},{"id":998,"name":"奇幻"}],"total":171,"last_rank":1}]}";
  JsonParser parser = new JsonParserImpl();
  JsonValue result = parser.parse(s);
  Map<String, Object> mapResult = (Map<String, Object>) result.convert();
  System.out.println((mapResult.get("code")));
  System.out.println(mapResult.get("msg"));
  for (Map itemData : (List<Map>) mapResult.get("data")) {
      System.out.println(itemData);
  }
```