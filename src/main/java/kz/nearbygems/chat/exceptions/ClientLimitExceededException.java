package kz.nearbygems.chat.exceptions;

public class ClientLimitExceededException extends RuntimeException {
    public ClientLimitExceededException() {
        super("You have exceeded the connection limit");
    }
}
