package dev.leeshuyun.Lifeguild.exceptions;

public class TelegramChatLoginFailedException extends Exception {

    public TelegramChatLoginFailedException() {
        super();
    }

    public TelegramChatLoginFailedException(String msg) {
        super(msg);
    }

}
