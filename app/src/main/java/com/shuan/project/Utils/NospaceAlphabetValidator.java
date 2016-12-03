package com.shuan.Project.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android on 12/1/2016.
 */

public class NospaceAlphabetValidator {

    private Pattern pattern;
    private Matcher matcher;

    public static final String NOSPACEALPHABET_PATTERN = "^[a-zA-Z][a-zA-Z]*$";

    public NospaceAlphabetValidator(){ pattern = pattern.compile(NOSPACEALPHABET_PATTERN);}

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
