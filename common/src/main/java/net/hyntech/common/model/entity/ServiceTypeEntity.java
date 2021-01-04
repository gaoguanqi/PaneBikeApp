package net.hyntech.common.model.entity;

public class ServiceTypeEntity {
    private String name;
    private String type;
    private boolean isSelected = false;

    public ServiceTypeEntity(String name, String type, boolean isSelected) {
        this.name = name;
        this.type = type;
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
