package cloud.crawler.parser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cloud.bean.FeedbackInfo;
import cloud.bean.sql.FeedbackBean;
import cloud.utils.MD5Util;

/**
 * @author zhangqi
 * @date 2017年11月29日
 * @说明:
 */
public class XMFeedbackParser{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(XMFeedbackParser.class);
    private final static String RESOURCE = "小米";

	public List<FeedbackInfo> getUrlList(String html) {
		// TODO Auto-generated method stub
    	List<FeedbackInfo> list = new ArrayList<>();
        Document document = Jsoup.parse(html);
    	Elements items = document.getElementsByTag("li");
    	for(Element ele:items){
    		String title = ele.getElementsByTag("a").get(0).text();
    		String href = ele.getElementsByTag("a").get(0).attr("href");
    		Pattern r = Pattern.compile("(.*)tid=([0-9]{1,})(.*)");
    	    Matcher m = r.matcher(href);
    		//thread-10987617-1-1.html
    	    if (m.find( ))
    	    	href = "http://www.miui.com/"+"thread-"+m.group(2)+"-1-1.html";
//    		String nickname = ele.getElementsByClass("col_name").text();
    	    String publishDate = "";
    	    if(ele.getElementsByClass("num").text().split("\\/").length==3)
    	    	publishDate = ele.getElementsByClass("num").text().split("\\/")[1];
//    		String status = ele.getElementsByClass("col_status").text();
//    		LOGGER.debug(title);
//    		LOGGER.debug(href);
//    		LOGGER.debug(nickname);
//    		LOGGER.debug(date);
//    		LOGGER.debug(status);
    		FeedbackInfo info = new FeedbackInfo(RESOURCE);
    		info.setHref(href);
    		info.setTitle(title);
    		info.setPublishDate(publishDate);
    		list.add(info);
    	}
		return list;
	}
	public List<FeedbackBean> parse(String html, FeedbackInfo entity) {
		// TODO Auto-generated method stub
		List<FeedbackBean> list = new ArrayList<>();
		
	    Document document = Jsoup.parse(html);
	    Elements divs = document.getElementsByClass("bug_desc");
	    if(divs.size()<6)
	    	return list;
		String publishDate = entity.getPublishDate();
		String href = entity.getHref();
		String title = entity.getTitle();
		String module = entity.getPhoneModule();
		String phoneModel = divs.get(0).text();
		String version = divs.get(1).text();
		String uIversion = divs.get(3).text();
		String demandDescription = divs.get(4).text();
		String demandScene = divs.get(5).text();
	    
	    FeedbackBean bean = new FeedbackBean();
	    bean.setDemandDescription(demandDescription);
	    bean.setHref(href);
	    bean.setTitle(title);
	    bean.setModule(module);
	    bean.setInfoSource(RESOURCE);
	    bean.setCrawlTime(Calendar.getInstance().getTimeInMillis()+"");
	    bean.setPublishDate(publishDate);
	    bean.setDemandDescription(demandDescription);
	    bean.setDemandScene(demandScene);
	    bean.setVersion(version);
	    bean.setUIversion(uIversion);
	    bean.setPhoneModel(phoneModel);
	    bean.setMd5(MD5Util.toMD5(RESOURCE+demandDescription));
	
	    list.add(bean);
	    
	    return list;
	}
}
