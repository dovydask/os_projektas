public class RealMachine{
	
	CPU cpu;
	Memory memory;
	
	public RealMachine(){
		cpu = new CPU();
		memory = new Memory(256, 256);
	}
	
	public void operate(){
		
		byte push = (byte) 10;
		byte[] value1address = {(byte) 10, (byte) 5};
		byte[] value2address = {(byte) 15, (byte) 10};
                byte[] value3address = {(byte) 20, (byte) 15};
                byte[] value4address = {(byte) 25, (byte) 20};
		byte value1 = (byte) 5;
		byte value2 = (byte) 7;
                byte value3 = (byte) 3;
                byte value4 = (byte) 2;
		
		memory.write(value1address, value1);
		memory.write(value2address, value2);
                memory.write(value3address, value3);
                memory.write(value4address, value4);
		
		byte[] address1 = {(byte) 0, (byte) 0};
		memory.write(address1, push);
		byte[] address2 = {(byte) 0, (byte) 1};
		memory.write(address2, (byte) 0);
		byte[] address3 = {(byte) 0, (byte) 2};
		memory.write(address3, (byte) 0);
		byte[] address4 = {(byte) 0, (byte) 3};
		memory.write(address4, push);
		byte[] address5 = {(byte) 0, (byte) 4};
                memory.write(address5, value1address[0]);
		byte[] address6 = {(byte) 0, (byte) 5};
		memory.write(address6, value1address[1]);
		byte[] address7 = {(byte) 0, (byte) 6};
		memory.write(address7, push);
		byte[] address8 = {(byte) 0, (byte) 7};
		memory.write(address8, (byte) 0);
		byte[] address9 = {(byte) 0, (byte) 8};
		memory.write(address9, (byte) 0);
		byte[] address10 = {(byte) 0, (byte) 9};
		memory.write(address10, push);
		byte[] address11 = {(byte) 0, (byte) 10};
		memory.write(address11, value2address[0]);
		byte[] address12 = {(byte) 0, (byte) 11};
		memory.write(address12, value2address[1]);
		byte[] address13 = {(byte) 0, (byte) 12};
		memory.write(address13, (byte) 30);
                byte[] address14 = {(byte) 0, (byte) 13};
		memory.write(address14, push);
                byte[] address15 = {(byte) 0, (byte) 14};
		memory.write(address15, (byte) 0);
		byte[] address16 = {(byte) 0, (byte) 15};
		memory.write(address16, (byte) 0);
                byte[] address17 = {(byte) 0, (byte) 16};
                memory.write(address17, push);
		byte[] address18 = {(byte) 0, (byte) 17};
		memory.write(address18, value3address[0]);
		byte[] address19 = {(byte) 0, (byte) 18};
		memory.write(address19, value3address[1]);
                byte[] address20 = {(byte) 0, (byte) 19};
		memory.write(address20, (byte) 40);
                //byte[] address21 = {(byte) 0, (byte) 20};
		//memory.write(address21, push);
                //byte[] address22 = {(byte) 0, (byte) 21};
		//memory.write(address22, (byte) 0);
		//byte[] address23 = {(byte) 0, (byte) 22};
		//memory.write(address23, (byte) 0);
                //byte[] address24 = {(byte) 0, (byte) 23};
                //memory.write(address24, push);
		//byte[] address25 = {(byte) 0, (byte) 24};
		//memory.write(address25, value4address[0]);
		//byte[] address26 = {(byte) 0, (byte) 25};
		//memory.write(address26, value4address[1]);
                //byte[] address27 = {(byte) 0, (byte) 26};
		//memory.write(address27, (byte) 60);
		
		byte[] SP = {(byte) 15, (byte) 15};
		
		cpu.setSP(SP);
		
		cpu.setMemory(memory);
		
		while(cpu.getTI() != 30){
			cpu.cycle();
		}
		
		byte[] SP0 = {(byte) 15, (byte) 14};
		byte[] SP1 = {(byte) 15, (byte) 16};
		byte[] SP2 = {(byte) 15, (byte) 17};
		byte[] SP3 = {(byte) 15, (byte) 18};
                byte[] SP4 = {(byte) 15, (byte) 19};
                byte[] SP5 = {(byte) 15, (byte) 20};
                byte[] SP6 = {(byte) 15, (byte) 21};
		
		System.out.println(memory.read(SP0));
		System.out.println(memory.read(SP));
		System.out.println(memory.read(SP1));
		System.out.println(memory.read(SP2));
		System.out.println(memory.read(SP3));
                System.out.println(memory.read(SP4));
                System.out.println(memory.read(SP5));
                System.out.println(memory.read(SP6));

		//System.out.println(memory.read({(byte) 15, (byte) 16}));
		//System.out.println(memory.read({(byte) 15, (byte) 17}));
		//System.out.println(memory.read({(byte) 15, (byte) 18}));
		//System.out.println(memory.read({(byte) 15, (byte) 19}));
	}
}