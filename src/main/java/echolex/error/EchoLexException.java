package echolex.error;

/**
 * Custom exception class representing errors related to the EchoLex application.
 */
public class EchoLexException extends Exception {

    /**
     * Constructs a new EchoLexException with the default error message "EchoLex exception occurred".
     */
    public EchoLexException() {
        super("EchoLex exception occurred");
    }

    /**
     * Constructs a new EchoLexException with the specified error message.
     *
     * @param message the detail message explaining the cause of the exception.
     */
    public EchoLexException(String message) {
        super(message);
    }

    /**
     * Constructs a new EchoLexException with the specified error message and cause.
     *
     * @param message the detail message explaining the cause of the exception.
     * @param cause the cause of the exception, which can be retrieved later using Throwable#getCause().
     */
    public EchoLexException(String message, Throwable cause) {
        super(message, cause);
    }
}
