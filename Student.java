//Zunaira Fauzan
//500975247
import java.util.ArrayList;
import java.util.List;

// Make class Student implement the Comparable interface
// Two student objects should be compared by their name
public class Student {
    private String name;
    private String id;
    public ArrayList<CreditCourse> courses;


    public Student(String name, String id) {
        this.name = name;
        this.id = id;
        courses = new ArrayList<CreditCourse>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<CreditCourse> getCreditCourseList(){
        return courses;
    }

    // add a credit course to list of courses for this student
    public void addCourse(String courseName, String courseCode, String descr, String format, String sem, double grade) {
        // create a CreditCourse object
        // set course active
        // add to courses array list

        CreditCourse newCreditCourse = new CreditCourse(courseName, courseCode, descr, format, sem, grade);
        newCreditCourse.setActive();
        courses.add(newCreditCourse);
    }

    // Prints a student transcript
    // Prints all completed (i.e. non active) courses for this student (course code, course name,
    // semester, letter grade
    // see class CreditCourse for useful methods
    public void printTranscript() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CreditCourse course : courses) {
            if (!course.getActive()) {
                stringBuilder.append(course.getInfo() + " "
                + course.getSemester() + " " + Course.convertNumericGrade(course.getGrade()) + "\n");
            }
        }
        System.out.println(stringBuilder.toString());
    }

    // Prints all active courses this student is enrolled in
    // see variable active in class CreditCourse
    public void printActiveCourses() {
        StringBuilder stringBuilder = new StringBuilder();
        for (CreditCourse course : courses) {
            if (course.getActive()) {
               stringBuilder.append(course.getDescription() + " \n");
            }
        }

        System.out.println(stringBuilder.toString());

    }

    // Drop a course (given by courseCode)
    // Find the credit course in courses arraylist above and remove it
    // only remove it if it is an active course
    public void removeActiveCourse(String courseCode) {
        for (CreditCourse creditCourse : courses) {
            if (creditCourse.getCode().equalsIgnoreCase(courseCode) && creditCourse.getActive()) {
                courses.remove(creditCourse);
                break;
            }
        }
    }

    /**
     * Get student grade from list of credit course
     * @param courseCode input the courseCode
     * @return
     */
    public double getStudentGrade(String courseCode){
        double studentGrade = 0.0;
        for(CreditCourse creditCourse : courses){
            if(creditCourse.getCode().equals(courseCode)){
               studentGrade = creditCourse.getGrade();
               break;
            }
        }
        return studentGrade;
    }

    /**
     * find the creditCourse in the course list
     * @param courseCode input the coursecode
     * @return
     */

    public CreditCourse findCreditCourse(String courseCode) {
        for (CreditCourse course : this.courses) {
            if (course.getCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }

    public String toString() {
        return "Student ID: " + id + " Name: " + name;
    }

    // override equals method inherited from superclass Object
    // if student names are equal *and* student ids are equal (of "this" student
    // and "other" student) then return true
    // otherwise return false
    // Hint: you will need to cast other parameter to a local Student reference variable
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || other.getClass() != this.getClass()) {
            return false;
        }

        Student student = (Student) other;
//        System.out.println(this.name + " " +  this.id + " " +  student.getName() + " " +  student.getId());

        if (this.name.equals(student.getName()) && this.id.equals(student.getId())) {
            return true;
        }

        return false;
    }

}
