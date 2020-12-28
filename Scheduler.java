//Zunaira Fauzan
//500975247
import java.util.Map.Entry;
import java.util.TreeMap;

public class Scheduler {
    private TreeMap<String, ActiveCourse> schedule;
    private String[] validDayValues = {"Mon", "Tue", "Wed", "Thur", "Fri"};
    private int[] validDurationValues = {1, 2, 3};
    private final int MIN_STARTTIME = 800;
    private final int MAX_DURATION = 1700;
    private final int MAX_HOURS = 11; // 8am-5pm

    public Scheduler(TreeMap<String, ActiveCourse> courses) {

        if (!courses.isEmpty()) {
            this.schedule = courses;
        }
    }

    public void setDayAndTime(String courseCode, String day, int startTime, int duration) {

        if (getActiveCourse(courseCode) == null) {
            throw new UnknownCourseException(courseCode);
        }

        if (!checkValidDay(day)) {
            throw new InvalidDayException(day);
        }

        if (!checkValidStartTime(startTime, duration)) {
            throw new InvalidTimeException(startTime, duration);
        }

        if (!checkValidDuration(duration)) {
            throw new InvalidDurationException(duration);
        }

        String overlapCourse;

        if ((overlapCourse = getOverlapCourseCode(courseCode, day, startTime, duration)) != null) {
            throw new LectureTimeCollisionException(courseCode, overlapCourse, day, startTime, duration);
        }

        for (Entry<String, ActiveCourse> activeCourseEntry : schedule.entrySet()) {
            if (activeCourseEntry.getKey().equalsIgnoreCase(courseCode)) {

                activeCourseEntry.getValue().setLectureDay(day);
                activeCourseEntry.getValue().setLectureStart(startTime);
                activeCourseEntry.getValue().setLectureDuration(duration);

                break;
            }
        }

    }

    public void clearSchedule(String courseCode) {
        for (Entry<String, ActiveCourse> activeCourseEntry : schedule.entrySet()) {
            if (activeCourseEntry.getKey().equalsIgnoreCase(courseCode)) {
                activeCourseEntry.getValue().setLectureDay("");
                activeCourseEntry.getValue().setLectureStart(0);
                activeCourseEntry.getValue().setLectureDuration(0);

                break;
            }
        }
    }

    public void printSchedule() {
        String[][] courseScheduleTable = new String[MAX_HOURS][validDayValues.length];

        //initializing all values by empty strings
        for (int i = 0; i < courseScheduleTable.length; i++) {
            for (int j = 0; j < courseScheduleTable[i].length; j++) {
                courseScheduleTable[i][j] = "";
            }
        }

        int timeHour = MIN_STARTTIME;
        for(int i=1;i<MAX_HOURS;i++){
            String timeHourString = String.valueOf(timeHour);

            if(timeHourString.length() == 3){
                courseScheduleTable[i][0] = "0" + timeHourString;
            }else{
                courseScheduleTable[i][0] = timeHourString;
            }

            timeHour += 100;
        }


        for (int k = 1; k<validDayValues.length;k++) {
            courseScheduleTable[0][k] = validDayValues[k-1];
        }

        for (Entry<String, ActiveCourse> activeCourseEntry : schedule.entrySet()) {

            if (activeCourseEntry.getValue().getLectureDay() != null
                    && activeCourseEntry.getValue().getLectureStart() > 0 &&
                    activeCourseEntry.getValue().getLectureDuration() > 0) {

                int foundDayIndex;
                if ((foundDayIndex = getDay(activeCourseEntry.getValue().getLectureDay())) != -1) {

                    int timeIndex = 1;
                    for (int courseTime = MIN_STARTTIME; courseTime <= MAX_DURATION; courseTime = courseTime + 100) {
                        int startTime = activeCourseEntry.getValue().getLectureStart();
                        int endTime = startTime + (activeCourseEntry.getValue().getLectureDuration() * 100);

                        if (startTime <= courseTime && endTime > courseTime) {
                            courseScheduleTable[timeIndex][foundDayIndex] = activeCourseEntry.getKey();
                        }
                        timeIndex++;
                    }
                }
            }
        }

        for (int i = 0; i < courseScheduleTable.length; i++) {
            for (int j = 0; j < courseScheduleTable[i].length; j++) {
                System.out.printf("%-12s", courseScheduleTable[i][j]);
            }
            System.out.println();
        }
    }


    /**
     * Get Index of Day in the day array
     * @param inputDay
     * @return
     */

    private int getDay(String inputDay) {
        for (int i = 0; i < validDayValues.length; i++) {
            if (inputDay.equalsIgnoreCase(validDayValues[i])) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Check if the input Day is valid or not
     * @param inputDay
     * @return
     */

    private boolean checkValidDay(String inputDay) {
        for (String validDay : validDayValues) {
            if (inputDay.equalsIgnoreCase(validDay)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Check if the input Start time and duration is valid
     * @param inputStartTime
     * @param inputDuration
     * @return
     */

    private boolean checkValidStartTime(int inputStartTime, int inputDuration) {
        if ((inputStartTime < MIN_STARTTIME) || (inputStartTime + (inputDuration * 100) > MAX_DURATION)) {
            return false;
        }

        return true;
    }


    /**
     * check if the duration is valid
     * @param inputDuration
     * @return
     */

    private boolean checkValidDuration(int inputDuration) {
        for (int validDuration : validDurationValues) {
            if (inputDuration == validDuration) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get value of overlapping course
     * @param code
     * @param inputDay
     * @param inputStartTime
     * @param inputDuration
     * @return
     */

    private String getOverlapCourseCode(String code, String inputDay, int inputStartTime, int inputDuration) {
        int courseEndTime = inputStartTime + (inputDuration * 100);

        for (Entry<String, ActiveCourse> activeCourseEntry : schedule.entrySet()) {
            if (!activeCourseEntry.getKey().equalsIgnoreCase(code)) {
                String lectureDay = activeCourseEntry.getValue().getLectureDay();
                if (lectureDay != null && lectureDay.equalsIgnoreCase(inputDay)) {

                    if (activeCourseEntry.getValue().getLectureStart() == inputStartTime ||
                            activeCourseEntry.getValue().getLectureDuration() == courseEndTime) {
                        return activeCourseEntry.getKey();
                    } else {
                        continue;
                    }
                }
            }
        }

        return null;

    }

    /**
     * Get Active Course based on course code
     * @param courseCode
     * @return
     */

    public ActiveCourse getActiveCourse(String courseCode) {
        for (Entry<String, ActiveCourse> activeCourseEntry : schedule.entrySet()) {
            if (activeCourseEntry.getKey().equalsIgnoreCase(courseCode)) {
                return activeCourseEntry.getValue();
            }
        }
        return null;
    }
}
