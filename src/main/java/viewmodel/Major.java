package viewmodel;

import java.util.HashMap;
import java.util.Map;

public enum Major {
    COMPUTER_SCIENCE("Computer_Science", "CSC"),
    COMPUTER_PROGRAMMING("Computer_Programming", "CPIS"),
    ENGLISH("English", "EGL"),
    MATHEMATICS("Mathematics", "MTH"),
    PHYSICS("Physics", "PHY"),
    CHEMISTRY("Chemistry", "CHE"),
    ENGINEERING("Engineering", "ENG"),
    BIOLOGY("Biology", "BIO"),
    BUSINESS("Business", "BUS"),
    ART("Art", "ART"),
    HISTORY("History", "HIS"),
    UNDECIDED("Undecided", "UND");

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