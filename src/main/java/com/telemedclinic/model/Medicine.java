package com.telemedclinic.model;

public class Medicine {

    // Attributes
    private final String medicineId;

    private String name;
    private String description;
    private String category;

    private boolean requiresPrescription;


    // Constructor
    public Medicine(
            String medicineId,
            String name,
            String description,
            String category,
            boolean requiresPrescription
    ) {

        if (medicineId == null || medicineId.isBlank()) {
            throw new IllegalArgumentException(
                    "Medicine ID cannot be empty."
            );
        }

        this.medicineId = medicineId;

        setName(name);
        setDescription(description);
        setCategory(category);

        this.requiresPrescription = requiresPrescription;
    }


    // Getter
    public String getMedicineId() {
        return medicineId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getCategory() {
        return category;
    }

    public boolean isRequiresPrescription() {
        return requiresPrescription;
    }


    // Setter
    public void setName(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Medicine name cannot be empty."
            );
        }

        this.name = name;
    }

    public void setDescription(String description) {

        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException(
                    "Medicine description cannot be empty."
            );
        }

        this.description = description;
    }

    public void setCategory(String category) {

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException(
                    "Medicine category cannot be empty."
            );
        }

        this.category = category;
    }

    public void setRequiresPrescription(boolean requiresPrescription) {
        this.requiresPrescription = requiresPrescription;
    }


    // Behavior methods
    public boolean isPrescriptionMedicine() {
        return requiresPrescription;
    }

    public boolean isSameCategory(String category) {

        return this.category.equalsIgnoreCase(category);
    }
}