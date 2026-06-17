package com.dnd.digimax.feature.updater.validator;

public class ChecksumValidator {

    public boolean validate(
            String expected,
            String actual) {

        if (expected == null
                || actual == null) {

            return false;
        }

        return expected.equalsIgnoreCase(
                actual
        );
    }
}