package cloud.crawler;

我:
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import cn.nubia.bean.BaseBean;
import cn.nubia.bean.FeedbackInfo;
import cn.nubia.bean.sql.FeedbackBean;
import cn.nubia.crawler.parser.XMFeedbackParser;
import cn.nubia.service.FeedbackService;
import cn.nubia.utils.HttpConnector;
import cn.nubia.utils.MD5Util;
import cn.nubia.utils.NubiaConfigInfo;

/**
 * @author zhangqi
 * @date 2017年11月29日
 * @说明:
 */
我:
public class XMFeedbackCrawler implements FeedBackCrawler,InitializingBean{
	
    private final static Logger LOGGER = LoggerFactory.getLogger(XMFeedbackCrawler.class);

	public final static String[] FEEDBACK_TYPE = {
			"通讯服务 > 电话:90",
			"通讯服务 > 短信:92",
			"通讯服务 > 联系人:91",
			"通讯服务 > 米Sim:638",
			"通讯服务 > 免费电话:531",
			"通讯服务 > 全球上网:544",
			"通讯服务 > 来电留言:751",
			"通讯服务 > 小米电话加油包 :572",
			"通讯服务 > 视频电话:582",
			"桌面主题 > 桌面:95",
			"桌面主题 > 锁屏:96",
			"桌面主题 > 主题:98",
			"桌面主题 > 分屏:718",
			"桌面主题 > 状态栏:97",
			"桌面主题 > 锁屏画报:607",
			"桌面主题 > 人脸解锁:744",
			"桌面主题 > 全面屏手势:755",
			"桌面主题 > 主题编辑器:179",
			"桌面主题 > 最近任务:641",
			"桌面主题 > 信息助手:681",
			"桌面主题 > 虚拟按键:742",
			"多媒体应用 > 相机:101",
			"多媒体应用 > 相册:99",
			"多媒体应用 > 音乐:100",
			"多媒体应用 > 视频:204",
			"系统应用 > 设置:110",
			"系统应用 > 浏览器:102",
			"系统应用 > 传送门:722",
			"系统应用 > 应用商店:103",
			"系统应用 > 小米生活:419",
我:
			"系统应用 > 小米快传:562",
			"系统应用 > 游戏中心:205",
			"系统应用 > 下载管理:405",
			"系统应用 > NFC功能:415",
			"系统应用 > 门卡模拟:758",
			"系统应用 > 小米支付:651",
			"系统应用 > 小米公交:652",
			"系统应用 > 用户反馈App:514",
			"系统应用 > 定制输入法:335",
			"实用工具 > 天气:106",
			"实用工具 > 便签:236",
			"实用工具 > 日历:407",
			"实用工具 > 时钟:141",
			"实用工具 > 录音机:235",
			"实用工具 > 手电筒:403",
			"实用工具 > 收音机:404",
			"实用工具 > 指南针:402",
			"实用工具 > 计算器:401",
			"实用工具 > 文件管理:140",
			"实用工具 > 电子邮件:234",
			"实用工具 > 语音助手:196",
			"实用工具 > 小米运动:564",
			"实用工具 > 小说之王:736",
			"实用工具 > 米家:588",
			"实用工具 > 万能遥控:602",
			"实用工具 > 扫一扫:506",
			"实用工具 > 悬浮球:620",
			"实用工具 > 远程协助:623",
			"实用工具 > 长截图:624",
			"实用工具 > 全局搜索:637",
			"实用工具 > 屏幕录制:692",
			"实用工具 > 直达服务:702",
			"安全中心 > 授权管理:112",
			"安全中心 > 网络助手:172",
			"安全中心 > 病毒扫描:223",
			"安全中心 > 骚扰拦截:94",
			"安全中心 > 省电优化:388",
			"安全中心 > 垃圾清理:400",
			"安全中心 > 应用锁:224",
			"安全中心 > 勿扰模式:553",
			"安全中心 > 手机分身:621",
			"安全中心 > 应用双开:622",
			"安全中心 > 支付安全:632",
			"安全中心 > 游戏加速:680",
			"安全中心 > 优化加速:704",
			"安全中心 > 应用管理:723",
			"安全中心 > 安全中心体验:640",
			"安全中心 > 小米红包助手:688",
			"系统与底层 > 重启:513",
			"系统与底层 > 耗电:512",
			"系统与底层 > 发热:511",
			"系统与底层 > 卡顿:510",
			"系统与底层 > 音频:648",
			"系统与底层 > 蓝牙:649",
			"系统与底层 > 按键:655",
			"系统与底层 > 指纹解锁:650",
			"系统与底层 > 指纹支付:664",
			"系统与底层 > 充电异常:639",
			"系统与底层 > 运行内存:574",
			"系统与底层 > 存储/SD卡:603",//与重启重合
			"系统与底层 > 底层驱动:113",
			"系统与底层 > Modem:635",
			"系统与底层 > GPS信号:642",
			"系统与底层 > 网络位置服务:693",
			"网络与升级 > 本地备份:104",
			"网络与升级 > WIFI故障:104",
			"网络与升级 > OTA升级:108",
			"网络与升级 > 桌面图标云备份:383",
			"第三方应用 > 第三方应用:107",
			"其它 > 其它:111"};
我:
    private static Map<String,Integer> proxyMap = new HashMap<>();
    
