package kz.nearbygems.chat.exceptions;

public class IncorrectJoinMessage extends RuntimeException {

    public IncorrectJoinMessage() {
        super("Please, write chat name without spaces.\n");
    }

}
