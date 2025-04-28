package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.print.PrinterException;

public class BillPanel extends JPanel {

    private JTextPane billTextPane;
    private JButton printButton;

    public BillPanel(String customerName, String carModel, String rentDays, String totalAmount) {
        setLayout(new BorderLayout());

        // HTML bill content
        String html = "<html><body style='font-family:sans-serif;padding:20px;'>"
                + "<h2 style='text-align:center;'>Rent-a-Car Bill Receipt</h2><hr>"
                + "<p><b>Customer Name:</b> " + customerName + "</p>"
                + "<p><b>Car Model:</b> " + carModel + "</p>"
                + "<p><b>Rental Days:</b> " + rentDays + "</p>"
                + "<p><b>Total Amount:</b> ₹" + totalAmount + "</p><hr>"
                + "<p style='text-align:center;'>Thank you for using our service!</p>"
                + "</body></html>";

        billTextPane = new JTextPane();
        billTextPane.setContentType("text/html");
        billTextPane.setText(html);
        billTextPane.setEditable(false);
        billTextPane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(billTextPane);

        printButton = new JButton("Print Bill");
        printButton.setFont(new Font("Tahoma", Font.BOLD, 14));
        printButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    boolean complete = billTextPane.print(); // Shows print dialog
                    if (complete) {
                        JOptionPane.showMessageDialog(null, "Print successful.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Print canceled.");
                    }
                } catch (PrinterException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(scrollPane, BorderLayout.CENTER);
        add(printButton, BorderLayout.SOUTH);
    }

    public static void showBill(String customerName, String carModel, String rentDays, String totalAmount) {
        JFrame frame = new JFrame("Customer Bill");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.getContentPane().add(new BillPanel(customerName, carModel, rentDays, totalAmount));
        frame.setSize(600, 600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
