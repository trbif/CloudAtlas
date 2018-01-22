package cloud.crawler;

我:
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import cn.nubia.bean.BaseBean;
import cn.nubia.bean.CommentsInfo;
import cn.nubia.bean.sql.CommentsBean;
import cn.nubia.crawler.parser.JDCommentsParser;
import cn.nubia.service.CommentsService;
import cn.nubia.utils.Constants;
import cn.nubia.utils.HttpConnector;
import cn.nubia.utils.NubiaConfigInfo;
import w2v.classify.W2VClassifier;
我:
@Component
public class JDCommentsCrawler implements CommentsCrawler,InitializingBean{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(JDCommentsCrawler.class);
	
	public final static String[] COMMENTSTYPE = {"0","4","5","3","2","1"};

    public static Map<String,Integer> proxyMap = new HashMap<>();
    
    private final static String RESOURCE = "京东";
    
    private final static String LOCALHOST = "localhost";
    
    private final static String UNICODE = "gbk";
    //sorttype 6t 5r
    private final static String URLMODEL = "https://club.jd.com/comment/skuProductPageComments.action?callback=fetchJSON_comment98vv33255&productId=(PRODUCTID)&score=(TYPE)&sortType=6&page=(PAGE)&pageSize=10&isShadowSku=0&fold=1";
    
    public final static String OUT_FILENAME_MODEL = "jd_type_(type)_productID_(productID).out";
    private String productID;
    private String productModule = "";
    private String imgUrl = "";
    private String productBrand = "";
    
    private int startPage = 1;
    
    private CommentsService commentsService;
    
    private String key;
我:
    /**
	 * @param startPage the startPage to set
	 */
	public JDCommentsCrawler setStartPage(int startPage) {
		this.startPage = startPage;
		return this;
	}
	
    /**
	 * @param storeID the storeID to set
	 */
	public JDCommentsCrawler setCommentsService(CommentsService commentsService) {
		this.commentsService = commentsService;
		return this;
	}

	/**
	 * @param productID the productID to set
	 */
	public JDCommentsCrawler setProductID(String productID) {
		this.productID = productID;
		return this;
	}
	/**
	 * @param productModule the productModule to set
	 */
	public JDCommentsCrawler setProductModule(String productModule) {
		this.productModule = productModule;
		return this;
	}
我:
	/**
	 * @param imgUrl the imgUrl to set
	 */
	public JDCommentsCrawler setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
		return this;
	}

	/**
	 * @param productBrand the productBrand to set
	 */
	public JDCommentsCrawler setProductBrand(String productBrand) {
		this.productBrand = productBrand;
		return this;
	}

	/**
	 * @param productBrand the productBrand to set
	 */
	public JDCommentsCrawler setKey(String key) {
		this.key = key;
		return this;
	}
    
    @Autowired
    private NubiaConfigInfo configInfo;
    
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
	@Override
	public String analyzeComments(String type){
		// TODO Auto-generated method stub
		if(productID==null)
			return "set店铺和商品id";
		String currentUrlModel = URLMODEL.replace("(PRODUCTID)", productID);
		int page = startPage;
		String result = "";
		Iterator iter = proxyMap.entrySet().iterator();
		String proxyIP = LOCALHOST;
		int proxyPort = 0;
		PrintWriter writer = null;
    	try {
    		String outPath = Constants.WEB_COMMENTS_OUT_PATH+key+"/";
    		File dir = new File(outPath);
    		if(!dir.exists())
    			dir.mkdirs();
			writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(outPath+OUT_FILENAME_MODEL.replace("(type)", type).replace("(productID)", productID)),"utf-8"));
	    	W2VClassifier w2v = new W2VClassifier.Builder(key).build();
			while(true){
				String finalUrl = currentUrlModel.replace("(TYPE)", type).replace("(PAGE)", page+"");
			    HttpConnector requestOSInfo = new HttpConnector(finalUrl, UNICODE);
			    if(!proxyIP.equals(LOCALHOST)){
			    	requestOSInfo.setProxyIP(proxyIP);
			    	requestOSInfo.setProxyPort(proxyPort);
			    }
		        requestOSInfo.setMethod("GET");
		        result = requestOSInfo.request();
		        CommentsParser parser = new JDCommentsParser();
		        CommentsInfo info = new CommentsInfo(RESOURCE);
		        info.setProductModel(productModule);
		        info.setBrand(productBrand);
		        info.setImgUrl(imgUrl);
		        info.setProductID(productID);
我:
		        try {
		        	List<CommentsBean> listPart = parser.parse(result,info);
					if(listPart==null||listPart.size()<=0){
						if(iter.hasNext()){
							Map.Entry entry = (Map.Entry) iter.next();
							proxyIP = (String) entry.getKey();
							proxyPort = (int) entry.getValue();
						}
						else
							break;
						continue;
					}
					for(CommentsBean bean:listPart){
						try {
							String content = bean.getContent().trim();
							String out = w2v.classifiedJudgement(bean.getContent().trim());
							writer.println(content+"\t"+out);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
//						try {
//							String[] content = bean.getContent().trim().split("[\\pP\\pS\\pZ]");
//							for(int i=0;i<content.length;i++){
//								if(content[i].trim().equals(""))
//									continue;
//								String out = w2v.classifiedJudgement(bean.getContent().trim());
//								writer.println(content[i]+"\t"+out);
//							}
//						} catch (IOException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
					}
我:
//		        	commentsService.saveCommentsList(listPart);
//		        	if(thisTypeCount>=crawlHowMany)
//		        		break;
				} catch (JSONException e) {
					// TODO Auto-generated catch block
			    	LOGGER.info("JSONException: "+e);
				}
		        page++;
			}
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			if(writer!=null){
				writer.close();
			}
			e1.printStackTrace();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			if(writer!=null){
				writer.close();
			}
			e1.printStackTrace();
		}
		return result;
	}
