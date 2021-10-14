import javax.swing.*;
import java.awt.*;

public class UserInformationPanel extends JPanel {

    private User user;

    public UserInformationPanel(User u) {
        this.user = u;
        this.initializeComponents();
    }

    private void initializeComponents() {
        GridBagLayout gbl_myInfo = new GridBagLayout();
        gbl_myInfo.columnWidths = new int[]{0, 151};
        gbl_myInfo.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 188, 0, 0, 0, 0};
        gbl_myInfo.columnWeights = new double[]{0.0, 0.0};
        gbl_myInfo.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        this.setLayout(gbl_myInfo);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 0;

        JLabel lblUsername_2 = new JLabel("Username: ");
        gbc.gridy = 1;
        this.add(lblUsername_2, gbc);

        JLabel lblCompany = new JLabel("Company: ");
        gbc.gridy = 2;
        this.add(lblCompany, gbc);

        JLabel lblPosition_1 = new JLabel("Position: ");
        gbc.gridy = 3;
        this.add(lblPosition_1, gbc);

        JLabel lblLegalName_2 = new JLabel("Legal Name: ");
        gbc.gridy = 4;
        this.add(lblLegalName_2, gbc);

        JLabel lblMobile_2 = new JLabel("Mobile: ");
        gbc.gridy = 5;
        this.add(lblMobile_2, gbc);

        JLabel lblEmail_2 = new JLabel("Email: ");
        gbc.gridy = 6;
        this.add(lblEmail_2, gbc);

        JLabel lblMessage_1 = new JLabel("Message: ");
        lblMessage_1.setEnabled(false);
        gbc.gridy = 8;
        this.add(lblMessage_1, gbc);

        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.fill = GridBagConstraints.BOTH;

        JTextField myMsg = new JTextField();
        myMsg.setEnabled(false);
        myMsg.setEditable(false);
        gbc.gridy = 9;
        gbc.gridwidth = 2;
        this.add(myMsg, gbc);

        gbc.gridx = 1;

        JTextField myUsername = new JTextField(this.user.getUsername());
        myUsername.setEditable(false);
        gbc.gridy = 1;
        this.add(myUsername, gbc);

        JTextField myCompany = new JTextField();
        if (this.user instanceof Coordinator) {
            myCompany.setText(((Coordinator) this.user).getCompany().getName());
        }
        myCompany.setEditable(false);
        gbc.gridy = 2;
        this.add(myCompany, gbc);

        JTextField myPosition = new JTextField();
        myPosition.setEditable(false);
        if (this.user instanceof Coordinator) {
            myPosition.setText("Coordinator");
        } else if (this.user instanceof Applicant) {
            myPosition.setText("Applicant");
        } else if (this.user instanceof Interviewer) {
            myPosition.setText("Interviewer");
        } else if (this.user instanceof Referrer) {
            myPosition.setText("Referrer");
        }
        gbc.gridy = 3;
        this.add(myPosition, gbc);

        JTextField myLegalName = new JTextField(this.user.getLegalName());
        myLegalName.setEditable(false);
        gbc.gridy = 4;
        this.add(myLegalName, gbc);

        JTextField myMobile = new JTextField(this.user.getPhoneNumber());
        myMobile.setEditable(false);
        gbc.gridy = 5;
        this.add(myMobile, gbc);

        JTextField myEmail = new JTextField(this.user.getEmail());
        myEmail.setEditable(false);
        gbc.gridy = 6;
        this.add(myEmail, gbc);
    }
}
