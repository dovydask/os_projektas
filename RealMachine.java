import java.awt.Component;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;

import javax.swing.table.TableModel;
import javax.swing.table.TableColumnModel;


public class RealMachine{
	
	public CPU cpu;
	public Memory memory;
	public ChannelDevice channeldevice;
	public JFrame frame;
	
	public RealMachine(){
		cpu = new CPU();
		memory = new Memory(256, 256);
		channeldevice = new ChannelDevice();
		channeldevice.setCPU(cpu);
		cpu.setChannelDevice(channeldevice);
		cpu.setMemory(memory);
		create_HDD_GUI();
	}
	
	public void operate(){
		
		
		//PRINT testas
		byte[] value1address = {(byte) 100, (byte) 0};
		byte[] value2address = {(byte) 100, (byte) 1};
        byte[] value3address = {(byte) 100, (byte) 2};
        byte[] value4address = {(byte) 100, (byte) 3};
		byte value1 = (byte) 101;	//e
		byte value2 = (byte) 102;	//f
        byte value3 = (byte) 103;	//g
        byte value4 = (byte) 104;	//h
		
		memory.write(value1address, value1);
		memory.write(value2address, value2);
        memory.write(value3address, value3);
        memory.write(value4address, value4);
		
		byte[] address1 = {(byte) 0, (byte) 0};
		memory.write(address1, (byte) 170);		//SETR
		byte[] address2 = {(byte) 0, (byte) 1};
		memory.write(address2, (byte) 100);		//R[0]
		byte[] address3 = {(byte) 0, (byte) 2};
		memory.write(address3, (byte) 0);		//R[1]
		byte[] address4 = {(byte) 0, (byte) 3};
		memory.write(address4, (byte) 0);		//R[2]
		byte[] address5 = {(byte) 0, (byte) 4};
        memory.write(address5, (byte) 0);		//R[3]
		byte[] address6 = {(byte) 0, (byte) 5};
		memory.write(address6, (byte) 130);		//PRINT
		
		//READ testas
		channeldevice.input("labas");
		byte[] address7 = {(byte) 0, (byte) 6};
		memory.write(address7, (byte) 170);		//SETR
		byte[] address8 = {(byte) 0, (byte) 7};
		memory.write(address8, (byte) 250);
		byte[] address9 = {(byte) 0, (byte) 8};
		memory.write(address9, (byte) 0);
		byte[] address10 = {(byte) 0, (byte) 9};
		memory.write(address10, (byte) 0);
		byte[] address11 = {(byte) 0, (byte) 10};
		memory.write(address11, (byte) 0);
		byte[] address12 = {(byte) 0, (byte) 11};
		memory.write(address12, (byte) 150);	//READ
	
		byte[] SP = {(byte) 15, (byte) 15};
		cpu.setSP(SP);
		cpu.setMemory(memory);
		
		
		while(cpu.getTI() != 30){
			cpu.cycle();
		}
		
		System.out.println("------------------------------");
		System.out.println(memory.byteToInt(cpu.getR()[0]));
		System.out.println("------------------------------");
		
		byte[] SP0 = {(byte) 250, (byte) 0};
		byte[] SP1 = {(byte) 250, (byte) 1};
		byte[] SP2 = {(byte) 250, (byte) 2};
		byte[] SP3 = {(byte) 250, (byte) 3};
		byte[] SP4 = {(byte) 250, (byte) 4};
		
		System.out.println(memory.byteToInt(memory.read(SP0)));
		System.out.println(memory.byteToInt(memory.read(SP1)));
		System.out.println(memory.byteToInt(memory.read(SP2)));
		System.out.println(memory.byteToInt(memory.read(SP3)));
        System.out.println(memory.byteToInt(memory.read(SP4)));
		/*
		byte[] SP0 = {(byte) 15, (byte) 14};
		byte[] SP1 = {(byte) 15, (byte) 16};
		byte[] SP2 = {(byte) 15, (byte) 17};
		byte[] SP3 = {(byte) 15, (byte) 18};
        byte[] SP4 = {(byte) 15, (byte) 19};
        byte[] SP5 = {(byte) 15, (byte) 20};
        byte[] SP6 = {(byte) 15, (byte) 21};
		
		System.out.println("---------------------------");
		System.out.println(memory.read(SP0));
		System.out.println(memory.read(SP));
		System.out.println(memory.read(SP1));
		System.out.println(memory.read(SP2));
		System.out.println(memory.read(SP3));
        System.out.println(memory.read(SP4));
        System.out.println(memory.read(SP5));
        System.out.println(memory.read(SP6));
		*/
		//System.out.println(memory.read({(byte) 15, (byte) 16}));
		//System.out.println(memory.read({(byte) 15, (byte) 17}));
		//System.out.println(memory.read({(byte) 15, (byte) 18}));
		//System.out.println(memory.read({(byte) 15, (byte) 19}));
	}
	
	private void create_HDD_GUI() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(150 + 800, 150, 640, 480);
		frame.setTitle("External Memory (HDD)");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		frame.setContentPane(scrollPane);

		JTable HDDtable = new JTable();
		HDDtable.setAlignmentY(Component.TOP_ALIGNMENT);
		HDDtable.setAlignmentX(Component.LEFT_ALIGNMENT);
		HDDtable.setRowSelectionAllowed(false);
		HDDtable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(HDDtable);

		JTable rowTable = new RowNumberTable(HDDtable);
		scrollPane.setRowHeaderView(rowTable);
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());

		HDDtable.setModel(new CustomTable(channeldevice.externalMemory.getMemory()));

		resizeColumnWidth(HDDtable);
	}
	
	
	public static void resizeColumnWidth(JTable table) {
		final TableColumnModel columnModel = table.getColumnModel();
		for (int column = 0; column < table.getColumnCount(); column++) {
			columnModel.getColumn(column).setMinWidth(25);
			columnModel.getColumn(column).setMaxWidth(25);
			columnModel.getColumn(column).setWidth(25);
		}
	}
}