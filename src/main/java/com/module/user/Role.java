package com.module.user;

public enum Role {
    ROLE_USER("USER"),
    ROLE_NOT_VERIFIED("VERIFIED"),
    ROLE_ADMIN("ADMIN");

    String role;

    Role(String role) {
        this.role = role;
    }

    public String withRolePrefix(){
        return "ROLE_"+role;
    }
    public String withoutRolePrefix() {
        return role;
    }
}
