
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

public class ChannelDevice{
	
	private final static byte PRINT = 	1;
	private final static byte PRINTS = 	2;
	private final static byte READ = 	3;
	private final static byte READS = 	4;
	private final static byte READI = 	5;
	private final static byte READSI = 	6;
	
        CPU cpu;
        Memory memory;
        private Byte[] lastCDR;
        private LinkedList<String> inputQueue = new LinkedList<String>();
        private boolean waitingForInput = false;
        
    
	public void ChannelDevice() {
	lastCDR = cpu.CDR;
		switch (lastCDR[0]) {
		
			case 1: {
				if (!inputQueue.isEmpty())
				writeFromInputToRam();
			else
				waitingForInput = true;
			break;
			}
			
			case 2: {
				break;
			}
			
			case 3: {
				break;
			}
			
			case 4: {
				break;
			}
			
			case 5: {
				break;
			}
			
			case 6: {
				break;
			}
			
			default: {
				break;
			}
		}
	}

    private void writeFromInputToRam() {
		waitingForInput = false;
		String s = inputQueue.removeFirst();
		byte[] bytes = s.getBytes(StandardCharsets.US_ASCII);
		Byte[] address = { lastCDR[1], lastCDR[2] };
		int i = 0;
                
	}

	
}
