import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicantPanel extends JPanel implements ActionListener, MouseListener {

    private JTextField txtUsername;
    private JTextField txtLegalName;
    private JTextField txtMobile;
    private JTextField txtEmail;
    private JTextField txtCreationDate;

    private JList lstApplications;

    private JButton btnUpdate;

    private DefaultListModel<Application> applications;

    private Applicant applicant;

    public ApplicantPanel() {
        this.applications = new DefaultListModel<>();
        this.initializeComponents();
    }

    public ApplicantPanel(Applicant applicant) {
        System.out.println(applicant.getLegalName());
        this.applicant = applicant;
        this.applications = new DefaultListModel<>();
        this.initializeComponents();
        this.populateFields();
    }

    public void updateApplicant(Applicant applicant) {
        this.applicant = applicant;
        this.populateFields();
    }

    private String formatDate(Date d) {
        return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }

    public void populateFields() {
        if (this.applicant == null) return;

        this.txtUsername.setText(this.applicant.getUsername());
        this.txtLegalName.setText(this.applicant.getLegalName());
        this.txtMobile.setText(this.applicant.getPhoneNumber());
        this.txtEmail.setText(this.applicant.getEmail());
        this.txtCreationDate.setText(this.formatDate(this.applicant.getAccountCreationDate()));

        this.applications.clear();
        for (Application app : this.applicant.getOngoingApplications())
            this.applications.addElement(app);
    }

    private void updateDataFromFields() {
        if (this.applicant == null) return;

        this.applicant.setUsername(this.txtUsername.getText());
        this.applicant.setLegalName(this.txtLegalName.getText());
        this.applicant.setPhoneNumber(this.txtMobile.getText());
        this.applicant.setEmail(this.txtEmail.getText());
    }

    private void initializeComponents() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 313, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 93, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 1;

        JLabel lblUsername = new JLabel("Username: ");
        gbc.gridy = 1;
        add(lblUsername, gbc);

        JLabel lblLegalName = new JLabel("Legal Name: ");
        gbc.gridy = 2;
        add(lblLegalName, gbc);

        JLabel lblMobile = new JLabel("Mobile: ");
        gbc.gridy = 3;
        add(lblMobile, gbc);

        JLabel lblEmail = new JLabel("Email: ");
        gbc.gridy = 4;
        add(lblEmail, gbc);

        JLabel lblCreationDate = new JLabel("Creation Date: ");
        gbc.gridy = 5;
        add(lblCreationDate, gbc);

        JLabel lblApplications = new JLabel("Applications: ");
        gbc.gridy = 6;
        add(lblApplications, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        btnUpdate = new JButton("Update");
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridy = 7;
        add(btnUpdate, gbc);

        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.gridx = 2;

        txtUsername = new JTextField();
        gbc.gridy = 1;
        add(txtUsername, gbc);
        txtUsername.setColumns(10);


        txtLegalName = new JTextField();
        gbc.gridy = 2;
        add(txtLegalName, gbc);
        txtLegalName.setColumns(10);

        txtMobile = new JTextField();
        gbc.gridy = 3;
        add(txtMobile, gbc);
        txtMobile.setColumns(10);

        txtEmail = new JTextField();
        gbc.gridy = 4;
        add(txtEmail, gbc);
        txtEmail.setColumns(10);

        txtCreationDate = new JTextField();
        gbc.gridy = 5;
        add(txtCreationDate, gbc);
        txtCreationDate.setColumns(10);
        txtCreationDate.setEditable(false);

        JScrollPane scrollPane = new JScrollPane();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 6;
        add(scrollPane, gbc);

        lstApplications = new JList(this.applications);
        lstApplications.addMouseListener(this);
        scrollPane.setViewportView(lstApplications);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.btnUpdate)) {
            this.updateDataFromFields();
        }
        Main.saveState();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Application app = this.applications.getElementAt(this.lstApplications.getSelectedIndex());
            if (app != null)
                new ApplicationFrame(app, false, false);
        }
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }
}

