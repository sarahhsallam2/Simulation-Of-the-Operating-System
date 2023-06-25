import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.io.FileReader;
import java.io.FileWriter;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;


public class mainOS {
	
	 private static Object [] memory= new Object [40];
	 private static Queue<Integer> readyQueue= new LinkedList<Integer>();
	 //offer() = enqueue, poll() = dequeue, peek to view 1st element without removing it
	 private static Queue<Integer> blocked=new LinkedList<Integer>();
	 private static mutex  userInputMutex ;
	 private static mutex  userOutputMutex ;
	 private static mutex  fileMutex ;
     private static int runningProcess;
//     private int runningProcessIndex; //TODO what????
     private static String temp1;
     private static String temp2;
     private static String temp3;
     private static boolean quantumOne1 = false;
     private static boolean quantumOne2 = false;
     private static boolean quantumOne3 = false;
     private static boolean secondHalf1=false;
     private static boolean secondHalf2=false;
     private static boolean secondHalf3=false;
     private static boolean assignIncrementI=false;
     private static boolean finish1=false;
     private static boolean finish2=false;
     private static boolean finish3=false;
     private static int quantum;
     private static int currentTime;
     private static Hashtable<Integer, Integer> arrivalTimes = new Hashtable<Integer,Integer>(); //<arrivalTime, processID>

// 	static {
// 		arrivalTimes.put(0 , 1);
// 		arrivalTimes.put(1 , 2);
// 		arrivalTimes.put(4 , 3);
// 		currentTime = 0;
// 		
// 		//--populate 'main' memory by creating processes according to arrivalTimes 
// 		//  (can use commented code written is 'schedule')
// 		
// 		//-- enqueue processes in readyQueue when 'ready'. When are processes ready?
// 		
// 		schedule();
// 	}
 	

