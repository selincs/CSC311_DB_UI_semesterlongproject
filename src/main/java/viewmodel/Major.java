package viewmodel;

import java.util.HashMap;
import java.util.Map;

public enum Major {
    COMPUTER_SCIENCE("Computer Science", "CSC"),
    COMPUTER_PROGRAMMING("Computer Programming", "CPIS"),
    ENGLISH("English", "EGL"),
    MATHEMATICS("Mathematics", "MTH"),
    PHYSICS("Physics", "PHY"),
    CHEMISTRY("Chemistry", "CHE"),
    ENGINEERING("Engineering", "ENG"),
    BIOLOGY("Biology", "BIO"),
    BUSINESS("Business", "BUS"),
    ART("Art", "ART"),
    HISTORY("History", "HIS");

    private final String displayName;
    private final String majorCode;

    //only need to populate depart by name, let it auto happen
     //Map to populate Dept Col by display name
    //String returns whole major, which I can THEN use getCode on
    private static final Map<String, Major> BY_DISPLAY_NAME = new HashMap<>();

    //initialize map for column population
    static {
        for (Major major : values()) {
            BY_DISPLAY_NAME.put(major.displayName, major);
        }
    }


    Major(String displayName, String majorCode) {
        this.displayName = displayName;
        this.majorCode = majorCode;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getMajorCode() {
        return majorCode;
    }
    public static Major getByDisplayName(String displayName) {
        return BY_DISPLAY_NAME.get(displayName);
    }

    public static String getMajorCodeByDisplayName(String displayName) {
        Major major = getByDisplayName(displayName);
        return (major != null) ? major.getMajorCode() : null;
    }
}


//    Major(String displayName/*, String majorCode*/) {
//        this.displayName = displayName;
////        this.majorCode = majorCode;
//    }



//
//    //Methods to populate user fields based on single entry
//

//
//    public static Major getByMajorCode(String majorCode) {
//        return BY_MAJOR_CODE.get(majorCode.toLowerCase());
//    }
//
//    public static String getDisplayNameByCode(String majorCode) {
//        Major major = getByMajorCode(majorCode);
//        return (major != null) ? major.getDisplayName() : null;
//    }
//

//



