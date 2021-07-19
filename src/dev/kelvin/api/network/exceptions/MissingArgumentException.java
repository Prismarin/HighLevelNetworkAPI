package dev.kelvin.api.network.exceptions;

public class MissingArgumentException extends RuntimeException {

    public MissingArgumentException(String methodName, int givenArgument, int requiredArgument) {
        super("Missing arguments for method \"" + methodName + "\": Given arguments: " + givenArgument + " Required arguments: " + requiredArgument);
    }

}
