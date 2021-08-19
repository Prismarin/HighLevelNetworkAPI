package dev.kelvin.api.network.exceptions;

public class IllegalMethodNameException extends RuntimeException {

    public IllegalMethodNameException(String methodName) {
        super("The methodName " + methodName + " is annotated twice with @Remote!");
    }

}
