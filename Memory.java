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
	
	public int byteToInt(byte a) {
		return Byte.toUnsignedInt(a);
	}
	
	public byte read(byte[] address){
		return memory[byteToInt(address[0])][byteToInt(address[1])];
	}
	
	public void write(byte[] address, byte data){
		memory[byteToInt(address[0])][byteToInt(address[1])] = data;
	}
	
	public void write(byte[] source, byte[] destination){
		memory[byteToInt(destination[0])][byteToInt(destination[1])] = memory[byteToInt(source[0])][byteToInt(source[1])];;
	}
	
	/*
	public byte[] pagingMechanism(byte[] register) {
		return pagingMechanism(register, CPU.getPTR());
	}
	*/
	
	public byte[] pagingMechanism(byte[] register, byte PTR) {
		byte[] newReg = {(byte) 0, register[1]};
		newReg[0] = (byte) (16 * PTR + Byte.toUnsignedInt(register[0]));
		return newReg;
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