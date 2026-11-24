package data;
import domain.Customer;
import domain.Receipt;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class CsvExporter {

    public static void exportCustomers(List<Customer> customers, String filePath) throws IOException {
    try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            
            writer.println("ID,Name,Phone,Email,Active");

            
            for (Customer c : customers) {
                writer.println(
                    c.getId() + "," +
                    c.getName() + "," +
                    c.getPhoneNumber() + "," +
                    c.getEmail() + "," +
                    (c.isActive() ? "Active" : "Inactive")
                );
            }
        }
    }
    public static void exportReceipt(List<Receipt> receipts, String filePath) throws IOException{
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))){

            writer.println("Id,Customer,Vehicle,ParkingSpot,Hours,issueDate,TotalAmount,Status");

            for (Receipt r : receipts){
                writer.println(
                    r.getId() + "," +
                    r.getCustomer().getName() + "," + 
                    r.getVehicle().getVehiclePlate() + "," + 
                    r.getParkingSpot().getSpaceNumber() + "," +
                    r.getHours() + "," +
                    r.getIssueDate() + "," +
                    r.getTotalAmount() + "," + 
                    (r.isCancelled() ? "Cancelled" : "Active")
                );
            }
        }

    }
}
