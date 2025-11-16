import java.io.*;
import java.util.*;

class Student {
    private String name;
    private int rollNo;
    private String grade;

    public Student(String name, int rollNo, String grade) {
        this.name = name;
        this.rollNo = rollNo;
        this.grade = grade;
    }

    public int getRollNo() {
        return rollNo;
    }

    public String getName() {
        return name;
    }

    public String getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return rollNo + "," + name + "," + grade;
    }
}

public class StudentManagementSystem {
    private static final String FILE_NAME = "students.txt";
    private ArrayList<Student> students = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public StudentManagementSystem() {
        loadFromFile();
    }

    private void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;
        try {
            Scanner fileScanner = new Scanner(file);
            while (fileScanner.hasNextLine()) {
                String[] data = fileScanner.nextLine().split(",");
                int roll = Integer.parseInt(data[0]);
                String name = data[1];
                String grade = data[2];
                students.add(new Student(name, roll, grade));
            }
            fileScanner.close();
        } catch (IOException | NumberFormatException e) {
            System.out.println("Error reading file!");
        }
    }

    private void saveToFile() {
        try (FileWriter writer = new FileWriter(FILE_NAME)) {
            for (Student s : students) {
                writer.write(s.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error writing to file!");
        }
    }

    public void addStudent() {
        System.out.print("Enter Roll Number: ");
        int roll = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.getRollNo() == roll) {
                System.out.println("❌ Roll number already exists!");
                return;
            }
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Grade: ");
        String grade = sc.nextLine();

        if (name.isEmpty() || grade.isEmpty()) {
            System.out.println("❌ Name and Grade cannot be empty!");
            return;
        }

        students.add(new Student(name, roll, grade));
        saveToFile();
        System.out.println("✔ Student added successfully.");
    }

    public void removeStudent() {
        System.out.print("Enter Roll Number to remove: ");
        int roll = sc.nextInt();
        for (Student s : students) {
            if (s.getRollNo() == roll) {
                students.remove(s);
                saveToFile();
                System.out.println("✔ Student removed successfully.");
                return;
            }
        }
        System.out.println("❌ Student not found!");
    }

    public void searchStudent() {
        System.out.print("Enter Roll Number to search: ");
        int roll = sc.nextInt();
        for (Student s : students) {
            if (s.getRollNo() == roll) {
                System.out.println("===== STUDENT FOUND =====");
                System.out.println("Name: " + s.getName());
                System.out.println("Roll No: " + s.getRollNo());
                System.out.println("Grade: " + s.getGrade());
                return;
            }
        }
        System.out.println("❌ Student not found!");
    }

    public void editStudent() {
        System.out.print("Enter Roll Number to edit: ");
        int roll = sc.nextInt();
        sc.nextLine();

        for (Student s : students) {
            if (s.getRollNo() == roll) {
                System.out.print("Enter new Name: ");
                String name = sc.nextLine();
                System.out.print("Enter new Grade: ");
                String grade = sc.nextLine();

                if (!name.isEmpty()) s.setName(name);
                if (!grade.isEmpty()) s.setGrade(grade);

                saveToFile();
                System.out.println("✔ Student updated successfully.");
                return;
            }
        }
        System.out.println("❌ Student not found!");
    }

    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }
        System.out.println("===== STUDENT LIST =====");
        for (Student s : students) {
            System.out.println("Roll: " + s.getRollNo() + " | Name: " + s.getName() + " | Grade: " + s.getGrade());
        }
    }

    public void run() {
        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student");
            System.out.println("4. Edit Student");
            System.out.println("5. Display All Students");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: addStudent(); break;
                case 2: removeStudent(); break;
                case 3: searchStudent(); break;
                case 4: editStudent(); break;
                case 5: displayAllStudents(); break;
                case 6: System.out.println("Thank you! Exiting..."); return;
                default: System.out.println("Invalid choice! Try again.");
            }
        }
    }

    public static void main(String[] args) {
        new StudentManagementSystem().run();
    }
}