我:
	/* (non-Javadoc)
	 * @see cn.nubia.crawler.CrawlerBase#seizeComments(java.lang.Object)
	 */
	@Override
	public String seizeComments(String type) {
		// TODO Auto-generated method stub
		String currentUrlModel = URLMODEL.replace("(PRODUCTID)", productID);
		int page = 1;
		String result = "";
		Iterator iter = proxyMap.entrySet().iterator();
//		String proxyIP = LOCALHOST;
//		int proxyPort = 0;
		int size = 0;
		while(true){
			String finalUrl = currentUrlModel.replace("(TYPE)", type).replace("(PAGE)", page+"");
		    HttpConnector requestOSInfo = new HttpConnector(finalUrl, UNICODE);
//		    if(!proxyIP.equals(LOCALHOST)){
//		    	requestOSInfo.setProxyIP(proxyIP);
//		    	requestOSInfo.setProxyPort(proxyPort);
//		    }
	        requestOSInfo.setMethod("GET");
	        result = requestOSInfo.request();
	        CommentsParser parser = new JDCommentsParser();
	        CommentsInfo info = new CommentsInfo(RESOURCE);
	        info.setProductModel(productModule);
	        info.setBrand(productBrand);
	        info.setImgUrl(imgUrl);
	        info.setProductID(productID);
我:
	        try {
	        	List<CommentsBean> listPart = parser.parse(result,info);
				if(listPart==null||listPart.size()<=0){
//					try {
//						Thread.currentThread().sleep(10000);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}; 
					if(iter.hasNext()){
						Map.Entry entry = (Map.Entry) iter.next();
//						proxyIP = (String) entry.getKey();
//						proxyPort = (int) entry.getValue();
					}
					else
						break;
//					continue;
					return page+"";
				}
				size += listPart.size();
	        	commentsService.saveBatch(listPart);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
		    	LOGGER.info("JSONException: "+e);
			}
	        page++;
		}
		System.out.println(size);
		return page+"";
	}