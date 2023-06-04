package kz.nearbygems.chat.exceptions;

public class ClientLimitExceededException extends ChatException {

    public ClientLimitExceededException() {
        super("""
              Chat have exceeded the connection limit.
              Please, try later.
              """);
    }

}
