package code.TestScenarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public class deserialization {

    public static void main(String[] args) throws JsonProcessingException {
//        {
//            "bookId":1,
//            "customerName":"Yakup"
//        }

        String jsonObject="        {\n" +
                "            \"bookId\":1,\n" +
                "            \"customerName\":\"Yakup\"\n" +
                "        }";


        //Create an object of ObjectMapper class
        ObjectMapper objectMapper=new ObjectMapper();

        //Deserialize
        serializationAndDeserialization deserialization= objectMapper.readValue(jsonObject, serializationAndDeserialization.class);

        System.out.println("bookId: "+deserialization.getBookId());
        System.out.println("customerName: "+deserialization.getCustomerName());

        Map<String, Object> orderAsMap=objectMapper.readValue(jsonObject,Map.class);
        System.out.println("Order As Map: "+orderAsMap);

        //We can use Map object above to access values
        System.out.println("Book Id :"+orderAsMap.get("bookId"));
        System.out.println("Customer Name : "+orderAsMap.get("customerName"));
    }
}
