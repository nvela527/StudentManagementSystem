import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * StudentManagementSystem class Demo
 * Stores all students and provides methods for managing them
 */
public class StudentManagementSystem {

    // 1. List of all students
    private ArrayList<Student> students;

    
    public StudentManagementSystem() {
        students = new ArrayList<>();
    }

  

        // Add a student by ID and name, with option for honors
    public void addStudent(String id, String name, boolean isHonors) 
    {
        // Optional: prevent duplicate IDs
        if (findStudentById(id) != null) 
        {
            System.out.println("Student with ID " + id + " already exists.");
            return;
        }

        if (isHonors) 
        {
            // Polymorphism: the list is of type Student,
            // but it can also hold HonorsStudent objects.
            students.add(new HonorsStudent(id, name));
        } 
        else 
        {
            students.add(new Student(id, name));
        }
    }


    // Remove a student by ID, return true if removed
    public boolean removeStudent(String id) {
        Student s = findStudentById(id);
        if (s != null) {
            students.remove(s);
            return true;
        }
        return false;
    }

    // Find a student by ID or return 
    public Student findStudentById(String id) {
        for (Student s : students) {
            if (s.getId().equalsIgnoreCase(id)) {
                return s;
            }
        }
        return null;
    }

    // List students
    public void listStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
            return;
        }

        System.out.println("=== All Students ===");
        for (Student s : students) {
            System.out.println(s); // uses Student.toString()
        }
    }

   

    // Show students sorted by average grade 
    public void printStudentsSortedByAverage() {
        if (students.isEmpty()) {
            System.out.println("No students to report.");
            return;
        }

        List<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparingDouble(Student::calculateAverage).reversed());

        System.out.println("=== Students Sorted by Average Grade (High to Low) ===");
        for (Student s : sorted) {
            System.out.println(
                s.getId() + " - " + s.getName()
                + " | Average: " + String.format("%.2f", s.calculateAverage())
            );
        }
    }

    // Print each student's average grade
    public void printAllStudentAverages() {
        if (students.isEmpty()) {
            System.out.println("No students to report.");
            return;
        }

        System.out.println("=== Average Grade for Each Student ===");
        for (Student s : students) {
            System.out.println(
                s.getId() + " - " + s.getName()
                + " | Average: " + String.format("%.2f", s.calculateAverage())
            );
        }
    }

    // Print highest and lowest grade
    public void printSubjectHighLow(String subjectName) {
        if (students.isEmpty()) {
            System.out.println("No students available.");
            return;
        }

        Student highestStudent = null;
        Student lowestStudent = null;
        Double highestGrade = null;
        Double lowestGrade = null;

        for (Student s : students) {
            List<Subject> subs = s.getSubjects(); // returns a copy
            for (Subject subj : subs) {
                if (subj.getName().equalsIgnoreCase(subjectName)) {
                    double g = subj.getGrade();

                    if (highestGrade == null || g > highestGrade) {
                        highestGrade = g;
                        highestStudent = s;
                    }
                    if (lowestGrade == null || g < lowestGrade) {
                        lowestGrade = g;
                        lowestStudent = s;
                    }
                }
            }
        }

        if (highestGrade == null) {
            System.out.println("No grades found for subject: " + subjectName);
            return;
        }

        System.out.println("=== Subject Report: " + subjectName + " ===");
        System.out.println(
            "Highest: " + highestStudent.getId() + " - " + highestStudent.getName()
            + " | Grade: " + highestGrade
        );
        System.out.println(
            "Lowest: " + lowestStudent.getId() + " - " + lowestStudent.getName()
            + " | Grade: " + lowestGrade
        );
    }

   
    // File save and load
    
    public void saveToFile(String fileName) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            for (Student s : students) {
                StringBuilder line = new StringBuilder();
                line.append(s.getId()).append("|")
                    .append(s.getName()).append("|");

                List<Subject> subs = s.getSubjects();
                for (int i = 0; i < subs.size(); i++) {
                    Subject subj = subs.get(i);
                    line.append(subj.getName())
                        .append(":")
                        .append(subj.getGrade());
                    if (i < subs.size() - 1) {
                        line.append(",");
                    }
                }

                out.println(line.toString());
            }
            System.out.println("Saved " + students.size() + " students to " + fileName);
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadFromFile(String fileName) {
        students.clear(); 

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\\|");
                if (parts.length < 2) continue;

                String id = parts[0];
                String name = parts[1];

                Student s = new Student(id, name);

                if (parts.length >= 3 && !parts[2].isEmpty()) {
                    String[] subjectTokens = parts[2].split(",");
                    for (String token : subjectTokens) {
                        String[] sg = token.split(":");
                        if (sg.length == 2) {
                            String subjName = sg[0];
                            try {
                                double grade = Double.parseDouble(sg[1]);
                                s.addOrUpdateSubject(subjName, grade);
                            } catch (NumberFormatException e) {
                                System.out.println("Skipping invalid grade: " + token);
                            }
                        }
                    }
                }

                students.add(s);
            }

            System.out.println("Loaded " + students.size() + " students from " + fileName);
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}
