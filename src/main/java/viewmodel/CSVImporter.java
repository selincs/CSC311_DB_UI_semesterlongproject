package viewmodel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Person;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVImporter {
    public static ObservableList<Person> importCSV(String filePath) {
        ObservableList<Person> persons = FXCollections.observableArrayList();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) { // Assuming 6 fields excluding ID
                    // Skip ID field, parse other fields because DB auto increments ID
                    String firstName = data[0].replaceAll("\"", "");
                    String lastName = data[1].replaceAll("\"", "");
                    String department = data[2].replaceAll("\"", "");
                    if (firstName.equals("First Name") && lastName.equals("Last Name") && department.equals("Department")) {
                        continue; //header row, skip insert after checking for stray ""
                    }
                    String majorString = data[3].replaceAll("\"", "");
                    Major major = null;
                    try {
                        major = Major.valueOf(data[3].toUpperCase());
                        System.out.println(Major.valueOf(data[3].toUpperCase()));
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid Major: " + data[3] + ". Defaulting to UNKNOWN.");
                        major = Major.UNDECIDED; // Defaulting data load error to default undecided major enum
                    }

                    String email = data[4].replaceAll("\"", "");;
                    String imageURL = data[5].replaceAll("\"", "");;

                    // Create a Person object and add it to the list
                    Person person = new Person(firstName, lastName, department, major, email, imageURL);
                    persons.add(person);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading CSV file: " + e.getMessage());
        }

        return persons; // Return the list of parsed Person objects
    }
}
