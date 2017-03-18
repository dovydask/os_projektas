import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;

public class Word{
	public static int SIZE = 4;
	private byte[] data;
	
	public Word(){
		data = new byte[SIZE];
	}

	public byte getByte(int index){
		return data[index];
	}
	
	public byte[] getBytes() {
		return Arrays.copyOf(data, SIZE);
	}

	public void setByte(int index, byte info){
		data[index] = info;
	}
	
	public static int wordToInt(Word word){
		ByteBuffer buff = ByteBuffer.allocateDirect(SIZE);
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.clear();
		for(int i=0; i<SIZE; i++){
			buff.put(word.getByte(i));
		}
		buff.position(0);
		return buff.getInt();
	}

	public static Word intToWord(int value) {
		ByteBuffer buff = ByteBuffer.allocateDirect(SIZE);
		buff.order(ByteOrder.LITTLE_ENDIAN);
		buff.clear();
		buff.putInt(value);
		Word word = new Word();
		for(int i=0; i<SIZE; i++){
			word.setByte(i, buff.get(i));
		}
		return word;
	}
}