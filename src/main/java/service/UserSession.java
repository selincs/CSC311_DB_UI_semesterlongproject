package service;

import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.prefs.Preferences;

public class UserSession {

    private static UserSession instance = null;

    //can probably move these fields to user class
    private String userName;
    private String password;
    private String privileges;
    //only class in this at end besides instance
    ArrayList<User> userList;

    //Takes user? or nothing?
    private UserSession(/*String userName, String password, String privileges*/) {
//        this.userName = userName;
//        this.password = password;
//        this.privileges = privileges;
//        Preferences userPreferences = Preferences.userRoot();
//        userPreferences.put("USERNAME",userName);
//        userPreferences.put("PASSWORD",password);
//        userPreferences.put("PRIVILEGES",privileges);
        userList = new ArrayList<>();
        readUserList();
        //I think I can put in privileges here after this once I get the right user from the stored list
        //Or should that happen on login?
    }


    //First call on Log In
    public static UserSession getInstance(/*String userName, String password, String privileges*/) {
        if(instance == null) {
            synchronized (UserSession.class) {
                //new user? do i really need to put a user in?
                if(instance == null) {
                    instance = new UserSession(/*userName, password, privileges*/);
                }
            }
        }
        return instance;
    }
/*  Not sure if this is necessary -- figure out after transfer functions for logging in
    public static UserSession getInstance(String userName, String password) {
        if (instance == null) {
            synchronized (UserSession.class) {
                instance = new UserSession(userName, password, "NONE");
            }
        }
        return instance;
    }
*/
    //saveUserList on new account creation (sign up GUI)
    public void saveUserList() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("userList.dat"));) {
            oos.writeObject(instance.userList);
        } catch (IOException ioe) {
            System.out.println("save user list ioe exception");
        } catch (Exception e) {
            System.out.println("save user list e exception");
        }
    }
    //read user list on program start to load valid users
    public void readUserList() {

        File file = new File("userList.dat");
        if (!file.exists()) {
            System.out.println("No existing user list found. Starting with an empty list.");
            userList = new ArrayList<>();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("userList.dat"));) {
            userList = (ArrayList<User>) ois.readObject();
        } catch (IOException ioe) {
            System.out.println("read user list ioe exception");
        } catch (Exception e) {
            System.out.println("read user list e exception");
        }
    }

    public ArrayList<User> getUserList() {
        return userList;
    }

    //Used in login to match user in UserSession with userIdx()
    public boolean findUser(User user) {
        return userList.contains(user);
    }

    public int userIdx(User user) {
        if (userList.contains(user)) {
            return userList.indexOf(user);
        } else {
            return -1;
        }
    }

//    public String getUserName() {
//        return this.userName;
//    }
//
//    public String getPassword() {
//        return this.password;
//    }
//
//    public String getPrivileges() {
//        return this.privileges;
//    }

    //probably need to change this to user for my implementation but shouldnt matter rn, never called
    public synchronized void cleanUserSession() {
        this.userName = "";// or null
        this.password = "";
        this.privileges = "";// or null
    }
    //probably need to change this to user for my implementation
    //or just get active user from session instance
    @Override
    public String toString() {
        return "UserSession{" +
                "userName='" + this.userName + '\'' +
                ", privileges=" + this.privileges +
                '}';
    }
}
