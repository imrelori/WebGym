package com.webbuilders.webgym.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {

    private final Pattern emailRegex = Pattern.compile("\\b[\\w.%-]+@[-.\\w]+(mail)\\.[A-Za-z]{2,4}\\b");
    private final Pattern nameRegex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])[a-zA-Z\\D]{2,}$");
    private final Pattern passwordRegex = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$");

    public Validator() {

    }

    public Boolean isValidEmail(String email) {

        Matcher matcher = emailRegex.matcher(email);

        if (matcher.find()) return true;

        return false;
    }

    public Boolean isValidFirstName(String firstName) {

        Matcher matcher = nameRegex.matcher(firstName);

        if (matcher.find()) return true;

        return false;
    }

    public Boolean isValidLastName(String lastName) {

        Matcher matcher = nameRegex.matcher(lastName);

        if (matcher.find()) return true;

        return false;
    }

    public Boolean isValidPassword(String password) {

        Matcher matcher = passwordRegex.matcher(password);

        if (matcher.find()) return true;

        return false;
    }
}
