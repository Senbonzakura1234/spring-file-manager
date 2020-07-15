package com.app.manager.model;

public class SelectOption {
    private String value;
    private String name;
    private boolean selected;

    public SelectOption() {
    }

    public SelectOption(String value, String name, boolean selected) {
        this.value = value;
        this.name = name.toLowerCase();
        this.selected = selected;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
