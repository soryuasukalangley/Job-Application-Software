import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;


public class SignupWindow extends JDialog implements ActionListener {

    private JTextField txtUsername, txtLegalName, txtPhoneNumber, txtPassword, txtEmail;
    private JComboBox cbbUserCategory;
    private JComboBox cbbCompany;
    private JButton btnSignup;

    private User user;

    public SignupWindow(JFrame parent) {
        super(parent, ModalityType.APPLICATION_MODAL);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 1;

        JLabel username_lbl = new JLabel("Username: ");
        gbc.gridy = 0;
        add(username_lbl, gbc);

        JLabel legalName_lbl = new JLabel("Legal Name:");
        gbc.gridy = 1;
        add(legalName_lbl, gbc);

        JLabel phoneNumber_lbl = new JLabel("Phone Number:");
        gbc.gridy = 2;
        add(phoneNumber_lbl, gbc);

        JLabel email_lbl = new JLabel("Email: ");
        gbc.gridy = 3;
        add(email_lbl, gbc);

        JLabel category_lbl = new JLabel("Category: ");
        gbc.gridy = 4;
        add(category_lbl, gbc);

        JLabel compant_lbl = new JLabel("Company: ");
        gbc.gridy = 5;
        add(compant_lbl, gbc);

        JLabel password_lbl = new JLabel("Password: ");
        gbc.gridy = 6;
        add(password_lbl, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;

        txtUsername = new JTextField();
        gbc.gridy = 0;
        add(txtUsername, gbc);
        txtUsername.setColumns(20);

        txtLegalName = new JTextField();
        txtLegalName.setColumns(20);
        gbc.gridy = 1;
        add(txtLegalName, gbc);

        txtPhoneNumber = new JTextField();
        gbc.gridy = 2;
        add(txtPhoneNumber, gbc);
        txtPhoneNumber.setColumns(20);

        txtEmail = new JTextField();
        txtEmail.setText("");
        gbc.gridy = 3;
        add(txtEmail, gbc);
        txtEmail.setColumns(20);

        cbbUserCategory = new JComboBox(new String[] {"Coordinator", "Interviewer", "Applicant", "Referrer"});
        cbbUserCategory.setMaximumRowCount(10);
        gbc.gridy = 4;
        add(cbbUserCategory, gbc);

        cbbCompany = new JComboBox(Main.getCompanies().toArray());
        gbc.gridy = 5;
        add(cbbCompany, gbc);

        txtPassword = new JTextField();
        gbc.gridy = 6;
        add(txtPassword, gbc);
        txtPassword.setColumns(20);

        btnSignup = new JButton("Sign up");
        btnSignup.addActionListener(this);
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridy = 7;
        add(btnSignup, gbc);

        pack();
        setVisible(true);
        setResizable(false);
        setBounds(100, 100, 353, 298);
        setSize(253, 198);
        setDefaultCloseOperation(HIDE_ON_CLOSE);
    }

    public User getUser() {
        return this.user;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.cbbUserCategory.getSelectedItem().equals("Coordinator")) {
            this.user = new Coordinator(
                    this.txtUsername.getText(),
                    this.txtPhoneNumber.getText(),
                    this.txtLegalName.getText(),
                    this.txtEmail.getText(),
                    this.txtPassword.getText(),
                    (Company) this.cbbCompany.getSelectedItem()
            );
        } else if (this.cbbUserCategory.getSelectedItem().equals("Applicant")) {
            this.user = new Applicant(
                    this.txtUsername.getText(),
                    this.txtPhoneNumber.getText(),
                    this.txtLegalName.getText(),
                    this.txtEmail.getText(),
                    this.txtPassword.getText(),
                    new Date()
            );
        } else if (this.cbbUserCategory.getSelectedItem().equals("Interviewer")) {
            this.user = new Interviewer(
                    this.txtUsername.getText(),
                    this.txtPhoneNumber.getText(),
                    this.txtLegalName.getText(),
                    this.txtEmail.getText(),
                    this.txtPassword.getText()
            );
        } else if (this.cbbUserCategory.getSelectedItem().equals("Referrer")) {
            this.user = new Referrer(
                    this.txtUsername.getText(),
                    this.txtPhoneNumber.getText(),
                    this.txtLegalName.getText(),
                    this.txtEmail.getText(),
                    this.txtPassword.getText()
            );
        }

        this.setVisible(false);
    }
}

