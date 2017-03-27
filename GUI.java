import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;
import java.awt.Dimension;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	public JPanel contentPane;
	public JTextField PTRtextField;
	public JTextField ICtextField;
	public JTextField SPtextField;
	public JTextField CDRtextField;
	public JTextField CFtextField;
	public JTextField RtextField;
	public JTextField MODEtextField;
	public JTextField TItextField;
	public JTextField PItextField;
	public JTextField SItextField;
	public JScrollPane scrollPane;
		public JScrollPane scrollPane2;
	public JTable MemoryTable;
	public JTable MemoryTable2;

	public JPanel inputPanel;
	public JPanel outputPanel;

	/**
	 * Create the frame.
	 */
	public GUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Real machine");
		setBounds(150, 150, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow][grow][grow][grow][grow][grow][grow][grow][grow][grow]", "[][][][grow][grow][][grow][grow]"));

		JLabel lblRegisters = new JLabel("Registrai");
		contentPane.add(lblRegisters, "wrap");

		JLabel lblMode = new JLabel("MODE");
		contentPane.add(lblMode);

		JLabel lblPtr = new JLabel("PTR");
		contentPane.add(lblPtr);
		
		JLabel lblIc = new JLabel("IC");
		contentPane.add(lblIc);

		JLabel lblSp = new JLabel("SP");
		contentPane.add(lblSp);
		
		JLabel lblr = new JLabel("R");
		contentPane.add(lblr);
		
		JLabel lblCdr = new JLabel("CDR");
		contentPane.add(lblCdr);
		
		JLabel lblTi = new JLabel("TI");
		contentPane.add(lblTi);
		
		JLabel lblPi = new JLabel("PI");
		contentPane.add(lblPi);
		
		JLabel lblSi = new JLabel("SI");
		contentPane.add(lblSi);
		
		JLabel lblCF = new JLabel("CF");
		contentPane.add(lblCF, "wrap");
		
		MODEtextField = new JTextField();
		contentPane.add(MODEtextField);
		//MODEtextField.setColumns(2);
		
		PTRtextField = new JTextField();
		contentPane.add(PTRtextField);
		//PTRtextField.setColumns(2);
		
		ICtextField = new JTextField();
		//ICtextField.setColumns(4);
		contentPane.add(ICtextField);

		SPtextField = new JTextField();
		contentPane.add(SPtextField);
		//SPtextField.setColumns(4);
		
		RtextField = new JTextField();
		contentPane.add(RtextField);
		//RtextField.setColumns(8);

		CDRtextField = new JTextField();
		contentPane.add(CDRtextField);
		//CDRtextField.setColumns(10);

		TItextField = new JTextField();
		contentPane.add(TItextField);
		//TItextField.setColumns(2);

		PItextField = new JTextField();
		contentPane.add(PItextField);
		//PItextField.setColumns(2);
		
		SItextField = new JTextField();
		contentPane.add(SItextField);
		//SItextField.setColumns(2);

		CFtextField = new JTextField();
		contentPane.add(CFtextField, "wrap");
		//CFtextField.setColumns(2);
		
		JLabel lblRam = new JLabel("Memory (Memory/Storage)");
		contentPane.add(lblRam, "wrap, aligny bottom");
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane, "span 5");

		MemoryTable = new JTable();
		MemoryTable.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		MemoryTable.setAlignmentX(Component.LEFT_ALIGNMENT);
		MemoryTable.setRowSelectionAllowed(false);
		MemoryTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane.setViewportView(MemoryTable);

		JTable rowTable = new RowNumberTable(MemoryTable);
		scrollPane.setRowHeaderView(rowTable);
		scrollPane.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
		
		scrollPane2 = new JScrollPane();
		scrollPane2.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
		scrollPane2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		contentPane.add(scrollPane2, "span 5, wrap");

		MemoryTable2 = new JTable();
		MemoryTable2.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		MemoryTable2.setAlignmentX(Component.LEFT_ALIGNMENT);
		MemoryTable2.setRowSelectionAllowed(false);
		MemoryTable2.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		scrollPane2.setViewportView(MemoryTable2);

		JTable rowTable2 = new RowNumberTable(MemoryTable2);
		scrollPane2.setRowHeaderView(rowTable2);
		scrollPane2.setCorner(JScrollPane.UPPER_LEFT_CORNER, rowTable.getTableHeader());
		

		JButton btnRunSingleCycle = new JButton("Begin Cycle");
		btnRunSingleCycle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Interface.cpu.cycle();
				printDataToOutput(Interface.channeldevice.lastOutput);
				repaint();
				refreshData();
			}
		});
		contentPane.add(btnRunSingleCycle);
		
		JButton btnReset = new JButton("Reset");
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Interface.cpu.resetCPU();
				repaint();
				refreshData();
			}
		});
		contentPane.add(btnReset, "wrap");

		inputPanel = new IOWindow(true);
		inputPanel.setMinimumSize(new Dimension(750, 75));
		contentPane.add(inputPanel, "wrap, span 10");

		outputPanel = new IOWindow(false);
		outputPanel.setMinimumSize(new Dimension(750, 75));
		contentPane.add(outputPanel, "span 10");	

		initTextFields();
		initActionListeners();
		refreshData();
	}

	private void initTextFields() {
		PTRtextField.getDocument().addDocumentListener(new CustomDocumentListener(PTRtextField));
		ICtextField.getDocument().addDocumentListener(new CustomDocumentListener(ICtextField));
		SPtextField.getDocument().addDocumentListener(new CustomDocumentListener(SPtextField));
		CDRtextField.getDocument().addDocumentListener(new CustomDocumentListener(CDRtextField));
		RtextField.getDocument().addDocumentListener(new CustomDocumentListener(RtextField));
		MODEtextField.getDocument().addDocumentListener(new CustomDocumentListener(MODEtextField));
		TItextField.getDocument().addDocumentListener(new CustomDocumentListener(TItextField));
		PItextField.getDocument().addDocumentListener(new CustomDocumentListener(PItextField));
		SItextField.getDocument().addDocumentListener(new CustomDocumentListener(SItextField));
		CFtextField.getDocument().addDocumentListener(new CustomDocumentListener(CFtextField));

		setTextFieldsColor();
	}

	private void setTextFieldsColor() {
		PTRtextField.setBackground(Color.WHITE);
		ICtextField.setBackground(Color.WHITE);
		SPtextField.setBackground(Color.WHITE);
		CDRtextField.setBackground(Color.WHITE);
		RtextField.setBackground(Color.WHITE);
		MODEtextField.setBackground(Color.WHITE);
		TItextField.setBackground(Color.WHITE);
		PItextField.setBackground(Color.WHITE);
		SItextField.setBackground(Color.WHITE);
		CFtextField.setBackground(Color.WHITE);
	}

	private void initActionListeners() {
		PTRtextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = convertToBytes(PTRtextField.getText());
				if (bytes.length == 1) {
					Interface.cpu.setPTR(bytes[0]);
				} else {
					Interface.cpu.setPTR((byte) 1);
				}
				refreshData();
			}
		});
		ICtextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = convertToBytes(ICtextField.getText());
				if (bytes.length == 2) {
					Interface.cpu.setIC(bytes);
				} else {
					byte[] b = new byte[] {0, 0};
					Interface.cpu.setIC(b);
				}
				refreshData();
			}
		});
		SPtextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = convertToBytes(SPtextField.getText());
				if (bytes.length == 2) {
					Interface.cpu.setSP(bytes);
				} else {
					byte[] b = new byte[] {0, 0};
					Interface.cpu.setSP(b);
				}
				refreshData();
			}
		});
		CDRtextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = convertToBytes(CDRtextField.getText());
				if (bytes.length == 5) {
					Interface.cpu.setCDR(bytes);
				} else {
					byte[] b = new byte[] {0, 0, 0, 0, 0};
					Interface.cpu.setCDR(b);
				}
				refreshData();
			}
		});
		RtextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = convertToBytes(CDRtextField.getText());
				if (bytes.length == 4) {
					Interface.cpu.setR(bytes);
				} else {
					byte[] b = new byte[] {0, 0, 0, 0};
					Interface.cpu.setR(b);
				}
				refreshData();
			}
		});
		MODEtextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = convertToBytes(MODEtextField.getText());
				if (bytes.length == 1) {
					Interface.cpu.setMODE(bytes[0]);
				} else {
					Interface.cpu.setMODE((byte) 0);
				}
				refreshData();
			}
		});
		TItextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = convertToBytes(TItextField.getText());
				if (bytes.length == 1) {
					Interface.cpu.setTI(bytes[0]);
				} else {
					Interface.cpu.setTI((byte) 0);
				}
				refreshData();
			}
		});
		SItextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = convertToBytes(SItextField.getText());
				if (bytes.length == 1) {
					Interface.cpu.setSI(bytes[0]);
				} else {
					Interface.cpu.setSI((byte) 0);
				}
				refreshData();
			}
		});
		PItextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = convertToBytes(PItextField.getText());
				if (bytes.length == 1) {
					Interface.cpu.setPI(bytes[0]);
				} else {
					Interface.cpu.setPI((byte) 0);
				}
				refreshData();
			}
		});
		CFtextField.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] bytes = convertToBytes(CFtextField.getText());
				if (bytes.length == 1) {
					Interface.cpu.setCF(bytes[0]);
				} else {
					Interface.cpu.setCF((byte) 0);
				}
				refreshData();
			}
		});
	}

	private byte[] convertToBytes(String s) {
		String[] s_bytes;
		if (s.indexOf('|') != -1) {
			s_bytes = s.split("\\|");
			s_bytes = Arrays.copyOfRange(s_bytes, 1, s_bytes.length);
		} else {
			s_bytes = s.split("(?<=\\G.{2})");
		}
		byte[] bytes = new byte[s_bytes.length];
		try {
			int i = 0;
			for (String b : s_bytes)
				bytes[i++] = (byte) Integer.parseInt(b);
			// System.out.println(Arrays.toString(bytes));
		} catch (Exception e) {
			bytes = new byte[] {};
		}
		return bytes;
	}

	public void refreshData() {
		PTRtextField.setText(formStringForByteArr(Interface.cpu.getPTR()));
		ICtextField.setText(formStringForByteArr(Interface.cpu.getIC()));
		SPtextField.setText(formStringForByteArr(Interface.cpu.getSP()));
		CDRtextField.setText(formStringForByteArr(Interface.cpu.getCDR()));
		RtextField.setText(formStringForByteArr(Interface.cpu.getR()));
		MODEtextField.setText(formStringForByteArr(Interface.cpu.getMODE()));
		TItextField.setText(formStringForByteArr(Interface.cpu.getTI()));
		PItextField.setText(formStringForByteArr(Interface.cpu.getPI()));
		SItextField.setText(formStringForByteArr(Interface.cpu.getSI()));
		CFtextField.setText(formStringForByteArr(Interface.cpu.getCF()));
		
		setTextFieldsColor();

		MemoryTable.repaint();
		MemoryTable2.repaint();
	}

	public void printDataToOutput(String s) {
		((IOWindow) outputPanel).appendOutput(s);
	}

	private String formStringForByteArr(Byte b) {
		return formStringForByteArr(new byte[] { b });
	}

	private String formStringForByteArr(byte[] bytes) {
		String a = " ";
		for (Byte b : bytes) {
			a += getByteAsHex(b) + " ";
		}
		return a;
	}
	
	public static String getByteAsHex(Byte b) {
		String a = Integer.toHexString(Byte.toUnsignedInt(b)).toUpperCase();
		if (a.length() == 1) {
			a = "0" + a;
		}
		return a;
	}
}
