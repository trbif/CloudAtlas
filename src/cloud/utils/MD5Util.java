package cloud.utils;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MD5Util {
    private static final Logger LOGGER = LoggerFactory.getLogger(MD5Util.class);
	public static String toMD5(String plainText) {
	    StringBuffer buf = new StringBuffer("");
		try {
		    //生成实现指定摘要算法的 MessageDigest 对象。
		    MessageDigest md = MessageDigest.getInstance("MD5"); 
		    //使用指定的字节数组更新摘要。
		    md.update(plainText.getBytes("UTF-8"));
		    //通过执行诸如填充之类的最终操作完成哈希计算。
		    byte b[] = md.digest();
		    //生成具体的md5密码到buf数组
		    int i;
		    for (int offset = 0; offset < b.length; offset++) {
		    	i = b[offset];
			    if (i < 0)
			    	i += 256;
			    if (i < 16)
			    	buf.append("0");
			    buf.append(Integer.toHexString(i));
		    }
		    LOGGER.debug("16位: " + buf.toString().substring(8, 24));
		} 
		catch (Exception e) {
			e.printStackTrace();
	   	}
	    return buf.toString().substring(8, 24);
	}
	
	public static String toMD5_32(String plainText) {
	    StringBuffer buf = new StringBuffer("");
		try {
		    //生成实现指定摘要算法的 MessageDigest 对象。
		    MessageDigest md = MessageDigest.getInstance("MD5"); 
		    //使用指定的字节数组更新摘要。
		    md.update(plainText.getBytes("UTF-8"));
		    //通过执行诸如填充之类的最终操作完成哈希计算。
		    byte b[] = md.digest();
		    //生成具体的md5密码到buf数组
		    int i;
		    for (int offset = 0; offset < b.length; offset++) {
		    	i = b[offset];
			    if (i < 0)
			    	i += 256;
			    if (i < 16)
			    	buf.append("0");
			    buf.append(Integer.toHexString(i));
		    }
		    LOGGER.debug("32位: " + buf.toString());
		} 
		catch (Exception e) {
			e.printStackTrace();
	   	}
	    return buf.toString();
	}
	
	public static String replaceBlank(String str){
        String dest = null;
        if(str == null){
            return dest;
        }else{
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
            return dest;
        }
    }
}
