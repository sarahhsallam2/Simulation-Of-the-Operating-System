import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SystemCalls {
  

	
public static String takeTextInput() {
	Scanner scanner = new Scanner(System.in);

    System.out.print("Enter your input ");
    String textInput = scanner.nextLine();
    
    return textInput;
}

public static void print (String s) {
	System.out.print(s);
}
	
	
public static void writeDataToDisk(String filePath,String content) {
	
	 try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath+""+".txt"))) {
         writer.write(content); 
         System.out.println("Data has been written to the file.");
     } catch (Exception e) {
         System.out.println("An error occurred while writing to the file.");
         e.printStackTrace();
     }	
	}

public static String readDataFromDisk(String filePath) {
	String r="";
	
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath+""+".txt"))) {
    	
        String line;
        while ((line = reader.readLine()) != null) {
            // Process the line as needed
           r=r+line;
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return r;
}

public static Object readFromMemory(String variable,int id ) {
	int n=0 ;
	if ((int)mainOS.getMemory()[0]==id) {
		n=5;
	}
	else if ((int)mainOS.getMemory()[20]==id)	{
		n=25;
	}
	int i =0;
	while(i<3) {
		if(((VarValue)mainOS.getMemory()[n])!= null) {
	
		   if(((VarValue)mainOS.getMemory()[n]).getVariable().equals(variable)) {
			  return ((VarValue)mainOS.getMemory()[n]).getValue();
		    }
		n++;
		i++;
		}	
	}
	return null;
}
	

public static void writeToMemory(int id, String variable , String value  ) {
	VarValue newInput = new VarValue(variable,value);
	int n=0 ;
	if ((int)mainOS.getMemory()[0]==id) {
		n=5;
	}
	else if ((int)mainOS.getMemory()[20]==id)	{
		n=25;
	}
	int i =0;
	while(i<3) {
		if(((VarValue)mainOS.getMemory()[n])== null ||
				((VarValue)mainOS.getMemory()[n]).getVariable().equals(variable)) {
			mainOS.getMemory()[n]=newInput;
			break;
		}
		n++;
		i++;
		}	
	}
	
}
		
