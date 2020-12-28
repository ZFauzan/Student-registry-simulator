//Zunaira Fauzan
//500975247
public class CreditCourse extends Course
{
	String semester;
	double grade;
	boolean active;

	// add a constructor method with appropriate parameters
	// should call the super class constructor
	public CreditCourse(String name, String code, String descr, String fmt,String semester, double grade)
	{
		super(name,code,descr,fmt);
		this.semester = semester;
		this.grade = grade;
		this.active = true;
	}

	public boolean getActive()
	{
		// add code and remove line below
		if(this.active)
			return true;

		return false;
	}

	public void setActive()
	{
		this.active = true;
	}

	public void setInactive()
	{
		this.active = false;
	}

	public void setGrade(double grade){
		this.grade = grade;
	}

	public double getGrade(){
		return this.grade;
	}

	public String getSemester(){
		return this.semester;
	}

	public String displayGrade()
	{
		// Change line below and print out info about this course plus which semester and the grade achieved
		// make use of inherited method in super class
		return super.getInfo() + " Semester: " + this.semester + " Grade: " + this.grade;
	}

}
