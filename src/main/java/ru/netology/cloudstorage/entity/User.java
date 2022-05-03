package ru.netology.cloudstorage.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author VladSemikin
 */

@Entity
@Table(name = "users")
public class User {

    @Id
    @Column(name = "login")
    private String login;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password")
    private String password;

    @Column(name = "authority")
    private String authority;

    @Column(name = "enabled")
    private Boolean enabled;


    public User(String login, String password, String authority, Boolean enabled) {
        this.login = login;
        this.password = password;
        this.authority = authority;
        this.enabled = enabled;
    }

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public static class Builder {
        private final User newUser;

        public Builder() {
            newUser = new User();
        }

        public Builder withLogin(String login) {
            newUser.login = login;
            return this;
        }

        public Builder withPassword(String password) {
            newUser.password = password;
            return this;
        }

        public Builder withAuthority(String authority) {
            newUser.authority = authority;
            return this;
        }

        public Builder withEnabled(boolean enabled) {
            newUser.enabled = enabled;
            return this;
        }

        public User build() {
            return newUser;
        }
    }
}