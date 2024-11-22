package service;

import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class UserSession {

    private static UserSession instance;

    //can probably move these fields to user class
    private String userName;
    private String password;
    private String privileges;
    ArrayList<User> userList = null;

    private UserSession(String userName, String password, String privileges) {
        this.userName = userName;
        this.password = password;
        this.privileges = privileges;
        Preferences userPreferences = Preferences.userRoot();
        userPreferences.put("USERNAME",userName);
        userPreferences.put("PASSWORD",password);
        userPreferences.put("PRIVILEGES",privileges);
    }


    //First call on Log In
    public static UserSession getInstance(String userName, String password, String privileges) {
        if(instance == null) {
            synchronized (UserSession.class) {
                instance = new UserSession(userName, password, privileges);
            }
        }
        return instance;
    }

    public static UserSession getInstance(String userName, String password) {
        if (instance == null) {
            synchronized (UserSession.class) {
                instance = new UserSession(userName, password, "NONE");
            }
        }
        return instance;
    }
    public void saveUserList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("userList.dat"));) {
            oos.writeObject(instance.userList);
        } catch (IOException ioe) {
            System.out.println("save user list ioe exception");
        } catch (Exception e) {
            System.out.println("save user list e exception");
        }
    }

    public void readUserList() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("userList.dat"));) {
            userList = (ArrayList<User>) ois.readObject();
        } catch (IOException ioe) {
            System.out.println("read user list ioe exception");
        } catch (Exception e) {
            System.out.println("read user list e exception");
        }
    }


    public String getUserName() {
        return this.userName;
    }

    public String getPassword() {
        return this.password;
    }

    public String getPrivileges() {
        return this.privileges;
    }

    //probably need to change this to user for my implementation
    public synchronized void cleanUserSession() {
        this.userName = "";// or null
        this.password = "";
        this.privileges = "";// or null
    }
    //probably need to change this to user for my implementation
    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + this.userName + '\'' +
                ", privileges=" + this.privileges +
                '}';
    }
}
