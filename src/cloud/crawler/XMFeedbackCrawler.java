package cloud.crawler;

��:
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
 * @date 2017��11��29��
 * @˵��:
 */
��:
public class XMFeedbackCrawler implements FeedBackCrawler,InitializingBean{
	
    private final static Logger LOGGER = LoggerFactory.getLogger(XMFeedbackCrawler.class);

	public final static String[] FEEDBACK_TYPE = {
			"ͨѶ���� > �绰:90",
			"ͨѶ���� > ����:92",
			"ͨѶ���� > ��ϵ��:91",
			"ͨѶ���� > ��Sim:638",
			"ͨѶ���� > ��ѵ绰:531",
			"ͨѶ���� > ȫ������:544",
			"ͨѶ���� > ��������:751",
			"ͨѶ���� > С�׵绰���Ͱ� :572",
			"ͨѶ���� > ��Ƶ�绰:582",
			"�������� > ����:95",
			"�������� > ����:96",
			"�������� > ����:98",
			"�������� > ����:718",
			"�������� > ״̬��:97",
			"�������� > ��������:607",
			"�������� > ��������:744",
			"�������� > ȫ��������:755",
			"�������� > ����༭��:179",
			"�������� > �������:641",
			"�������� > ��Ϣ����:681",
			"�������� > ���ⰴ��:742",
			"��ý��Ӧ�� > ���:101",
			"��ý��Ӧ�� > ���:99",
			"��ý��Ӧ�� > ����:100",
			"��ý��Ӧ�� > ��Ƶ:204",
			"ϵͳӦ�� > ����:110",
			"ϵͳӦ�� > �����:102",
			"ϵͳӦ�� > ������:722",
			"ϵͳӦ�� > Ӧ���̵�:103",
			"ϵͳӦ�� > С������:419",
��:
			"ϵͳӦ�� > С�׿촫:562",
			"ϵͳӦ�� > ��Ϸ����:205",
			"ϵͳӦ�� > ���ع���:405",
			"ϵͳӦ�� > NFC����:415",
			"ϵͳӦ�� > �ſ�ģ��:758",
			"ϵͳӦ�� > С��֧��:651",
			"ϵͳӦ�� > С�׹���:652",
			"ϵͳӦ�� > �û�����App:514",
			"ϵͳӦ�� > �������뷨:335",
			"ʵ�ù��� > ����:106",
			"ʵ�ù��� > ��ǩ:236",
			"ʵ�ù��� > ����:407",
			"ʵ�ù��� > ʱ��:141",
			"ʵ�ù��� > ¼����:235",
			"ʵ�ù��� > �ֵ�Ͳ:403",
			"ʵ�ù��� > ������:404",
			"ʵ�ù��� > ָ����:402",
			"ʵ�ù��� > ������:401",
			"ʵ�ù��� > �ļ�����:140",
			"ʵ�ù��� > �����ʼ�:234",
			"ʵ�ù��� > ��������:196",
			"ʵ�ù��� > С���˶�:564",
			"ʵ�ù��� > С˵֮��:736",
			"ʵ�ù��� > �׼�:588",
			"ʵ�ù��� > ����ң��:602",
			"ʵ�ù��� > ɨһɨ:506",
			"ʵ�ù��� > ������:620",
			"ʵ�ù��� > Զ��Э��:623",
			"ʵ�ù��� > ����ͼ:624",
			"ʵ�ù��� > ȫ������:637",
			"ʵ�ù��� > ��Ļ¼��:692",
			"ʵ�ù��� > ֱ�����:702",
			"��ȫ���� > ��Ȩ����:112",
			"��ȫ���� > ��������:172",
			"��ȫ���� > ����ɨ��:223",
			"��ȫ���� > ɧ������:94",
			"��ȫ���� > ʡ���Ż�:388",
			"��ȫ���� > ��������:400",
			"��ȫ���� > Ӧ����:224",
			"��ȫ���� > ����ģʽ:553",
			"��ȫ���� > �ֻ�����:621",
			"��ȫ���� > Ӧ��˫��:622",
			"��ȫ���� > ֧����ȫ:632",
			"��ȫ���� > ��Ϸ����:680",
			"��ȫ���� > �Ż�����:704",
			"��ȫ���� > Ӧ�ù���:723",
			"��ȫ���� > ��ȫ��������:640",
			"��ȫ���� > С�׺������:688",
			"ϵͳ��ײ� > ����:513",
			"ϵͳ��ײ� > �ĵ�:512",
			"ϵͳ��ײ� > ����:511",
			"ϵͳ��ײ� > ����:510",
			"ϵͳ��ײ� > ��Ƶ:648",
			"ϵͳ��ײ� > ����:649",
			"ϵͳ��ײ� > ����:655",
			"ϵͳ��ײ� > ָ�ƽ���:650",
			"ϵͳ��ײ� > ָ��֧��:664",
			"ϵͳ��ײ� > ����쳣:639",
			"ϵͳ��ײ� > �����ڴ�:574",
			"ϵͳ��ײ� > �洢/SD��:603",//�������غ�
			"ϵͳ��ײ� > �ײ�����:113",
			"ϵͳ��ײ� > Modem:635",
			"ϵͳ��ײ� > GPS�ź�:642",
			"ϵͳ��ײ� > ����λ�÷���:693",
			"���������� > ���ر���:104",
			"���������� > WIFI����:104",
			"���������� > OTA����:108",
			"���������� > ����ͼ���Ʊ���:383",
			"������Ӧ�� > ������Ӧ��:107",
			"���� > ����:111"};
��:
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
��:
    
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
				LOGGER.warn("�ô����쳣��{}",membersArr.toString());
				continue;
			}
			int port = Integer.valueOf(membersArr[1]);
			if(port<=0){
				continue;
			}
			proxyMap.put(membersArr[0], Integer.valueOf(membersArr[1]));
		}
	}
��:
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
��:
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
��:
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
��:
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
