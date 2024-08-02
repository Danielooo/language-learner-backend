package org.novi.languagelearner.models;

public class RoleModel {
    private Long id;
    private String roleName;
    private boolean active;
    private String description;

    public RoleModel(Long id) {
        this.id = id;
    }

    public RoleModel(String roleName) {
        this.id = -1L;
        this.roleName = roleName;
    }

    public Long getId() {
        return id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
