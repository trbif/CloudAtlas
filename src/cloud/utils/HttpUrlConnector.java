package cloud.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author zhangqi
 * @date 2018年1月18日 下午1:40:25
 * @version V1.0
 * @说明:
 */
public class HttpUrlConnector {
	
    private final Logger LOGGER = LoggerFactory.getLogger(HttpUrlConnector.class);
    
    private String urlStr;
    private String unicode;
    private String cookie = "aliyungf_tc=AQAAADUzOQLrvgwAEjJkyp1u58fLlCho; acw_tc=AQAAAHDgdTUaUg0AEjJkyr+k1rhZdljv; focs_2132_saltkey=gSjSL6F3; focs_2132_lastvisit=1516178278; focs_2132_auth=cd5akv%2B%2BxpWwzLjfy7RI50yABbvirrNsbvmJMrzRF6qZ32CiEg; focs_2132_atarget=1; focs_2132_nofavfid=1; pgv_pvi=6323872544; pgv_info=ssi=s1113350387; focs_2132_ulastactivity=6db1azpFM3oO9OPG8FyDZZit3vYDNuY%2B9yPZIP07eU39zfXK7EVq; focs_2132_visitedfid=49D770D771; focs_2132_st_p=7625139%7C1516259710%7C263efeb3cf8aff95acd52bbd99ffea57; focs_2132_viewid=tid_1166109; focs_2132_smile=4D1; focs_2132_home_diymode=1; focs_2132_lip=202.100.50.18%2C1516259412; acw_sc__=5a606ad9770a93a571e4c32293d9c5739337259d; focs_2132_mobile=no; focs_2132_st_t=7625139%7C1516268251%7C6fd79aeadbc051c95c7d23cdd20ddcc9; focs_2132_forum_lastvisit=D_771_1516254348D_770_1516255347D_49_1516268251; focs_2132_sid=XaJp4d; focs_2132_checkpm=1; focs_2132_sendmail=1; focs_2132_noticeTitle=1; Hm_lvt_992e7e455bd7926883c661798ce17d7a=1516177560; Hm_lpvt_992e7e455bd7926883c661798ce17d7a=1516268250; focs_2132_lastact=1516268252%09connect.php%09check; focs_2132_connect_is_bind=0; SERVERID=cdeea4003cb8b15bb49bfc71a7a4b3dd|1516268252|1516265835; _ga=GA1.2.1473882126.1516177560; _gid=GA1.2.60534674.1516177560; _gat=1";
    
    private HttpUrlConnector(){}
    
    public HttpUrlConnector(String urlStr,String unicode){
    	this.urlStr = urlStr;
    	this.unicode = unicode;
    }
public HttpUrlConnector(String urlStr,String unicode,String cookie){
    	this.urlStr = urlStr;
    	this.unicode = unicode;
    	this.cookie = cookie;
    }
    
    /**
     * 接口调用 GET
     */
	public String request() {
    	BufferedReader br = null;
        try {
            URL url = new URL(urlStr);    // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
    		connection.setRequestProperty("Cookie",cookie);
            connection.connect();// 连接会话
            // 获取输入流
            br = new BufferedReader(new InputStreamReader(connection.getInputStream(), unicode));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            return sb.toString();
        } catch (Exception e) {
	    	LOGGER.error("io stream close exception", e);
        }
        return null;
    }
}