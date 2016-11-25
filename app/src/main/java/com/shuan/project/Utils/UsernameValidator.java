package com.shuan.project.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android on 11/24/2016.
 */

public class UsernameValidator {

    private Pattern pattern;
    private Matcher matcher;


    private static final String USERNAME_PATTERN = "^\\d*[a-zA-Z][a-zA-Z\\d]*$";

    public UsernameValidator() { pattern = Pattern.compile(USERNAME_PATTERN);}

    /**
     * Validate hex with regular expression
     *
     * @param hex
     *            hex for validation
     * @return true valid hex, false invalid hex
     */

    public boolean validate(final String hex){

        matcher = pattern.matcher(hex);
        return matcher.matches();
    }

}