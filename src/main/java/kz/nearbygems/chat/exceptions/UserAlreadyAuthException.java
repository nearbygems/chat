package kz.nearbygems.chat.exceptions;

public class UserAlreadyAuthException extends ChatException {

    public UserAlreadyAuthException() {
        super("You've already logged in.\n");
    }

}
