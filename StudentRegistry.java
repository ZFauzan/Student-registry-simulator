//Zunaira Fauzan
//500975247
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class StudentRegistry {
    private TreeMap<String,Student> studentTreeMap = new TreeMap<>();
    private TreeMap<String,ActiveCourse> courseTreeMap = new TreeMap<>();

    public StudentRegistry() {
        // Add some students
        // in A2 we will read from a file

        //Reading student records from the file
        try(BufferedReader studentRecordReader = new BufferedReader(new FileReader(new File("studentRecord.txt")))){
            String readStudentRecord;
            while((readStudentRecord = studentRecordReader.readLine()) != null){
                Student student = null;
                String [] studentRecord = readStudentRecord.split(" ");
                //check if studentName and Id are present
                if(studentRecord.length ==2){
                    String name = studentRecord[0];
                    String id = studentRecord[1];
                    student = new Student(name,id);
                    studentTreeMap.put(id,student);
                }else{
                    System.out.println("Bad file format studentRecord.txt");
                    System.exit(0);
                }
            }
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //Reading course records from the file
        try(BufferedReader courseListReader = new BufferedReader(new FileReader(new File("courseList.txt")))){
            String readCourseList = "";
            ArrayList<String> courseDetails = new ArrayList<>();

            //there are 6 lines needs to be run for each course from the file
            while((readCourseList = courseListReader.readLine()) != null){
                courseDetails.add(readCourseList);
                //checking if the list contains 6 strings about each course
                if(courseDetails.size() == 6){
                    String name = courseDetails.get(0);
                    String code = courseDetails.get(1);
                    String description = courseDetails.get(2);
                    String format = courseDetails.get(3);
                    String semester = courseDetails.get(4);
                    String registeredIds = courseDetails.get(5);

                    String[] studentId = registeredIds.split(" ");
                    if(studentId.length !=0){
                        ArrayList<Student> studentsList = new ArrayList<>();
                        for(int i=0;i<studentId.length;i++){
                            Student student = getStudent(studentId[i]);
                            if(student != null){
                                //if the student found - adding student to the list
                                studentsList.add(student);
                                //adding course to the student course
                                student.addCourse(name,code,description,format,semester,0.0);
                            }
                        }
                        ActiveCourse activeCourse = new ActiveCourse(name,code,description,format,semester,studentsList);
                        courseTreeMap.put(code,activeCourse);
                    }
                    courseDetails = new ArrayList<>();
                }
            }
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public TreeMap<String, Student> getStudentTreeMap() {
        return studentTreeMap;
    }

    public TreeMap<String, ActiveCourse> getCourseTreeMap() {
        return courseTreeMap;
    }

    // Add new student to the registry (students arraylist above)
    public boolean addNewStudent(String name, String id) {
        // Create a new student object
        // check to ensure student is not already in registry
        // if not, add them and return true, otherwise return false
        // make use of equals method in class Student

        boolean recordExisted = false;
        Student newStudent = new Student(name, id);
        for (Map.Entry<String, Student> student : studentTreeMap.entrySet()) {
            if (student.getValue().equals(newStudent)) {
                recordExisted = true;
                break;
            }
        }

        if (!recordExisted) {
            studentTreeMap.put(id,newStudent);
            return true;
        }

        return false;
    }

    // Remove student from registry
    public boolean removeStudent(String studentId) {
        // Find student in students arraylist
        // If found, remove this student and return true

        for (Map.Entry<String, Student> student : studentTreeMap.entrySet()) {
            if (student.getValue().equals(studentId)) {
                studentTreeMap.remove(studentId);
                return true;
            }
        }
        return false;
    }

    // Print all registered students
    public void printAllStudents() {
        for (Map.Entry<String, Student> student : studentTreeMap.entrySet()) {
            System.out.println("ID: " + student.getValue().getId() + " Name: " + student.getValue().getName());
        }

    }

    // Given a studentId and a course code, add student to the active course
    public void addCourse(String studentId, String courseCode) {
        // Find student object in registry (i.e. students arraylist)
        // Check if student has already taken this course in the past Hint: look at their credit course list
        // If not, then find the active course in courses array list using course code
        // If active course found then check to see if student already enrolled in this course
        // If not already enrolled
        //   add student to the active course
        //   add course to student list of credit courses with initial grade of 0

        Student student = getStudent(studentId);
        if (student != null) {
            if(student.findCreditCourse(courseCode) == null){
                ActiveCourse activeCourse = getActiveCourse(courseCode);
                if (activeCourse.getActiveStudent(studentId) == null) {
                    student.addCourse(activeCourse.getName(), courseCode, activeCourse.getDescription(),
                            activeCourse.getFormat(), activeCourse.getSemester(), 0.0);
                    activeCourse.addActiveCourseStudent(student);
                }
            }
        }

    }

    // Given a studentId and a course code, drop student from the active course
    public void dropCourse(String studentId, String courseCode) {
        // Find the active course
        // Find the student in the list of students for this course
        // If student found:
        //   remove the student from the active course
        //   remove the credit course from the student's list of credit courses


        ActiveCourse activeCourse = getActiveCourse(courseCode);
        if (activeCourse != null) {
            Student student = getStudent(studentId);
            if (student != null) {
                activeCourse.removeActiveCourseStudent(studentId);
                student.removeActiveCourse(courseCode);
            }
        }
    }

    // Print all active courses
    public void printActiveCourses() {
        for (Map.Entry<String,ActiveCourse> activeCourse : courseTreeMap.entrySet()) {
            System.out.println(activeCourse.getValue().getDescription());
        }
    }

    // Print the list of students in an active course
    public void printClassList(String courseCode) {
        for (Map.Entry<String,ActiveCourse> activeCourse : courseTreeMap.entrySet()) {
            if (activeCourse.getKey().equalsIgnoreCase(courseCode)) {
                activeCourse.getValue().printClassList();
                break;
            }
        }
    }

    // Given a course code, find course and sort class list by student name
    public void sortCourseByName(String courseCode) {
        for (Map.Entry<String,ActiveCourse> activeCourse : courseTreeMap.entrySet()) {
            if (activeCourse.getKey().equalsIgnoreCase(courseCode)) {
                activeCourse.getValue().sortByName();
                break;
            }
        }

    }

    // Given a course code, find course and sort class list by student name
    public void sortCourseById(String courseCode) {
        for (Map.Entry<String,ActiveCourse> activeCourse : courseTreeMap.entrySet()) {
            if (activeCourse.getKey().equalsIgnoreCase(courseCode)) {
                activeCourse.getValue().sortById();
                break;
            }
        }
    }

    // Given a course code, find course and print student names and grades
    public void printGrades(String courseCode) {
        for (Map.Entry<String,ActiveCourse> activeCourse : courseTreeMap.entrySet()) {
            if (activeCourse.getKey().equalsIgnoreCase(courseCode)) {
                activeCourse.getValue().printGrades();
            }
        }
    }

    // Given a studentId, print all active courses of student
    public void printStudentCourses(String studentId) {
        Student student = getStudent(studentId);
        if (student.getId().equals(studentId)) {
            student.printActiveCourses();
        }
    }

    // Given a studentId, print all completed courses and grades of student
    public void printStudentTranscript(String studentId) {
        Student student = getStudent(studentId);
        if (student != null) {
            student.printTranscript();
        }
    }

    // Given a course code, student id and numeric grade
    // set the final grade of the student
    public void setFinalGrade(String courseCode, String studentId, double grade) {
        // find the active course
        // If found, find the student in class list
        // then search student credit course list in student object and find course
        // set the grade in credit course and set credit course inactive

        ActiveCourse activeCourse = getActiveCourse(courseCode);
        if (activeCourse != null) {
            Student student = activeCourse.getActiveStudent(studentId);
            if (student != null) {
                CreditCourse creditCourse = student.findCreditCourse(courseCode);
                if(creditCourse != null){
                    creditCourse.setGrade(grade);
                    creditCourse.setInactive();
                }

            }
        }
    }

    /**
     * Find the student in the list of students
     * @param studentId input the studentId to find it
     * @return
     */
    public Student getStudent(String studentId) {
        for (Map.Entry<String,Student> studentEntry : studentTreeMap.entrySet()) {
            if (studentEntry.getKey().equalsIgnoreCase(studentId)) {
                return studentEntry.getValue();
            }
        }
        return null;
    }

    /**
     * Get the ActiveCourse from list of active courses
     * @param courseCode input the course code
     * @return
     */
    public ActiveCourse getActiveCourse(String courseCode) {
        for (Map.Entry<String,ActiveCourse> activeCourseEntry : courseTreeMap.entrySet()) {
            if (activeCourseEntry.getKey().equalsIgnoreCase(courseCode)) {
                return activeCourseEntry.getValue();
            }
        }
        return null;
    }

}
