//Zunaira Fauzan
//500975247
import java.util.Scanner;

public class StudentRegistrySimulator {
    public static void main(String[] args) {
        try{
            StudentRegistry registry = new StudentRegistry();
            Scheduler scheduler = new Scheduler(registry.getCourseTreeMap());

            Scanner scanner = new Scanner(System.in);
            System.out.print(">");

            while (scanner.hasNextLine()) {
                try{
                    String inputLine = scanner.nextLine();
                    if (inputLine == null || inputLine.equals("")) continue;

                    Scanner commandLine = new Scanner(inputLine);
                    String command = commandLine.next();

                    if (command == null || command.equals("")) continue;

                    else if (command.equalsIgnoreCase("L") || command.equalsIgnoreCase("LIST")) {
                        registry.printAllStudents();
                    } else if (command.equalsIgnoreCase("Q") || command.equalsIgnoreCase("QUIT")) {
                        commandLine.close();
                        return;
                    } else if (command.equalsIgnoreCase("REG")) {
                        // register a new student in registry
                        // get name and student id string
                        // e.g. reg JohnBoy 74345
                        // Checks:
                        //  ensure name is all alphabetic characters
                        //  ensure id string is all numeric characters

                        // flag to check the student input record is valid or not
                        String studentName = commandLine.next();
                        String studentId = commandLine.next();

                        //check if the Student Name entered contains only alphabets
                        if (isStringOnlyAlphabet(studentName) && isNumeric(studentId)) {
                            if (registry.addNewStudent(studentName, studentId)) {
                                System.out.println("Student Successfully Registered");
                            } else {
                                System.out.println("Student " + studentName + " already registered");
                            }
                        }else if(!isStringOnlyAlphabet(studentName)) {
                            displayNameErrorMessage(studentName);
                        }else if(!isNumeric(studentId)) {
                            displayIdErrorMessage(studentId);
                        }
                    } else if (command.equalsIgnoreCase("DEL")) {
                        // delete a student from registry
                        // get student id
                        // ensure numeric
                        // remove student from registry

                        boolean validStudentId = false;
                        String studentId = commandLine.next();
                        if (studentId == null || studentId.length() == 0) {
                            System.out.println("Please enter a valid student id");
                        } else {
                            if (isNumeric(studentId)) {
                                validStudentId = true;
                            } else {
                                displayIdErrorMessage(studentId);
                            }
                        }
                        //delete the student record only all the inputs are valid
                        if (validStudentId) {
                            if (registry.removeStudent(studentId)) {
                                System.out.println("Student Record Successfully Deleted");
                            } else {
                                System.out.println("Student Id " + studentId + " doesn't existed");
                            }
                        }
                    } else if (command.equalsIgnoreCase("ADDC")) {
                        // add a student to an active course
                        // get student id and course code strings
                        // add student to course (see class StudentRegistry)


                        String studentId = commandLine.next();
                        String courseCode = commandLine.next();

                        if(studentId.length() ==0 || courseCode.length() == 0){
                            System.out.println("Please enter valid id and course code");
                        }else{
                            if(isNumeric(studentId)){
                                registry.addCourse(studentId,courseCode);
                            }else{
                                displayIdErrorMessage(studentId);
                            }
                        }

                    } else if (command.equalsIgnoreCase("DROPC")) {
                        // get student id and course code strings
                        // drop student from course (see class StudentRegistry)

                        String studentId = commandLine.next();
                        String courseCode = commandLine.next();

                        if(studentId.length() ==0 || courseCode.length() == 0){
                            System.out.println("Please enter valid id and course code");
                        }else{
                            if(isNumeric(studentId)){
                                registry.dropCourse(studentId,courseCode);
                            }else{
                                displayIdErrorMessage(studentId);
                            }
                        }

                    } else if (command.equalsIgnoreCase("PAC")) {
                        // print all active courses
                        registry.printActiveCourses();


                    } else if (command.equalsIgnoreCase("PCL")) {
                        // get course code string
                        // print class list (i.e. students) for this course

                        String courseCode = commandLine.next();
                        registry.printClassList(courseCode);

                    } else if (command.equalsIgnoreCase("PGR")) {
                        // get course code string
                        // print name, id and grade of all students in active course

                        String courseCode = commandLine.next();
                        registry.printGrades(courseCode);


                    } else if (command.equalsIgnoreCase("PSC")) {
                        // get student id string
                        // print all credit courses of student

                        String studentId = commandLine.next();
                        if(studentId.length() ==0){
                            System.out.println("Please enter valid id");
                        }else{
                            if(isNumeric(studentId)){
                                registry.printStudentCourses(studentId);
                            }else{
                                displayIdErrorMessage(studentId);
                            }
                        }


                    } else if (command.equalsIgnoreCase("PST")) {
                        // get student id string
                        // print student transcript
                        String studentId = commandLine.next();
                        if(studentId.length() ==0){
                            System.out.println("Please enter valid id");
                        }else{
                            if(isNumeric(studentId)){
                                registry.printStudentTranscript(studentId);
                            }else{
                                displayIdErrorMessage(studentId);
                            }
                        }


                    } else if (command.equalsIgnoreCase("SFG")) {
                        // set final grade of student
                        // get course code, student id, numeric grade
                        // use registry to set final grade of this student (see class StudentRegistry)

                        String courseCode = commandLine.next();
                        String studentId = commandLine.next();
                        double courseGrade = commandLine.nextDouble();

                        if(studentId.length() ==0 || courseCode.length() == 0){
                            System.out.println("Please enter valid id and course code");
                        }else{
                            if(isNumeric(studentId)){
                                registry.setFinalGrade(courseCode,studentId,courseGrade);
                            }else{
                                displayIdErrorMessage(studentId);
                            }
                        }


                    } else if (command.equalsIgnoreCase("SCN")) {
                        // get course code
                        // sort list of students in course by name (i.e. alphabetically)
                        // see class StudentRegistry
                        String courseCode = commandLine.next();
                        registry.sortCourseByName(courseCode);


                    } else if (command.equalsIgnoreCase("SCI")) {
                        // get course code
                        // sort list of students in course by student id
                        // see class StudentRegistry

                        String courseCode = commandLine.next();
                        registry.sortCourseById(courseCode);

                    }else if (command.equalsIgnoreCase("SCH")) {
                        String courseCode = commandLine.next();
                        String day = commandLine.next();
                        int startTime = commandLine.nextInt();
                        int duration = commandLine.nextInt();
                        scheduler.setDayAndTime(courseCode, day, startTime, duration);

                    } else if (command.equalsIgnoreCase("CSCH")) {
                        String courseCode = commandLine.next();
                        scheduler.clearSchedule(courseCode);

                    } else if (command.equalsIgnoreCase("PSCH")) {
                        scheduler.printSchedule();
                    }

                    System.out.print("\n>");
                    commandLine.close();

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            scanner.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getLocalizedMessage());
        }

    }

    private static boolean isStringOnlyAlphabet(String str) {
        // write method to check if string str contains only alphabetic characters
        boolean checkStringOnlyAlphabet = false;
        if (str == null || str.length() == 0) {
            return false;
        }

        for (int i = 0; i < str.length(); i++) {
            char inputChar = str.charAt(i);
            if ((!(inputChar >= 'a' && inputChar <= 'z'))
                    && (!(inputChar >= 'A' && inputChar <= 'Z'))) {
                checkStringOnlyAlphabet = false;
                break;
            } else {
                checkStringOnlyAlphabet = true;
            }
        }
        return checkStringOnlyAlphabet;
    }

    private static boolean isNumeric(String str) {
        // write method to check if string str contains only numeric characters
        if (str == null || str.length() != 5) {
            return false;
        }

        return str.matches("[0-9]+");
    }

    private static void displayNameErrorMessage(String studentName){
        System.out.println("Invalid Character in Name: " + studentName);
    }

    private static void displayIdErrorMessage(String studentId){
        System.out.println("Invalid Character or length <5 in Id: " + studentId);
    }
}
