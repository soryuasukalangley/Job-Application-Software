package jobmanager.data;

import java.io.Serializable;

public class Reference implements Serializable {
    private Application application;
    private Referrer referrer;
    private Material material;

    public Reference(Application app, Referrer ref) {
        this.application = app;
        this.referrer = ref;
    }

    /**
     * Gets the application that this Reference is submitted to
     * @return The application that this Reference is submitted to
     */
    public Application getApplication() {
        return application;
    }

    /**
     * Gets the Referrer that submitted this reference
     * @return The Referrer that submitted this reference
     */
    public Referrer getReferrer() {
        return referrer;
    }

    /**
     * Gets the material of this reference
     * @return The material of this reference
     */
    public Material getMaterial() {
        return this.material;
    }

    /**
     * Adds the material of this reference
     * @param mat The material of this reference
     */
    public void addMaterial(Material mat) {
        this.material = mat;
    }

    /**
     * Check if this reference has any materials
     * @return
     */
    public boolean isMaterialPresent() {
        return material != null;
    }

    @Override
    public String toString() {
        return this.application.getApplicant().getLegalName() + " - " + this.application.getJobPosting().getName();
    }
}
