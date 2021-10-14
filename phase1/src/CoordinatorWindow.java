import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class CoordinatorWindow extends JFrame implements ActionListener {

    private JComboBox ccbApplicant;

    private ApplicantPanel pnlApplicant;
    private Coordinator coordinator;

    private DefaultComboBoxModel<Applicant> applicants;

    JMenuItem mntmLogOut;

    public CoordinatorWindow(Coordinator c) {
        this.coordinator = c;
        this.applicants = new DefaultComboBoxModel<>();
        for (Applicant a : this.coordinator.getCompany().getApplicants())
            this.applicants.addElement(a);

        this.initializeComponents();
    }

    private void initializeComponents() {
        setTitle("Coordinator");

        GridBagLayout gridBagLayout = new GridBagLayout();
        setLayout(gridBagLayout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 5, 5);

        JTabbedPane companyInfo = new JTabbedPane(JTabbedPane.TOP);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(companyInfo, gbc);

        companyInfo.addTab("Job Postings", null,
                new JobPostingPanel(this.coordinator), null);

        JPanel pnlApplicantList = new JPanel();
        companyInfo.addTab("Applicants", null, pnlApplicantList, null);
        pnlApplicantList.setLayout(new BorderLayout());

        JPanel pnlSelectApplicant = new JPanel();
        pnlSelectApplicant.setLayout(new FlowLayout());
        pnlSelectApplicant.add(new JLabel("Applicants:"));

        ccbApplicant = new JComboBox(this.applicants);
        ccbApplicant.addActionListener(this);
        pnlSelectApplicant.add(this.ccbApplicant);

        this.pnlApplicant = new ApplicantPanel();
        pnlApplicantList.add(pnlSelectApplicant, BorderLayout.NORTH);
        pnlApplicantList.add(this.pnlApplicant, BorderLayout.CENTER);

        JPanel interviewer_panel = new JPanel();
        companyInfo.addTab("Interviewers", null, interviewer_panel, null);
        GridBagLayout gbl_interviewer_panel = new GridBagLayout();
        gbl_interviewer_panel.columnWidths = new int[]{82, 331, 273};
        gbl_interviewer_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_interviewer_panel.columnWeights = new double[]{0.0, 0.0, 0.0};
        gbl_interviewer_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        interviewer_panel.setLayout(gbl_interviewer_panel);

        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;

        JLabel lblInterviewers = new JLabel("Interviewers: ");
        gbc.gridy = 1;
        interviewer_panel.add(lblInterviewers, gbc);

        JLabel lblUsername_1 = new JLabel("Username: ");
        gbc.gridy = 3;
        interviewer_panel.add(lblUsername_1, gbc);

        JLabel lblLegalName_1 = new JLabel("Legal Name: ");
        gbc.gridy = 4;
        interviewer_panel.add(lblLegalName_1, gbc);

        JLabel lblEmail_1 = new JLabel("Email: ");
        gbc.gridy = 5;
        interviewer_panel.add(lblEmail_1, gbc);

        JLabel lblMobile_1 = new JLabel("Mobile: ");
        gbc.gridy = 6;
        interviewer_panel.add(lblMobile_1, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;

        JButton updateIntInfo = new JButton("Update");
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridy = 8;
        interviewer_panel.add(updateIntInfo, gbc);

        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 1;

        JComboBox intervierwer = new JComboBox();
        gbc.gridy = 1;
        interviewer_panel.add(intervierwer, gbc);

        gbc.fill = GridBagConstraints.BOTH;

        JTextPane intUsername = new JTextPane();
        gbc.gridy = 3;
        interviewer_panel.add(intUsername, gbc);

        JTextPane intLegalName = new JTextPane();
        gbc.gridy = 4;
        interviewer_panel.add(intLegalName, gbc);

        JTextPane intEmail = new JTextPane();
        gbc.gridy = 5;
        interviewer_panel.add(intEmail, gbc);

        JTextPane intMobile = new JTextPane();
        gbc.gridy = 6;
        interviewer_panel.add(intMobile, gbc);

        UserInformationPanel myInfo = new UserInformationPanel(this.coordinator);
        gbc.gridx = 2;
        gbc.gridy = 2;
        getContentPane().add(myInfo, gbc);

        setBounds(100, 100, 984, 706);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnAccount = new JMenu("Account");
        menuBar.add(mnAccount);

        JMenuItem mntmUpdateAccountInformation = new JMenuItem("Update account information");
        mnAccount.add(mntmUpdateAccountInformation);

        JMenuItem mntmChangeMyPassword = new JMenuItem("Change my password");
        mnAccount.add(mntmChangeMyPassword);

        mntmLogOut = new JMenuItem("Log out");
        mnAccount.add(mntmLogOut);
        setVisible(true);
        mntmLogOut.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.ccbApplicant)) {
            Applicant app = this.applicants.getElementAt(this.ccbApplicant.getSelectedIndex());
            if (app != null)
                this.pnlApplicant.updateApplicant(app);
        }else if(e.getSource().equals(mntmLogOut)){
            setVisible(false);
            new LoginWindow();
        }

        Main.saveState();
    }
}