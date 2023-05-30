package dev.leeshuyun.Lifeguild.exceptions;

public class RegisteringUserFailedException extends RuntimeException {

    public RegisteringUserFailedException() {
        super();
    }

    public RegisteringUserFailedException(String msg) {
        super(msg);
    }

}
