package com.dnd.digimax.feature.content.validator;

import java.io.File;

public class ContentValidator {

    //================================================
    // VALIDATE FILE
    //================================================

    public boolean isValid(
            File file
    ) {

        if (file == null)
            return false;

        if (!file.exists())
            return false;

        if (file.length() <= 0)
            return false;

        return true;
    }
}