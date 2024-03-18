import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MessageFrame extends JFrame { // Frame to display any message

	private MessageFrame ef = this; // this class reference

	MessageFrame(String message, String Title) { // constructor for setting up frame
		JPanel panel = new JPanel();
		JPanel bpanel = new JPanel();
		JLabel label = new JLabel(message);
		JButton ok = new JButton("Ok");
		ok.setBackground(Color.GREEN);
		this.setTitle(Title);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLocation(520, 290);
		this.setPreferredSize(new Dimension(350, 100));
		panel.add(label);
		bpanel.add(ok);
		ok.addActionListener(new okAction());
		this.add(panel, BorderLayout.CENTER);
		this.add(bpanel, BorderLayout.SOUTH);
		this.pack();
		this.setVisible(true);
	}

	private class okAction implements ActionListener { // button listener
		public void actionPerformed(ActionEvent e) {
			ef.dispose();
		}
	}

}
