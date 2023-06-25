import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.LinkedList;
import java.util.Queue;

public class mutex {

private static Queue<Integer> mutexBlocked ;

  private boolean locked;
  private int  ownerID;


	public mutex() {

		 mutexBlocked = new LinkedList<>();
		 locked = false;
		 
		 
	}
	public void SemWait(int pid) {
//		 System.out.println("Semwait "+ pid);
			if(locked==true && ownerID !=pid) { 
				mutexBlocked.add(pid);
				mainOS.getBlocked().add(pid);
				
			    mainOS.printList(mainOS.getBlocked());
				int index=0;
				if(pid == (int)mainOS.getMemory()[0]) {
					index = 1;
				}
				else if(pid == (int)mainOS.getMemory()[20]) {
					index = 21;
				}
				mainOS.getMemory()[index]= "Blocked";
			}
			else {  
				 ownerID=pid;
				 locked = true;
			}
		
	}
	public void SemSignal(int pid) {
		if(ownerID == pid) {
			if (!mutexBlocked.isEmpty() ) {
				ownerID=mutexBlocked.poll();
				mainOS.getBlocked().remove(ownerID);

//				System.out.println("OwnerID "+ownerID);
				mainOS.getReadyQueue().add(ownerID);
				int index=0;
				if(ownerID == (int)mainOS.getMemory()[0]) {
					index = 1;
					mainOS.getMemory()[index]= "Ready";
				}
				else if(ownerID == (int)mainOS.getMemory()[20]) {
					index = 21;
					mainOS.getMemory()[index]= "Ready";
				}
				else {
					changeState();
				}
			}
			 else {
				 ownerID= -1;
			     locked = false ;
			}	
	    }
	   
	}
	 public static void changeState() {
	    	String content="";

//	    	}
	    	int i =0;
	    		try {
	    			BufferedReader reader = new BufferedReader(new FileReader("Disk.txt"));
		            String line ;
		            
		            while ((line = reader.readLine()) != null) {
		            	if (i==1) {
		            		content+="Ready"+"\n";
		            	}
		            	else {
		            	content+=line +"\n";
		            	}
		            	i++;
		            	
		            }
//		            BufferedWriter writer = new BufferedWriter(new FileWriter("Disk.txt"));
	    	}
	    		catch (Exception e) {
		            
		            e.printStackTrace();
		        }
	    	try (BufferedWriter writer = new BufferedWriter(new FileWriter("Disk.txt"))) {
	            writer.write(content); 
	           
	        } catch (Exception e) {
	            
	            e.printStackTrace();
	        }	
             
	   	}

		public static void deleteFromQueue(Queue<Integer> x, int pid) {
			Queue<Integer> temp = new LinkedList<>();
			while (!x.isEmpty()) {
				int y = x.poll();
				if (!(y == pid))
					temp.add(y);
			}
			while (!temp.isEmpty()) {
				x.add(temp.poll());
			}
		}

	
}

	


