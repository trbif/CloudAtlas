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
		    //����ʵ��ָ��ժҪ�㷨�� MessageDigest ����
		    MessageDigest md = MessageDigest.getInstance("MD5"); 
		    //ʹ��ָ�����ֽ��������ժҪ��
		    md.update(plainText.getBytes("UTF-8"));
		    //ͨ��ִ���������֮������ղ�����ɹ�ϣ���㡣
		    byte b[] = md.digest();
		    //���ɾ����md5���뵽buf����
		    int i;
		    for (int offset = 0; offset < b.length; offset++) {
		    	i = b[offset];
			    if (i < 0)
			    	i += 256;
			    if (i < 16)
			    	buf.append("0");
			    buf.append(Integer.toHexString(i));
		    }
		    LOGGER.debug("16λ: " + buf.toString().substring(8, 24));
		} 
		catch (Exception e) {
			e.printStackTrace();
	   	}
	    return buf.toString().substring(8, 24);
	}
	
	public static String toMD5_32(String plainText) {
	    StringBuffer buf = new StringBuffer("");
		try {
		    //����ʵ��ָ��ժҪ�㷨�� MessageDigest ����
		    MessageDigest md = MessageDigest.getInstance("MD5"); 
		    //ʹ��ָ�����ֽ��������ժҪ��
		    md.update(plainText.getBytes("UTF-8"));
		    //ͨ��ִ���������֮������ղ�����ɹ�ϣ���㡣
		    byte b[] = md.digest();
		    //���ɾ����md5���뵽buf����
		    int i;
		    for (int offset = 0; offset < b.length; offset++) {
		    	i = b[offset];
			    if (i < 0)
			    	i += 256;
			    if (i < 16)
			    	buf.append("0");
			    buf.append(Integer.toHexString(i));
		    }
		    LOGGER.debug("32λ: " + buf.toString());
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
