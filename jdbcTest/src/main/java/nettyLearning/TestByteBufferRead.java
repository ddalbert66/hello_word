package nettyLearning;

import java.nio.ByteBuffer;

public class TestByteBufferRead {

	public static void main(String[] args) {
		ByteBuffer bf = ByteBuffer.allocate(10);
		bf.put(new byte[]{(byte)0x62,(byte)0x63,(byte)0x64,(byte)0x65});
		
		bf.flip();
		
		System.out.println((char)bf.get());
		System.out.println((char)bf.get());
		System.out.println((char)bf.get());
		ByteBufferUtil bbutil = new ByteBufferUtil();
		bbutil.debugAll(bf);
		
		// 透過 bf.rewind() 重頭讀取
		bf.rewind();
		bbutil.debugAll(bf);
		System.out.println((char)bf.get());
		System.out.println((char)bf.get());
		System.out.println((char)bf.get());
		bf.rewind();
		
		//mark reset
		bbutil.debugAll(bf);
		System.out.println((char)bf.get());
		System.out.println((char)bf.get());
		//標記
		bf.mark();
		bbutil.debugAll(bf);
		System.out.println((char)bf.get());
		System.out.println((char)bf.get());
		bbutil.debugAll(bf);
		//重置到mark位置
		bf.reset();
		bbutil.debugAll(bf);
		bf.rewind();
		
		
		//bf.get(i) 不會影響讀寫位置
		System.out.println((char)bf.get(1));
		bbutil.debugAll(bf);
		
		
	}

}
