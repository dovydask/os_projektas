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
	
	public static byte USER = 			(byte) 0;
	public static byte SUPERVISOR = 	(byte) 1;
	
	private byte MODE = SUPERVISOR; 	//Supervizoriaus rezimas - 1, vartotojo - 0.
	private byte SI = 		(byte) 0;
	private byte TI = 		(byte) 50;
	private byte PI = 		(byte) 0;
	private byte PTR = 		(byte) 1;
	private byte[] IC = 	{(byte) 0, (byte) 0};
	private byte[] SP =	 	{(byte) 0, (byte) 0};
	private byte[] CDR =	{(byte) 0, (byte) 0, (byte) 0};
	private byte[] R = 		{(byte) 0, (byte) 0};
	private byte CF = 		(byte) 0;
	
	private Memory mem;	//Bendroji atmintis! Irasymo/skaitymo operacijos vykdomos perduodant adresus siai klasei.
	
	public CPU(){

    }
	
	public void setMemory(Memory mem){
		this.mem = mem;
	}
	
	public byte getMODE(){
		return MODE;
	}
	
	public byte getSI(){
		return SI;
	}
	
	public byte getTI(){
		return TI;
	}
	
	public byte getPI(){
		return PI;
	}
	
	public byte getPTR(){
		return PTR;
	}
	
	public byte[] getIC(){
		return IC;
	}
	
	public byte[] getSP(){
		return SP;
	}
	
	public byte[] getCDR(){
		return CDR;
	}
	
	public byte[] getR(){
		return R;
	}
	
	public byte getCF(){
		return CF;
	}
	
	public byte[] iterateRegister(byte[] register, int amount) {
		return iterateRegister(register, amount, getMODE());
	}

	public byte[] iterateRegister(byte[] register, int amount, byte mode) {
		byte[] newReg = {register[0], register[1]};
		int steps = Math.abs(amount);
		for(int i=0; i<steps; i++){
			if(amount > 0){
				if(++newReg[1] == 0x00){
					if(++newReg[0] == 0x10 && mode == 0){
						newReg[0] = 0;
					}
				}
			} 
			else{
				if(--newReg[1] == (byte) 0xFF){
					if(--newReg[0] == (byte) 0xFF && mode == 0){
						newReg[0] = 15;
					}
				}
			}
		}
		return newReg;
	}
	
	private byte[] addressConversion(byte[] address) {
		if(MODE == 1){
			return address.clone();
		}
		return mem.pagingMechanism(address, this.PTR);
	}

	private byte[] iterateAndConvert(byte[] address, int amount) {
		byte[] newAddress = iterateRegister(address, amount);
		byte[] convertedAddress = addressConversion(newAddress);
		return convertedAddress;
	}
	
	//Masininis ciklas
	public void cycle(){
		command(mem.read(IC));	//Skaitom komanda is atminties zodzio, nurodyto IC.
		checkInterrupts();
		TI--;
	}
	
	private void command(byte instruction){
			switch(instruction){
				case PUSH: {
					System.out.println("PUSH");
					byte[] sp = addressConversion(this.SP);
					byte[] temp = iterateAndConvert(this.IC, 1);
					byte x = mem.read(temp);
					temp = iterateAndConvert(this.IC, 2);
					byte y = mem.read(temp);
					byte[] xy = {x, y};
					xy = addressConversion(xy);
					sp = iterateRegister(sp, 1);
					mem.write(xy, SP);
					this.SP = iterateRegister(this.SP, 1);
					this.IC = iterateRegister(this.IC, 3);
					break;
				}
				
				case POP: {
					System.out.println("POP");
					byte[] sp = addressConversion(this.SP);
					byte[] ic = iterateAndConvert(this.IC, 1);
					byte x = mem.read(ic);
					ic = iterateAndConvert(this.IC, 2);
					byte y = mem.read(ic);
					byte[] xy = {x, y};
					xy = addressConversion(xy);
					mem.write(sp, xy);
					this.SP = iterateRegister(this.SP, -1);
					this.IC = iterateRegister(this.IC, 3);
					break;
				}
				
				case ADD: {

					break;
				}
				
				case SUB: {

					break;
				}
				
				case MUL: {

					break;
				}
				
				case DIV: {

					break;
				}
				
				case CMP: {
					System.out.println("CMP");
					byte[] sp = addressConversion(this.SP);
					byte x = mem.read(sp);
					sp = iterateAndConvert(this.SP, -1);
					byte y = mem.read(sp);
	
					if(x == y){
						CF = 0;
					}
					else if(x > y){
						CF = 1;
					}
					else if(x < y){
						CF = 2;
					}
					this.IC = iterateRegister(this.IC, 1);
					break;
				}
				
				case JP: {
					byte[] ic = iterateAndConvert(this.IC, 1);
					byte x = mem.read(ic);
					ic = iterateAndConvert(this.IC, 2);
					byte y = mem.read(ic);
					byte[] xy = {x, y};
					this.IC = xy;
					break;
				}
				
				case JG: {
					if(CF == 1){
						byte[] ic = iterateAndConvert(this.IC, 1);
						byte x = mem.read(ic);
						ic = iterateAndConvert(this.IC, 2);
						byte y = mem.read(ic);
						byte[] xy = {x, y};
						this.IC = xy;
					}
					else{
						this.IC = iterateRegister(this.IC, 3);
					}
					break;
				}
				
				case JL: {
					if(CF == (byte) 2){
						byte[] ic = iterateAndConvert(this.IC, 1);
						byte x = mem.read(ic);
						ic = iterateAndConvert(this.IC, 2);
						byte y = mem.read(ic);
						byte[] xy = {x, y};
						this.IC = xy;
					}
					else{
						this.IC = iterateRegister(this.IC, 3);
					}
					break;
				}
				
				case JE: {
					if(CF == 0){
						byte[] ic = iterateAndConvert(this.IC, 1);
						byte x = mem.read(ic);
						ic = iterateAndConvert(this.IC, 2);
						byte y = mem.read(ic);
						byte[] xy = {x, y};
						this.IC = xy;
					}
					else{
						this.IC = iterateRegister(this.IC, 3);
					}
					break;
				}
				
				case STOP: {
					System.out.println("STOP (QUITS PROGRAM)");
					SI = 7;
					System.exit(0);
					//this.IC = iterateRegister(this.IC, 1);
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
					SI = 6;
					break;
				}
				
				case SETR: {
					byte[] ic = iterateAndConvert(this.IC, 1);
					byte x = mem.read(ic);
					ic = iterateAndConvert(this.IC, 2);
					byte y = mem.read(ic);
					byte[] xy = {x, y};
					this.R = xy;
					this.IC = iterateRegister(this.IC, 3);
					break;
				}
				
				default: {
					PI = 2;	//Neteisingas operacijos kodas
					break;
				}
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
	
	public void setSP(byte[] SP){
		this.SP = SP;
	}
}