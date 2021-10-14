import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextPane;
import java.awt.Insets;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public class InterviewerWindow extends JFrame{

	public static void main(String[] args) {
		InterviewerWindow window = new InterviewerWindow();

	}
	
	public InterviewerWindow() {
		
		setTitle("Interviewer");
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 172, 0, 207, 34, 277, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 258, 0, 0};
		gridBagLayout.columnWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		GridBagConstraints gbc = new GridBagConstraints();

		JPanel interviewerInfo = new JPanel();
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 5;
		gbc.gridy = 2;
		gbc.gridheight = 2;
		add(interviewerInfo, gbc);

		GridBagLayout gbl_interviewerInfo = new GridBagLayout();
		gbl_interviewerInfo.columnWidths = new int[]{72, 163, 0};
		gbl_interviewerInfo.rowHeights = new int[]{0, 0, 0, 0, 18, 0, 0, 0, 0, 0, 17, 0};
		gbl_interviewerInfo.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
		gbl_interviewerInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		interviewerInfo.setLayout(gbl_interviewerInfo);

		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 1;

		JLabel lblInterviewees = new JLabel("Interviewees");
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 1;
		add(lblInterviewees, gbc);

		JComboBox date_ccb = new JComboBox();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridy = 2;
		add(date_ccb, gbc);

		gbc.gridx = 3;

		JLabel lblNotes = new JLabel("Notes:");
		gbc.anchor = GridBagConstraints.WEST;
		gbc.gridy = 1;
		add(lblNotes, gbc);

		JTextPane notes = new JTextPane();
		gbc.gridheight = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridy = 2;
		add(notes, gbc);

		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 0;

		JLabel lblUsername = new JLabel("Username: ");
		gbc.gridy = 1;
		interviewerInfo.add(lblUsername, gbc);

		JLabel lblCompany = new JLabel("Company: ");
		gbc.gridy = 2;
		interviewerInfo.add(lblCompany, gbc);

		JLabel lblPosition = new JLabel("Position: ");
		gbc.gridy = 3;
		interviewerInfo.add(lblPosition, gbc);

		JLabel lblLegalName = new JLabel("Legal Name: ");
		gbc.gridy = 4;
		interviewerInfo.add(lblLegalName, gbc);

		JLabel lblMobile = new JLabel("Mobile: ");
		gbc.gridy = 5;
		interviewerInfo.add(lblMobile, gbc);

		JLabel lblEmail = new JLabel("Email: ");
		gbc.gridy = 6;
		interviewerInfo.add(lblEmail, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 0, 5, 0);

		JButton saveNotes = new JButton("Save current notes");
		gbc.gridwidth = 2;
		gbc.gridy = 8;
		interviewerInfo.add(saveNotes, gbc);

		JButton submitNotes = new JButton("Complete interview");
		gbc.gridy = 9;
		gbc.gridwidth = 2;
		interviewerInfo.add(submitNotes, gbc);

		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;

		JTextPane username = new JTextPane();
		username.setEditable(false);
		gbc.gridy = 1;
		interviewerInfo.add(username, gbc);

		JTextPane textPane = new JTextPane();
		textPane.setEditable(false);
		gbc.gridy = 2;
		interviewerInfo.add(textPane, gbc);

		JTextPane position = new JTextPane();
		position.setEditable(false);
		gbc.gridy = 3;
		interviewerInfo.add(position, gbc);

		JTextPane legalName = new JTextPane();
		legalName.setEditable(false);
		gbc.gridy = 4;
		interviewerInfo.add(legalName, gbc);

		JTextPane mobile = new JTextPane();
		mobile.setEditable(false);
		gbc.gridy = 5;
		interviewerInfo.add(mobile, gbc);

		JTextPane email = new JTextPane();
		email.setEditable(false);
		gbc.gridy = 6;
		interviewerInfo.add(email, gbc);

		JScrollPane scrollPane = new JScrollPane();
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridy = 3;
		add(scrollPane, gbc);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnAccount = new JMenu("Account");
		menuBar.add(mnAccount);
		
		JMenuItem mntmUpdateAccountInformation = new JMenuItem("Update account information");
		mnAccount.add(mntmUpdateAccountInformation);
		
		JMenuItem mntmChangeMyPassword = new JMenuItem("Change my password");
		mnAccount.add(mntmChangeMyPassword);
		
		JMenuItem mntmLogOut = new JMenuItem("Log out");
		mnAccount.add(mntmLogOut);
		
		setBounds(100, 100, 755, 409);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}

}
