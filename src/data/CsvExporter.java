package data;

import domain.Customer;
import domain.Receipt;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// Utility class for exporting parking lot data into CSV report files
public class CsvExporter {

    // Exports the list of customers to a CSV file at the given path
    public static void exportCustomers(List<Customer> customers, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            // Write CSV header row
            writer.println("ID,Name,Phone,Email,Active");

            // Write one row per customer
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

    // Exports the list of receipts to a CSV file at the given path
    public static void exportReceipt(List<Receipt> receipts, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {

            // Write CSV header row
            writer.println("Id,Customer,Vehicle,ParkingSpot,Hours,issueDate,TotalAmount,Status");

            // Write one row per receipt
            for (Receipt r : receipts) {
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
