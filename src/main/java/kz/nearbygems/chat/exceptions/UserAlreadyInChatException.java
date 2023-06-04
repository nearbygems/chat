package kz.nearbygems.chat.exceptions;

public class UserAlreadyInChatException extends ChatException {

    public UserAlreadyInChatException() {
        super("You have to leave from previous chat.\n");
    }

}
