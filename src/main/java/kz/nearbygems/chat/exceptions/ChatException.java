package kz.nearbygems.chat.exceptions;

public abstract class ChatException extends RuntimeException {

    public ChatException(String message) {
        super(message);
    }

}