	public mainOS() {
		userInputMutex= new mutex();
		userOutputMutex= new mutex();
		fileMutex= new mutex();
		currentTime = 0;
		
		arrivalTimes.put(0 , 1);
		arrivalTimes.put(1 , 2);
		arrivalTimes.put(4 , 3);
		
		Scanner scanner = new Scanner(System.in);

	    System.out.print("Enter your quantum ");
	    quantum = scanner.nextInt();
	    
	}
	private static void executeCurrentProcess() {
		
		executeProgram(getProcessPC(runningProcess)+1,getProcessPC(runningProcess));
		
	}
	public static boolean isAssign() {

		
		String instruction = (String)memory[getProcessPC(runningProcess)];
		if ( instruction != null) {
		    String [] input = instruction.split(" ");
		    return input[0].equals("assign");
		}
		return false;
	}
    public static void executeProgram(int upper , int lower) {
    	 for ( int i =lower; i <upper;i++ ) {
    		 String instruction="";
    		 if(memory[i] instanceof String) {
    		    instruction = (String)memory[i];
    		 }
    		 executeLine(instruction);
    		 
//    		 if(getProcessPC(runningProcess)== getProcessInstEnd(runningProcess)) {
//    			 setProcessState(runningProcess, "Finished");
//    			 
//    		 }
    	 }
    }
    public static void executeLine(String instruction) {
    	String [] input = instruction.split(" ");
    	String action = input[0];
    	System.out.println("Currently Executing Process : "+runningProcess);
    	System.out.println("Currently Executing Instruction : "+instruction);
    	switch(action) {
    	
    	case "print": SystemCalls.print(input[1]+""); break;
    	
    	case "assign": bigAssign(input); break;
    			
    	case "writeFile": SystemCalls.writeDataToDisk(input[1], input[2]);break;
    	
    	case "readFile": SystemCalls.readDataFromDisk(input[1]);break;
    	
    	case "printFromTo": printFromTo(input[1], input[2]);break;
    	
    	case "semWait":	
    		if(input[1].equals("userInput")) {
//    			System.out.println("line 115 Running process "+runningProcess);
    			userInputMutex.SemWait(runningProcess);
    		}
    		else if(input[1].equals("userOutput")) {
    			userOutputMutex.SemWait(runningProcess);
    		}
    		else if (input[1].equals("file")) {
    			fileMutex.SemWait(runningProcess);
    		}
    		
    		break;
    	case "semSignal": 	
    		if(input[1].equals("userInput")) {
    			userInputMutex.SemSignal(runningProcess);
    		}
    		else if(input[1].equals("userOutput")) {
    			System.out.println("Line 131 "+ runningProcess);
    			userOutputMutex.SemSignal(runningProcess);
    		}
    		else if (input[1].equals("file")) {
    			fileMutex.SemSignal(runningProcess);
    		}
    		break;
    	default : System.out.println("Invalid Instruction");break;	
    	}
    	memory[(int)getProcessIndex(runningProcess)+2]= (getProcessPC(runningProcess)+1);
    	int currPC= getProcessPC(runningProcess);
    	int indexLastInst= getProcessInstEnd(runningProcess);
//    	System.out.println(indexLastInst);
    	if (currPC==indexLastInst+1 ) {
//    		System.out.println("Line 144 "+ indexLastInst);
    		setProcessState(runningProcess,"Finished");
    		
    		if(runningProcess ==1)
    			finish1=true;
    		else if(runningProcess ==2)
    			finish2=true;
    		else if(runningProcess ==3)
    			finish3=true;
    	}
    	
    	currentTime++;
    	System.out.println("At "+currentTime+" ,Memory is : ");
    	mainOS.maintoString();
    	
    	
    }
    public static void bigAssign(String [] input){
//    	System.out.println("Big Assign: "+ runningProcess);
//    	System.out.println(quantumOne1);
//    	System.out.println(quantumOne2);
//    	System.out.println(quantumOne3);
//    	System.out.println(secondHalf1);
//    	System.out.println(secondHalf2);
//    	System.out.println(secondHalf3);
    	if(runningProcess==1 && quantumOne1) {
			temp1 =assign1(input);
			quantumOne1= false;
			secondHalf1=true;
			
		  }  
		  else if(runningProcess==2 && quantumOne2) {
			temp2 = assign1(input);
			quantumOne2= false;
			secondHalf2=true;
		  }
		  else if(runningProcess==3 && quantumOne3) {
			temp3= assign1(input);
			quantumOne3= false;
			secondHalf3=true;
		  }
		  else if(runningProcess==1 && secondHalf1) {
			  assign2(input,temp1);
			  secondHalf1=false;
  		  }  
  		  else if(runningProcess==2 && secondHalf2) {
  			assign2(input,temp2);
			secondHalf2=false;
  		  }
  		  else if(runningProcess==3 && secondHalf3) {
  			assign2(input,temp3);
			 secondHalf3=false;
  		  }
		  else {
			  assign2(input,assign3(input));
			  currentTime++;
			  assignIncrementI=true;
		  }
    	
    }
    public static String assign1(String [] input) {
        String temp="";
    	if(input.length==3 ){
    		temp=SystemCalls.takeTextInput();

    	}
    	else if(input.length==4) {
    		temp=SystemCalls.readDataFromDisk(input[3]);

    	}
    	memory[(int)getProcessIndex(runningProcess)+2]= getProcessPC(runningProcess)-1;
    	return temp;
    	
    }
   
     public static void assign2(String [] input, String temp) {
    	
    	
    		SystemCalls.writeToMemory(runningProcess, input[1], temp);
    	}
     public static String assign3(String [] input) {
         String temp="";
     	if(input.length==3 ){
     		temp=SystemCalls.takeTextInput();

     	}
     	else if(input.length==4) {
     		temp=SystemCalls.readDataFromDisk(input[3]);

     	}

     	return temp;
     	
     }
    	
    public static void printFromTo(Object a , Object b) {
    	int index = whichProcess();
    	int lower=0;
    	int upper=0;
    	
    	for (int i =index ; i< index+3; i++ ) {
    		if(mainOS.getMemory()[i]!=null) {
    		VarValue temp = (VarValue)mainOS.getMemory()[i] ;
    		if(temp.getVariable().equals(a)) {
    		   	 lower= Integer.parseInt(temp.getValue()+"");
    		}
    		else if(temp.getVariable().equals(b)) {
    			 upper= Integer.parseInt(temp.getValue()+"");
    		}
    	}
    	}
    	for(int i = lower ; i<=upper ; i++) {
    		SystemCalls.print(i+" ");
//    		System.out.println();
    	}
    	
    }
    
