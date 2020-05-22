
class WrongH13Exception extends IllegalArgumentException {
        public WrongH13Exception(String message) {
            super(message);
        }
    }

    class CRCHeaderException extends IllegalArgumentException{//тут я зібрала всі свої власно названі ексепшени для роботи з джюніт тестами
        public CRCHeaderException(String message) {
            super(message);
        }
    }

    class CRCMessageException extends IllegalArgumentException {
        public CRCMessageException(String message) {
            super(message);
        }
    }




