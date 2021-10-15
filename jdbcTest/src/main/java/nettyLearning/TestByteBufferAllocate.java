package nettyLearning;

import java.nio.ByteBuffer;

public class TestByteBufferAllocate {

	public static void main(String[] args) {
		
		System.out.println(ByteBuffer.allocate(16).getClass());
		System.out.println(ByteBuffer.allocateDirect(16).getClass());
		
//		class java.nio.HeapByteBuffer   使用java內存 讀寫效能較差(因為多了一個複製的步驟)  受到java gc影響
//		class java.nio.DirectByteBuffer  直接使用系統內存 讀寫效能較好  不受到gc影響  分配內存效率較差

	}

}
