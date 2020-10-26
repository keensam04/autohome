package com.autohome;

public class Test2 {

    public static void main(String[] args) {
      //  String word = "{\"0\": { \"name\": \"John\"},\"1\":{\"name\":\"micheal\"}}";
        /*String word = "{\n" +
                "  \"name\": \"positive\",\n" +
                "  \"class\":\"xyz\",\n" +
                "  \"children\": [\n" +
                "    {\n" +
                "      \"name\": \"product service\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"name\": \"price\",\n" +
                "          \"children\": [\n" +
                "            {\n" +
                "              \"name\": \"cost\",\n" +
                "              \"size\": 8\n" +
                "            }\n" +
                "          ]\n" +
                "        },\n" +
                "        {\n" +
                "          \"name\": \"quality\",\n" +
                "          \"children\": [\n" +
                "            {\n" +
                "              \"name\": \"messaging\",\n" +
                "              \"size\": 4\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"customer service\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"name\": \"Personnel\",\n" +
                "          \"children\": [\n" +
                "            {\n" +
                "              \"name\": \"CEO\",\n" +
                "              \"size\": 7\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    },\n" +
                "    {\n" +
                "      \"name\": \"product\",\n" +
                "      \"children\": [\n" +
                "        {\n" +
                "          \"name\": \"Apple\",\n" +
                "          \"children\": [\n" +
                "            {\n" +
                "              \"name\": \"iPhone 4\",\n" +
                "              \"size\": 10\n" +
                "            }\n" +
                "          ]\n" +
                "        }\n" +
                "      ]\n" +
                "    }\n" +
                "  ]\n" +
                "}";*/


        String word = "{\n" +
                "  \"name\": \"positive\",\n" +
                "  \"class\":[],\n" +
                "  \"children\": \"ashdh\"\n" +
                "}";
        int count = 1;
        for(int i = 0 ; i < word.length(); i++){
            if(word.charAt(i)==':' && (word.charAt(i+1) == '{' || word.charAt(i+1) == '['))
                break;
            if(word.charAt(i)==',')
                count++;
        }

        System.out.println(count);
    }
}
