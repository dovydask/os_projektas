taipublic class CPU{
	
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
	public final static byte PRINTI = 	(byte) 140;
	public final static byte READ = 	(byte) 150;
	public final static byte READI = 	(byte) 160;
	public final static byte SETR = 	(byte) 170;
	
	public static byte USER = 			(byte) 0;
	public static byte SUPERVISOR = 	(byte) 1;
	
	private byte MODE = 	SUPERVISOR; 	//Supervizoriaus rezimas - 1, vartotojo - 0.
	private byte SI = 		(byte) 0;
	private byte TI = 		(byte) 50;
	private byte PI = 		(byte) 0;
	private byte PTR = 		(byte) 1;
	private byte[] IC = 	{(byte) 0, (byte) 0};
	private byte[] SP =	 	{(byte) 12, (byte) 0};
	private byte[] CDR =	{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
	private byte[] R = 		{(byte) 0, (byte) 0, (byte) 0, (byte) 0};
	private byte CF = 		(byte) 0;
	
	private Memory mem;
	private ChannelDevice channeldevice;

	public CPU(){

    }
	
	public void setMemory(Memory mem){
		this.mem = mem;
	}
	
	public void setChannelDevice(ChannelDevice channeldevice){
		this.channeldevice = channeldevice;
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
	
	public Memory getMemory(){
		return mem;
	}
	
	public void setMODE(byte MODE){
		this.MODE = MODE;
	}
	
	public void setPTR(byte PTR){
		this.PTR = PTR;
	}
	
	public void setPI(byte PI){
		this.PI = PI;
	}
	
	public void setSI(byte SI){
		this.SI = SI;
	}
	
	public void setTI(byte TI){
		this.TI = TI;
	}
	
	public void setCF(byte CF){
		this.CF = CF;
	}
	
	public void setIC(byte[] IC){
		this.IC = IC;
	}
	
	public void setR(byte[] R){
		this.R = R;
	}
	
	public void setCDR(byte[] CDR){
		this.CDR = CDR;
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
		
		if(TI == 0){
			modeToSupervisor();
		}
		while(TI != 0){
			if(MODE == SUPERVISOR){
				command(mem.read(IC));
			}
			else{
				//IC = mem.pagingMechanism(IC, PTR);
				command(mem.read(IC));
			}
			checkInterrupts();
			TI--;
		}
	}
	
	public void resetTI(){
		TI = 50;
	}
	
	public void resetCPU(){
		TI = 50;
		IC[0] = (byte) 0;
		IC[1] = (byte) 0;
		IC = addressConversion(IC);
		PTR = (byte) 1;
		R[0] = (byte) 0;
		R[1] = (byte) 0;
		R[2] = (byte) 0;
		R[3] = (byte) 0;
		CDR[0] = (byte) 0;
		CDR[1] = (byte) 0;
		CDR[2] = (byte) 0;
		CDR[3] = (byte) 0;
		CDR[4] = (byte) 0;
		SP[0] = 12;
		SP[1] = 0;
		SP = addressConversion(SP);
		SI = (byte) 0;
		PI = (byte) 0;
		CF = (byte) 0;
		MODE = USER;
	}
	
	private void command(byte instruction){
			switch(instruction){
				case PUSH: {
					System.out.println("PUSH");
					byte[] sp = this.SP;
					byte[] temp = iterateRegister(this.IC, 1);
					byte x = mem.read(temp);
					temp = iterateRegister(this.IC, 2);
					byte y = mem.read(temp);
					byte[] xy = {x, y};
					System.out.println(mem.read(xy));
					//xy = addressConversion(xy);
					System.out.println(mem.read(xy));
					sp = iterateRegister(sp, 1);
					mem.write(xy, SP);
					this.SP = iterateRegister(this.SP, 1);
					this.IC = iterateRegister(this.IC, 3);
					break;
				}
				
				case POP: {
					System.out.println("POP");
					byte[] sp = this.SP;
					byte[] ic = iterateRegister(this.IC, 1);
					byte x = mem.read(ic);
					ic = iterateRegister(this.IC, 2);
					byte y = mem.read(ic);
					byte[] xy = {x, y};
					//xy = addressConversion(xy);
					mem.write(sp, xy);
					this.SP = iterateRegister(this.SP, -1);
					this.IC = iterateRegister(this.IC, 3);
					break;
				}
				
				case ADD: {
					System.out.println("Add");
                                        byte[] tempSP = iterateRegister(SP, -4);
                                        byte a = mem.read(tempSP);
                                        tempSP = iterateRegister(SP, -3);
                                        byte b = mem.read(tempSP);
                                        tempSP = iterateRegister(SP, -2);
                                        byte c = mem.read(tempSP);
                                        tempSP = iterateRegister(SP, -1);
                                        byte d = mem.read(tempSP);
					
					System.out.println("A: "+ a);
					System.out.println("B: "+ b);
					System.out.println("C: "+ c);
					System.out.println("D: "+ d);
					
                                        short value1 = (short) (((a) << 8) | (b));
                                        short value2 = (short) (((c) << 8) | (d));
                                        short sum = (short) (value1 + value2);
                                        byte a1 = (byte) sum;
                                        byte a2 = (byte) (sum >> 8);

                                        tempSP = iterateRegister(SP, -3);
                                        mem.write(tempSP, a1);
                                        tempSP = iterateRegister(SP, -4);
                                        mem.write(tempSP, a2);

                                        tempSP = iterateRegister(this.SP, -2);
                                        
                                        this.SP = tempSP;
                                        this.IC = iterateRegister(this.IC, 1);
                   
					break;
				
				
				}
				
				case SUB: {

                    byte[] tempSP = iterateRegister(SP, -4);
                    byte a = mem.read(tempSP);
                    tempSP = iterateRegister(SP, -3);
                    byte b = mem.read(tempSP);
                    tempSP = iterateRegister(SP, -2);
                    byte c = mem.read(tempSP);
                    tempSP = iterateRegister(SP, -1);
                    byte d = mem.read(tempSP);
					short value1 = (short) (((a) << 8) | (b));
                    short value2 = (short) (((c) << 8) | (d));
                    short sum = (short) (value1 - value2);
                    byte a1 = (byte) sum;
                    byte a2 = (byte) (sum >> 8);

                    tempSP = iterateRegister(SP, -3);
                    mem.write(tempSP, a1);
                    tempSP = iterateRegister(SP, -4);
                    mem.write(tempSP, a2);
                    tempSP = iterateRegister(this.SP, -2);
                    this.SP = tempSP;
                    this.IC = iterateRegister(this.IC, 1);
                                        
                    break;
				}
				
				case MUL: {

					byte[] tempSP = iterateRegister(SP, -4);
                    byte a = mem.read(tempSP);
                    tempSP = iterateRegister(SP, -3);
                    byte b = mem.read(tempSP);
                    tempSP = iterateRegister(SP, -2);
                    byte c = mem.read(tempSP);
					tempSP = iterateRegister(SP, -1);
                    byte d = mem.read(tempSP);
					short value1 = (short) (((a) << 8) | (b));
                    short value2 = (short) (((c) << 8) | (d));
                    short sum = (short) (value1 * value2);
                    byte a1 = (byte) sum;
                    byte a2 = (byte) (sum >> 8);

                    tempSP = iterateRegister(SP, -3);
                    mem.write(tempSP, a1);
                    tempSP = iterateRegister(SP, -4);
                    mem.write(tempSP, a2);

                    tempSP = iterateRegister(this.SP, -2);
                                        
                    this.SP = tempSP;
                    this.IC = iterateRegister(this.IC, 1);
                                        
                    break;
				}
				
				case DIV: {

                    byte[] tempSP = iterateRegister(SP, -4);
                    byte a = mem.read(tempSP);
                    tempSP = iterateRegister(SP, -3);
                    byte b = mem.read(tempSP);
                    tempSP = iterateRegister(SP, -2);
                    byte c = mem.read(tempSP);
                    tempSP = iterateRegister(SP, -1);
                    byte d = mem.read(tempSP);
                    short value1 = (short) (((a) << 8) | (b));
                    short value2 = (short) (((c) << 8) | (d));
                    short sum = (short) (value1 / value2);
                    byte a1 = (byte) sum;
                    byte a2 = (byte) (sum >> 8);

                    tempSP = iterateRegister(SP, -3);
                    mem.write(tempSP, a1);
                    tempSP = iterateRegister(SP, -4);
                    mem.write(tempSP, a2);

                    tempSP = iterateRegister(this.SP, -2);
                                        
                    this.SP = tempSP;
                    this.IC = iterateRegister(this.IC, 1);
                                        
                    break;
				}
				
				case CMP: {
					System.out.println("CMP");
					byte[] sp = this.SP;
					sp = iterateRegister(this.SP, -1);
					byte x = mem.read(sp);
					sp = iterateRegister(this.SP, -2);
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
					byte[] ic = iterateRegister(this.IC, 1);
					byte x = mem.read(ic);
					ic = iterateRegister(this.IC, 2);
					byte y = mem.read(ic);
					byte[] xy = {x, y};
					this.IC = xy;
					break;
				}
				
				case JG: {
					if(CF == 1){
						byte[] ic = iterateRegister(this.IC, 1);
						byte x = mem.read(ic);
						ic = iterateRegister(this.IC, 2);
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
						byte[] ic = iterateRegister(this.IC, 1);
						byte x = mem.read(ic);
						ic = iterateRegister(this.IC, 2);
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
						byte[] ic = iterateRegister(this.IC, 1);
						byte x = mem.read(ic);
						ic = iterateRegister(this.IC, 2);
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
					System.out.println("STOP");
					modeToSupervisor();
					SI = 7;
					//this.IC = iterateRegister(this.IC, 1);
					break;
				}
				
				case PRINT: {
					System.out.println("PRINT");
					SI = 1;
					//this.IC = iterateRegister(this.IC, 1);
					//callChannelDevice();
					break;
				}
				
				case PRINTI: {
					System.out.println("PRINTI");
					SI = 2;
					//this.IC = iterateRegister(this.IC, 1);
					//callChannelDevice();
					break;
				}
				
				case READ: {
					System.out.println("READ");
					SI = 3;
					//this.IC = iterateRegister(this.IC, 1);
					//callChannelDevice();
					break;
				}
				
				case READI: {
					System.out.println("READI");
					SI = 4;
					//this.IC = iterateRegister(this.IC, 1);
					//callChannelDevice();
					break;
				}
				
				case SETR: {
					byte[] ic = iterateRegister(this.IC, 1);
					byte x1 = mem.read(ic);
					ic = iterateRegister(this.IC, 2);
					byte y1 = mem.read(ic);
					ic = iterateRegister(this.IC, 3);
					byte x2 = mem.read(ic);
					ic = iterateRegister(this.IC, 4);
					byte y2 = mem.read(ic);
					byte[] xy = {x1, y1, x2, y2};
					this.R = xy;
					this.IC = iterateRegister(this.IC, 5);
					break;
				}
				
				default: {
					PI = 2;	//Neteisingas operacijos kodas
					break;
				}
			}
	}
	
	public void modeToSupervisor() {
		MODE = 1;
		TI = 50;
		IC = iterateRegister(IC, 1);
		byte[] ic_0 = {(byte) 11, (byte) (4*(PTR-1))};
		byte[] ic_1 = {(byte) 11, (byte) (4*(PTR-1) + 1)};
		byte[] sp_0 = {(byte) 11, (byte) (4*(PTR-1) + 2)};
		byte[] sp_1 = {(byte) 11, (byte) (4*(PTR-1) + 3)};
		mem.write(ic_0, IC[0]);
		mem.write(ic_1, IC[1]);
		mem.write(sp_0, SP[0]);
		mem.write(sp_1, SP[1]);
		
		IC[0] = 0;
		IC[1] = 0;
		SP[0] = 12;
		SP[1] = 0;
	}

	public void modeToUser() {
		MODE = 0;
		byte[] ic_0 = {(byte) 11, (byte) (4*(PTR-1))};
		byte[] ic_1 = {(byte) 11, (byte) (4*(PTR-1) + 1)};
		byte[] sp_0 = {(byte) 11, (byte) (4*(PTR-1) + 2)};
		byte[] sp_1 = {(byte) 11, (byte) (4*(PTR-1) + 3)};
		IC[0] = mem.read(ic_0);
		IC[1] = mem.read(ic_1);
		SP[0] = mem.read(sp_0);
		SP[1] = mem.read(sp_1);
	}
	
	public void callChannelDevice(){
		CDR[0] = this.SI;
		CDR[1] = R[0];
		CDR[2] = R[1];
		CDR[3] = R[2];
		CDR[4] = R[3];
		modeToSupervisor();
		channeldevice.setMemory(this.mem);
		channeldevice.command(CDR);
		modeToUser();
		SI = 0;
	}
	
	private void checkInterrupts(){
		switch(SI){
			case 1: {
				callChannelDevice();
				break;
			}
			
			case 2: {
				callChannelDevice();
				break;
			}
			
			case 3: {
				callChannelDevice();
				break;
			}
			
			case 4: {
				callChannelDevice();
				break;
			}

			default: {
				break;
			}	
		}
		//modeToUser();
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