    private final static String UNICODE = "utf-8";
    
    private final static String BASE_URL = "http://www.miui.com/forum.php?mod=bugfeedback&fid=(TYPE)&page=(PAGE)";
    //http://www.miui.com/forum.php?mod=bugfeedback&fid=16&page=1
    

    @Autowired
    private NubiaConfigInfo configInfo;
    
    private FeedbackService feedbackService;
    
    


    /**
	 * @param feedbackService the feedbackService to set
	 */
	public XMFeedbackCrawler setFeedbackService(FeedbackService feedbackService) {
		this.feedbackService = feedbackService;
		return this;
	}
我:
    
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		String proxyIpHost = configInfo.getProxyIpHost();
		String[] proxyIpHostArr = proxyIpHost.split(",");
		for(String members:proxyIpHostArr){
			String[] membersArr = members.split(":");
			if(membersArr.length!=2){
				LOGGER.warn("该代理异常：{}",membersArr.toString());
				continue;
			}
			int port = Integer.valueOf(membersArr[1]);
			if(port<=0){
				continue;
			}
			proxyMap.put(membersArr[0], Integer.valueOf(membersArr[1]));
		}
	}
我:
	private String replace(String str){
		return str.replaceAll("<title>.*</title>", "").replaceAll("page=\\d{1,}", "").replaceAll("STYLEID.*static/js/mobile/common.js", "");
	}
    
	/* (non-Javadoc)
	 * @see cn.nubia.crawler.CrawlerBase#getComments()
	 */
	@Override
	public String seizeComments(String type) {
		// TODO Auto-generated method stub
		String[] typeInfo = type.split(":");
		int page = 1;
		String result = "";
		String baseUrl = BASE_URL;
		String verifyMD5 = "";
		while(true){
			String finalUrl = baseUrl.replace("(TYPE)", typeInfo[1]).replace("(PAGE)", page+"");
		    HttpConnector requestOSInfo = new HttpConnector(finalUrl, UNICODE);
	        requestOSInfo.setMethod("GET");
	        result = requestOSInfo.request();
	        String currentMD5 = MD5Util.toMD5(replace(result));
	        LOGGER.debug("verifyMD5: "+verifyMD5);
	        LOGGER.debug("currentMD5: "+currentMD5);
	        if(verifyMD5.equals(currentMD5)){
	        	break;
	        }
我:
	        verifyMD5 = currentMD5;
	        XMFeedbackParser parser = new XMFeedbackParser();
	        List<FeedbackInfo> fbList = parser.getUrlList(result);
	        for(FeedbackInfo fbInfo:fbList){
	        	String finalUrlItems = fbInfo.getHref();
			    HttpConnector requestOSInfoItems = new HttpConnector(finalUrlItems, UNICODE);
			    requestOSInfoItems.setMethod("GET");
		        result = requestOSInfoItems.request();
	        	fbInfo.setPhoneModule(typeInfo[0]);
		        List<FeedbackBean> list = parser.parse(result,fbInfo);
		        feedbackService.saveBatch(list);
	        }
	        page++;
		}
		return result;
	}
我:
	public String toJson(List<FeedbackBean> listAll) {
		// TODO Auto-generated method stub
    	JSONArray arr = new JSONArray();
    	for(BaseBean c:listAll){
    		JSONObject obj;
			try {
					obj = JSONObject.parseObject((c).toString());
					arr.add(obj);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    	return arr.toString();
	}
我:
	public String addToSql(List<FeedbackBean> listAll) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see cn.nubia.crawler.CrawlerBase#analyzeComments(java.lang.Object)
	 */
	@Override
	public String analyzeComments(String type) {
		// TODO Auto-generated method stub
		return null;
	}

}
