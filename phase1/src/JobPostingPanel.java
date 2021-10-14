import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class JobPostingPanel extends JPanel implements ActionListener, MouseListener {
    private JComboBox ccbPosition;

    private JTextField txtPositionName;
    private JTextField txtPostingStatus;
    private JTextField txtStartDate;
    private JTextField txtEndDate;
    private JTextField txtPositionsAvailable;
    private JTextField txtNumReferencesRequired;
    private JTextField txtReferencesDeadline;
    private JTextArea txtDescription;

    private JList lstCurrentApplications;

    private JButton btnUpdatePosting;
    private JButton btnAddPosting;
    private JButton btnApply;

    private Coordinator coordinator;
    private Applicant applicant;
    private DefaultComboBoxModel<JobPosting> postings;
    private DefaultListModel<Application> currentApplications;

    private String formatDate(Date d) {
        return new SimpleDateFormat("yyyy-MM-dd").format(d);
    }

    private Date parseDateFromString(String str) throws ParseException {
        return new SimpleDateFormat("yyyy-MM-dd").parse(str);
    }

    public JobPostingPanel(ArrayList<JobPosting> jobPostings, Applicant applicant) {
        this.currentApplications = new DefaultListModel<>();
        this.postings = new DefaultComboBoxModel<>();
        this.applicant = applicant;
        this.refreshPostings(jobPostings);
        this.initializeComponents(true);

        if (this.postings.getSelectedItem() != null)
            this.populateFields((JobPosting) this.postings.getSelectedItem());
    }

    public JobPostingPanel(ArrayList<JobPosting> jobPostings) {
        this.currentApplications = new DefaultListModel<>();
        this.postings = new DefaultComboBoxModel<>();
        this.refreshPostings(jobPostings);
        this.initializeComponents(false);

        if (this.postings.getSelectedItem() != null)
            this.populateFields((JobPosting) this.postings.getSelectedItem());
    }

    public JobPostingPanel(Coordinator c) {
        this.coordinator = c;
        this.currentApplications = new DefaultListModel<>();
        this.postings = new DefaultComboBoxModel<>();
        this.refreshPostings(c.getCompany().getOngoingPostings());
        this.initializeComponents(false);

        if (this.postings.getSelectedItem() != null)
            this.populateFields((JobPosting) this.postings.getSelectedItem());
    }

    private void refreshPostings(ArrayList<JobPosting> jobPostings) {
        this.postings.removeAllElements();
        for (JobPosting p : jobPostings)
            this.postings.addElement(p);
    }

    private void populateFields(JobPosting p) {
        if (p == null) return;

        this.txtPositionName.setText(p.getName());
//        this.txtPostingStatus.setText(p.getStatus().toString());
        this.txtStartDate.setText(this.formatDate(p.getStartDate()));
        this.txtEndDate.setText(this.formatDate(p.getEndDate()));
        this.txtPositionsAvailable.setText(Integer.toString(p.getNumSpots()));
        this.txtNumReferencesRequired.setText(Integer.toString(p.getNumReferencesRequired()));
        this.txtReferencesDeadline.setText(this.formatDate(p.getReferenceDeadline()));
        this.txtDescription.setText(p.getDescription());

        this.currentApplications.clear();
        for (Application app : p.getApplications())
            this.currentApplications.addElement(app);
    }

    private void updateDataFromFields(JobPosting p) {
        if (p == null) return;

        try {
            p.setName(this.txtPositionName.getText());
            p.setStartDate(this.parseDateFromString(this.txtStartDate.getText()));
            p.setEndDate(this.parseDateFromString(this.txtEndDate.getText()));
            p.setNumSpots(Integer.parseInt(this.txtPositionsAvailable.getText()));
            p.setNumReferencesRequired(Integer.parseInt(this.txtNumReferencesRequired.getText()));
            p.setReferenceDeadline(this.parseDateFromString(this.txtReferencesDeadline.getText()));
            p.setDescription(this.txtDescription.getText());

            JOptionPane.showMessageDialog(this, "Fields updated");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "The formatting of one or more of the inputs is invalid.");
        }

        this.populateFields(p);
    }

    private void initializeComponents(boolean isApplicantView) {
        GridBagLayout gbl_jobs_panel = new GridBagLayout();
        gbl_jobs_panel.columnWidths = new int[]{0, 106, 427};
        gbl_jobs_panel.columnWidths = new int[]{0, 31, 21, 0, 25, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_jobs_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 13, 101, 0, 0};
        gbl_jobs_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 0.0};
        gbl_jobs_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, 1.0};
        this.setLayout(gbl_jobs_panel);

        JLabel lblPosition = new JLabel("Positions: ");
        GridBagConstraints gbc_lblPosition = new GridBagConstraints();
        gbc_lblPosition.insets = new Insets(0, 0, 5, 5);
        gbc_lblPosition.anchor = GridBagConstraints.WEST;
        gbc_lblPosition.gridx = 1;
        gbc_lblPosition.gridy = 1;
        this.add(lblPosition, gbc_lblPosition);

        ccbPosition = new JComboBox(this.postings);
        ccbPosition.addActionListener(this);
        GridBagConstraints gbc_jobs_ccb = new GridBagConstraints();
        gbc_jobs_ccb.insets = new Insets(0, 0, 5, 5);
        gbc_jobs_ccb.fill = GridBagConstraints.HORIZONTAL;
        gbc_jobs_ccb.gridx = 2;
        gbc_jobs_ccb.gridy = 1;
        this.add(ccbPosition, gbc_jobs_ccb);

        JLabel lblName = new JLabel("Name: ");
        GridBagConstraints gbc_lblName = new GridBagConstraints();
        gbc_lblName.anchor = GridBagConstraints.WEST;
        gbc_lblName.insets = new Insets(0, 0, 5, 5);
        gbc_lblName.gridx = 1;
        gbc_lblName.gridy = 4;
        this.add(lblName, gbc_lblName);

        txtPositionName = new JTextField();
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.insets = new Insets(0, 0, 5, 5);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 2;
        gbc_textField.gridy = 4;
        this.add(txtPositionName, gbc_textField);
        txtPositionName.setColumns(10);

        btnUpdatePosting = new JButton("Update");
        btnUpdatePosting.setVisible(!isApplicantView);
        btnUpdatePosting.addActionListener(this);
        GridBagConstraints gbc_updateJob = new GridBagConstraints();
        gbc_updateJob.fill = GridBagConstraints.HORIZONTAL;
        gbc_updateJob.insets = new Insets(0, 0, 5, 5);
        gbc_updateJob.gridx = 3;
        gbc_updateJob.gridy = 4;
        this.add(btnUpdatePosting, gbc_updateJob);

        btnApply = new JButton("Apply");
        btnApply.setVisible(isApplicantView);
        btnApply.addActionListener(this);
        GridBagConstraints gbc_Apply = new GridBagConstraints();
        gbc_Apply.fill = GridBagConstraints.HORIZONTAL;
        gbc_Apply.insets = new Insets(0, 0, 5, 5);
        gbc_Apply.gridx = 3;
        gbc_Apply.gridy = 5;
        this.add(btnApply, gbc_Apply);

        JLabel lblStatus = new JLabel("Status: ");
        GridBagConstraints gbc_lblStatus = new GridBagConstraints();
        gbc_lblStatus.anchor = GridBagConstraints.WEST;
        gbc_lblStatus.insets = new Insets(0, 0, 5, 5);
        gbc_lblStatus.gridx = 1;
        gbc_lblStatus.gridy = 5;
        this.add(lblStatus, gbc_lblStatus);

        txtPostingStatus = new JTextField();
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.insets = new Insets(0, 0, 5, 5);
        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.gridx = 2;
        gbc_textField_1.gridy = 5;
        this.add(txtPostingStatus, gbc_textField_1);
        txtPostingStatus.setColumns(10);

        btnAddPosting = new JButton("Add Position");
        btnAddPosting.setVisible(!isApplicantView);
        btnAddPosting.addActionListener(this);
        GridBagConstraints gbc_addJob = new GridBagConstraints();
        gbc_addJob.fill = GridBagConstraints.HORIZONTAL;
        gbc_addJob.insets = new Insets(0, 0, 5, 5);
        gbc_addJob.gridx = 3;
        gbc_addJob.gridy = 5;
        this.add(btnAddPosting, gbc_addJob);

        JLabel lblStart = new JLabel("Posted On: ");
        GridBagConstraints gbc_lblStart = new GridBagConstraints();
        gbc_lblStart.anchor = GridBagConstraints.WEST;
        gbc_lblStart.insets = new Insets(0, 0, 5, 5);
        gbc_lblStart.gridx = 1;
        gbc_lblStart.gridy = 6;
        this.add(lblStart, gbc_lblStart);

        txtStartDate = new JTextField();
        GridBagConstraints gbc_textField_2 = new GridBagConstraints();
        gbc_textField_2.insets = new Insets(0, 0, 5, 5);
        gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_2.gridx = 2;
        gbc_textField_2.gridy = 6;
        this.add(txtStartDate, gbc_textField_2);
        txtStartDate.setColumns(10);

        JLabel lblEnd = new JLabel("End Date: ");
        GridBagConstraints gbc_lblEnd = new GridBagConstraints();
        gbc_lblEnd.anchor = GridBagConstraints.WEST;
        gbc_lblEnd.insets = new Insets(0, 0, 5, 5);
        gbc_lblEnd.gridx = 1;
        gbc_lblEnd.gridy = 7;
        this.add(lblEnd, gbc_lblEnd);

        txtEndDate = new JTextField();
        GridBagConstraints gbc_textField_3 = new GridBagConstraints();
        gbc_textField_3.insets = new Insets(0, 0, 5, 5);
        gbc_textField_3.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_3.gridx = 2;
        gbc_textField_3.gridy = 7;
        this.add(txtEndDate, gbc_textField_3);
        txtEndDate.setColumns(10);

        JLabel lblSpots = new JLabel("Positions Available: ");
        GridBagConstraints gbc_lblSpots = new GridBagConstraints();
        gbc_lblSpots.anchor = GridBagConstraints.WEST;
        gbc_lblSpots.insets = new Insets(0, 0, 5, 5);
        gbc_lblSpots.gridx = 1;
        gbc_lblSpots.gridy = 8;
        this.add(lblSpots, gbc_lblSpots);

        txtPositionsAvailable = new JTextField();
        GridBagConstraints gbc_textField_4 = new GridBagConstraints();
        gbc_textField_4.insets = new Insets(0, 0, 5, 5);
        gbc_textField_4.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_4.gridx = 2;
        gbc_textField_4.gridy = 8;
        this.add(txtPositionsAvailable, gbc_textField_4);
        txtPositionsAvailable.setColumns(10);

        JLabel lblReferenceRequired = new JLabel("Reference Required: ");
        GridBagConstraints gbc_lblReferenceRequired = new GridBagConstraints();
        gbc_lblReferenceRequired.anchor = GridBagConstraints.WEST;
        gbc_lblReferenceRequired.insets = new Insets(0, 0, 5, 5);
        gbc_lblReferenceRequired.gridx = 1;
        gbc_lblReferenceRequired.gridy = 9;
        this.add(lblReferenceRequired, gbc_lblReferenceRequired);

        txtNumReferencesRequired = new JTextField();
        GridBagConstraints gbc_textField_5 = new GridBagConstraints();
        gbc_textField_5.insets = new Insets(0, 0, 5, 5);
        gbc_textField_5.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_5.gridx = 2;
        gbc_textField_5.gridy = 9;
        this.add(txtNumReferencesRequired, gbc_textField_5);
        txtNumReferencesRequired.setColumns(10);

        JLabel lblSubmissionDeadline = new JLabel("Submission Deadline: ");
        GridBagConstraints gbc_lblSubmissionDeadline = new GridBagConstraints();
        gbc_lblSubmissionDeadline.anchor = GridBagConstraints.WEST;
        gbc_lblSubmissionDeadline.insets = new Insets(0, 0, 5, 5);
        gbc_lblSubmissionDeadline.gridx = 1;
        gbc_lblSubmissionDeadline.gridy = 10;
        this.add(lblSubmissionDeadline, gbc_lblSubmissionDeadline);

        txtReferencesDeadline = new JTextField();
        GridBagConstraints gbc_textField_6 = new GridBagConstraints();
        gbc_textField_6.insets = new Insets(0, 0, 5, 5);
        gbc_textField_6.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_6.gridx = 2;
        gbc_textField_6.gridy = 10;
        this.add(txtReferencesDeadline, gbc_textField_6);
        txtReferencesDeadline.setColumns(10);

        JLabel lblDescription = new JLabel("Description: ");
        GridBagConstraints gbc_lblDescription = new GridBagConstraints();
        gbc_lblDescription.anchor = GridBagConstraints.WEST;
        gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
        gbc_lblDescription.gridx = 1;
        gbc_lblDescription.gridy = 12;
        this.add(lblDescription, gbc_lblDescription);

        txtDescription = new JTextArea();
        GridBagConstraints gbc_textArea = new GridBagConstraints();
        gbc_textArea.insets = new Insets(0, 0, 5, 5);
        gbc_textArea.fill = GridBagConstraints.BOTH;
        gbc_textArea.gridx = 2;
        gbc_textArea.gridy = 12;
        this.add(txtDescription, gbc_textArea);

        JLabel lblApplications = new JLabel("Applications: ");
        lblApplications.setVisible(this.coordinator != null);
        GridBagConstraints gbc_lblApplications = new GridBagConstraints();
        gbc_lblApplications.anchor = GridBagConstraints.WEST;
        gbc_lblApplications.insets = new Insets(0, 0, 5, 5);
        gbc_lblApplications.gridx = 1;
        gbc_lblApplications.gridy = 13;
        this.add(lblApplications, gbc_lblApplications);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setVisible(this.coordinator != null);
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.insets = new Insets(0, 0, 5, 5);
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 2;
        gbc_scrollPane.gridy = 13;
        this.add(scrollPane, gbc_scrollPane);

        if (this.coordinator != null) {
            lstCurrentApplications = new JList(this.currentApplications);
            lstCurrentApplications.addMouseListener(this);
            scrollPane.setViewportView(lstCurrentApplications);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.ccbPosition)) {
            this.populateFields((JobPosting) this.postings.getSelectedItem());

        } else if (e.getSource().equals(this.btnUpdatePosting)) {
            JobPosting posting = (JobPosting) this.ccbPosition.getSelectedItem();
            if (posting != null)
                this.updateDataFromFields(posting);
        } else if (e.getSource().equals(this.btnAddPosting)) {
            JobPosting posting = new JobPosting(this.coordinator.getCompany());
            this.coordinator.getCompany().addJobPosting(posting);
            this.refreshPostings(this.coordinator.getCompany().getOngoingPostings());
            this.ccbPosition.setSelectedIndex(this.ccbPosition.getItemCount() - 1);
        } else if (e.getSource().equals(this.btnApply)) {
            JobPosting posting = (JobPosting) this.ccbPosition.getSelectedItem();
            if (posting != null) {
                for (Application app : this.applicant.getOngoingApplications()) {
                    if (app.getJobPosting().equals(posting)) {
                        JOptionPane.showMessageDialog(this, "You have already applied for this position.");
                        return;
                    }
                }
                Application app = new Application(this.applicant, posting, null, null);
                posting.addApplication(app);
                new ApplicationFrame(app, true, false);
            }
        }

        Main.saveState();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getClickCount() == 2) {
            Application app = this.currentApplications.getElementAt(this.lstCurrentApplications.getSelectedIndex());
            if (app != null)
                new ApplicationFrame(app, false, true);
        }

        Main.saveState();
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
