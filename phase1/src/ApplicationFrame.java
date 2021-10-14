import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ApplicationFrame extends JFrame implements ActionListener {

    private JFileChooser fileChooser;

    private JPanel contentPane;
    private JTextField txtApplicantName;
    private JTextField txtApplicationStatus;
    private JTextField txtCoverLetterPath;
    private JTextField txtResumePath;
    private JTextField txtAdditionalFiles;

    private JComboBox cbbReferences;
    private JComboBox cbbInterviews;

    private JButton btnViewApplicant;
    private JButton btnViewResume;
    private JButton btnAddResume;
    private JButton btnViewCoverLetter;
    private JButton btnAddCoverLetter;

    private JButton btnViewReference;
    private JButton btnViewInterview;
    private JButton btnAddInterview;

    private JButton btnAddAdditionalFiles;
    private JButton btnViewAdditionalFiles;

    private JButton btnWithdraw;
    private JButton btnAcceptOffer;
    private JButton btnDeclineOffer;
    private JButton btnRequest;

    private Application application;
    private DefaultComboBoxModel<Reference> references;
    private DefaultComboBoxModel<Interview> interviews;

    public ApplicationFrame(Application application, boolean initialApplication, boolean editable) {
        this.application = application;
        this.fileChooser = new JFileChooser();
        this.references = new DefaultComboBoxModel<>();
        this.interviews = new DefaultComboBoxModel<>();
        this.initializeComponents(initialApplication, editable);
        this.populateFields();
        this.setVisible(true);
    }

    private void populateFields() {
        this.txtApplicantName.setText(this.application.getApplicant().getLegalName());
        this.txtApplicationStatus.setText(this.application.getStatus().toString());
        if (this.application.getCv() != null)
            this.txtResumePath.setText(this.application.getCv().getAbsolutePath());

        if (this.application.getCl() != null)
            this.txtCoverLetterPath.setText(this.application.getCl().getAbsolutePath());

        this.references.removeAllElements();
        for (Reference ref : this.application.getReferences()) this.references.addElement(ref);

        this.interviews.removeAllElements();
        for (Interview i : this.application.getInterviews()) this.interviews.addElement(i);
    }

    private void initializeComponents(boolean initialApp, boolean editable) {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 652, 300);
        setResizable(false);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        GridBagLayout gbl_contentPane = new GridBagLayout();
        gbl_contentPane.columnWidths = new int[]{0, 0, 312, 89, 99, 0};
        gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        contentPane.setLayout(gbl_contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.gridx = 1;

        JLabel lblApplicant = new JLabel("Applicant: ");
        gbc.gridy = 1;
        contentPane.add(lblApplicant, gbc);

        JLabel lblStatus = new JLabel("Status: ");
        gbc.gridy = 2;
        contentPane.add(lblStatus, gbc);

        JLabel lblResume = new JLabel("Resume: ");
        gbc.gridy = 3;
        contentPane.add(lblResume, gbc);

        JLabel lblCoverLetter = new JLabel("Cover Letter: ");
        gbc.gridy = 4;
        contentPane.add(lblCoverLetter, gbc);

        JLabel lblReferences = new JLabel("References: ");
        gbc.gridy = 5;
        contentPane.add(lblReferences, gbc);

        JLabel lblInterviews = new JLabel("Interviews: ");
        gbc.gridy = 6;
        contentPane.add(lblInterviews, gbc);

        JLabel lblAdditionalFiles = new JLabel("AdditionalFiles");
        gbc.gridy = 7;
        contentPane.add(lblAdditionalFiles, gbc);

        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;

        txtApplicantName = new JTextField();
        txtApplicantName.setEditable(false);
        gbc.gridy = 1;
        contentPane.add(txtApplicantName, gbc);
        txtApplicantName.setColumns(10);

        txtApplicationStatus = new JTextField();
        txtApplicationStatus.setEditable(false);
        gbc.gridy = 2;
        contentPane.add(txtApplicationStatus, gbc);
        txtApplicationStatus.setColumns(10);

        txtResumePath = new JTextField();
        txtResumePath.setEditable(false);
        gbc.gridy = 3;
        contentPane.add(txtResumePath, gbc);
        txtResumePath.setColumns(10);

        txtCoverLetterPath = new JTextField();
        txtCoverLetterPath.setEditable(false);
        gbc.gridy = 4;
        contentPane.add(txtCoverLetterPath, gbc);
        txtCoverLetterPath.setColumns(10);

        cbbReferences = new JComboBox(this.references);
        cbbReferences.setEnabled(editable);
        gbc.gridy = 5;
        contentPane.add(cbbReferences, gbc);

        cbbInterviews = new JComboBox(this.interviews);
        cbbInterviews.setEnabled(editable);
        gbc.gridy = 6;
        contentPane.add(cbbInterviews, gbc);

        txtAdditionalFiles = new JTextField();
        txtAdditionalFiles.setEditable(false);
        gbc.gridy = 7;
        contentPane.add(txtAdditionalFiles, gbc);
        txtAdditionalFiles.setColumns(10);

        btnRequest = new JButton("Request More Information");
        btnRequest.addActionListener(this);
        btnRequest.setEnabled(editable);
        gbc.gridy = 8;
        contentPane.add(btnRequest, gbc);

        gbc.gridx = 3;

        btnViewApplicant = new JButton("View");
        btnViewApplicant.addActionListener(this);
        btnViewApplicant.setEnabled(editable);
        gbc.gridy = 1;
        contentPane.add(btnViewApplicant, gbc);

        btnViewResume = new JButton("View");
        btnViewResume.addActionListener(this);
        btnViewResume.setEnabled(!initialApp);
        gbc.gridy = 3;
        contentPane.add(btnViewResume, gbc);

        btnViewCoverLetter = new JButton("View");
        btnViewCoverLetter.addActionListener(this);
        btnViewCoverLetter.setEnabled(!initialApp);
        gbc.gridy = 4;
        contentPane.add(btnViewCoverLetter, gbc);

        btnViewReference = new JButton("View");
        btnViewReference.setEnabled(editable);
        gbc.gridy = 5;
        contentPane.add(btnViewReference, gbc);

        btnViewInterview = new JButton("View");
        btnViewInterview.addActionListener(this);
        btnViewInterview.setEnabled(editable);
        gbc.gridy = 6;
        contentPane.add(btnViewInterview, gbc);

        btnViewAdditionalFiles = new JButton("View");
        btnViewAdditionalFiles.addActionListener(this);
        btnViewAdditionalFiles.setEnabled(!initialApp);
        gbc.gridy = 7;
        contentPane.add(btnViewAdditionalFiles, gbc);

        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.gridx = 4;

        btnAddResume = new JButton("Add");
        btnAddResume.addActionListener(this);
        btnAddResume.setEnabled(initialApp);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 3;
        contentPane.add(btnAddResume, gbc);

        btnAddCoverLetter = new JButton("Add");
        btnAddCoverLetter.addActionListener(this);
        btnAddCoverLetter.setEnabled(initialApp);
        gbc.gridy = 4;
        contentPane.add(btnAddCoverLetter, gbc);

        btnAddInterview = new JButton("Add");
        btnAddInterview.addActionListener(this);
        btnAddInterview.setEnabled(editable);
        gbc.gridy = 6;
        contentPane.add(btnAddInterview, gbc);

        btnAddAdditionalFiles = new JButton("Add");
        btnAddAdditionalFiles.addActionListener(this);
        btnAddAdditionalFiles.setEnabled(initialApp);
        gbc.gridy = 7;
        contentPane.add(btnAddAdditionalFiles, gbc);

        btnWithdraw = new JButton("Withdraw");
        btnWithdraw.addActionListener(this);
        btnWithdraw.setEnabled(!editable);
        gbc.gridy = 8;
        contentPane.add(btnWithdraw, gbc);

        btnAcceptOffer = new JButton("Accept Offer");
        btnAcceptOffer.addActionListener(this);
        btnAcceptOffer.setEnabled(this.application.getStatus() == Application.ApplicationStatus.JOB_OFFERED &&
                !editable);

        gbc.gridy = 9;
        contentPane.add(btnAcceptOffer, gbc);

        btnDeclineOffer = new JButton("Decline Offer");
        btnDeclineOffer.addActionListener(this);
        btnDeclineOffer.setEnabled(this.application.getStatus() == Application.ApplicationStatus.JOB_OFFERED &&
                !editable);

        gbc.gridy = 10;
        contentPane.add(btnDeclineOffer, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.btnViewApplicant)) {
            JFrame f = new JFrame("Applicant View");
            f.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            f.setSize(450, 255);
            f.setResizable(false);
            f.add(new ApplicantPanel(this.application.getApplicant()), BorderLayout.CENTER);
            f.setVisible(true);
        } else if (e.getSource().equals(this.btnViewResume)) {
            Main.viewFile(this.application.getCv());
        } else if (e.getSource().equals(this.btnViewCoverLetter)) {
            Main.viewFile(this.application.getCl());
        } else if (e.getSource().equals(this.btnAddResume)) {
            this.fileChooser.showOpenDialog(this);
            this.application.setCv(this.fileChooser.getSelectedFile());
        } else if (e.getSource().equals(this.btnAddCoverLetter)) {
            this.fileChooser.showOpenDialog(this);
            this.application.setCl(this.fileChooser.getSelectedFile());
        } else if (e.getSource().equals(this.btnWithdraw)) {
            int opt = JOptionPane.showConfirmDialog(null,
                    "Are you sure you want to withdraw your application?",
                    "Withdraw Application",
                    JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                this.application.withdrawApplication();
            }
        } else if (e.getSource().equals(this.btnViewReference)) {
            if (this.references.getSelectedItem() != null) {
                Reference ref = (Reference) this.references.getSelectedItem();
                Main.viewFile(ref.letter);
            } else {
                JOptionPane.showMessageDialog(this, "You must first select a reference.");
            }
        } else if (e.getSource().equals(this.btnViewInterview)) {
            if (this.interviews.getSelectedItem() != null) {
                Interview interview = (Interview) this.interviews.getSelectedItem();
                InterviewPanel pnlInterview = new InterviewPanel();
                pnlInterview.updateInterview(interview);

                JFrame frmInterview = new JFrame();
                frmInterview.setSize(500, 275);
                frmInterview.setResizable(false);
                frmInterview.add(pnlInterview, BorderLayout.CENTER);
                frmInterview.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
                frmInterview.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "You must first select an interview.");
            }
        } else if (e.getSource().equals(this.btnAddInterview)) {
            DefaultComboBoxModel<Interviewer> interviewers = new DefaultComboBoxModel<>();
            for (Interviewer it : this.application.getJobPosting().getCompany().getInterviewers())
                interviewers.addElement(it);

            System.out.println(interviewers.getSize());

            JComboBox cbbInterviewers = new JComboBox(interviewers);
            JOptionPane.showMessageDialog(this, cbbInterviewers, "Select interviewer", JOptionPane.QUESTION_MESSAGE);

            try {
                Date interviewDate = new SimpleDateFormat("yyyy-MM-dd KK:mm").parse(
                        JOptionPane.showInputDialog(this, "Enter interview date and time (yyyy-mm-dd hh:mm)")
                );

                Interview interview = new Interview((Interviewer) interviewers.getSelectedItem(), interviewDate,
                        this.application);
                this.application.selectForInterview(interview);
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(this, "Invalid date.");
                return;
            } catch (Application.InvalidStateException ex) {
                JOptionPane.showMessageDialog(this, "Invalid state. You cannot schedule an interview at the moment.");
            }

        } else if (e.getSource().equals(this.btnAddAdditionalFiles)) {
            this.fileChooser.showOpenDialog(this);
            this.application.submitInformation(this.fileChooser.getSelectedFile());
        } else if (e.getSource().equals(this.btnRequest)) {
            String information = JOptionPane.showInputDialog("Specific the information you request.");
            try {
                this.application.requestMoreInformation(information);
                txtAdditionalFiles.setText(information);
            } catch (Application.InvalidStateException ex) {
                JOptionPane.showConfirmDialog(this, "Invalid state. You can not request information when the application closed.");
            }
        } else if (e.getSource().equals(this.btnAcceptOffer)) {
            try {
                this.application.acceptOffer();
            } catch (Application.InvalidStateException ex) {
                JOptionPane.showConfirmDialog(this, "Invalid state. You cannot do so unless if the job is offered.");
            }
        } else if (e.getSource().equals(this.btnDeclineOffer)) {
            try {
                this.application.declineOffer();
            } catch (Application.InvalidStateException ex) {
                JOptionPane.showConfirmDialog(this, "Invalid state. You cannot do so unless if the job is offered.");
            }
        }

        this.populateFields();
        Main.saveState();
    }

}
