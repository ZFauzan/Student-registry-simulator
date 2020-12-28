//Zunaira Fauzan
//500975247
public class LectureTimeCollisionException extends RuntimeException {

    public LectureTimeCollisionException(String courseCode, String overlapCourse, String day,
                                         int startTime, int duration) {

        super(courseCode + "and " +  overlapCourse + " overlap on day:" + day
                + " at startTime: " + startTime + " and duration " + duration);
    }

}
