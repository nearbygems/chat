package kz.nearbygems.chat.exceptions;

public class ClientLimitExceededException extends RuntimeException {

    public ClientLimitExceededException() {
        super("Chat have exceeded the connection limit.\n");
    }

}
