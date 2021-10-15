package nettyLearning;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class TestByteBufferString {

	public static void main(String[] args) {
		// 
		ByteBuffer bf = ByteBuffer.allocate(16);
		
		//1.轉成自符串
		bf.put("hello".getBytes());
		ByteBufferUtil bbutil = new ByteBufferUtil();
		bbutil.debugAll(bf);
		
		//2.轉成自符串
		ByteBuffer bf2 = StandardCharsets.UTF_8.encode("hello"); 
		bbutil.debugAll(bf2);
		
		//3.轉成自符串
		ByteBuffer bf3 = ByteBuffer.wrap("hello".getBytes());
		bbutil.debugAll(bf3);

		
		//4.轉成自符串
		System.out.println(StandardCharsets.UTF_8.decode(bf2).toString());
		
		//執行 bf 還是要使用 flip 讓他從頭讀取
		bf.flip();
		System.out.println(StandardCharsets.UTF_8.decode(bf).toString());
	}
	
	

}
