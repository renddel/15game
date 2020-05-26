package game.rest;

public class InvalidMoveException extends RuntimeException{

    public InvalidMoveException(String string) {
        super(string);
    }

    public InvalidMoveException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public InvalidMoveException(Throwable thrwbl) {
        super(thrwbl);
    }
}
