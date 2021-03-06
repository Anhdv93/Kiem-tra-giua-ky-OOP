package th.system.user_interface;

import java.util.Scanner;

import th.system.application.SubjectRegisterApplication;

public class UpdateScoreFunction implements ConsoleInterfaceExecutable {
    
    private Scanner scanner;
    private SubjectRegisterApplication application;
    
    public UpdateScoreFunction() {
        scanner = new Scanner(System.in);
        application = new SubjectRegisterApplication();
    }
    
    @Override
    public void execute() {
        displayConsole();
        displayStudentList();
        String studentId = getString("Please enter the student id: ");
        if (!application.doesStudentExist(studentId)) {
            System.out.println("This student id does not exist");
            return;
        }
        
        displaySubjectList(studentId);
        String subjectId = getString("Please enter the subject id: ");
        if (!application.doesSubjectExist(studentId, subjectId)) {
            System.out.println(String.format("Student %s still doesn't register the subject %s.", studentId, subjectId));
            return;
        }
        application.updateScore(
            studentId,
            subjectId,
            getFloatInRange("Please enter the midterm score: ", 0, 10),
            getFloatInRange("Please enter the final score: ", 0, 10));
    }
    
    private float getFloatInRange(String message, int lower, int upper) {
        while (true) {
            try {
                float parsedFloat = Float.parseFloat(getString(message));
                if (parsedFloat >= lower && parsedFloat <= upper) {
                    return parsedFloat;
                }
                System.out.println(String.format("Out of range! Please enter a float between %d and %d.", lower, upper));
            } catch (NumberFormatException ex) {
                // Do nothing
            }
        }
    }
    
    private void displaySubjectList(String studentId) {
        application.getRegisterSubjects(studentId);
    }
    
    private void displayStudentList() {
        application.displayAllStudentList();
    }
    
    private void displayConsole() {
        System.out.println("Update score function.");
    }
    
    private String getString(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }
    
}
