package cloud.crawler.parser;

我:
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import cn.nubia.bean.BaseBean;
import cn.nubia.bean.CommentsInfo;
import cn.nubia.bean.sql.CommentsBean;
import cn.nubia.crawler.CommentsParser;
import cn.nubia.crawler.ParserBase;
import cn.nubia.utils.MD5Util;
我:
public class JDCommentsParser implements CommentsParser{

    private static final Logger LOGGER = LoggerFactory.getLogger(JDCommentsParser.class);

    /*
     * 将时间戳转换为时间
     */
    private String stampToDate(String s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
我:
	/* (non-Javadoc)
	 * @see cn.nubia.crawler.CrawlerParserBase#getUrlList(java.lang.String)
	 */
	@Override
	public List<CommentsInfo> getUrlList(String html) {
		// TODO Auto-generated method stub
		return null;
	}
我:
	/* (non-Javadoc)
	 * @see cn.nubia.crawler.ParserBase#parse(java.lang.String, java.lang.Object)
	 */
	@Override
	public List<CommentsBean> parse(String jsonStr, CommentsInfo entity) {
    	String productModel = entity.getProductModel();
    	String brand = entity.getBrand();
    	String source = entity.getSource();
    	String imgUrl = entity.getImgUrl();
    	String productID = entity.getProductID();

        List<CommentsBean> list = new ArrayList<>();

        int start = jsonStr.indexOf("(") + 1;
        int end = jsonStr.lastIndexOf(")");
        if (start == -1 || end == -1)
            return list;
        String json = "[" + jsonStr.substring(start, end) + "]";
        if (json.contains("class=\"link-login\"")) {
            return list;
        }
我:
JSONArray jsonArr = JSONArray.parseArray(json);
        JSONArray comments = jsonArr.getJSONObject(0).getJSONArray("comments");
        for (int i = 0; i < comments.size(); i++) {
            CommentsBean bean = new CommentsBean();
            JSONObject oneComent = comments.getJSONObject(i);
            String content = oneComent.getString("content").replace("\r", " ").replace("\n", " ").trim();
            bean.setContent(content);
            int score = oneComent.getIntValue("score");
            bean.setScore(score);
            bean.setCreationTime(oneComent.getString("creationTime"));
            bean.setNickname(oneComent.getString("nickname"));
            bean.setReferenceName(oneComent.getString("referenceName"));
            if (score > 3 && score <= 5)
                bean.setIsBad(false);
            else
                bean.setIsBad(true);

            bean.setProductModel(productModel);
            bean.setBrand(brand);
            bean.setSource(source);
            bean.setImgUrl(imgUrl);
            bean.setProductID(productID);
            String md5 = MD5Util.toMD5(content+brand+source);
            bean.setMd5(md5);

            list.add(bean);
        }

        return list;
	}

}