import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Invoice extends JFrame {
	private static int invoicenumber;
	private int id;
	private String hours;
	private String baseprice;
	private String cost;

	private String first;
	private String last;
	private String start;
	private String end;
	private Date date;
	private ArrayList<String> data = new ArrayList<String>();

	private JPanel inHeader;
	private JPanel container;
	private JScrollPane cont;
	private JPanel entire;
	private JPanel command;

	private JButton finish;

	private JCheckBox agree;

	private JLabel invoiceheader;
	private JLabel personname;
	private JLabel inDate;

	private JTable table;

	public Invoice() {
		// invoicenumber++;
		// id = invoicenumber;
		date = new Date();

		// Creating the panels.
		inHeader = new JPanel();
		command = new JPanel();

		container = new JPanel();
		entire = new JPanel();

		// Getting data from a file.
		getData("uow textfile.txt");

		// Creating the header.
		setTitle("Invoice");
		String numberString = "Invoice";
		String billTo = "Bill to: " + first + " " + last;
		personname = new JLabel(billTo);
		String datestring = "Date: " + String.valueOf(date);
		invoiceheader = new JLabel(numberString);
		inDate = new JLabel(datestring);

		// Formatting the header text.
		invoiceheader.setFont(new Font("Arial", Font.BOLD, 30));
		invoiceheader.setForeground(Color.GREEN);

		// Formatting and adding items to the header.
		inHeader.setBackground(Color.WHITE);
		inHeader.add(invoiceheader);
		inHeader.add(personname);
		inHeader.add(inDate);

		// Creating a table based on the data received from the file
		String[][] information = { { hours, baseprice, cost, start, end } };
		String[] columnNames = { "Hours Chosen", "Base Price", "Amount Paid", "Start Time", "End Time" };
		table = new JTable(information, columnNames);
		cont = new JScrollPane(table);
		// Creating a checkbox.
		agree = new JCheckBox("Accept the Terms and Conditions of using the UOW system.");

		// Adding the elements to the body of the panel.
		container.add(agree);
		entire.add(cont);
		entire.add(container);

		// Creating buttons

		finish = new JButton("Finish");

		// Adding actionlisteners to the buttons

		// back.addActionListener(new BackButtonListener());
		finish.addActionListener(new FinsihButtonListener());

		finish.setBackground(Color.GREEN);

		// Adding the buttons to a panel

		command.add(finish);

		// Adding the panels to the frame
		add(inHeader, BorderLayout.NORTH);
		add(entire, BorderLayout.CENTER);
		add(command, BorderLayout.SOUTH);

		// Formatting the panels and frame.
		cont.setSize(400, 120);
		cont.setBackground(Color.WHITE);
		command.setBackground(Color.WHITE);
		inHeader.setLayout(new GridLayout(3, 1));
		container.setLayout(new GridLayout(3, 1));
		setSize(480, 680);
		setLocation(500, 50);
		setResizable(false);
		setVisible(true);

	}

	public void getData(String file) {
		// Get data from a file.
		Scanner scan = null;

		try {
			scan = new Scanner(new File(file));
			while (scan.hasNext()) {
				String[] line = scan.nextLine().split(" ");
				first = line[0];
				last = line[1];
				hours = line[2];
				baseprice = line[3];
				cost = line[4];
				start = line[5];
				end = line[6];

			}
			scan.close();

		} catch (Exception e) {
			System.out.println("wow");
		}
	}

	private class FinsihButtonListener implements ActionListener {
		// Adding functionality to the finish button.
		public void actionPerformed(ActionEvent e) {
			dispose();
			new MessageFrame("Bike " + SecondFrame.bikeID + " succesfully Linked", "Success!");

		}
	}

}
