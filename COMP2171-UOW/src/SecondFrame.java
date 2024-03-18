import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class SecondFrame extends JFrame {

	private JTable table;
	static DefaultTableModel model;
	protected JTextField txtId;
	private JScrollPane scrollPane;
	protected SecondFrame secondFrame = this;
	static double payPrice;
	static Bike bikeOfuser;
	static int bikeID;
	private enterID enterid;
	static boolean timeSelected = false;
	private ArrayList<Bike> blist = new ArrayList<Bike>();
	static ArrayList<Bike> availBikes = new ArrayList<Bike>();
	private ArrayList<Bike> bikesToshow = new ArrayList<Bike>();
	//private User currentUser;

	// Class Constructor
	SecondFrame() {
		this.setTitle(BicycleRental.SecondFrameName + "-Available Bikes");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocation(500, 90);
		this.setPreferredSize(new Dimension(400, 680));

		String[] columnNames = { "ID Num", "Condition", "Rating", "Price", "LateFee" };
		model = new DefaultTableModel(columnNames, 0);
		table = new JTable(model);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setFillsViewportHeight(true);
		table.setBackground(Color.gray);
		table.setOpaque(true);
		scrollPane = new JScrollPane(table);

		JButton backButton = new JButton("Back");
		JButton linkButton = new JButton("Link bike");
		JButton unlinkButton = new JButton("Unlink bike");

		JPanel display = new JPanel(new GridLayout());
		JPanel buttonPanel = new JPanel(new GridLayout());

		display.add(scrollPane);
		buttonPanel.add(backButton);
		buttonPanel.add(linkButton);
		buttonPanel.add(unlinkButton);
		backButton.setBackground(Color.GREEN);
		linkButton.setBackground(Color.GREEN);
		unlinkButton.setBackground(Color.GREEN);
		backButton.addActionListener(new backButtonaction());
		linkButton.addActionListener(new linkaction());
		unlinkButton.addActionListener(new unlinkaction());

		this.add(buttonPanel, BorderLayout.SOUTH);
		this.add(display);
		this.pack();
		this.setVisible(true);

		this.getAvaiablebikesfromList();
		this.loadBikesbyLocation(BicycleRental.location, availBikes);
		this.showTableData(bikesToshow);

		try (Scanner bscan = new Scanner(new File("userbike.txt"))) {
			while (bscan.hasNext()) {
				String[] nextLine = bscan.nextLine().split("_");

				// Extract bike attributes from the line
				int bicycleID = Integer.parseInt(nextLine[0]);
				String location = nextLine[1];
				String userRating = nextLine[2];
				String condition = nextLine[3];
				boolean isAvailable = Boolean.parseBoolean(nextLine[4]);
				double price = Double.parseDouble(nextLine[5]);
				double latefee = Double.parseDouble(nextLine[6]);

				// Create a Bike object
				bikeOfuser = new Bike(bicycleID, location, condition, userRating, isAvailable, price, latefee);

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void addToTable(Bike bike)
	// Adds a bike to the table
	{
		String[] item = { "" + bike.getBicycleID(), "" + bike.getCondition(), "" + bike.getUserRating(),
				bike.getPrice() + "0", bike.getlatefee() + "0" };
		model.addRow(item);
	}

	public void showTableData(ArrayList<Bike> bikesToshow) { // display from list to table
		if (bikesToshow.size() > 0) {
			for (Bike b : bikesToshow) {
				addToTable(b);
			}
		}

	}

	// searches for the bike to see if it exists
	public boolean bikeExist(int bicycleID, ArrayList<Bike> list) {
		for (Bike bike : list) {
			if (bike.getBicycleID() == (bicycleID)) {
				return true;
			}
		}
		return false;
	}

	// return a bike from its id
	public Bike findBike(int bicycleID) {
		for (Bike bike : availBikes) {
			if (bike.getBicycleID() == (bicycleID)) {
				return bike;
			}
		}
		return null;
	}

	public void getAvaiablebikesfromList() { // Read bike data from a file and create bike then add to a list
		availBikes.clear();
		blist = Bike.addbikes("file.txt");
		if (blist.size() != 0) {
			for (Bike bike : blist) {
				if (bike.getAvailable()) {
					availBikes.add(bike);
				}
			}
		}

	}

	public void loadBikesbyLocation(String location, ArrayList<Bike> availBikes) {
		bikesToshow.clear();
		if (availBikes.size() != 0) {
			for (Bike b : availBikes) {
				if (b.getLocation().equals(location)) {
					bikesToshow.add(b);
				}
			}
		}
	}

	public void rentBike(int ID) {
		if (availBikes.size() != 0) {
			for (int b = 0; b < availBikes.size(); b++) {
				if (availBikes.get(b).getBicycleID() == ID) {
					bikeOfuser = availBikes.get(b);
					availBikes.remove(b);
					bikeOfuser.setAvailable(false);

				}
			}
		}
	}

	public void returnBike() {

		bikeOfuser.setLocation(BicycleRental.location);
		bikeOfuser.setAvailable(true);
		availBikes.add(bikeOfuser);
		bikeOfuser = null;

	}

	private class backButtonaction implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			secondFrame.dispose();
			new BicycleRental();

		}

	}

	private class unlinkaction extends JFrame implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (bikeOfuser != null) {
				returnBike();
				model.setRowCount(0);
				loadBikesbyLocation(BicycleRental.location, availBikes);
				showTableData(bikesToshow);
				new MessageFrame("Unlinked with bike sucesfully", "Success!");
				new UpdateFile();
			} else {
				new MessageFrame("No bike currently likned to this account", "Error");
			}

		}

	}

	private class linkaction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			if (bikeOfuser == null) {
				new enterID();
			} else {
				new MessageFrame("Unlink current bike before getting another", "Error");
			}

		}

	}

	private class enterID extends JFrame {

		private JPanel pDisplay = new JPanel();
		private JPanel bPanel = new JPanel();
		private JButton getButton = new JButton("Get");
		private JButton bkButton = new JButton("Back");

		enterID() {
			enterid = this;
			this.setTitle("ID Entry");
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			this.setResizable(false);
			this.setLocation(550, 290);
			this.setPreferredSize(new Dimension(300, 200));

			pDisplay.add(new JLabel("Enter ID of Bike:"));
			txtId = new JTextField(20);
			pDisplay.add(txtId);
			bkButton.setBackground(Color.GREEN);
			getButton.setBackground(Color.GREEN);
			getButton.addActionListener(new getAction());
			bkButton.addActionListener(new bkAction());
			bPanel.add(bkButton);
			bPanel.add(getButton);

			this.add(pDisplay);
			this.add(bPanel, BorderLayout.SOUTH);
			this.pack();
			this.setVisible(true);
		}

	}

	private class getAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (txtId.getText().isEmpty()) {

				new MessageFrame("Enter a Valid ID", "Error");

			} else if (txtId.getText().isEmpty() == false) {
				try {

					bikeID = Integer.parseInt(txtId.getText());
					if (bikeExist(bikeID, bikesToshow)) {
						enterid.dispose();
						new Time(findBike(bikeID).getPrice());
					}

					else {
						new MessageFrame("Bike not available", "Error");
					}

				} catch (NumberFormatException n) {
					new MessageFrame("Enter Numbers only", "Error");
				}

			}

		}
	}

	private class bkAction implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			enterid.dispose();

		}

	}

	////// Private Class to show time frame to select a rental time

	private class Time {

		JRadioButton radioButton1;
		JRadioButton radioButton2;
		JRadioButton radioButton3;
		JButton ok;
		double bikePrice;
		JFrame frame;

		Time(double bikePrice) {
			this.bikePrice = bikePrice;

			frame = new JFrame("Time Seclection");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new FlowLayout());

			JPanel panel = new JPanel();

			// Create buttons
			radioButton1 = new JRadioButton("5 Hrs 3.5xPrice");
			radioButton2 = new JRadioButton("2 Hr 2xPrice");
			radioButton3 = new JRadioButton("1 Hr");
			ok = new JButton("done");
			ok.setBackground(Color.GREEN);

			// Create a button group to ensure only one radio button is selected at a time
			ButtonGroup buttonGroup = new ButtonGroup();
			buttonGroup.add(radioButton1);
			buttonGroup.add(radioButton2);
			buttonGroup.add(radioButton3);

			ok.addActionListener(new oklistener());

			// Add radio buttons to the frame
			frame.add(radioButton1);
			frame.add(radioButton2);
			frame.add(radioButton3);

			panel.add(ok);

			frame.add(panel);
			frame.setSize(300, 150);
			frame.setLocationRelativeTo(null); // Center the frame
			frame.setVisible(true);

		}

		private class oklistener implements ActionListener { // button listener to handle a bike chosen
			int hour;
			LocalDateTime currentDateTime = LocalDateTime.now();

			public void actionPerformed(ActionEvent e) {

				if (radioButton1.isSelected()) {
					payPrice = 3.5 * bikePrice;
					hour = 5;
					timeSelected = true;

				}
				if (radioButton2.isSelected()) {
					payPrice = 2 * bikePrice;
					hour = 2;
					timeSelected = true;

				}
				if (radioButton3.isSelected()) {
					payPrice = bikePrice;
					hour = 1;
					timeSelected = true;

				}
				if (timeSelected == false) {
					new MessageFrame("Please select a time", "Error");
				}
				// System.out.println(Login.findUser(Login.id).getfname());
				if (timeSelected == true) {
					frame.dispose();
					rentBike(bikeID);
					timeSelected = false;
					model.setRowCount(0);
					loadBikesbyLocation(BicycleRental.location, availBikes);
					showTableData(bikesToshow);
					new UpdateFile();

					new InvoiceFile(Login.findUser(Login.id).getfname(), Login.findUser(Login.id).getlname(), hour,
							bikePrice, payPrice, currentDateTime);

					new Invoice();

				}

			}

		}

	}

}
