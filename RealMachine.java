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
		byte value1 = (byte) 5;
		byte value2 = (byte) 6;
		
		memory.write(value1address, value1);
		memory.write(value2address, value2);
		
		byte[] address1 = {(byte) 0, (byte) 0};
		memory.write(address1, push);
		byte[] address2 = {(byte) 0, (byte) 1};
		memory.write(address2, value1address[0]);
		byte[] address3 = {(byte) 0, (byte) 2};
		memory.write(address3, value1address[1]);
		byte[] address4 = {(byte) 0, (byte) 3};
		memory.write(address4, push);
		byte[] address5 = {(byte) 0, (byte) 4};
		memory.write(address5, value2address[0]);
		byte[] address6 = {(byte) 0, (byte) 5};
		memory.write(address6, value2address[1]);
		
		byte[] SP = {(byte) 15, (byte) 15};
		cpu.setSP(SP);
		
		cpu.setMemory(memory);
		
		while(cpu.getTI() != 48){
			cpu.cycle();
		}
		
		byte[] SP1 = {(byte) 15, (byte) 16};
		byte[] SP2 = {(byte) 15, (byte) 17};
		byte[] SP3 = {(byte) 15, (byte) 18};
		
		System.out.println(memory.read(SP));
		System.out.println(memory.read(SP1));
		System.out.println(memory.read(SP2));
		System.out.println(memory.read(SP3));
		
		byte pop = (byte) 20;
		byte[] destination = {(byte) 256, (byte) 256};
		byte[] address7 = {(byte) 0, (byte) 6};
		memory.write(address7, pop);
		byte[] address8 = {(byte) 0, (byte) 7};
		memory.write(address8, destination[0]);
		byte[] address9 = {(byte) 0, (byte) 8};
		memory.write(address8, destination[1]);
		
		cpu.setMemory(memory);
		
		while(cpu.getTI() != 0){
			cpu.cycle();
		}
		System.out.println("---");
		System.out.println(memory.read(destination));
		//System.out.println(memory.read({(byte) 15, (byte) 16}));
		//System.out.println(memory.read({(byte) 15, (byte) 17}));
		//System.out.println(memory.read({(byte) 15, (byte) 18}));
		//System.out.println(memory.read({(byte) 15, (byte) 19}));
	}
}