package dev.kelvin.api.network.exceptions;

public class TooManyArgumentsException extends RuntimeException {

    public TooManyArgumentsException(String methodName, int givenArgument, int requiredArgument) {
        super("Too many arguments for method \"" + methodName + "\": Given arguments: " + givenArgument + " Required arguments: " + requiredArgument);
    }

}
