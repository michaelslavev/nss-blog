/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.model;

/**
 * Model enum of Role
 *
 * Two types: ROLE_ADMIN, ROLE_USER
 */
public enum Role {
    ADMIN("ROLE_ADMIN"), USER("ROLE_USER");

    private final String name;

    Role(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}

