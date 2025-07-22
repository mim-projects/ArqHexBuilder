package com.github.medina1402.model;

public class ColumnModel {
    private String name;
    private String type;
    private boolean isPrimaryKey;

    public ColumnModel() {
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

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    @Override
    public String toString() {
        return "ColumnModel{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", isPrimaryKey=" + isPrimaryKey +
                '}';
    }
}
