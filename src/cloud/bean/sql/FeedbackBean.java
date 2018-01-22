package cloud.bean.sql;

我:
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.alibaba.fastjson.JSONObject;

import cn.nubia.bean.BaseBean;

/**
 * @author zhangqi
 * @date 2017年12月11日 下午1:39:45
 * @version V1.0
 * @说明:
 */
@Entity
@Table(name = "feedback")
我:
public class FeedbackBean extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1169473819794792722L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true, nullable = false)
	private Long id;

	@Column(columnDefinition="TEXT")
	private String crawlTime;
	@Column(columnDefinition="TEXT")
	private String publishDate;
	@Column(columnDefinition="TEXT")
	private String status;
	@Column(columnDefinition="TEXT")
	private String title;
	@Column(columnDefinition="TEXT")
	private String otherStatus;
	@Column(columnDefinition="TEXT")
	private String nickname;

	@Column(columnDefinition="TEXT")
	private String href;
我:
	@Column(columnDefinition="TEXT")
	private String phoneModel;
	@Column(columnDefinition="TEXT")
	private String version;
	@Column(columnDefinition="TEXT")
	private String UIversion;
	@Column(columnDefinition="TEXT")
	private String module;
	@Column(columnDefinition="TEXT")
	private String typeOfFeedback;
	@Column(columnDefinition="TEXT")
	private String typeOfDemand;
	@Column(columnDefinition="TEXT")
	private String demandDescription;
	@Column(columnDefinition="TEXT")
	private String demandScene;
	@Column(columnDefinition="TEXT")
	private String demandValue;

	@Column(columnDefinition="TEXT")
	private String infoSource;

	@Column(columnDefinition="TEXT")
	private String md5;
	
	@Override
    public String toString() {
        JSONObject obj = new JSONObject();
        obj.put("id", this.id);
        obj.put("publishDate", this.publishDate);
        obj.put("status", this.status);
        obj.put("title", this.title);
        obj.put("otherStatus", this.otherStatus);
        obj.put("nickname", this.nickname);
        obj.put("href", this.href);
        obj.put("phoneModel", this.phoneModel);
        obj.put("version", this.version);
        obj.put("UIversion", this.UIversion);
        obj.put("module", this.module);
        obj.put("typeOfFeedback", this.typeOfFeedback);
        obj.put("typeOfDemand", this.typeOfDemand);
        obj.put("demandDescription", this.demandDescription);
        obj.put("demandScene", this.demandScene);
        obj.put("demandValue", this.demandValue);
        obj.put("infoSource", this.infoSource);
        return obj.toString();
    }