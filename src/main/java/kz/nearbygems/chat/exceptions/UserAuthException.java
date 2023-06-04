package kz.nearbygems.chat.exceptions;

public class UserAuthException extends ChatException {

    public UserAuthException() {
        super("You haven't logged in yet.\n");
    }

}
