//Zunaira Fauzan
//500975247
public class InvalidDayException extends RuntimeException {

    public InvalidDayException(String day) {
        super("Entered " + day
                + " is not in the list of valid days [Mon,Tue,Wed,Thur,Fri]");

    }

}
