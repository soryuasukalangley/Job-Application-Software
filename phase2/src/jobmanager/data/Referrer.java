package jobmanager.data;

import java.io.Serializable;
import java.util.*;

public class Referrer extends User implements Serializable {
    private List<Reference> references;

    public Referrer(String username, String phoneNumber, String legalName, String email) {
        super(username, phoneNumber, legalName, email, "");
        this.references = new ArrayList<>();
    }

    /**
     * @return A list of completed references where the current referrer is invited to complete
     */
    public ArrayList<Reference> getCompletedReferences() {
        ArrayList<Reference> referencesCompleted = new ArrayList<>();
        for (Reference ref : this.references) {
            if (ref.getReferrer().equals(this) && ref.getMaterial() != null) {
                referencesCompleted.add(ref);
                break;
            }
        }
        return referencesCompleted;
    }

    /**
     * @return A list of not yet completed references where the current referrer is invited to complete
     */
    public ArrayList<Reference> getIncompleteReferences() {
        ArrayList<Reference> incompleteReferences = new ArrayList<>();
        for (Reference ref : this.references) {
            if (ref.getReferrer().equals(this) && ref.getMaterial() == null) {
                incompleteReferences.add(ref);
                break;
            }
        }
        return incompleteReferences;
    }

    /**
     * Adds a reference to be complete
     * @param ref
     */
    public void addReferenceToComplete(Reference ref) {
        this.references.add(ref);
    }

    @Override
    public String getFileString() {
        return "referrer";
    }

}

