package com.hertzai.hevolve.helpers;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FormValidator {
    public static boolean requiredField(String fieldStr, int length) {
        if (fieldStr == null) {
            return false;
        } else {
            return fieldStr.length() >= length;
        }
    }

    public static boolean validateEmail(String emailAddress) {

        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(emailAddress).matches();
    }

    public static boolean validateStringByRule(String string, String rule) {
        Pattern p = Pattern.compile(rule);
        // Match the given string with the pattern
        Matcher m = p.matcher(string);
        // check whether match is found
        return m.matches();
    }

    public static boolean requiredFieldBatch(String batchname, int length) {
        if (batchname == null) {
            return false;
        } else {
            return batchname.length() >= length;
        }
    }

    public static boolean requiredFieldSchool(String schoolname, int length) {
        if (schoolname == null) {
            return false;
        } else {
            return schoolname.length() >= length;
        }
    }
    public static boolean requiredClientName(String client_name, int length) {
        if (client_name == null) {
            return false;
        } else {
            return client_name.length() >= length;
        }
    }
    public static boolean requiredFieldWhoPays(String whopays, int length) {
        if (whopays == null) {
            return false;
        } else {
            return whopays.length() >= length;
        }
    }

    public static boolean name(String name, int length) {
        if (name == null) {
            return false;
        } else {
            return name.length() >= length;
        }
    }
    public static boolean requiredFieldPhone(String phone, int length) {
        if (phone == null) {
            return false;
        } else {
            return phone.length() >= length;
        }
    }
}
