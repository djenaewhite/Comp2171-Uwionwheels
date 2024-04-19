import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.Color;

public class Login extends JFrame {
	private JPanel mainPanel;
    //private JPanel buttons;
	private JPanel imgPnel = new JPanel();
	// private JLabel header;
	static String id;

	private JTextField idText = new JTextField();
	private JPasswordField passwordText = new JPasswordField();;
	private JLabel passwordLabel = new JLabel("PASSWORD: ");
	private JLabel idLabel = new JLabel("ID:");

	private JButton loginButton;
	private JButton signUpButton;
	private JCheckBox adminorno = new JCheckBox("ADMINISTRATOR");
	private static ArrayList<User> users = new ArrayList<>();
	private ArrayList<Admin> admins = new ArrayList<>();
	public static final String file = "userdata.txt";
	public static final String emailfile = "emaildata.txt";
	ArrayList<String> idmail = new ArrayList<String>();

		// Define RGB values for nude colors
	private static final int LIGHT_NUDE_RED = 255;
	private static final int LIGHT_NUDE_GREEN = 220;
	private static final int LIGHT_NUDE_BLUE = 187;
	
	private static final int MEDIUM_NUDE_RED = 200;
	private static final int MEDIUM_NUDE_GREEN = 150;
	private static final int MEDIUM_NUDE_BLUE = 130;
	
	private static final int DARK_NUDE_RED = 160;
	private static final int DARK_NUDE_GREEN = 100;
	private static final int DARK_NUDE_BLUE = 90;
	