    public static int whichProcess() {
    	
    	int n=0 ;
    	if((int)mainOS.getMemory()[0]==runningProcess){
    		n=5;
    	}
    	else if((int)mainOS.getMemory()[20]==runningProcess ){
    		n=25;
    	}
    	return n;
    }
    public static void createProcess(int pid) {
//    	System.out.println("create process 232");
       if(!readyQueue.contains(pid)) {
    	String state = "Ready";
    	Object var1= null;
    	Object var2= null;
    	Object var3= null;
    	
    	int n=-1 ;
    	if (memory[0]==null) {
    		n=0;
    	}
    	else if (memory[20]==null){
    		n=20;
    	}
    	else {
    		if(runningProcess==(int)memory[0]) {
    			
    			swapOut(20,39);
    			n=20;
    		}
    		else{
    			swapOut(0,19);
    			n=0;
    		}	
    	}
    	if(n!=-1) {
    		System.out.println(memory[n] + " is SWAPPED OUT");
    		System.out.println(pid + " is SWAPPED IN");
    		memory[n]=pid;
    		memory[n+1]=state;
    		memory[n+2]=n+8;
    		memory[n+3]=n+8;
    		memory[n+4]=readProgramFile(pid,n+8);
    		memory[n+5]=var1;
    		memory[n+6]=var2;
    		memory[n+7]=var3;
    		
    		
    	}
    	readyQueue.add(pid);
       }
       else {
//    	   System.out.println("process already exist"); 
       }

    }
    public static void swapRunningProcess(int start, int end) {
    	String content="";
    	int n=start;
    	while(start<=end ){
    		if( memory[start]!=null) {
    		   
    		   if(start==5 || start ==6 || start ==7 )  {
    			 content+=""+((VarValue)memory[start]).getVariable()+" "+
    		    ((VarValue)memory[start]).getValue()+"\n";
    		   }
    		   else
    			   content+=""+ memory[start]+"\n";
    		}
    		else {
    			content+="null"+"\n";
    		}
    		start++;
    	}
    	System.out.println(memory[0] + " is SWAPPED OUT");
    	swapIn(n,end);
    	System.out.println(runningProcess + " is SWAPPED IN");
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter("Disk.txt"))) {
            writer.write(content); 
//            System.out.println("Data has been written to the Disk.");
        } catch (Exception e) {
//            System.out.println("An error occurred while writing to the Disk.");
            e.printStackTrace();
        }	
    	
    }
    public static void swapOut(int start, int end) {
    	String content="";
    	while(start<=end ){
    		if( memory[start]!=null) {
    		   if(start==5 || start ==6 || start ==7 )  {
    			 content+=""+((VarValue)memory[start]).getVariable()+" "+
    		    ((VarValue)memory[start]).getValue()+"\n";
    		   }
    		   else
    			   content+=""+ memory[start]+"\n";
    		}
    		else {
    			content+="null"+"\n";
    		}
    		start++;
    	}
    	try (BufferedWriter writer = new BufferedWriter(new FileWriter("Disk.txt"))) {
            writer.write(content); 
//            System.out.println("Data has been written to the Disk. ");
        } catch (Exception e) {
//            System.out.println("An error occurred while writing to the Disk.");
            e.printStackTrace();
        }	
   	}
    public static void swapIn(int start, int end) {
    	
    	try (BufferedReader reader = new BufferedReader(new FileReader("Disk.txt"))) {
            String line ;
            
            while ((line = reader.readLine()) != null) {
//            	System.out.println(line+" line 315");
//            	System.out.println(start);
            	if(line.equals("null")) {
            		memory[start]=null;
            	}	
            	else if(start ==0 || start==2 || start==3 || start==4) {
            		memory[start]=Integer.parseInt(line);
            	}
            	else if(start ==5 || start ==6 || start ==7) {
            		String [] n = line.split(" ");
            		memory[start]=new VarValue(n[0],(Object)n[1]);
            	}
            	else {
                   memory[start]=line;
            	}
//            	System.out.println(memory[start]+" line 347");
                start++;
            } 
            
            while(start<=end) {
            	memory[start]=null;
            	start++;
            }
       }catch (Exception e) {
            
            e.printStackTrace();
        }	
   	}

    public static int readProgramFile(int pid , int index ) {
    	
    	try (BufferedReader reader = new BufferedReader(new FileReader("Program_"+pid+".txt"))){
			
			String line;
            while ((line = reader.readLine()) != null) {
            	    memory[index]=line;
            		index++;	
            }
		} catch (Exception e) {
			
			e.printStackTrace();
		}
    	return index-1;
    	
    	
    }
    
	public static void schedule() {
		
//		if(arrivalTimes.containsKey(currentTime)) {
//			if('main' memory is not full){
//				//write to 'main' memory process with id = arrivalTimes.get(currentTime);
//			}
//			else{
//				//write to disk memory process with id = arrivalTimes.get(currentTime);
//			}
//		}
		
//		while(!readyQueue.isEmpty()) {
//			System.out.println("While Schedule line 352");
		    
		while (!finish1 || !finish2 || !finish3) {
			if(checkArrival()) {
//				  System.out.println("check 3963");
				createProcess(arrivalTimes.get(currentTime));
//				maintoString();
				
			}
//			System.out.print("Ready : ");
//		    printList(readyQueue);
//		    System.out.print("Blocked : ");
//		    printList(blocked);
//		    maintoString();
		    
			runningProcess = readyQueue.peek();
			System.out.println("After Process: "+runningProcess+" is CHOSEN");
			System.out.print("Ready Queue: ");
			printList(readyQueue);
//			System.out.print("Ready : ");
//		    printList(readyQueue);
//		    System.out.print("Blocked : ");
//		    printList(blocked);
			if(getProcessIndex(runningProcess) == -1) {
//				System.out.println("LINE 439  IF IF");
				swapRunningProcess(0,19);
				
//				maintoString();
			}
			setProcessState(runningProcess,"Running");
			
			for(int i = 0; i< quantum ;i++) {
				if(quantum-i==1 && isAssign()) {
					if(runningProcess==1) {
						quantumOne1=true;
					}
					else if (runningProcess==2) {
						quantumOne2=true;
					}
					else if (runningProcess==3) {
						quantumOne3=true;
					}	
					
				}
				executeCurrentProcess();
				if(checkArrival()) {
//					  System.out.println("check 3963");
					createProcess(arrivalTimes.get(currentTime));
//					maintoString();
					
				   }
				
				
				
				if(assignIncrementI) {
					i++;
					assignIncrementI=false;
				}
//				System.out.print("Ready finished 474 : ");
//			    printList(readyQueue);
				if(getProcessState(runningProcess).equals("Finished")) {
					System.out.println("After Process: "+runningProcess+" is FINISHED");
					System.out.print("Ready Queue: ");
					printList(readyQueue);
					readyQueue.poll();
//					readyQueue.remove(runningProcess);
					
//					System.out.print("Ready finished 480 : ");
//				    printList(readyQueue);
				    break;
				}
				
				else if(getProcessState(runningProcess).equals("Blocked")){
					readyQueue.poll();
//					readyQueue.remove(runningProcess);
//					System.out.print("Ready Blocked 488 : ");
//				    printList(readyQueue);
					break;
				}
				
				
			}	
			if(!getProcessState(runningProcess).equals("Finished")) { 
				
				   readyQueue.offer(readyQueue.poll());
//				   System.out.print("Ready NOT finished 498 : ");
//				    printList(readyQueue);
//				readyQueue.add(readyQueue.peek());
//				readyQueue.remove(runningProcess);
			}
//			else if (getProcessState(runningProcess).equals("Finished")) {
//				readyQueue.poll();
//				System.out.print("Ready finished 505 : ");
//			    printList(readyQueue);
////				readyQueue.remove(runningProcess);
//			}
			 if (getProcessState(runningProcess).equals("Running")){
				setProcessState(runningProcess,"Ready");
			}
			
			
		}
		
		
		
	}
