import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.awt.image.BufferedImage;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RateBike extends JFrame {
    private JTextField bikeID = new JTextField(20);
    private JTextArea experience = new JTextArea();
    private JTextArea improvements = new JTextArea();
    private JComboBox<String> gradeDropdown;

    public RateBike() {
        setTitle("Rate a Bike");

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));

        addLabelAndField(contentPane, "Please enter the Bike's ID:", bikeID);
        addLabelAndDropdown(contentPane, "Letter Grade [A, B, C, D, E]:", new String[]{"A", "B", "C", "D", "E"});
        addLabelAndTextArea(contentPane, "Tell us about your riding experience:", experience);
        addLabelAndTextArea(contentPane, "What are some improvements to be made:", improvements);

        BufferedImage myPicture = null;
        try {
            myPicture = ImageIO.read(new File("rates.jpg"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        JLabel picLabel = new JLabel(new ImageIcon(myPicture));
        picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(picLabel);

        JPanel buttonPanel = new JPanel();
        JButton cmdRate = new JButton("Rate Bike");
        JButton cmdMenu = new JButton("Return to Menu");
        cmdRate.addActionListener(new RateBikeButtonListener());
        cmdMenu.addActionListener(new CloseButtonListener());
        buttonPanel.add(cmdMenu);
        buttonPanel.add(cmdRate);
        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPane.add(buttonPanel);

        setContentPane(contentPane);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void addLabelAndField(Container container, String labelText, JTextField textField) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(labelText));
        panel.add(textField);
        container.add(panel);
        container.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing
    }

    private void addLabelAndDropdown(Container container, String labelText, String[] options) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(labelText));
        gradeDropdown = new JComboBox<>(options);
        panel.add(gradeDropdown);
        container.add(panel);
        container.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing
    }

    private void addLabelAndTextArea(Container container, String labelText, JTextArea textArea) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(new JLabel(labelText));
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(200, 100)); // Set preferred size for scroll pane
        panel.add(scrollPane);
        container.add(panel);
        container.add(Box.createRigidArea(new Dimension(0, 5))); // Add spacing
    }

    private boolean isValidGrade(String grade) {
        return grade.equals("A") || grade.equals("B") || grade.equals("C") || grade.equals("D") || grade.equals("E");
    }

    public class CloseButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            dispose();
            MainPage main = new MainPage();
        }
    }

    private boolean isValidID(String filePath, String ID) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("file.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("_");
                for (String part : parts) {
                    if (part.equals(ID)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public class RateBikeButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("Rates.txt", true))) {
                String ID = bikeID.getText();
                String bikegrade = (String) gradeDropdown.getSelectedItem();
                String bikingexperience = experience.getText();
                String improve = improvements.getText();

                if (!isValidGrade(bikegrade)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid grade from the specified list");
                    return;
                }

                if (!isValidID("file.txt", ID)) {
                    JOptionPane.showMessageDialog(null, "Please enter a valid bike ID number");
                    return;
                }

                writer.write("BikeID: " + ID);
                writer.newLine();
                writer.write("Bike Rating: " + bikegrade);
                writer.newLine();
                writer.write("Experience: " + bikingexperience);
                writer.newLine();
                writer.write("Improvements: " + improve);
                writer.newLine();
                writer.newLine();

                JOptionPane.showMessageDialog(null, "Thank you for your rating!");
            } catch (IOException error) {
                JOptionPane.showMessageDialog(null, error);
                dispose();
                MainPage main = new MainPage();
            }
            dispose();
            MainPage main = new MainPage();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RateBike());
    }
}
