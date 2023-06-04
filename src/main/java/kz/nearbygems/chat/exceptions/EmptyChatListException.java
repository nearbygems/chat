package kz.nearbygems.chat.exceptions;

public class EmptyChatListException extends ChatException {

    public EmptyChatListException() {
        super("You haven't joined any chat yet.\n");
    }

}
