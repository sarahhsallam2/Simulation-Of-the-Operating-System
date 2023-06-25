
public class PCB {
	private int processID;
    private state processState; 
    private int programCounter;
    private int memoryStartAddress;
    private int memoryEndAddress;


    public PCB(int id, state processState) {
        processID = id;
        this.processState = processState;
        programCounter = 0;
        
   
    }
	public PCB() {
		
	}
	public int getProcessID() {
		return processID;
	}
	public state getProcessState() {
		return processState;
	}
	public int getProgramCounter() {
		return programCounter;
	}
	public int getMemoryStartAddress() {
		return memoryStartAddress;
	}
	public int getMemoryEndAddress() {
		return memoryEndAddress;
	}
	public void setProcessID(int processID) {
		this.processID = processID;
	}
	public void setProcessState(state processState) {
		this.processState = processState;
	}
	public void setProgramCounter(int programCounter) {
		this.programCounter = programCounter;
	}
	public void setMemoryStartAddress(int memoryStartAddress) {
		this.memoryStartAddress = memoryStartAddress;
	}
	public void setMemoryEndAddress(int memoryEndAddress) {
		this.memoryEndAddress = memoryEndAddress;
	}
	

}