		public Login() {
			setTitle("UWI ON WHEELS");
			setBounds(300, 90, 420, 700);
			setResizable(true);
	
			mainPanel = new JPanel();
			add(mainPanel);
			mainPanel.setLayout(null);
			mainPanel.setBackground(new Color(DARK_NUDE_RED, LIGHT_NUDE_GREEN, LIGHT_NUDE_BLUE));
	
			adminorno.setBounds(150, 50, 120, 25);
			mainPanel.add(adminorno);
	
			idText = new JTextField();
			idLabel.setBounds(20, 100, 100, 25);
			idText.setBounds(130, 100, 170, 25);
	
			BufferedImage myPicture = null;
			try {
				myPicture = ImageIO.read(new File("Logo.png"));
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			JLabel picLabel = new JLabel(new ImageIcon(myPicture));
			Dimension size = new Dimension(400, 375);
			picLabel.setPreferredSize(size);
			imgPnel.add(picLabel);
	
			add(imgPnel, BorderLayout.NORTH);
	
			passwordLabel.setBounds(20, 150, 100, 25);
			passwordText.setBounds(130, 150, 170, 25);
			mainPanel.add(idText);
			mainPanel.add(passwordText);
			mainPanel.add(passwordLabel);
			mainPanel.add(idLabel);
	
			loginButton = new JButton("LOGIN");
			loginButton.setBounds(90, 200, 100, 25);
			mainPanel.add(loginButton);
	
			signUpButton = new JButton("SIGN UP");
			signUpButton.setBounds(210, 200, 100, 25);
			mainPanel.add(signUpButton);
	
			loginButton.addActionListener(new LoginListener());
			signUpButton.addActionListener(new SignUpListener());
	
			setVisible(true);
			readFile(file);
		}
	

	private boolean idCheck(String id) {
		if (id != null && (id.length() == 9 && id.charAt(0) == '6' && id.charAt(1) == '2')) {
			// System.out.println(id.length());
			return true;
		} else {
			return false;
		}
	}

	private boolean passwordCheck(String password) {
		if (password.length() >= 8 && password.length() <= 25 && password != null) {
			return true;
		} else {
			return false;
		}
	}

	private class LoginListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			id = idText.getText();
			String password = passwordText.getText();
			// System.out.println(idCheck(id));
			// MainPage page = new MainPage();
			readFile(file);

			if (idCheck(id)) {
				// System.out.println("HELLO1");
				
				  User user1 = new User("Djenae", "White", "620155860", "djenaewhite@gmail.com",
				  "56tghfi1v"); User user2 = new User("Jane", "Potier", "620338741",
				  "salal@bing.com", "569ghfi1v"); User user3 = new User("Jane", "Potier",
				 "620345741", "salal@bing.com", "");
				 
				 users.add(user1);
				 
				boolean idExists = false;
				boolean passwordExists = false;
				boolean admincheck = adminorno.isSelected();
				if (admincheck == false) {
					for (User user : users) {

						if (user.getid().equals(id)) {
							// System.out.println("HELLO");
							idExists = true;
						}

						if (passwordCheck(password)) {

							if (user.getpassword().equals(password)) {
								passwordExists = true;
							}

						}

					}

					if (idExists && passwordExists) {

						// System.out.println("HELLO4");
						MainPage main = new MainPage();
					} else {
						JOptionPane.showMessageDialog(null, "CREATE AN ACCOUNT TO LOG IN", "NO ACCOUNT",
								JOptionPane.ERROR_MESSAGE);

					}
				}

				else if (admincheck == true) {
					boolean adminpasses = false;
					boolean keypasses = false;
					Admin cal = new Admin("password", "password", "620138741", "mail@mail.com", "password", "IAMADMIN");
					admins.add(cal);

					String passString = JOptionPane.showInputDialog("ENTER THE ADMIN ACCESSKEY");
					for (Admin admin : admins) {
						if (admin.getid().equals(id) && admin.getpassword().equals(password)) {
							adminpasses = true;
						}
						if (admin.getAccesskey().equals(passString)) {
							keypasses = true;
						}

					}
					if (adminpasses == true && keypasses) {
						dispose();
						MainPageAdmin mpa = new MainPageAdmin();
					} else {
						JOptionPane.showMessageDialog(null, "THE USER DOES NOT HAVE ADMIN PRIVILEGES", "NOT ADMIN",
								JOptionPane.ERROR_MESSAGE);

					}
				} else {
					JOptionPane.showMessageDialog(null,
							"THE ID/PASSWORD YOU ENTERED DOES NOT EXIST \n OR DOES NOT MEET REQUIREMENTS", "NO MATCH",
							JOptionPane.ERROR_MESSAGE);
				}

			} else {
				JOptionPane.showMessageDialog(null, "ID IS OF THE INCORRECT FORMAT", "INCORRECT ID FORMAT",
						JOptionPane.ERROR_MESSAGE);

			}

		}

	}

	private class SignUpListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			id = idText.getText();
			String password = passwordText.getText();
	
			try {
				if (id != null && (id.length() == 9 && id.charAt(0) == '6' && id.charAt(1) == '2')
						&& (password.length() >= 8 && password.length() <= 25 && password != null)) {
	
					// Debug: Print entered ID
					System.out.println("Entered ID: " + id);
	
					readEmailFile(emailfile);
	
					boolean idValid = false;
					for (int index = 0; index < idmail.size(); index++) {
						String[] parts = idmail.get(index).split(" ");
						String firstname = parts[0];
						String lasttname = parts[1];
						String fileid = parts[2];
						String fileemail = parts[3];
	
						// Debug: Print data from emailfile
						System.out.println("File ID: " + fileid);
						System.out.println("File Email: " + fileemail);
	
						if (id.equals(fileid.trim())) {
							idValid = true;
							String code = null;
							try {
								new App(fileemail); // Ensure this constructor is correct
								code = App.getRandomString();
	
								// Debug: Print generated code
								System.out.println("Generated Code: " + code);
							} catch (IOException exception) {
								// Handle exception
							} catch (MessagingException mException) {
								// Handle exception
							}
	
							String confirmCode = JOptionPane.showInputDialog("Enter Verification Code:");
							if (confirmCode.equals(code)) {
								JOptionPane.showMessageDialog(null, "Email Verified and Account Successfully Created",
										"Verification", JOptionPane.INFORMATION_MESSAGE);
	
								User user = new User(firstname, lasttname, fileid, fileemail, password);
								users.add(user);
								addToUserData(file);
	
								MainPage main = new MainPage();
							} else {
								JOptionPane.showMessageDialog(null, "THE VERIFICATION CODE IS INVALID", "INVALID CODE",
										JOptionPane.INFORMATION_MESSAGE);
							}
						}
					}
	
					if (!idValid) {
						JOptionPane.showMessageDialog(null, "THE ID ENTERED DOES NOT MATCH THE ID FROM FILE",
								"INVALID ID", JOptionPane.INFORMATION_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "THE ID OR PASSWORD ENTERED IS INVALID OR LEFT BLANK",
							"ENTRY ERROR", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (NullPointerException np) {
				// Handle null pointer exception
			}
		}
	}
	
//OTHER PAGES

	public class MainPageAdmin extends JFrame {
		private JLabel header = new JLabel("UWI ON WHEELS");
		private JPanel mainScreenPanel = new JPanel();
		private JButton viewUsers = new JButton("VIEW USERS");
		private JButton deleteUsers = new JButton("DELETE USERS");
		private JButton logout = new JButton("LOGOUT");
		private JButton viewIncidents = new JButton("VIEW INCIDENT REPORTS");

		public MainPageAdmin() {
			setTitle("MainScreen Admin");
			setBounds(300, 90, 420, 700);
			setResizable(true);
	
			header.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
			header.setBounds(120, 20, 200, 30);
			mainScreenPanel.add(header);
	
			add(mainScreenPanel);
			mainScreenPanel.setLayout(null);
			mainScreenPanel.setBackground(new Color(DARK_NUDE_RED, LIGHT_NUDE_GREEN, LIGHT_NUDE_BLUE));
	
			viewUsers.setBounds(110, 250, 200, 35);
			deleteUsers.setBounds(110, 450, 200, 35);
			logout.setBounds(110, 550, 200, 35);
			viewIncidents.setBounds(110, 350, 200, 35);

	
			mainScreenPanel.add(viewUsers);
			mainScreenPanel.add(deleteUsers);
			mainScreenPanel.add(logout);
			mainScreenPanel.add(viewIncidents);

	
			setVisible(true);
			viewUsers.addActionListener(new ViewUserListener());
        	deleteUsers.addActionListener(new DeleteListener());
        	logout.addActionListener(new LogoutAdminListner());
		
        	viewIncidents.addActionListener(new ViewIncidentsListener());
    }

		private class LogoutAdminListner implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login login = new Login();
			}
		}
		
		private class ViewUserListener implements ActionListener {
			/**
			 * This method calls the select bike class
			 */
			public void actionPerformed(ActionEvent e) {
				try {
					dispose();
					ViewUsers view = new ViewUsers();
				} catch (NullPointerException nulP) {
				}
			}
		}

		private class DeleteListener implements ActionListener {
			/**
			 * This method calls the select bike class
			 */
			public void actionPerformed(ActionEvent e) {
				dispose();
				try {
					// Implement a search for a user with a specific id
					// If the id is found, remove that person from the arraylist
					// Else, error message, person with id """" not found
					ViewUsers view = new ViewUsers();
					String idToDelete = JOptionPane.showInputDialog("ENTER THE ID OF THE USER TO DELETE");
					boolean idFound = false;
					int userIndex = 0;
					for (int index = 0; index < users.size(); index++) {
						if (users.get(index).getid().equals(idToDelete) && (users.size() != 0)) {
							idFound = true;
							users.remove(userIndex);
						}

					}
					if (idFound == false) {
						JOptionPane.showMessageDialog(null, "THE ID YOU ENTERED DOES NOT EXIST", "NO MATCH",
								JOptionPane.ERROR_MESSAGE);
					}

					addToUserData(file);
					view.viewData();
				} catch (NullPointerException nulP) {
				}
			}
		}

	}

	private class ViewUsers extends JFrame {
		public JTextArea displayArea = new JTextArea();
		private JLabel header = new JLabel("UWI ON WHEELS");
		private JPanel displayPanel = new JPanel();
		private JScrollPane scrollPane;
		private JButton deleteButton = new JButton("DELETE");
		private JButton logout = new JButton("LOGOUT");
		private JButton returnMain = new JButton("RETURN");
        private JPanel button= new JPanel();

		public ViewUsers() {
			setBounds(300, 90, 420, 700);
			setResizable(false);

			header.setFont(new Font("TIMES NEW ROMAN", Font.BOLD, 20));
			header.setBounds(120, 20, 200, 30);
			displayPanel.add(header);

			add(displayPanel);
            add(button, BorderLayout.SOUTH);
			displayPanel.setBackground(new Color(DARK_NUDE_RED, LIGHT_NUDE_GREEN, LIGHT_NUDE_BLUE));
			displayArea = new JTextArea(34, 30);
			displayArea.setBorder(BorderFactory.createLineBorder(Color.black));

			displayPanel.add(displayArea);
			scrollPane = new JScrollPane(displayArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			displayPanel.add(scrollPane);

			logout.setBounds(10, 500, 80, 25);
			//displayPanel.add(logout);
            button.add(logout);

			returnMain.setBounds(10, 600, 80, 25);
			//displayPanel.add(returnMain);
            button.add(returnMain);


			displayArea.setEditable(false);

			viewData();
			setVisible(true);
			// Button Listeners
			logout.addActionListener(new LogoutListener());
			returnMain.addActionListener(new ReturnListener());
			deleteButton.addActionListener(new DeleteListener());
		}

		private void viewData() {

			displayArea.setText("");
			for (int index = 0; index < users.size(); index++) {
				String textSeparator = "\n___________________________________";
				displayArea.append("  USER FIRSTNAME: \t" + users.get(index).getfname() + " \n  USER LASTNAME : \t"
						+ users.get(index).getlname() + " \n  ID : " + users.get(index).getid() + " \n  EMAIL: \t "
						+ users.get(index).getemail() + "\nPassword: \t ************" + textSeparator + "\n");
			}
		}

		private class LogoutListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dispose();
				Login login = new Login();
			}
		}

		private class ReturnListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				dispose();
				MainPageAdmin main = new MainPageAdmin();
			}
		}

		private class DeleteListener implements ActionListener {
			/**
			 * This method removes a specific person from the arraylist
			 */
			public void actionPerformed(ActionEvent e) {
				dispose();
				try {
					// Implement a search for a user with a specific id
					// If the id is found, remove that person from the arraylist
					// Else, error message, person with id """" not found
					ViewUsers view = new ViewUsers();
					String idToDelete = JOptionPane.showInputDialog("ENTER THE ID OF THE USER TO DELETE");
					boolean idFound = false;
					int userIndex = 0;
					for (int index = 0; index < users.size(); index++) {
						if (users.get(index).getid().equals(idToDelete)) {
							idFound = true;
							users.remove(userIndex);
						}

					}
					if (idFound == false) {
						JOptionPane.showMessageDialog(null, "THE ID YOU ENTERED DOES NOT EXIST", "NO MATCH",
								JOptionPane.ERROR_MESSAGE);
					}

					addToUserData(file);
					view.viewData();
				} catch (NullPointerException nulP) {
				}
			}
		}

	}

	// Updating the data file
	private void addToUserData(String file) {
		try {
			Formatter printFile = new Formatter(file);

			for (int index = 0; index < users.size(); index++) {
				printFile.format(
						users.get(index).getfname() + " " + users.get(index).getlname() + " " + users.get(index).getid()
								+ " " + users.get(index).getemail() + " " + users.get(index).getpassword() + "\n");
			}
			printFile.close();
		} catch (IOException ioException) {
			System.out.println("Error in writing to the file");
		}
	}

	// Read the user data file
	private void readFile(String file) {
		// Reads file and adds the
		String fileData = "";

		users.clear();

		try {

			FileReader reader = new FileReader(file);
			Scanner scanner = new Scanner(reader);

			while (scanner.hasNextLine() == true) {
				String[] line = scanner.nextLine().split(" ");
				String firstname = line[0];
				String lastname = line[1];
				String id = line[2];
				String email = line[3];
				String password = line[4];

				// Can Probably Use one file and just not add password to the arraylist
				/*
				 * System.out.println(firstname);
				 * System.out.println(lastname);System.out.println(id);System.out.println(email)
				 * ;System.out.println(password);
				 */
				if (firstname.equals(" ") || lastname.equals(" ") || id.equals(" ") || email.equals(" ")
						|| password.equals(" ")) {
					System.out.println("UNABLE TO ADD PERSON TO ARRAYLIST");
				} else {
					User newUser = new User(firstname, lastname, id, email, password);
					users.add(newUser);
					// System.out.println(users);

				}

			}
			scanner.close();
			reader.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + file);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error reading the file: " + file);
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException aiob) {

		}

	}

	private void readEmailFile(String emailfile) {
		// Reads file and adds the
		String fileData = "";

		idmail.clear();

		try {

			FileReader reader = new FileReader(emailfile);
			Scanner scanner = new Scanner(reader);

			while (scanner.hasNextLine() == true) {
				String[] line = scanner.nextLine().split(" ");
				String firstname = line[0];
				String lastname = line[1];
				String id = line[2];
				String email = line[3];
				String idemail = firstname + " " + lastname + " " + id + " " + email;
				// System.out.println(idemail);
				idmail.add(idemail);

				// Can Probably Use one file and just not add password to the arraylist
				// System.out.println(id);
				// System.out.println(email);

			}
			scanner.close();
			reader.close();

		} catch (FileNotFoundException e) {
			System.out.println("File not found: " + file);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Error reading the file: " + file);
			e.printStackTrace();
		} catch (ArrayIndexOutOfBoundsException aiob) {

		}

	}

	private class LogoutListener implements ActionListener {
		/**
		 * This method calls Login class
		 */
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				Login login = new Login();
			} catch (NullPointerException nulP) {
			}
		}
	}

	public static User findUser(String id) {
		for (User user : users) {
			if (user.getid().equals(id)) {
				return user;
			}
		}
		return null;
	}
	private class ViewIncidentsListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				dispose();
				JFrame frame = new JFrame("Incidents");
				frame.setBounds(300, 90, 420, 700);
				frame.setResizable(false);
	
				JTextArea textArea = new JTextArea(20, 30);
				textArea.setEditable(false);
				JScrollPane scrollPane = new JScrollPane(textArea);
				frame.add(scrollPane);
				
				StringBuilder incidentsData = new StringBuilder();
				BufferedReader reader = new BufferedReader(new FileReader("Incidents.txt"));
				String line;
				while ((line = reader.readLine()) != null) {
					incidentsData.append(line).append("\n");
				}
				reader.close();
	
				textArea.setText(incidentsData.toString());
	
				frame.setVisible(true);
			} catch (IOException ex) {
				JOptionPane.showMessageDialog(null, "Error reading Incidents file", "Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
    }

