package cloud.bean.sql;

我:
import java.util.List;

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
@Table(name = "comments")
我:
public class CommentsBean extends BaseBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2592795498824640139L;
	
	class ProductCommentSummary{
		public int goodRateShow;
		public int poorRateShow;
		public int averageScore;
		public int oneYear;
		public int goodCount;
		public double generalRate;
		public int generalCount;
		public Long skuId;
		public double poorRate;
		public int afterCount;
		public int goodRateStyle;
		public int poorCount;
		public int commentCount;
		public Long productId;
		public double goodRate;
		public int generalRateShow;
	}
	class HotCommentTagStatistics{
		public Long id;
		public String name;
		public int status;
		public Long rid;
		public Long productId;
		public int count;
		public String created;
		public String modified;
	}
我:
	private static ProductCommentSummary productCommentSummary;
	private static List<HotCommentTagStatistics> hotCommentTagStatisticsList;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true, nullable = false)
	private Long id;
	private String guid;
	@Column(name="content",length=2500)
	private String content;
	@Column(name="md5Str",length=32)
	private String md5Str;
	private String creationTime;
	private Boolean isTop;
	private String referenceId;
	private String referenceImage;
	private String referenceName;
	private String referenceTime;
	private String referenceType;
	private Long referenceTypeId;
	private Long firstCategory;
	private Long secondCategory;
	private Long thirdCategory;
	private int replyCount;
	private int score;
	private int status;
	private String title;
	private int usefulVoteCount;
	private int uselessVoteCount;
	private String userImage;
	private String userImageUrl;
	private String userLevelId;
	private String userProvince;
	private int viewCount;
	private int orderId;
	private Boolean isReplyGrade;
	private String nickname;
	private int userClient;
	private String productColor;
	private String productSize;
	private int integral;
	private int userImgFlag;
	private int anonymousFlag;
	private String userLevelName;
	private int plusAvailable;
	private int productSales_dim;
	private String productSales_saleName;
	private String productSales_saleValue;
	private Boolean recommend;
	private String userLevelColor;
	private String userClientShow;
	private Boolean isMobile;
	private int days;
	private int afterDays;
	
我:
	private Boolean isBad;
	private String dataBaseID;
	
	private String productModel;
	private String brand;
	private String source;
	private String imgUrl;
	private String productID;