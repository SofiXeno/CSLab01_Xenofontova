import java.sql.SQLException;

class WrongH13Exception extends IllegalArgumentException {
    public WrongH13Exception(String message) {
        super(message);
    }
}

class CRCHeaderException extends IllegalArgumentException {

    public CRCHeaderException(String message) {
        super(message);
    }
}

class CRCMessageException extends IllegalArgumentException {
    public CRCMessageException(String message) {
        super(message);
    }
}

class FoundOfSQLClassException extends SQLException {

    public FoundOfSQLClassException(String errorMessage) {
        super(errorMessage);
    }
}




