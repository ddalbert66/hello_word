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
		// 1. ��J��X�B 2.RandomAccessFile
		try (FileChannel channel = new FileInputStream("D:\\Test\\test.txt").getChannel()) {

			ByteBuffer buffer = ByteBuffer.allocate(10);
			while (true) {
				
				int len = channel.read(buffer);
				logger.debug("Ū���쪺�r�`{}",len);
				if(len == -1){
					break;
				}

				buffer.flip(); //������Ū�Ҧ�

				while (buffer.hasRemaining()) {
					byte b = buffer.get();
					logger.debug("Ū���쪺�r��{}",(char) b);
				}
				
				buffer.clear(); //�������g�Ҧ�
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
