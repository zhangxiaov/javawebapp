package webApp.http;

import java.util.Arrays;

/**
 * 
 * @author zhangxinwei
 *
 */

public class ByteReader {
	private byte[] bytes = null;
	private int index = 0;
	
	public ByteReader(byte[] bytes) {
		this.bytes = bytes;
	}
	
	
	/**
	 * 跳过空格\r\n
	 * @return
	 */
	public int curIndex() {
		while(bytes[index] == '\r' || bytes[index] == '\n') {
			++index;
		}
		
		return index;
	}
	
	public byte[] readLine() {
		if(index >= bytes.length)
			return null;
		
		int start = 0;
		int end = 0;
		
		for(;;) {
			if(bytes[index] != '\r' && bytes[index] != '\n') {
				++index;
			}else {
				end = index;
				
				while(bytes[index] == '\r') {
					++index;
				}
				if(bytes[index] == '\n') {
					++index;
				}
				break;
			}
				
		}
		return Arrays.copyOfRange(bytes, start, end);
	}
	
	/**
	 * bytes position in self,same string indexOf
	 * @param self
	 * @param bytes
	 * @return
	 */
	public static int indexOf(byte[] self, byte[] bytes) {
		if(self == null || bytes == null)
			return -1;
		
		for (int i = 0; i < self.length; i++) {
			if(bytes[0] == self[i]) {
				for (int j = 1; j < bytes.length; j++) {
					if(bytes[j] != self[i+j])
						break;
				}
				return i;
			}
		}
		
		return -1;
	}
	
	
	/**
	 * 分成俩串,滤空格\r\n
	 * @param self
	 * @param c
	 * @return
	 */
	public static String[] kvSplit(byte[] self, char c) {
		int index = 0;
		for (int i = 0; i < self.length; i++) {
			if(c == self[i]) {
				index = i;
				break;
			}
		}
		
		String[] kv = new String[2];
		// key
		int from = 0;
		int to = 0;
		for (int i = 0; i < index; i++) {
			if(self[i] == ' ') {
			}else {
				from = i;
				break;
			}
		}
		for (int i = index - 1; i > from; i--) {
			if(self[i] == ' ') {

			}else {
				to = i + 1;
				break;
			}
		}
		kv[0] = new String(Arrays.copyOfRange(self, from, to));
		
		// val
		int from2 = 0;
		int to2 = 0;
		for (int i = index + 1; i < self.length; i++) {
			if(self[i] == ' ') {
			}else {
				from2 = i;
				break;
			}
		}
		for (int i = self.length - 1; i > from2; i--) {
			if(self[i] == ' ') {

			}else {
				to2 = i + 1;
				break;
			}
		}
		kv[1] = new String(Arrays.copyOfRange(self, from2, to2));
		
		return kv;
	}
	
	/**
	 * 分成数组，滤空格\r\n
	 * @param self
	 * @param c
	 * @return
	 */
	public static String[] split(byte[] self, char c) {
		int count = 0;
		
		class list {
			int val = 0;
			list prev = null;
			list self = null;
			
			list put(int i) {
				list l = new list();
				l.val = i;
				l.prev = this;
				l.self = l;
				return l;
			}
			
			int get() {
				int i = self.val;
				self = self.prev;
				return i;
			}
		}
		
		list l = new list();
		for (int i = 0; i < self.length; i++) {
			if(c == self[i]) {
				l = l.put(i);
				++count;
			}
		}
		
		int t = self.length - 1;
		int index = 0;

		String[] ss = new String[count+1];
		for (int i = count; i >= 0; i--) {
			
//			index = l.get();
			if(i == 0)
				index = -1;
			else
				index = l.get();
			
			int from = 0;
			int to = 0;
			for (int j = index+1; j < t; j++) {
				if(self[j] == ' ') {
					
				}else {
					from = j;
					break;
				}
			}
			for (int j = t; j > from; j--) {
				if(self[j] == ' ') {
					
				}else {
					to = j+1;
					break;
				}
			}
			
			t = index;
			
			ss[i] = new String(Arrays.copyOfRange(self, from, to));
		}
						
		return ss;
	}
}
