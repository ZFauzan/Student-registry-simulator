//Zunaira Fauzan
//500975247
public class InvalidDurationException extends RuntimeException {

    public InvalidDurationException(int duration) {
        super("Entered duration " + duration + " is not in valid hours list [1,2,3]");
    }

}
