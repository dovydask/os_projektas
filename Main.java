
public class Main{
	
	public static void main(String[] args){
		
		CPU cpu = new CPU();		
		Memory memory = new Memory(256);
		
		Word test1 = new Word(); //push
		Word test2 = new Word(); //add
		Word test3 = new Word(); //readsi
		Word test4 = new Word();
		Word test5 = new Word();
		Word test6 = new Word();
		
		test4.setByte(0, (byte) 5);
		memory.write(test4, 15);
		test5.setByte(0, (byte) 4);
		memory.write(test5, 16);
		
		test1.setByte(3, (byte) 10);
		test1.setByte(0, (byte) 15);
		
		test6.setByte(3, (byte) 10);
		test6.setByte(0, (byte) 16);
		
		test2.setByte(3, (byte) 30);
		
		
		memory.write(test1, 5);
		memory.write(test6, 6);
		memory.write(test2, 7);
		
		cpu.setMemory(memory);
		
		while(cpu.getTI() != 0){ //pratestavimui, pirmi 50 atminties blokai
			cpu.cycle();
		}
		
		System.out.println(Word.wordToInt(memory.read(0)));
		System.out.println(Word.wordToInt(memory.read(1)));
		System.out.println(Word.wordToInt(memory.read(2)));
		System.out.println(Word.wordToInt(memory.read(3)));
	}
}