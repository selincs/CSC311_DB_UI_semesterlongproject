OFFICIAL CAT DISTRIBUTION SYSTEM FOR STUDENTS

Program allows an individual to sign up for an account, and then log in to that account to begin inserting records in to a table view and database via GUI and keyboard commands. Importing data and exporting data from CSV files is supported. 
User may upload profile pictures. Themed around letting students sign up for fate's mysterious cat distribution system in an effort to send emotional support cats to students in need :)
The program has trouble making edits to records without selecting a value from the ComboBox drop down list before editing a selection, sorry about that!


Personal Additions :

o	Theme : OFFICIAL CAT DISTRIBUTION SYSTEM FOR STUDENTS
School is a stressful time, so I’ve created a system that allows Farmingdale students to put their name forward for the unofficially deemed “Cat Distribution System” which supposedly exists in real life. 
This phenomenon is seen when an individual goes about their regular every day life doing nothing extraordinary in their daily routine, when a cat suddenly runs up to them and decides that this person now owns a cat. 
The mechanisms behind this system are mysterious and poorly understood, but I think students need the extra support, so this is where they can sign up for fate. 
Cat themes and a randomly assigned cat image buddy on the DB_Interface page (a new cat is given each login) to simulate this phenomenon we all desire.

Other additions :

o	Enums used for Department TextField autofill based on major selection in drop down menu for user convenience – Enum values since the values are static, so I could link the department code for each major and retrieve based on the Drop Down selection

o	Sign up validation with double password verification to ensure a user does not misenter their password and create an account they cannot access.

o	Attempted to integrate Serialization to UserSession class to host User accounts locally and provide Sign Up and Login functionality while maintaining a active and synchronized logged in state. (Incomplete)
