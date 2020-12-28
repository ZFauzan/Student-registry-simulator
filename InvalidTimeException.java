//Zunaira Fauzan
//500975247
public class InvalidTimeException extends RuntimeException {

    public InvalidTimeException(int startTime, int duration) {
        super("StartTime " + startTime + " and duration " + duration + " should exist between 0800-1700");
    }

}