package nettyLearning;

import java.nio.ByteBuffer;

public class TestByteBufferReadAndWrite {

	public static void main(String[] args) {
		ByteBuffer bf = ByteBuffer.allocate(10);
		bf.put((byte)(0x61));
		
		ByteBufferUtil bbu = new ByteBufferUtil();
		
		bbu.debugAll(bf);
		bf.put(new byte[]{(byte)0x62,(byte)0x63,(byte)0x64});
		bbu.debugAll(bf);
		
		bf.flip();
		bbu.debugAll(bf);
		System.out.println((char)bf.get());
		System.out.println((char)bf.get());
		
		
		bf.compact();
		
		bbu.debugAll(bf);
		bf.put(new byte[]{(byte)0x65,(byte)0x66,(byte)0x67});
		bbu.debugAll(bf);
		
		
	}

}
