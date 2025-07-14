package uz.app.pc_market.exceptions;

public class UserBadRequestException extends RuntimeException {
    public UserBadRequestException(String message) {
        super(message);
    }
}
