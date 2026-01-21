//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//import java.sql.SQLOutput;
//Q) What if the user made a mistake and want to change it learn how to do it
//In the End try figuring out an AI model into it and also think of how it should be useful for this??
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
		StudentManager manager = new StudentManager();
		manager.loadFromFile(students);
		manager.saveToFile(students);
		int menuChoice;
		do
		{
			//System.out.println("\n===== MENU =====");
			//menuChoice = scan.nextInt();
			System.out.println("///--Menu Details--//");
			System.out.println("1. Add Student");
			System.out.println("2. View Students");
			System.out.println("3. Delete Student");
			System.out.println("4. Student Search");
			System.out.println("5. Edit Student Details");
			System.out.println("6. Exit");
			System.out.print("Enter choice: ");
			menuChoice=scan.nextInt();
		switch (menuChoice)
			{
				case 1:
					manager.addStudent(scan, students);
					break;
				case 2:
					manager.viewStudent(students);
					break;
				case 3:
					manager.deleteStudent(scan, students);
					break;
				case 4:
					manager.searchStudent(scan, students);
					break;
				case 5:
					manager.editStudent(scan, students);
					break;
				case 6:
				System.out.println("Before exiting Changes are being Saved");
				manager.saveToFile(students);
				//System.out.println("Saving the changes made :-");
				System.out.println("Exiting program...");
				break;
				default:
				System.out.println("Invalid choice. Try again.");
				//break;
			}
		}while (menuChoice != 6);
	}
}
class StudentManager
{
	void addStudent(Scanner scan, ArrayList<Student> students)
	{
		System.out.println("Ask the user to enter students info:");
		System.out.print("Ask User To Enter Student ID:- ");
		int id = scan.nextInt();
		System.out.print("Ask User To Enter Student Name:- ");
		String name = scan.next();
		System.out.print("Ask User To Enter Number Of Subjects:- ");
		int n = scan.nextInt();
		int[] marks = new int[n];
		for (int i = 0; i < marks.length; i++)
		{
			marks[i] = scan.nextInt();
		}
		System.out.println("Ask user if he entered the correct values (Yes/No)");
		String confirm = scan.next();
		while (confirm.equals("No"))
		{
			System.out.println("Review the Details");
			System.out.println("ID :- " + id);
			System.out.println("Name :- " + name);
			System.out.println("Marks :- " + Arrays.toString(marks));
			System.out.println("Ask User if everything is ok (Yes/No)");
			confirm = scan.next();
			if (confirm.equals("No"))
			{
				System.out.println("Ask him to reassign the values");
				System.out.println("1 for ID");
				System.out.println("2 for Name");
				System.out.println("3 for Marks");
				int editValue = scan.nextInt();
				switch (editValue)
				{
					case 1:
						System.out.print("Re-enter the ID:- ");
						id = scan.nextInt();
						boolean duplicateFound = false;
						for (Student existingStudent : students)
						{
							if (existingStudent.id == id)
							{
								duplicateFound = true;
								break;
							}
						}
						if (duplicateFound)
						{
							System.out.println("Student ID already exists. Please enter a different ID.");
							break; // go back to add-student loop
					    }
						break;
						case 2:
							System.out.print("Re-enter the Name:- ");
							name = scan.next();
							break;
						case 3:
							for (int k = 0; k < marks.length; k++)
							{
								System.out.print("Re-enter marks for subject " + (k + 1) + ": ");
								marks[k] = scan.nextInt();
							}
							break;
							default:
								System.out.println("Invalid choice");
				}
				System.out.println("Ask User is everything ok now (Yes/No)");
	            confirm = scan.next();
			}
		}
		students.add(new Student(id, name, marks));
		System.out.println("Student saved successfully.");
	}
	void viewStudent(ArrayList<Student> students)
	{
		System.out.println("View Students selected");
		for (int j = 0; j < students.size(); j++)
		{
			Student s = students.get(j);
			int total = s.totalMarks();
			double avgMarks = s.averageMarks();
			String result = s.isPass() ? "Pass" : "Fail";
			System.out.println("Final result of : " + s.name + " with student ID is: " + s.id);
			System.out.println(total + "," + avgMarks + "," + result);
		}
	}
	void searchStudent(Scanner scan, ArrayList<Student> students)
	{
		System.out.println("Search Student by ID");
				System.out.println("Ask user to enter student ID for searching");
				int id = scan.nextInt();
				Student foundStudent = null;
				//boolean found=false;
				for (Student searchStudent : students )
				{
					if (searchStudent.id==id)
					{
						foundStudent=searchStudent;
						//found=true;
						break;
					}
				} 
				if (foundStudent != null)
				{
					System.out.println("Display details of the student");
					System.out.println("The ID of the student is -"+foundStudent.id);
					System.out.println("The Name of the Student is -"+foundStudent.name);
					System.out.println(Arrays.toString(foundStudent.marks));
					int totalMarks=foundStudent.totalMarks();
					double avgMarks=foundStudent.averageMarks();
					String result=foundStudent.isPass() ? "Pass" : "Fail";
					System.out.println(totalMarks);
					System.out.println(avgMarks);
					System.out.println(result);
				}
				else
				{
					System.out.println("Enter Correct ID, Student Not Found");
				}
	}
	void editStudent(Scanner scan, ArrayList<Student> students)
	{
		System.out.println("Ask user to Enter the student ID");
				int id = scan.nextInt();
				Student editStudentDetails = null;
				for (Student editStudent : students) 
				{
					if (editStudent.id == id) 
					{
						editStudentDetails = editStudent;
						break;
					}
				}

				if (editStudentDetails == null) 
				{
					System.out.println("Student not found. Returning to menu.");
					//break;
					return;
				}

				// ---- Save ORIGINAL data (snapshot) ----
				int originalId = editStudentDetails.id;
				String originalName = editStudentDetails.name;
				int[] originalMarks = Arrays.copyOf(
					editStudentDetails.marks,
					editStudentDetails.marks.length
				);
				System.out.println("Displaying the Details for the ID Entered");
				System.out.println("ID: " + editStudentDetails.id);
				System.out.println("Name: " + editStudentDetails.name);
				System.out.println("Marks: " + Arrays.toString(editStudentDetails.marks));
				System.out.println("Ask user what he want to edit");
				System.out.println("1. ID");
				System.out.println("2. Name");
				System.out.println("3. Marks");
				int editChoice = scan.nextInt();
				// ---- TEMP variables (delayed mutation) ----
				int newId = originalId;
				String newName = originalName;
				int[] newMarks = Arrays.copyOf(originalMarks, originalMarks.length);
				if (editChoice == 1) 
				{
					System.out.print("Re-enter the ID of the Student: ");
					newId = scan.nextInt();
				}
				else if (editChoice == 2) 
				{
					System.out.print("Re-enter the Name of the Student: ");
					newName = scan.next();
				}
				else if (editChoice == 3) 
				{
					System.out.println("Re-enter the Marks of the Student");
					for (int k = 0; k < newMarks.length; k++) 
					{
						System.out.print("Marks for subject " + (k + 1) + ": ");
						newMarks[k] = scan.nextInt();
					}
				}
				else 
				{
					System.out.println("Invalid option. Returning to menu.");
					//break;
					return;
				}
					// ---- CONFIRMATION ----
				System.out.println("Ask user if he wants to save the changes (Yes/No)");
				String editStudentconfirm = scan.next();
				if (editStudentconfirm.equals("Yes")) 
				{
					// APPLY changes (commit)
					editStudentDetails.id = newId;
					editStudentDetails.name = newName;
					editStudentDetails.marks = newMarks;
					System.out.println("Changes saved successfully.");
				} 
				else
				{
					// ROLLBACK (restore originals)
					editStudentDetails.id = originalId;
					editStudentDetails.name = originalName;
					editStudentDetails.marks = originalMarks;
					System.out.println("Changes discarded. Original data restored.");
				}
	}
	void deleteStudent(Scanner scan, ArrayList<Student> students)
	{
		System.out.println("Delete Selected Student");
				System.out.println("Ask the user to enter the ID for deletion");
				int id=scan.nextInt();
				Student studentToDelete=null;
				boolean deleteFound=false;
				for (Student deleteStudent : students )
				{
					if (deleteStudent.id==id)
					{
						studentToDelete = deleteStudent;
						deleteFound=true;
						break;
					}
				}
				if (studentToDelete != null) 
				{
					System.out.print("Are you sure you want to delete this student? (Yes/No): ");
					String deleteConfirm = scan.next();
					if (deleteConfirm.equals("Yes")) 
					{
						students.remove(studentToDelete);
						System.out.println("Student deleted successfully.");
					}					
					else 
					{
						System.out.println("Deletion cancelled.");
					}
				} 
				else 
				{
					System.out.println("Student not found.");
				}
	}
	void loadFromFile(ArrayList<Student> students)
	{
		File file = new File("students.Data.txt");
		if (!file.exists())
		{
			return;
		}
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line;
			while ((line = reader.readLine()) != null)
			{
				System.out.println(line);
				String[] parts = line.split("\\|");
				int id = Integer.parseInt(parts[0]);
				String name = parts[1];
				String[] marksSheet = parts[2].split(",");
				int[] marks = new int[marksSheet.length];
				for (int i=0;i<marksSheet.length ;i++ )
				{
					marks[i]= Integer.parseInt(marksSheet[i]);
					//System.out.println(marks[i]);
				}
				Student s = new Student(id, name, marks);
				students.add(s);
			}
			reader.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	void saveToFile(ArrayList<Student> students)
	{
		File file = new File("students.Data.txt");
		try
		{
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			//writer.write();
			for (Student student : students )
			{
				StringBuilder lineBuilder = new StringBuilder();
				// add id
				lineBuilder.append(student.getId());
				lineBuilder.append("|");
				// add name
				lineBuilder.append(student.getName());
				lineBuilder.append("|");
				// add marks
				int[] marks = student.getMarks();
				for (int i = 0; i < marks.length; i++)
				{
					if (i > 0)
					{
						lineBuilder.append(",");
					}
					lineBuilder.append(marks[i]);
				}
				// final line for this student
				String line = lineBuilder.toString();
				writer.write(line);
				writer.newLine();
			}
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
}
class Student
{
    int id;
    String name;
    int[] marks;
    Student(int id, String name, int[] marks)
    {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }
    int totalMarks()
    {
        int sum = 0;
        for (int i = 0; i < marks.length; i++)
        {
            sum += marks[i];
        }
        return sum;
    }
    double averageMarks()
    {
        return (double) totalMarks() / marks.length;
    }
    boolean isPass()
    {
        return averageMarks() >= 40;
    }
	public int getId()
	{
		return id;
	}
	public String getName()
	{
		return name;
	}
	public int[] getMarks()
	{
		return marks;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	public void setMarks(int[] marks)
	{
		this.marks = marks;
	}
}
