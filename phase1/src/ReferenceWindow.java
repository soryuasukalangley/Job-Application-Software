import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReferenceWindow extends JFrame implements ActionListener {
	
	private JTextField txtApplicant, txtRrfPath;
	private JButton btnSubmitReference, btnSelectFile;
	private JComboBox cbbPosition;

	private JFileChooser fileChooser;
	private DefaultComboBoxModel<JobPosting> posting;

	private Reference reference;

	public ReferenceWindow(Reference ref) {
		this.fileChooser = new JFileChooser();
		this.posting = new DefaultComboBoxModel<>();
		this.reference = ref;

		for (Company c : Main.getCompanies()) {
			for (JobPosting p : c.getOngoingPostings()) {
				this.posting.addElement(p);
			}
		}

		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 175, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.WEST;
		gbc.insets = new Insets(0, 0, 5, 5);
		gbc.gridx = 1;

		JLabel lblApplicant = new JLabel("Applicant: ");
		gbc.gridy = 1;
		add(lblApplicant, gbc);

		JLabel lblPosition = new JLabel("Position: ");
		gbc.gridy = 2;
		add(lblPosition, gbc);

		JLabel lblReferenceLetter = new JLabel("Reference Letter: ");
		gbc.gridy = 3;
		add(lblReferenceLetter, gbc);

		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 2;

		txtApplicant = new JTextField();
		gbc.gridy = 1;
		add(txtApplicant, gbc);
		txtApplicant.setColumns(10);

		cbbPosition = new JComboBox(this.posting);
		gbc.gridy = 2;
		add(cbbPosition, gbc);

		txtRrfPath = new JTextField();
		txtRrfPath.setEditable(false);
		gbc.gridy = 3;
		add(txtRrfPath, gbc);
		txtRrfPath.setColumns(10);

		btnSubmitReference = new JButton("Submit Reference");
		gbc.insets = new Insets(0, 0, 0, 5);
		gbc.gridy = 4;
		add(btnSubmitReference, gbc);

		btnSelectFile = new JButton("Select File");
		gbc.insets = new Insets(0, 0, 5, 0);
		gbc.gridx = 3;
		gbc.gridy = 3;
		add(btnSelectFile, gbc);

		setVisible(true);
		setResizable(false);
		setBounds(100, 100, 426, 195);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(this.btnSelectFile)) {
			this.fileChooser.showOpenDialog(this);
			this.reference.setLetter(this.fileChooser.getSelectedFile());
		} else if (e.getSource().equals(this.btnSubmitReference)) {
			//TODO: Implement
		}
	}
}
