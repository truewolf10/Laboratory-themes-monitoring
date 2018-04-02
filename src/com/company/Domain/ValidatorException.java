package com.company.Domain;

public class ValidatorException extends Exception {


    private static String joinMessages(Iterable<String> messages){
        String message = "Validation errors: \n";
        for(String s :messages)
            message += s + "\n";

        return message;
    }

    public ValidatorException(Iterable<String> messages){
        super(joinMessages(messages));
    }
}
