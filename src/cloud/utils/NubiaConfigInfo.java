package cloud.utils;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;

@Component("configInfo")
public class NubiaConfigInfo {

    @Value("${proxy_ip_host_list}")
    private String proxyIpHost;
    
    public void setProxyIpHost(String proxyIpHost) {
        this.proxyIpHost = proxyIpHost;
    }

    public String getProxyIpHost() {
        return proxyIpHost;
    }


}
