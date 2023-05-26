package code.TestScenarios;

public class orderBookPojo {

    public String bookId;
//    public int bookID;
    public String customerName;

    public orderBookPojo(String bookId, String customerName){
        this.bookId=bookId;
        this.customerName=customerName;
    }
    public orderBookPojo(String customerName){
        this.customerName=customerName;
    }

//    public orderBookPojo(int bookID){
//        this.bookID=bookID;
//
//    }


}
