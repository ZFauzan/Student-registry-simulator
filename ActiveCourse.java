//Zunaira Fauzan
//500975247
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Active University Course

public class ActiveCourse extends Course
{
	private ArrayList<Student> students;
	private String semester;
	private String lectureDay;
	private int lectureStart;
	private int lectureDuration;


   // Add a constructor method with appropriate parameters
   // should call super class constructor to initialize inherited variables
   // make sure to *copy* students array list being passed in into new arraylist of students
   // see class StudentRegistry to see how an ActiveCourse object is created and used
   public ActiveCourse(String name, String code, String descr, String fmt,
                       String semester,ArrayList<Student> students)
   {
	  super(name,code,descr,fmt);
      this.semester = semester;
      this.students = new ArrayList<>(students);
   }

   public String getSemester()
   {
	   return semester;
   }

   public List<Student> getStudentList(){
      return this.students;
   }

   public String getLectureDay() {
      return lectureDay;
   }

   public void setLectureDay(String lectureDay) {
      this.lectureDay = lectureDay;
   }

   public int getLectureStart() {
      return lectureStart;
   }

   public void setLectureStart(int lectureStart) {
      this.lectureStart = lectureStart;
   }

   public int getLectureDuration() {
      return lectureDuration;
   }

   public void setLectureDuration(int lectureDuration) {
      this.lectureDuration = lectureDuration;
   }

    /**
     * Add student to the active course
     * @param student Input the student object
     */
   public void addActiveCourseStudent(Student student){
       this.students.add(student);
   }

    /**
     * Remove the student from active list
     * @param id input student id
     */

   public void removeActiveCourseStudent(String id){
      if(id != null && this.students != null){
         for(Student student : students){
            if(student.getId().equals(id)){
               students.remove(student);
               break;
            }
         }
      }
   }

   // Prints the students in this course  (name and student id)
   public void printClassList()
   {
	   for(Student student : students){
          System.out.println("Id: " +  student.getId() + " Name: " + student.getName());
       }
   }

   // Prints the grade of each student in this course (along with name and student id)
   //
   public void printGrades()
   {
      StringBuilder stringBuilder = new StringBuilder();
      for(Student student : students){
         stringBuilder.append(student.getId() + " " +  student.getName() + " " + getGrade(student.getId()) + "\n");
      }

      System.out.println(stringBuilder.toString());

   }

   // Returns the numeric grade in this course for this student
   // If student not found in course, return 0
   public double getGrade(String studentId)
   {
	  // Search the student's list of credit courses to find the course code that matches this active course
	  // see class Student method getGrade()
	  // return the grade stored in the credit course object

      for(Student student : students){
         if(student.getId().equalsIgnoreCase(studentId)){
            List<CreditCourse> creditCourseList = student.getCreditCourseList();
            for(CreditCourse creditCourse : creditCourseList){
               if(creditCourse.getCode().equalsIgnoreCase(this.getCode())){
                  return creditCourse.getGrade();
               }
            }
         }
      }
	  return 0;
   }

   // Returns a String containing the course information as well as the semester and the number of students
   // enrolled in the course
   // must override method in the superclass Course and use super class method getDescription()
   public String getDescription()
   {
      return "Course Info: " + getInfo() + "\nDescription: " + getDescr() + "\nFormat: " + getFormat()
              + "\nSemester: " + this.semester + "\nEnrolment: " +  this.students.size() + "\n";
   }

   public Student getActiveStudent(String studentId){
      for(Student student : students){
         if(student.getId().equals(studentId)){
            return student;
         }
      }
      return null;
   }


   // Sort the students in the course by name using the Collections.sort() method with appropriate arguments
   // Make use of a private Comparator class below
   public void sortByName()
   {
      Collections.sort(this.students,new NameComparator());
   }

   // Fill in the class so that this class implement the Comparator interface
   // This class is used to compare two Student objects based on student name
   private class NameComparator implements Comparator<Student>
   {

      @Override
      public int compare(Student student1, Student student2) {
         return student1.getName().compareTo(student2.getName());
      }
   }

   // Sort the students in the course by student id using the Collections.sort() method with appropriate arguments
   // Make use of a private Comparator class below
   public void sortById()
   {
 	  Collections.sort(students,new IdComparator());
   }

   // Fill in the class so that this class implement the Comparator interface
   // This class is used to compare two Student objects based on student id
   private class IdComparator implements Comparator<Student>
   {
      @Override
      public int compare(Student student1, Student student2) {
         return student1.getId().compareTo(student2.getId());
      }
   }
}
