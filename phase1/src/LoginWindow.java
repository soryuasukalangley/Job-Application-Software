import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame implements ActionListener {

    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnSignup;

    public LoginWindow() {

        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWeights = new double[]{1, 2};
        getContentPane().setLayout(gridBagLayout);

        JLabel username_lbl = new JLabel("Username: ");
        GridBagConstraints gbc_username_lbl = new GridBagConstraints();
        gbc_username_lbl.anchor = GridBagConstraints.SOUTHWEST;
        gbc_username_lbl.insets = new Insets(0, 0, 5, 5);
        gbc_username_lbl.gridx = 0;
        gbc_username_lbl.gridy = 0;
        getContentPane().add(username_lbl, gbc_username_lbl);

        txtUsername = new JTextField("", 20);
        GridBagConstraints gbc_username_txtpane = new GridBagConstraints();
        gbc_username_txtpane.fill = GridBagConstraints.HORIZONTAL;
        gbc_username_txtpane.insets = new Insets(0, 0, 5, 0);
        gbc_username_txtpane.gridx = 1;
        gbc_username_txtpane.gridy = 0;
        getContentPane().add(txtUsername, gbc_username_txtpane);
        txtUsername.setColumns(20);

        JLabel lblPassword = new JLabel("Password: ");
        GridBagConstraints gbc_password_lbl = new GridBagConstraints();
        gbc_password_lbl.anchor = GridBagConstraints.WEST;
        gbc_password_lbl.insets = new Insets(0, 0, 5, 5);
        gbc_password_lbl.gridx = 0;
        gbc_password_lbl.gridy = 1;
        getContentPane().add(lblPassword, gbc_password_lbl);

        txtPassword = new JPasswordField("", 20);
        GridBagConstraints gbc_password_txtpane = new GridBagConstraints();
        gbc_password_txtpane.fill = GridBagConstraints.HORIZONTAL;
        gbc_password_txtpane.insets = new Insets(0, 0, 5, 0);
        gbc_password_txtpane.gridx = 1;
        gbc_password_txtpane.gridy = 1;
        getContentPane().add(txtPassword, gbc_password_txtpane);
        txtPassword.setColumns(20);

        btnLogin = new JButton("Log in");
        btnLogin.addActionListener(this);
        GridBagConstraints gbc_login_btn = new GridBagConstraints();
        gbc_login_btn.insets = new Insets(0, 0, 5, 5);
        gbc_login_btn.gridx = 0;
        gbc_login_btn.gridy = 2;
        getContentPane().add(btnLogin, gbc_login_btn);

        btnSignup = new JButton("Sign Up");
        btnSignup.addActionListener(this);
        GridBagConstraints gbc_signup_btn = new GridBagConstraints();
        gbc_signup_btn.insets = new Insets(0, 0, 5, 5);
        gbc_signup_btn.gridx = 1;
        gbc_signup_btn.gridy = 2;
        getContentPane().add(btnSignup, gbc_signup_btn);


        setBounds(100, 100, 293, 154);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.btnLogin)) {
            for (User u : Main.getUsers()) {
                if (u.getUsername().equals(this.txtUsername.getText())) {
                    if (u.validatePassword(this.txtPassword.getText())) {
                        if (u instanceof Applicant) {
                            new ApplicantFrame((Applicant) u);
                        } else if (u instanceof Coordinator) {
                            new CoordinatorWindow((Coordinator) u);
                        } else if (u instanceof Interviewer) {
                            new InterviewerFrame((Interviewer) u);
                        } else if (u instanceof Referrer) {
                            new ReferrerWindow((Referrer) u);
                        }

                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "Invalid Password");
                    }

                    return;
                }
            }

            JOptionPane.showMessageDialog(this, "User not found");
        } else if (e.getSource().equals(this.btnSignup)) {
            SignupWindow signupWindow = new SignupWindow(this);
            if (signupWindow.getUser() != null) {
                Main.users.add(signupWindow.getUser());
            }
        }
    }
}


