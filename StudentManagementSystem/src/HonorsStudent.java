/**
 * HonorsStudent class
 * This class extends Student and slightly boosts the average grade.
 * It shows an example of inheritance and polymorphism in the project.
 */
public class HonorsStudent extends Student 
{

    // Extra field just for honors students
    private double bonusPoints;

    /**
     * Basic constructor for an honors student.
     * Uses the Student constructor for id and name.
     */
    public HonorsStudent(String id, String name) 
    {
        super(id, name);
        this.bonusPoints = 5.0; // small bonus to show different behavior
    }

    /**
     * Constructor that lets us choose the bonus amount.
     */
    public HonorsStudent(String id, String name, double bonusPoints) 
    {
        super(id, name);
        this.bonusPoints = bonusPoints;
    }

    /**
     * Overrides the normal calculateAverage to add a small bonus.
     * This is where polymorphism happens: if a Student variable
     * actually refers to an HonorsStudent object, this version
     * will run instead of the one in Student.
     */
    @Override
    public double calculateAverage() 
    {
        double baseAverage = super.calculateAverage();

        // If there are no subjects yet, just return 0.0
        if (baseAverage == 0.0) 
        {
            return 0.0;
        }

        double withBonus = baseAverage + bonusPoints;

        // Keep the grade between 0 and 100
        if (withBonus > 100.0) 
        {
            withBonus = 100.0;
        }

        return withBonus;
    }

    @Override
    public void printDetails() 
    {
        System.out.println("[Honors Student]");
        super.printDetails();
    }
}
