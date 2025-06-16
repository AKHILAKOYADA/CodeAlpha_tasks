import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

class Student {
    String name;
    ArrayList<Integer> grades;

    Student(String name, ArrayList<Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    double getAverage() {
        int sum = 0;
        for (int g : grades) sum += g;
        return grades.size() > 0 ? (double) sum / grades.size() : 0;
    }

    int getHighest() {
        return Collections.max(grades);
    }

    int getLowest() {
        return Collections.min(grades);
    }
}

public class StudentGradeTracker extends JFrame implements ActionListener {
    private JTextField nameField, gradesField;
    private JTextArea outputArea;
    private JButton addButton, showButton;

    private ArrayList<Student> studentList = new ArrayList<>();

    public StudentGradeTracker() {
        setTitle("Student Grade Tracker");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grades (comma separated):"));
        gradesField = new JTextField();
        inputPanel.add(gradesField);

        addButton = new JButton("Add Student");
        addButton.addActionListener(this);
        inputPanel.add(addButton);

        showButton = new JButton("Show Summary");
        showButton.addActionListener(this);
        inputPanel.add(showButton);

        add(inputPanel, BorderLayout.NORTH);

        // Output area
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        add(new JScrollPane(outputArea), BorderLayout.CENTER);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            String name = nameField.getText().trim();
            String[] gradeStrings = gradesField.getText().trim().split(",");
            ArrayList<Integer> grades = new ArrayList<>();

            try {
                for (String g : gradeStrings) {
                    grades.add(Integer.parseInt(g.trim()));
                }

                Student s = new Student(name, grades);
                studentList.add(s);

                outputArea.append("Added: " + name + "\n");
                nameField.setText("");
                gradesField.setText("");

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for grades.", "Input Error", JOptionPane.ERROR_MESSAGE);
            }

        } else if (e.getSource() == showButton) {
            outputArea.setText("--- Student Summary ---\n\n");
            for (Student s : studentList) {
                outputArea.append("Name: " + s.name + "\n");
                outputArea.append("Grades: " + s.grades + "\n");
                outputArea.append(String.format("Average: %.2f\n", s.getAverage()));
                outputArea.append("Highest: " + s.getHighest() + "\n");
                outputArea.append("Lowest: " + s.getLowest() + "\n\n");
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeTracker().setVisible(true);
        });
    }
}
