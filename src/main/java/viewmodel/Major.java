package viewmodel;

public enum Major {
    COMPUTER_SCIENCE("Computer Science"),
    COMPUTER_PROGRAMMING("Computer Programming"),
    MATHEMATICS("Mathematics"),
    PHYSICS("Physics"),
    CHEMISTRY("Chemistry"),
    ENGINEERING("Engineering"),
    BIOLOGY("Biology"),
    BUSINESS("Business"),
    ART("Art"),
    HISTORY("History");

    private final String displayName;

    Major(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

    //   private final String majorCode;

    // Maps for reverse lookups
//    private static final Map<String, Major> BY_DISPLAY_NAME = new HashMap<>();
//    private static final Map<String, Major> BY_MAJOR_CODE = new HashMap<>();

//    static {
//        for (Major major : values()) {
//            BY_DISPLAY_NAME.put(major.displayName.toLowerCase(), major);
//            BY_MAJOR_CODE.put(major.majorCode.toLowerCase(), major);
//        }
//    }

//    Major(String displayName/*, String majorCode*/) {
//        this.displayName = displayName;
////        this.majorCode = majorCode;
//    }


//    public String getMajorCode() {
//        return majorCode;
//    }
//
//    //Methods to populate user fields based on single entry
//
//    public static Major getByDisplayName(String displayName) {
//        return BY_DISPLAY_NAME.get(displayName.toLowerCase());
//    }
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
//    public static String getMajorCodeByDispName(String displayName) {
//        Major major = getByDisplayName(displayName);
//        return (major != null) ? major.getMajorCode() : null;
//    }
//



