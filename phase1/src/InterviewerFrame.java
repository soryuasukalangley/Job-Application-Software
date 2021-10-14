import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InterviewerFrame extends JFrame implements ActionListener {

    private DefaultComboBoxModel<Interview> interviews;
    private JComboBox cbcInterviews;
    private InterviewPanel pnlInterview;

    public InterviewerFrame(Interviewer i) {
        this.interviews = new DefaultComboBoxModel<>();
        for (Interview interview : i.getPendingInterviews()) {
            this.interviews.addElement(interview);
        }

        this.setSize(500, 300);
        this.setTitle("Interviewer View");
        this.setResizable(false);

        JPanel pnlSelectInterview = new JPanel();
        pnlSelectInterview.setLayout(new FlowLayout());
        pnlSelectInterview.add(new JLabel("Interviews:"));

        this.cbcInterviews = new JComboBox(this.interviews);
        this.cbcInterviews.addActionListener(this);
        pnlSelectInterview.add(cbcInterviews);

        this.pnlInterview = new InterviewPanel();
        this.add(pnlSelectInterview, BorderLayout.NORTH);
        this.add(this.pnlInterview, BorderLayout.CENTER);

        if (this.interviews.getSelectedItem() != null)
            this.pnlInterview.updateInterview((Interview) this.interviews.getSelectedItem());

        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.cbcInterviews)) {
            if (this.interviews.getSelectedItem() != null)
                this.pnlInterview.updateInterview((Interview) this.interviews.getSelectedItem());
        }

        Main.saveState();
    }
}
