package mastering.spring.boot.chapter1.exceptions;

public class NonUniqueEmailException extends RuntimeException {
    public NonUniqueEmailException(String msg) {
        super(msg);
    }
}
