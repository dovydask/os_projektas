public class Memory{
	
	private int size;
	private Word[] memory;
	
	public Memory(int size){
		this.size = size;
		memory = new Word[size];
			
		for(int i = 0; i < size; i++){
			memory[i] = new Word();
		}
	}
	
	public Word read(int address){
		return memory[address];
	}
	
	public void write(Word word, int address){
		memory[address] = word;
	}
	
	public int getSize(){
		return size;
	}
}