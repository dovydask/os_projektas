public class Memory{
	
	private int blocks;
	private int size;
	private byte[][] memory;
	
	public Memory(int blocks, int size){
		this.blocks = blocks;
		this.size = size;
		
		memory = new byte[blocks][size];
			
		for(int i=0; i<256; ++i){
			for(int j=0; j<256; ++j){
				memory[i][j] = (byte) 0;
			}
		}
	}
	
	public byte[] read(byte[] address){
		return memory[CPU.byteToInt(address[0])][CPU.byteToInt(address[1])];
	}
	
	public void write(byte[] address, byte data){
		memory[CPU.byteToInt(address[0])][CPU.byteToInt(address[1])] = data;
	}
	
	public int getSize(){
		return size;
	}
	
	public int getBlocks(){
		return blocks;
	}
	
	public byte[][] getMemory(){
		return memory;
	}
}