//	public void run() {
//		//TODO to take as input arriving time and process id
//		
//		System.out.println("Run 392");
//		System.out.println(currentTime);
//		while (!finish1 || !finish2 || !finish3) {
//			System.out.println("while 394");
//		    if(checkArrival()) {
//			  System.out.println("check 3963");
//			createProcess(arrivalTimes.get(currentTime));
//			maintoString();
//			
//		   }
//		  schedule();
//		  currentTime++;
//		}
//		
//	}
	
	
	//To check if in the clock cycle that is executing a new process arrived 
	public static boolean checkArrival()  {
		if (arrivalTimes.containsKey(currentTime)) 
			return true;
		return false;	
		}
	
	

	// used to know if arrived process is in memory or is in disk to know if i want to swapIn disk or not
	public static boolean inMemory(int pid) {
		
		if (memory[0] != null  ) {
//			System.out.println(memory[0]);
//			System.out.println("inMemory line 436");
			if( (int)memory[0]== pid)
				return true;
		}
		else if (memory[20] != null ) {
			if((int)memory[20]== pid)
				return true;
		}
		return false;
	}
	

	 public static int getProcessPC(Integer processID) {
//		    System.out.println(getProcessIndex(processID));
//		    System.out.println( memory[getProcessIndex(processID)+2]);
//		    System.out.println( "getProcessPC line 463");
//		    maintoString();
			return (int) memory[getProcessIndex(processID)+2];
			
	 }
		
	 public static void setProcessState(Integer processID, String state) {
			memory[getProcessIndex(processID)+1] = state;
	 }
		
	 public static String getProcessState(Integer processID) {
			return (String) memory[getProcessIndex(processID)+1].toString();
	}
		
    public static int getProcessInstEnd(Integer processID) {
			return (int) memory[getProcessIndex(processID)+4];
		}
		
	public static int getProcessIndex(Integer processID) {
//	    	System.out.println(processID + " line 481");
	    	
	    	if (memory[0]!=null && memory[0] == processID) {
	    		return 0;
	    	}
	    	else if (memory[20]!=null && memory[20] == processID){
	    		return 20;
	    	}
	    	else {
	    		return -1;
	    	}
	    	
	 }
	
	//To keep track of process arriving time	
	public static void setArrivalTimes(int processID, int arrivalTime) {
		arrivalTimes.put(arrivalTime, processID);
	}

	public static Object [] getMemory() {
		return memory;
	}

	public static void setMemory(Object [] memory) {
		mainOS.memory = memory;
	}

	public static Queue<Integer> getBlocked() {
		return blocked;
	}

	public static void setBlocked(Queue<Integer> blocked) {
		mainOS.blocked = blocked;
	}

	public static Queue<Integer> getReadyQueue() {
		return readyQueue;
	}

	public  void setReadyQueue(Queue<Integer> readyQueue) {
		this.readyQueue = readyQueue;
	}
	public static void printList(Queue<Integer> list) {
        StringBuilder sb = new StringBuilder();
         sb.append('{');
         if(!list.isEmpty()) {
        	 
        for (Object item : list) {
        	if(item != null)
             sb.append(item.toString()).append(", "); // Customize how each item is printed
        }
         }
        //to remove the last ", "
//         if(!list.isEmpty()) {
//        sb.deleteCharAt(sb.length()-1);
//        
//         sb.deleteCharAt(sb.length()-1);
//         }
        sb.append('}');
        System.out.println(sb.toString());
    }

	
	public int getRunningProcess() {
		return runningProcess;
	}
	public void setRunningProcess(int runningProcess) {
		this.runningProcess = runningProcess;
	}
//	public int getRunningProcessIndex() {
//		return runningProcessIndex;
//	}
//	public void setRunningProcessIndex(int runningProcessIndex) {
//		this.runningProcessIndex = runningProcessIndex;
//	}
	public static void maintoString() {
		for (int i =0 ; i<memory.length;i++) {
			System.out.println(memory[i]+" ");
		}
	}
	public static void main(String [] args) {
		mainOS ourMain = new mainOS();
		ourMain.schedule();
		ourMain.maintoString();
	}
	
}
