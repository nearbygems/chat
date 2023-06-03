package kz.nearbygems.chat.exceptions;

public class NoChatException extends RuntimeException {

    public NoChatException() {
        super("You haven't joined any chat yet.\n");
    }

}
