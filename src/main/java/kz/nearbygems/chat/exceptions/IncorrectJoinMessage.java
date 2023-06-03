package kz.nearbygems.chat.exceptions;

public class IncorrectJoinMessage extends RuntimeException {
    public IncorrectJoinMessage() {
        super("Please, write channel name without spaces.");
    }
}
