public class ChannelDevice{
	
	private final static byte PRINT = 	1;
	private final static byte PRINTS = 	2;
	private final static byte READ = 	3;
	private final static byte READS = 	4;
	private final static byte READI = 	5;
	private final static byte READSI = 	6;
	
	public ChannelDevice(){
		
	}
	
	public void activate(Word word){
		byte operation = word.getByte(3);
		word.setByte(3, (byte) 0);
		int address = Word.wordToInt(word);
		
		switch(operation){
			case PRINT: {
				break;
			}
			
			case PRINTS: {
				break;
			}
			
			case READ: {
				break;
			}
			
			case READS: {
				break;
			}
			
			case READI: {
				break;
			}
			
			case READSI: {
				break;
			}
			
			default: {
				break;
			}
		}
	}
	
}