//Zunaira Fauzan
//500975247
public class UnknownCourseException extends RuntimeException {

    public UnknownCourseException(String courseCode) {
        super(courseCode + " doesn't existed");
    }

}
