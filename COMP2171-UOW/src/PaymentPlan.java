import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class PaymentPlan extends JFrame {
    private String cardnum;
    private String cvvnum;
    private String first;
    private String last;
    private String nameCard;
    private String expDate;
    private String paymentdata;
    private String file;

    private ArrayList<String> data = new ArrayList<String>();

    private JTextField name;
    private JTextField cardnumber;
    private JTextField expiredate;
    private JTextField cvv;

    private JPanel buttons;
    private JPanel displayinfo;
    private JPanel displayheader;

    private JButton save;
    private JButton cancel;
    

    private JLabel header;
    private JLabel header2;

    public PaymentPlan() {
        // Adding a title to the frame
        setTitle("ADD PAYMENT INFORMATION");

        // Creating a header
        header = new JLabel("Payment");
        header2 = new JLabel("Information");

        // Formatting the header
        header.setFont(new Font("Arial", Font.BOLD, 30));
        header2.setFont(new Font("Arial", Font.BOLD, 30));
        header.setForeground(Color.gray);
        header2.setForeground(Color.GREEN);

        // Creating a panel, adding the header to it and formatting it.
        displayheader = new JPanel();
        displayheader.add(header);
        displayheader.add(header2);
        displayheader.setBackground(Color.WHITE);

        // Creating a panel and adding all four textfields to it.

        displayinfo = new JPanel();
        displayinfo.add(new JLabel("Name of Card Holder"));
        name = new JTextField(20);
        displayinfo.add(name);

        displayinfo.add(new JLabel("Card Number"));
        cardnumber = new JTextField(20);
        displayinfo.add(cardnumber);

        displayinfo.add(new JLabel("CVV"));
        cvv = new JTextField(20);
        displayinfo.add(cvv);

        displayinfo.add(new JLabel("Expiration (MM/YY)"));
        expiredate = new JTextField(20);
        displayinfo.add(expiredate);
        displayinfo.setLayout(new GridLayout(5, 2));

        // Creating and formatting a panel and buttons.
        buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        save = new JButton("Save");
        save.setBackground(Color.GREEN);
        cancel = new JButton("Main Menu");
        cancel.setBackground(Color.GREEN);
        

        // Adding buttons to panel
        buttons.add(save);
        buttons.add(cancel);
        

        // Adding actionlistener to buttons
        cancel.addActionListener(new CancelButtonListener());
        save.addActionListener(new SaveButtonListener());
        

        // Adding panels to frame
        add(displayheader, BorderLayout.NORTH);
        add(displayinfo, BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);
        pack();

        // Formatting frame
        setSize(400, 680);
        setLocation(540, 50);
        setResizable(false);
        setVisible(true);

    }

    public void toFile(String paymentdata) {
        // Adds data to a file
        try (BufferedWriter writefile = new BufferedWriter(new FileWriter("storepayment.txt", true))) {
            PrintWriter p = new PrintWriter(writefile);
            p.println(paymentdata);

            // Shows the user that the data was saved.
            JOptionPane.showMessageDialog(null, "SuccessFully Saved");

            p.close();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    private class SaveButtonListener implements ActionListener {
        // Adding functionality to the save button
        public void actionPerformed(ActionEvent e) {
            try {
                // Getting user input from the texfields.
                nameCard = name.getText();
                cardnum = cardnumber.getText();
                cvvnum = cvv.getText();
                expDate = expiredate.getText();

                // Checking whether digits were entered for the card number and cvv
                Long.parseLong(cardnumber.getText());
                Long.parseLong(cvv.getText());

                // Checking if the card number and cvv is of the correct length.
                if (cardnumber.getText().length() == 16 && cvv.getText().length() == 3) {
                    paymentdata = nameCard + " " + cardnum + " " + cvvnum + " " + expDate;
                    toFile(paymentdata);
                }

                else {
                    // Prompts the user to enter a valid cvv or card number if they are not the
                    // correct length
                    JOptionPane.showMessageDialog(null, "Invalid card number or cvv");
                }

            } catch (NumberFormatException exception) {
                // Prompts the user to enter a valid cvv or card number if they contain
                // non-digit values.
                JOptionPane.showMessageDialog(null, "Invalid card number or cvv");
            }
        }
    }

    private class CancelButtonListener implements ActionListener {
        // Adds functionlity to the cancel button
        public void actionPerformed(ActionEvent e) {
            setVisible(false);
            dispose();
            MainPage main = new MainPage();

        }
    }

    

    public static void main(String[] arg) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PaymentPlan();
            }
        });

    }
}