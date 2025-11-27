import java.util.Scanner;

/**
 * Main class
 * This runs the Student Management System.
 * I used a scanner for input and a menu loop so the user can pick what to do.
 * Most of the work is done inside StudentManagementSystem, which my partner made.
 */
public class Main 
{

    public static void main(String[] args) 
    {

        Scanner scanner = new Scanner(System.in);
        StudentManagementSystem sms = new StudentManagementSystem();

        int choice;

        // Loop keeps showing the menu until the user exits
        do 
        {
            printMenu();
            choice = readInt(scanner, "Enter your choice: ");

            switch (choice) 
            {

                case 1:
                    handleAddStudent(scanner, sms);
                    break;

                case 2:
                    handleRemoveStudent(scanner, sms);
                    break;

                case 3:
                    handleUpdateStudentName(scanner, sms);
                    break;

                case 4:
                    handleAddOrUpdateGrade(scanner, sms);
                    break;

                case 5:
                    handleViewStudentDetails(scanner, sms);
                    break;

                case 6:
                    sms.listStudents();
                    break;

                case 7:
                    sms.printStudentsSortedByAverage();
                    break;

                case 8:
                    sms.printAllStudentAverages();
                    break;

                case 9:
                    handleSubjectReport(scanner, sms);
                    break;

                case 10:
                    handleSaveToFile(scanner, sms);
                    break;

                case 11:
                    handleLoadFromFile(scanner, sms);
                    break;

                case 0:
                    System.out.println("Exiting. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid choice. Try again.");
            }

            System.out.println();

        } 
        while (choice != 0);

        scanner.close();
    }

    /**
     * Prints the menu options.
     */
    private static void printMenu() 
    {
        System.out.println("======================================");
        System.out.println("       Student Management System      ");
        System.out.println("======================================");
        System.out.println("1. Add student");
        System.out.println("2. Remove student");
        System.out.println("3. Update student name");
        System.out.println("4. Add or update grade");
        System.out.println("5. View student details");
        System.out.println("6. List all students");
        System.out.println("7. Show students sorted by average");
        System.out.println("8. Report: average grade for each student");
        System.out.println("9. Report: high/low grade for a subject");
        System.out.println("10. Save to file");
        System.out.println("11. Load from file");
        System.out.println("0. Exit");
        System.out.println("======================================");
    }

    // -------------------------
    // Input helpers
    // -------------------------

    /**
     * Reads an integer safely.
     * If the user types something wrong, they will retry until valid.
     */
    private static int readInt(Scanner scanner, String prompt) 
    {
        while (true) 
        {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try 
            {
                return Integer.parseInt(input.trim());
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    /**
     * Reads a non-empty string (for names, IDs, subject names, etc.)
     */
    private static String readNonEmptyString(Scanner scanner, String prompt) 
    {
        while (true) 
        {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) 
            {
                return input;
            } 
            else 
            {
                System.out.println("Input cannot be empty.");
            }
        }
    }

    /**
     * Reads a double safely. Used for grades.
     */
    private static double readDouble(Scanner scanner, String prompt) 
    {
        while (true) 
        {
            System.out.print(prompt);
            String input = scanner.nextLine();

            try 
            {
                return Double.parseDouble(input.trim());
            } 
            catch (NumberFormatException e) 
            {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // -------------------------
    // Menu option handlers
    // -------------------------

    private static void handleAddStudent(Scanner scanner, StudentManagementSystem sms) 
{
    String id = readNonEmptyString(scanner, "Enter student ID: ");
    String name = readNonEmptyString(scanner, "Enter student name: ");

    System.out.print("Is this an honors student? (Y/N): ");
    String honorsInput = scanner.nextLine().trim();

    boolean isHonors = honorsInput.equalsIgnoreCase("Y");

    // Call the overloaded method that knows about honors students
    sms.addStudent(id, name, isHonors);

    if (isHonors) 
    {
        System.out.println("Honors student added.");
    } 
    else 
    {
        System.out.println("Student added.");
    }
}


    private static void handleRemoveStudent(Scanner scanner, StudentManagementSystem sms) 
    {
        String id = readNonEmptyString(scanner, "Enter ID to remove: ");

        if (sms.removeStudent(id)) 
        {
            System.out.println("Student removed.");
        } 
        else 
        {
            System.out.println("Student not found.");
        }
    }

    private static void handleUpdateStudentName(Scanner scanner, StudentManagementSystem sms) 
    {
        String id = readNonEmptyString(scanner, "Enter ID: ");
        Student student = sms.findStudentById(id);

        if (student == null) 
        {
            System.out.println("Student not found.");
            return;
        }

        String newName = readNonEmptyString(scanner, "Enter new name: ");
        student.setName(newName);

        System.out.println("Name updated.");
    }

    private static void handleAddOrUpdateGrade(Scanner scanner, StudentManagementSystem sms) 
    {
        String id = readNonEmptyString(scanner, "Enter ID: ");
        Student student = sms.findStudentById(id);

        if (student == null) 
        {
            System.out.println("Student not found.");
            return;
        }

        String subjectName = readNonEmptyString(scanner, "Subject name: ");

        double grade;
        while (true) 
        {
            grade = readDouble(scanner, "Grade (0–100): ");
            if (grade >= 0 && grade <= 100) 
            {
                break;
            }
            System.out.println("Grade must be between 0 and 100.");
        }

        student.addOrUpdateSubject(subjectName, grade);
        System.out.println("Grade saved.");
    }

    private static void handleViewStudentDetails(Scanner scanner, StudentManagementSystem sms) 
    {
        String id = readNonEmptyString(scanner, "Enter ID: ");
        Student student = sms.findStudentById(id);

        if (student == null)
        {
            System.out.println("Student not found.");
        } 
        else 
        {
            student.printDetails();
        }
    }

    private static void handleSubjectReport(Scanner scanner, StudentManagementSystem sms) 
    {
        String subject = readNonEmptyString(scanner, "Enter subject: ");
        sms.printSubjectHighLow(subject);
    }

    private static void handleSaveToFile(Scanner scanner, StudentManagementSystem sms) 
    {
        String file = readNonEmptyString(scanner, "File name: ");
        sms.saveToFile(file);
    }

    private static void handleLoadFromFile(Scanner scanner, StudentManagementSystem sms) 
    {
        String file = readNonEmptyString(scanner, "File name: ");
        sms.loadFromFile(file);
    }
}
