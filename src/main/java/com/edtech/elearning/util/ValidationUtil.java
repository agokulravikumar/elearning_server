package com.edtech.elearning.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static boolean isValidMobileNo(String mobileNo) {
        if (mobileNo == null) {
            return false;
        }

        Pattern p = Pattern.compile("(0|91)?[6-9][0-9]{9}");
        Matcher m = p.matcher(mobileNo);
        return (m.find() && m.group().equals(mobileNo));
    }

    public static boolean isValidEmailId(String emailId) {
        if (emailId == null) {
            return false;
        }

        Pattern p = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$");
        Matcher m = p.matcher(emailId);
        return (m.find() && m.group().equals(emailId));
    }
}
