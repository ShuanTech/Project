package com.shuan.project.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android on 12/1/2016.
 */

public class PhoneNumberValidator {

    private Pattern pattern;
    private Matcher matcher;

    private static final String PHONENUMBER_PATTERN = "^[0-9][0-9]*$";

    public PhoneNumberValidator() {
        pattern = Pattern.compile(PHONENUMBER_PATTERN);
    }

    /**
     * Validate hex with regular expression
     *
     * @param hex hex for validation
     * @return true valid hex, false invalid hex
     */
    public boolean validate(final String hex) {

        matcher = pattern.matcher(hex);
        return matcher.matches();

    }
}