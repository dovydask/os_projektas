public class CPU{
	
	public final static byte PUSH = 	(byte) 10;
	public final static byte POP = 		(byte) 20;
	public final static byte ADD = 		(byte) 30;
	public final static byte SUB = 		(byte) 40;
	public final static byte MUL = 		(byte) 50;
	public final static byte DIV = 		(byte) 60;
	public final static byte CMP = 		(byte) 70;
	public final static byte JP = 		(byte) 80;
	public final static byte JG = 		(byte) 90;
	public final static byte JL = 		(byte) 100;
	public final static byte JE = 		(byte) 110;
	public final static byte STOP = 	(byte) 120;
	public final static byte PRINT = 	(byte) 130;
	public final static byte PRINTS = 	(byte) 140;
	public final static byte READ = 	(byte) 150;
	public final static byte READS = 	(byte) 160;
	public final static byte READI = 	(byte) 170;
	public final static byte READSI = 	(byte) 180;
	public final static byte SETR = 	(byte) 190;
	
	public static int USER = 		0;
	public static int SUPERVISOR = 	1;
	
	private int MODE; 	//Supervizoriaus režimas - 1, vartotojo - 0.
	private int SI;
	private int TI;
	private int PI;
	private int PTR;
	private int IC;
	private int SP;
	private int CDR;
	private int R;
	private int CF;
	
	private Memory mem;	//Bendroji atmintis! Žodžių įrašymo/skaitymo operacijos vykdomos perduodant adresus šiai klasei.
	
	public CPU(){
		MODE = 	1;
		SI = 	0;
		TI = 	50;	//Taimerio skaitliukas. 50 laiko vienetų (ciklo iteracijų).
		PI = 	0;
		PTR = 	0;
		IC = 	0;
		SP = 	0;
		CDR =	0;
		R = 	0;
		CF = 	0;
    }
	
	public void setMemory(Memory mem){
		this.mem = mem;
	}
	
	public int getMode(){
		return MODE;
	}
	
	//Mašininis ciklas
	public void cycle(){
		try{
			command(mem.read(IC));	//Skaitom komandą iš atminties žodžio, nurodyto IC.
			checkInterrupts();
			if(1 == 0){
				throw new MemoryException();
			}
		}
		catch(MemoryException e){	
			/*
			TODO: Kažką darom, kilus atminties išimčiai. Tikriausiai reiks nustatyti programinius
			pertraukimus (PI) - nepakanka atminties, neteisingas adresas ir pan.
			*/
		}
		TI--;
	}
	
	private void command(Word word){
		IC++;
		byte instruction = word.getByte(3);	//Instrukcijos kodą laikome paskutiniame žodžio baite.
		word.setByte(3, (byte) 0);	//Užnulinam paskutinį žodžio baitą. Pirmuose 3-juose baituose užrašytas adresas.
		try{
			switch(instruction){
				case PUSH: {
					System.out.println("PUSH");
					SP++;
					mem.write(mem.read(Word.wordToInt(word)), SP);
					break;
				}
				
				case POP: {
					mem.write(mem.read(SP), Word.wordToInt(word));
					SP--;
					break;
				}
				
				case ADD: {
					System.out.println("ADD");
					mem.write(Word.intToWord(Word.wordToInt(mem.read(SP)) + Word.wordToInt(mem.read(SP-1))), SP-1);
					SP--;
					break;
				}
				
				case SUB: {
					mem.write(Word.intToWord(Word.wordToInt(mem.read(SP)) - Word.wordToInt(mem.read(SP-1))), SP-1);
					SP--;
					break;
				}
				
				case MUL: {
					mem.write(Word.intToWord(Word.wordToInt(mem.read(SP)) * Word.wordToInt(mem.read(SP-1))), SP-1);
					SP--;
					break;
				}
				
				case DIV: {
					mem.write(Word.intToWord(Word.wordToInt(mem.read(SP)) / Word.wordToInt(mem.read(SP-1))), SP-1);
					SP--;
					break;
				}
				
				case CMP: {
					if(Word.wordToInt(mem.read(SP-1)) == Word.wordToInt(mem.read(SP))){
						CF = 0;
					}
					else if(Word.wordToInt(mem.read(SP-1)) < Word.wordToInt(mem.read(SP))){
						CF = 1;
					}
					else if(Word.wordToInt(mem.read(SP-1)) > Word.wordToInt(mem.read(SP))){
						CF = 2;
					}
					break;
				}
				
				case JP: {
					IC = Word.wordToInt(word);
					break;
				}
				
				case JG: {
					if(CF == 1){
						IC = Word.wordToInt(word);
					}
					break;
				}
				
				case JL: {
					if(CF == 2){
						IC = Word.wordToInt(word);
					}
					break;
				}
				
				case JE: {
					if(CF == 0){
						IC = Word.wordToInt(word);
					}
					break;
				}
				
				case STOP: {
					SI = 7;
					break;
				}
				
				case PRINT: {
					SI = 1;
					break;
				}
				
				case PRINTS: {
					SI = 2;
					break;
				}
				
				case READ: {
					SI = 3;
					break;
				}
				
				case READS: {
					SI = 4;
					break;
				}
				
				case READI: {
					SI = 5;
					break;
				}
				
				case READSI: {
					System.out.println("READSI");
					SI = 6;
					break;
				}
				
				case SETR: {
					R = Word.wordToInt(word);
					break;
				}
				
				default: {
					PI = 2;	//Neteisingas operacijos kodas
					break;
				}
			}
			if(1 == 0){
				throw new MemoryException();
			}
		}
		catch(MemoryException e){
			//TODO
		}	
	}
	
	private void checkInterrupts(){
		switch(SI){
			case 1: {
				
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
			
			case 7: {
				break;
			}
			
			default: {
				break;
			}
		}
		//System.out.println("SI = " + SI);
		SI = 0;
		
		switch(PI){	
			case 1: {
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
			
			default: {
				break;
			}
		}
		//System.out.println("PI = " + PI);
		PI = 0;
		
		if(TI == 0){
			//TODO
			System.out.println("TI = 0");
			TI = 50;
		}
	}
	
	public int getTI(){
		return TI;
	}
}