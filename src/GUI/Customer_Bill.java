package GUI;

import BackendCode.Customer;
import BackendCode.Booking;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Customer_Bill {

    private JFrame frame;
    private JTextPane billTextPane; // Use JTextPane for styled text
    private JButton printButton;
    private Customer customer;

    public Customer_Bill(Customer customer) {
        this.customer = customer;

        // Initialize the frame
        frame = new JFrame("Customer Bill");
        frame.setSize(500, 600);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        // Set layout
        frame.setLayout(new BorderLayout());

        // Bill text pane
        billTextPane = new JTextPane();
        billTextPane.setEditable(false);
        billTextPane.setFont(new Font("Monospaced", Font.PLAIN, 14));
        billTextPane.setContentType("text/html"); // Set content type to HTML for styling
        billTextPane.setText(generateStyledBillText(customer));
        JScrollPane scrollPane = new JScrollPane(billTextPane);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Print button
        printButton = new JButton("Print Bill");
        printButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        printButton.setBackground(new Color(52, 152, 219));
        printButton.setForeground(Color.WHITE);
        printButton.setFocusPainted(false);
        printButton.setBorderPainted(false);
        printButton.setOpaque(true);
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                printBill();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(printButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add outline border to the bill text pane
        Border border = BorderFactory.createLineBorder(Color.BLACK, 2); // Black border, 2 pixels thick
        scrollPane.setBorder(BorderFactory.createCompoundBorder(border, scrollPane.getBorder())); // Add some padding

        // Display the frame
        frame.setVisible(true);
    }

    // Generate the styled bill text for the customer using HTML
    private String generateStyledBillText(Customer customer) {
        StringBuilder billText = new StringBuilder();
        billText.append("<html><body style='font-family: Monospaced; font-size: 14px;'>");
        billText.append("<div style='text-align:center;'>");
        billText.append("<h2 style='color: blue;'>Rent-A-Car Management System</h2>");
        billText.append("<hr style='border: 1px solid black;'>");
        billText.append("</div><br>");

        billText.append("<strong style='color: green;'>Customer Details:</strong><br>");
        billText.append("Customer ID: <span style='color: #333;'>").append(customer.getID()).append("</span><br>");
        billText.append("Customer Name: <span style='color: #333;'>").append(customer.getName()).append("</span><br>");
        billText.append("CNIC: <span style='color: #333;'>").append(customer.getCNIC()).append("</span><br>");
        billText.append("Contact: <span style='color: #333;'>").append(customer.getContact_No()).append("</span><br>");
        billText.append("<strong style='color: red;'>Total Bill: ").append(customer.getBill()).append("</strong><br><br>");

        billText.append("<strong style='color: green;'>Booked Cars:</strong><br>");

        ArrayList<Booking> bookings = Booking.SearchByCustomerID(customer.getID());
        if (!bookings.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm");

            for (Booking booking : bookings) {
                billText.append("<div style='margin-left: 20px;'>");
                billText.append("<strong style='color: #007bff;'>Car Details:</strong><br>");
                billText.append("Car ID: <span style='color: #333;'>").append(booking.getCar().getID()).append("</span><br>");
                billText.append("Car Name: <span style='color: #333;'>").append(booking.getCar().getName()).append("</span><br>");
                billText.append("Rent/Hour: <span style='color: #333;'>").append(booking.getCar().getRentPerHour()).append("</span><br>"); // Added Rent/Hour
                billText.append("Car Owner: <span style='color: #333;'>").append(booking.getCar().getCarOwner().getName()).append("</span><br>"); // Added Car Owner Name
                billText.append("Rent Time: <span style='color: #333;'>").append(dateFormat.format(new Date(booking.getRentTime()))).append("</span><br>");
                if (booking.getReturnTime() == 0) {
                    billText.append("Return Time: <span style='color: orange;'>Not returned yet</span><br><br>");
                } else {
                    billText.append("Return Time: <span style='color: #333;'>").append(dateFormat.format(new Date(booking.getReturnTime()))).append("</span><br><br>");
                }
                billText.append("</div>");
            }
        } else {
            billText.append("<span style='color: orange;'>No Cars Booked</span><br>");
        }

        billText.append("<br><div style='text-align:center; color: blue;'>");
        billText.append("Thank you for using Rent-A-Car Management System!");
        billText.append("</div></body></html>");
        return billText.toString();
    }

    // Print the bill
    private void printBill() {
        try {
            PrinterJob printerJob = PrinterJob.getPrinterJob();
            printerJob.setPrintable((graphics, pageFormat, pageIndex) -> {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                graphics.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());

                // Directly print the JTextPane
                billTextPane.print(graphics);

                return Printable.PAGE_EXISTS;
            });

            // Display the print dialog before printing
            if (printerJob.printDialog()) {
                printerJob.print();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(frame, "Error printing the bill: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method for testing (you can modify this to use your actual data)
    public static void main(String[] args) {
        // This is a simplified example assuming you have a way to get a Customer object
        // with associated Bookings that have Car objects with CarOwner details.

        // Replace this with how you would normally retrieve a Customer object
     

        // For the bill to show booked cars, the Booking.SearchByCustomerID() method
        // needs to return a list of Booking objects associated with this customer.
        // Each Booking object should have a Car object, and each Car object should
        // have a CarOwner object with a name and a rentPerHour value.

        // Since you mentioned you already have data, ensure that your data retrieval
        // mechanism populates these relationships correctly.

       
    }
}