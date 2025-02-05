package echolex.error;

public class EchoLexException extends Exception {
    public EchoLexException() {
        super("EchoLex exception occurred");
    }

    public EchoLexException(String message) {
        super(message);
    }

    public EchoLexException(String message, Throwable cause) {
        super(message, cause);
    }
}
