package nettyLearning;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.Log4J2LoggerFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChannelBuffer {

	protected static InternalLogger logger = Log4J2LoggerFactory.getInstance(ChannelBuffer.class);
	public static void main(String[] args) {

		// FileChannel
		// 1. 輸入輸出劉 2.RandomAccessFile
		try (FileChannel channel = new FileInputStream("D:\\Test\\test.txt").getChannel()) {

			ByteBuffer buffer = ByteBuffer.allocate(10);
			while (true) {
				
				int len = channel.read(buffer);
				logger.debug("讀取到的字節{}",len);
				if(len == -1){
					break;
				}

				buffer.flip(); //切換成讀模式

				while (buffer.hasRemaining()) {
					byte b = buffer.get();
					logger.debug("讀取到的字元{}",(char) b);
				}
				
				buffer.clear(); //切換成寫模式
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
