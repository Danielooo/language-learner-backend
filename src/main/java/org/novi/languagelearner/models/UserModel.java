//package org.novi.languagelearner.models;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class UserModel {
//
//    private Long id = -1L;
//    private String userName;
//    private String password;
//    private List<RoleModel> roles = new ArrayList<>();
//    private boolean isExpired;
//    private boolean isLocked;
//    private boolean areCredentialsExpired;
//    private boolean isEnabled;
//
//    public UserModel() {
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public UserModel(Long id) {
//        this.id = id;
//    }
//
//    public boolean isExpired() {
//        return isExpired;
//    }
//
//    public void setExpired(boolean expired) {
//        isExpired = expired;
//    }
//
//    public boolean isLocked() {
//        return isLocked;
//    }
//
//    public void setLocked(boolean locked) {
//        isLocked = locked;
//    }
//
//    public boolean areCredentialsExpired() {
//        return areCredentialsExpired;
//    }
//
//    public void areCredentialsExpired(boolean areCredentialsExpired) {
//        this.areCredentialsExpired = areCredentialsExpired;
//    }
//
//    public boolean isEnabled() {
//        return isEnabled;
//    }
//
//    public void setEnabled(boolean enabled) {
//        isEnabled = enabled;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public List<RoleModel> getRoles() {
//        return roles;
//    }
//
//    public void setRoles(List<RoleModel> roles) {
//        this.roles = roles;
//    }
//
//    public List<String> getRoleNames() {
//        return roles.stream()
//                .map(RoleModel::getRoleName) // Converts each Role object to its name
//                .collect(Collectors.toList());
//    }
//
//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//}
//
