import java.io.*;
import java.io.IOException;

public class InputDevice {

    public String doInput( ) {
		InputStreamReader isr = new InputStreamReader ( System.in );
		BufferedReader br = new BufferedReader ( isr );
		String inputText = "";
		try {
			inputText = br.readLine();
		}
		catch (IOException e) {
		    System.exit( 0 );
		}
		return inputText;
	};

}
