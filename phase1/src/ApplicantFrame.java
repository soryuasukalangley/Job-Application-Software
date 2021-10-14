import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ApplicantFrame extends JFrame implements ChangeListener {
    private Applicant applicant;

    private JobPostingPanel pnlJobPosting;
    private ApplicantPanel pnlApplicant;

    public ApplicantFrame(Applicant applicant) {
        this.applicant = applicant;
        this.pnlJobPosting = new JobPostingPanel(Main.getAllPosting(), this.applicant);
        this.pnlApplicant = new ApplicantPanel(this.applicant);

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addChangeListener(this);
        tabbedPane.addTab("Available Positions", this.pnlJobPosting);
        tabbedPane.addTab("My Information", this.pnlApplicant);

        this.add(tabbedPane, BorderLayout.CENTER);

        this.setSize(600, 500);
        this.setResizable(false);
        this.setVisible(true);
    }

    @Override
    public void stateChanged(ChangeEvent changeEvent) {
        this.pnlApplicant.populateFields();
        Main.saveState();
    }
}
