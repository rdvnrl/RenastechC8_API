package code.TestScenarios;

public class serializationAndDeserialization {
    //This will our pojo class
    //What is serialization and deserialization
    // S is conversion of Java Object to Json, D is the reverse which is JSON to JAva object
    //POJO - Plain Old Java Object
    //Convert a java object into a JSON object >>> Serialization
    //Convert a JSON object to Java Object >>> Deserialization
    //Jackson or Gson libraries etc.

    // bookId and customerName
    //Which OOP concept we are using? Encapsulation
    //Is it possible to access private variables if we dont use Setters and Getters?
    //Reflection

    private String bookId;
    private String customerName;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
