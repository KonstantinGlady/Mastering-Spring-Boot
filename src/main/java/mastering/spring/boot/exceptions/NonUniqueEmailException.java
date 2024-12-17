package mastering.spring.boot.exceptions;

public class NonUniqueEmailException extends RuntimeException {
    public NonUniqueEmailException(String msg) {
        super(msg);
    }
}
