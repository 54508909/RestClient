package com.liao;

public class restClientEnum {

    public static String createIndexTemplate ="{\n" +
            "  \"mappings\": {\n" +
            "  \"properties\":{\n" +
            "    \"id\":{\n" +
            "      \"type\":\"long\"\n" +
            "    },\n" +
            "    \"name\":{\n" +
            "      \"type\": \"text\",\n" +
            "      \"copy_to\": \"all\"\n" +
            "    },\n" +
            "    \"address\":{\n" +
            "      \"type\":\"keyword\",\n" +
            "      \"index\": false\n" +
            "    },\n" +
            "    \"price\":{\n" +
            "      \"type\":\"float\"\n" +
            "    },\n" +
            "    \"score\":{\n" +
            "      \"type\":\"float\"\n" +
            "    },\n" +
            "    \"brand\":{\n" +
            "      \"type\":\"keyword\",\n" +
            "      \"copy_to\": \"all\"\n" +
            "    },\n" +
            "    \"city\":{\n" +
            "      \"type\":\"keyword\"\n" +
            "    },\n" +
            "    \"starName\":{\n" +
            "      \"type\":\"keyword\"\n" +
            "    },\n" +
            "    \"business\":{\n" +
            "      \"type\":\"keyword\"\n" +
            "    },\n" +
            "    \"location\":{\n" +
            "      \"type\":\"geo_point\"\n" +
            "    },\n" +
            "    \"pic\":{\n" +
            "      \"type\":\"keyword\",\n" +
            "      \"index\": false\n" +
            "    },\n" +
            "    \"all\":{\n" +
            "      \"type\": \"text\"\n" +
            "    }\n" +
            "  }\n" +
            "  }\n" +
            "}";
}
