
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReferrerWindow extends JFrame implements ActionListener {

    private Referrer ref;
    private JButton btnSubmitReference;

    public ReferrerWindow(Referrer ref) {
        this.ref = ref;

        this.setSize(200, 100);
        this.setResizable(false);
        this.setTitle("Referrer View");
        this.setLayout(new FlowLayout());

        this.btnSubmitReference = new JButton("Submit New Reference");
        this.btnSubmitReference.addActionListener(this);

        this.add(btnSubmitReference);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(this.btnSubmitReference)) {
            Reference reference = new Reference(this.ref);
            new AddReferenceWindow(reference);
        }

        Main.saveState();
    }
}
