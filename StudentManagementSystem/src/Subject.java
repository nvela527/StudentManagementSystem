/**
 * Subject class
 * This class just holds one subject and the grade for it.
 * I’m using basic encapsulation so everything is private,
 * and only accessible through getters/setters.
 */
public class Subject 
{

    private String name;
    private double grade; // grade will always be between 0 and 100

    /**
     * Creates a subject with a name and grade.
     */
    public Subject(String name, double grade) 
    {
        this.name = name;
        this.grade = grade;
    }

    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public double getGrade() 
    {
        return grade;
    }

    /**
     * Updates the grade for the subject.
     * Validation (0–100) is handled in the main menu, not here.
     */
    public void setGrade(double grade) 
    {
        this.grade = grade;
    }

    @Override
    public String toString() 
    {
        return name + ": " + grade;
    }
}
