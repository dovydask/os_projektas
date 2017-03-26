
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class ChannelDevice{
	
	private final static byte PRINT = 	1;
	private final static byte PRINTI = 	2;
	private final static byte READ = 	3;
	private final static byte READI = 	4;
	
    public CPU cpu;
    public Memory memory;
	public Memory externalMemory = new Memory(512, 256);
    private byte[] lastCDR;
    private LinkedList<String> inputQueue = new LinkedList<String>();
    private boolean waitingForInput = false;
	public String lastOutput = "";
        
    public ChannelDevice(){
		
	}
	
	public void setCPU(CPU cpu){
		this.cpu = cpu;
	}
	
	public void setMemory(Memory memory){
		this.memory = memory;
	}
	
	public void command(byte[] CDR){
		this.lastCDR = CDR;
		switch(lastCDR[0]) {
			case 1: {
				int i = 0;
				String out = "";
				byte[] address = {lastCDR[1], lastCDR[2]};
				while(i++ < 100){
					byte b = memory.read(address);
					address = cpu.iterateRegister(address, 1, (byte) 1);
					String add = new String(new byte[] {b}, StandardCharsets.US_ASCII);
					if (add.equals("$"))
						break;
					out += add;
				}
				this.lastOutput = out;
				break;	
			}
			
			case 2: {
				int i = 0;
				byte[] addressMem = {lastCDR[1], lastCDR[2]};
				byte[] addressExt = {lastCDR[3], lastCDR[4]};
				while(i++ < 100){
					byte b = memory.read(addressExt);
					if(b == (byte) 0x62)
						break;
					memory.write(addressMem, b);
					addressMem = cpu.iterateRegister(addressMem, 1, (byte) 1);
					addressExt = cpu.iterateRegister(addressExt, 1, (byte) 1);
				}
				break;
			}
			
			
			case 3: {
				if(!inputQueue.isEmpty()){
					String s = inputQueue.removeFirst();
					byte[] bytes = s.getBytes(StandardCharsets.US_ASCII);
					byte[] address = {lastCDR[1], lastCDR[2]};
					int i = 0;
					for(byte b : bytes){
						memory.write(address, b);
						address = cpu.iterateRegister(address, 1, (byte) 1);
						if(++i == 100)
							break;
					}
					//cpu.PI = (byte) 6;
					//MissingLink.frame.refreshData();
				}
				else
					waitingForInput = true;
				break;
			}
			
			case 4: {
				int i = 0;
				byte[] addressMem = {lastCDR[1], lastCDR[2]};
				byte[] addressExt = {lastCDR[3], lastCDR[4]};
				while(i++ < 100){
					byte b = memory.read(addressMem);
					if (b == (byte) 0x62)
						break;
					memory.write(addressExt, b);
					addressMem = cpu.iterateRegister(addressMem, 1, (byte) 1);
					addressExt = cpu.iterateRegister(addressExt, 1, (byte) 1);
				}
				break;
			}
			
			default: {
				break;
			}
		}
	}
	
	public void input(String input) {
		inputQueue.add(input);
	}
}
