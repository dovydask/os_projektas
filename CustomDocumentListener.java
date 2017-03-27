import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class CustomDocumentListener implements DocumentListener {

	private JTextField txtField;
	
	public CustomDocumentListener(JTextField txtField){
		this.txtField = txtField;
	}
	
	@Override
	public void changedUpdate(DocumentEvent e) {}

	@Override
	public void insertUpdate(DocumentEvent e) {
		txtField.setBackground(new Color(0xff, 0x4d, 0x4d, 255));
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		txtField.setBackground(new Color(0xff, 0x4d, 0x4d, 255));
	}
	
}
