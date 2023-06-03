package kz.nearbygems.chat.exceptions;

public class DeviceSavingException extends RuntimeException {
    public DeviceSavingException() {
        super("Couldn't save your device. Please try later.");
    }
}
