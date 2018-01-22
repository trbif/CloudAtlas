package cloud.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpHost;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultProxyRoutePlanner;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpConnector.class);
    
    
    private String url;
    private String method;
    private String data;
    private String charset = "UTF-8";
    
    private String proxyIP;
    private int proxyPort;
    
    private static PoolingHttpClientConnectionManager conmgr = null;

    static {
        conmgr = new PoolingHttpClientConnectionManager();
        conmgr.setMaxTotal(512);
        conmgr.setDefaultMaxPerRoute(100);

        LOGGER.info("http client pool init : {}", conmgr.toString());
    }
   
    public HttpConnector(String url) {
        this.url = url;
    }
    
    public HttpConnector(String url, String charset){
        this.url = url;
        this.charset = charset;
    }

    
    public void setMethod(String method) {
        this.method = method;
    }
    
    public void setData(String data) {
        this.data = data;
    }
    
    public void setProxyIP(String proxyIP) {
        this.proxyIP = proxyIP;
    }
    
    public void setProxyPort(int proxyPort) {
        this.proxyPort = proxyPort;
    }
    
    public String request() {
        String result = "";
        
        CloseableHttpClient client = null;
        BufferedReader br = null;
        InputStreamReader isr = null;
        InputStream is = null;
        CloseableHttpResponse response = null;
        HttpRequestBase http = null;
        
        try {
            if(proxyIP!=null&&proxyPort!=0){
            	HttpHost proxy = new HttpHost(proxyIP,proxyPort,"http");
            	DefaultProxyRoutePlanner routePlanner = new DefaultProxyRoutePlanner(proxy); 
            	client = HttpClients.custom().setConnectionManager(conmgr).setRoutePlanner(routePlanner).build();
            }
            else
                client = HttpClients.custom().setConnectionManager(conmgr).build();
            if ("POST".equals(method) || null != data) {
                http = new HttpPost(url);
                if (null != data) {
                    ((HttpPost) http).setEntity(new StringEntity(data, "UTF-8"));
                }
            } else if ("GET".equals(method)) {
                http = new HttpGet(url);
            } else if("DELETE".equals(method)){
                http = new HttpDelete(url);
            } else if("HEAD".equals(method)){
                http = new HttpHead(url);
            }
            
            StringBuffer sb = new StringBuffer();
            response = client.execute(http);
            
            HttpEntity entity = response.getEntity();
            is = entity.getContent();
            isr = new InputStreamReader(is, charset);
            br = new BufferedReader(isr);
            
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            
            result = sb.toString();
        } catch (Exception e) {
        	result = "es";
        	LOGGER.error("client execute exception", e);
        	return "";
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                
                if (isr != null) {
                    isr.close();
                }
                
                if (is != null) {
                    is.close();
                }
                
                if (http != null) {
                    http.releaseConnection();
                }
                
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                LOGGER.error("io stream close exception", e);
            }
        }
我:
        
        return result;
    }
    
    /**
     * 获取访问者IP
     * 
     * 在一般情况下使用Request.getRemoteAddr()即可，但是经过nginx等反向代理软件后，这个方法会失效。
     * 
     * 本方法先从Header中获取X-Real-IP，如果不存在再从X-Forwarded-For获得第一个IP(用,分割)，
     * 如果还不存在则调用Request .getRemoteAddr()。
     * 
     * @param request
     * @return
     */
//public static String getIpAddr(HttpServletRequest request) throws Exception {
//        String ip = request.getHeader("X-Real-IP");
//        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
//            return ip;
//        }
//        ip = request.getHeader("X-Forwarded-For");
//        if (!StringUtils.isBlank(ip) && !"unknown".equalsIgnoreCase(ip)) {
//            // 多次反向代理后会有多个IP值，第一个为真实IP。
//            int index = ip.indexOf(',');
//            if (index != -1) {
//                return ip.substring(0, index);
//            } else {
//                return ip;
//            }
//        } else {
//            return request.getRemoteAddr();
//        }
//    }
    
    
    public static String getUa(HttpServletRequest request){
        return request.getHeader("User-Agent");
    }
}