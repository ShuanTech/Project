package com.shuan.project.Utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Android on 11/25/2016.
 */

public class AlphabetValidator {

    private Pattern pattern;
    private Matcher matcher;

    public static final String ALPHABET_PATTERN = "^[a-zA-Z]+[\\p{L} .'-]*$";

    public AlphabetValidator() {
        pattern = pattern.compile(ALPHABET_PATTERN);
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
