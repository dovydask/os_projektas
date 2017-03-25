import javax.swing.JPanel;
import javax.swing.JTextArea;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;

public class IOWindow extends JPanel {

	private static final long serialVersionUID = 1L;

	JTextArea textArea;
	public IOWindow(boolean isInput) {
		setLayout(new MigLayout("", "[grow][]", "[][grow]"));

		JLabel lbl_IO = new JLabel(isInput ? "Input screen" : "Output screen");
		add(lbl_IO, "cell 0 0");

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		add(scrollPane, "cell 0 1 2 1,grow");

		textArea = new JTextArea();
		textArea.setEditable(isInput);
		textArea.setLineWrap(true);
		scrollPane.setViewportView(textArea);

		if (isInput) {
			textArea.addKeyListener(new KeyListener() {

				@Override
				public void keyPressed(KeyEvent arg0) {
				}

				@Override
				public void keyReleased(KeyEvent arg0) {
				}

				@Override
				public void keyTyped(KeyEvent arg0) {
					if (arg0.getKeyChar() == '$') {
						String[] commands = textArea.getText().split("\\$");
						Interface.realmachine.channeldevice.input(commands[commands.length - 1]);
						arg0.consume();
						textArea.append("$\n");
					}

				}
			});
		}

		JButton btnNewButton = new JButton("Clear");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				textArea.setText("");
			}
		});
		add(btnNewButton, "cell 1 0");
	}
	
	public void appendOutput(String s){
		textArea.append(">"+s+"\n");
	}

}
