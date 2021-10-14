package jobmanager.data;

import java.io.File;
import java.io.Serializable;

public class Material implements Serializable {
    public enum MaterialType {
        COVER_LETTER("Cover Letter"),
        CURRICULUM_VITAE("Curriculum Vitae"),
        REFERENCE("Reference");

        private final String name;

        MaterialType(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }



    private String name;
    private MaterialType type;
    private File file;

    public Material(String name, MaterialType type, File file) {
        this.name = name;
        this.type = type;
        this.file = file;
    }

    public Material(MaterialType type, File file) {
        this.type = type;
        this.file = file;
    }

    /**
     * Gets the type of this material
     * @return The type of this material
     */
    public MaterialType getType() {
        return type;
    }

    /**
     * Gets the file from the material
     * @return The file from the material
     */
    public File getFile() {
        return file;
    }

    /**
     * Sets the file from the material
     * @param file The file from the material
     */
    public void setFile(File file) {
        this.file = file;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String toString() {
        if (this.name != null)
            return String.format("%s (%s)", this.getType(), this.getName());
        return String.format("%s", this.getType());
    }

    public boolean equals(Object o) {
        if (!(o instanceof Material)) return false;
        Material m = (Material) o;
        return this.getFile().equals(m.getFile()) && this.getType() == m.getType();
    }
}
