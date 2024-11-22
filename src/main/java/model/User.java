package model;

import java.io.Serializable;
import java.util.prefs.Preferences;

public class User implements Serializable {
    private String username;
    private String password;
    private String privileges;

    //Lets just try logging in without privileges first
    //Anon user created for validating login against serialized users
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    //For signup
    public User(String username, String password, String privileges) {
        this.username = username;
        this.password = password;
        this.privileges = privileges;
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("USERNAME",username);
        userPreferences.put("PASSWORD",password);
        userPreferences.put("PRIVILEGES",privileges);
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getPrivileges() {
        return this.privileges;
    }

    public String toString() {
        return "Username: " + username + " Password: " + password;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User u = (User) obj;
        return u.getUsername().equalsIgnoreCase(this.username) && u.getPassword().equals(this.password);
    }
}
