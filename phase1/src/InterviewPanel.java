import javax.swing.*;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class InterviewPanel extends JPanel implements ActionListener {
    private Interview interview;
    private JTextField txtInterviewer, txtApplicant, txtDate, txtTime, txtLocation;
    private JTextArea txtComments;
    private JButton btnCompleteInterview;
    private JButton btnSave;

    public InterviewPanel() {
        this.initializeComponent();
    }

    public void updateInterview(Interview i) {
        this.interview = i;
        this.populateFields();
    }

    private void populateFields() {
        this.txtInterviewer.setText(this.interview.getInterviewer().getLegalName());
        this.txtApplicant.setText(this.interview.getInterviewee().getLegalName());
        this.txtDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(this.interview.getInterviewDate()));
        this.txtTime.setText(new SimpleDateFormat("HH:mm").format(this.interview.getInterviewDate()));
        this.txtLocation.setText("Not yet implemented");

        this.txtComments.setText(this.interview.getNote());
    }

    private void initializeComponent() {
        GridBagLayout gridBagLayout = new GridBagLayout();
        gridBagLayout.columnWidths = new int[]{0, 0, 302, 0};
        gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0};
        gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
        gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        setLayout(gridBagLayout);

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 0, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel lblInterviewer = new JLabel("Interviewer: ");
        gbc.gridy = 1;
        add(lblInterviewer, gbc);

        JLabel lblApplicant = new JLabel("Applicant: ");
        gbc.gridy = 2;
        add(lblApplicant, gbc);

        JLabel lblDate = new JLabel("Date: ");
        gbc.gridy = 3;
        add(lblDate, gbc);

        JLabel lblTime = new JLabel("Time: ");
        gbc.gridy = 4;
        add(lblTime, gbc);

        JLabel lblLocation = new JLabel("Location: ");
        gbc.gridy = 5;
        add(lblLocation, gbc);

        JLabel lblComments = new JLabel("Comments: ");
        gbc.gridy = 6;
        add(lblComments, gbc);

        btnSave = new JButton("Save");
        btnSave.addActionListener(this);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 5);
        gbc.gridy = 7;
        add(btnSave, gbc);

        gbc.insets = new Insets(0, 0, 5, 0);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;

        txtInterviewer = new JTextField();
        txtInterviewer.setEditable(false);
        gbc.gridy = 1;
        add(txtInterviewer, gbc);
        txtInterviewer.setColumns(10);

        txtApplicant = new JTextField();
        txtApplicant.setEditable(false);
        gbc.gridy = 2;
        add(txtApplicant, gbc);
        txtApplicant.setColumns(10);

        txtDate = new JTextField();
        txtDate.setEditable(false);
        gbc.gridy = 3;
        add(txtDate, gbc);
        txtDate.setColumns(10);

        txtTime = new JTextField();
        txtTime.setEditable(false);
        gbc.gridy = 4;
        add(txtTime, gbc);
        txtTime.setColumns(10);

        txtLocation = new JTextField();
        txtLocation.setEditable(false);
        gbc.gridy = 5;
        add(txtLocation, gbc);
        txtLocation.setColumns(10);

        txtComments = new JTextArea();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.gridy = 6;
        add(txtComments, gbc);

        btnCompleteInterview = new JButton("Complete interview");
        btnCompleteInterview.addActionListener(this);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridy = 7;
        add(btnCompleteInterview, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (this.interview == null) {
            JOptionPane.showMessageDialog(this, "You must select an interview first");
            return;
        }

        if (e.getSource().equals(this.btnCompleteInterview)) {
            try {
                this.interview.getApplication().completeInterview();
                JOptionPane.showMessageDialog(this, "Interview Completed");
            } catch (Application.InvalidStateException ex) {
                JOptionPane.showMessageDialog(this, "Invalid State");
            }
        } else if (e.getSource().equals(this.btnSave)) {
            this.interview.setNote(this.txtComments.getText());
            JOptionPane.showMessageDialog(this, "Interview comments saved!");
        }

        this.populateFields();
        Main.saveState();
    }
}
