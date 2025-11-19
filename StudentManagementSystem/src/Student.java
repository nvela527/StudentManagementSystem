import java.util.ArrayList;
import java.util.List;

/**
 * Student class
 * This class stores the student’s ID, name, and their list of subjects.
 * It uses encapsulation and simple methods to update grades and get averages.
 */
public class Student 
{

    private String id;
    private String name;
    private ArrayList<Subject> subjects;

    /**
     * Creates a student with an ID and name.
     */
    public Student(String id, String name) 
    {
        this.id = id;
        this.name = name;
        this.subjects = new ArrayList<>();
    }

    public String getId() 
    {
        return id;
    }

    public void setId(String id) 
    { 
        this.id = id;
    }

    public String getName() 
    {
        return name;
    }

    /**
     * Allows the student’s name to be updated if needed.
     */
    public void setName(String name) 
    {
        this.name = name;
    }

    /**
     * Returns a copy of the subjects list.
     * This helps keep data safe because the real list stays private.
     */
    public List<Subject> getSubjects() 
    {
        return new ArrayList<>(subjects);
    }

    /**
     * Adds a new subject OR updates the grade if the subject already exists.
     */
    public void addOrUpdateSubject(String subjectName, double grade) 
    {
        for (Subject s : subjects) 
        {
            if (s.getName().equalsIgnoreCase(subjectName)) 
            {
                s.setGrade(grade);
                return;
            }
        }
        subjects.add(new Subject(subjectName, grade));
    }

    /**
     * Calculates the student’s average grade across all subjects.
     */
    public double calculateAverage() 
    {
        if (subjects.isEmpty()) 
        {
            return 0.0;
        }

        double sum = 0.0;
        for (Subject s : subjects) 
        {
            sum += s.getGrade();
        }
        return sum / subjects.size();
    }

    @Override
    public String toString() 
    {
        return id + " - " + name + " (Average: " + String.format("%.2f", calculateAverage()) + ")";
    }

    /**
     * Prints a full breakdown of the student's info.
     * This is called from the main menu when the user picks “view details.”
     */
    public void printDetails() 
    {
        System.out.println("Student ID: " + id);
        System.out.println("Name      : " + name);
        System.out.println("Subjects:");

        if (subjects.isEmpty()) 
        {
            System.out.println("  No subjects added yet.");
        } else {
            for (Subject s : subjects) 
            {
                System.out.println("  " + s.toString());
            }
        }

        System.out.println("Average grade: " + String.format("%.2f", calculateAverage()));
    }
}
