package code.TestScenarios;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

public class serialization {
    public static void main(String[] args) throws JsonProcessingException {
        //1.step is to create java object
        serializationAndDeserialization serialization=new serializationAndDeserialization();
        serialization.setBookId(1);
        serialization.setCustomerName("Omer");
        ArrayList<Integer> str=new ArrayList<>();
        str.add(1);
        str.add(4);
        serialization.setBookIds(str);
        //2.Converting java object into JSON object - serialization

        ObjectMapper objectMapper=new ObjectMapper();

        String jsonPayload= objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(serialization);
        System.out.println("JSON object we created : "+jsonPayload);
        serialization.setBookId(10);
        serialization.setCustomerName("Alisan");

        String jsonPayload2=objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(serialization);
        System.out.println("Second JSON Payload : "+jsonPayload2);


    }
}
