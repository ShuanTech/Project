package com.shuan.Project.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android on 11/23/2016.
 */

public class TextfieldValidator {
    private Pattern pattern;
    private Matcher matcher;


    private static final String TEXTFIELD_PATTERN = "^[a-zA-z]+.*(?=.*[a-zA-Z]).*$";

    public TextfieldValidator() { pattern = Pattern.compile(TEXTFIELD_PATTERN);}

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
