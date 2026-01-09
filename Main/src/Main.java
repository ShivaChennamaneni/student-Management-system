//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
//import java.sql.SQLOutput;
//Q) What if the user made a mistake and want to change it learn how to do it
//In the End try figuring out an AI model into it and also think of how it should be useful for this??
import java.util.*;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
		int menuChoice;
		do
		{
			//System.out.println("\n===== MENU =====");
			//menuChoice = scan.nextInt();
			System.out.println("///--Menu Details--//");
			System.out.println("1. Add Student");
			System.out.println("2. View Students");
			System.out.println("3. Delete Student");
			System.out.println("4. Exit");
			System.out.print("Enter choice: ");
			menuChoice=scan.nextInt();
			switch (menuChoice) 
			{
				case 1:
				System.out.println("Ask the user to enter students info:");
				//String choice = "Yes";
				//while (choice.equals("Yes"))
				//{
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
					//System.out.println("Ask user if he want to add more students like (Yes/No):- ");
					//choice = scan.next();
				//}
					break;
					case 2:
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
						break;
				 	case 3:
					System.out.println("Delete Selected Student");
					System.out.println("Ask the user to enter the ID for deletion");
					id=scan.nextInt();
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
					break;
					case 4:
					System.out.println("Exiting program...");
					break;
					default:
					System.out.println("Invalid choice. Try again.");
					//break;
			}
		}while (menuChoice != 4);
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
}
