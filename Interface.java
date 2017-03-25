import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableColumnModel;

public class Interface {
	public static CPU cpu;
	public static Memory memory;
	public static GUI gui;
	public static ChannelDevice channeldevice;
	public static RealMachine realmachine;

	public Interface() {
		/*
		memory = new Memory(256, 256);
		cpu = new CPU();
		channeldevice = new ChannelDevice();
		channeldevice.setCPU(cpu);
		cpu.setChannelDevice(channeldevice);
		cpu.setMemory(memory);
		*/
		realmachine = new RealMachine();
		this.cpu = realmachine.cpu;
		this.memory = realmachine.memory;
		this.channeldevice = realmachine.channeldevice;
		gui = new GUI();
		gui.setVisible(true);
		realmachine.frame.setVisible(false);

		TableModel table = new CustomTable(cpu.getMemory().getMemory());
		gui.MemoryTable.setModel(table);
		resizeColumnWidth(gui.MemoryTable);
		
		TableModel table2 = new CustomTable(channeldevice.externalMemory.getMemory());
		gui.MemoryTable2.setModel(table2);
		resizeColumnWidth(gui.MemoryTable2);
		
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
