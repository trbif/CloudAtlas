package cloud.bean.sql;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import cn.nubia.bean.BaseBean;

/**
 * @author zhangqi
 * @date 2017年12月11日 下午1:39:45
 * @version V1.0
 * @说明:
 */
@Entity
@Table(name = "comments_info")
public class CommentsInfoBean extends BaseBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5081577969548535473L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",unique = true, nullable = false)
	private Long id;

    @Column(name="productID",unique = true, nullable = false)
    private Long productID;
    private Long storeID;
	
	private String imageUrl;
	private String productModel;
	private String creatTime;
	@Column(name="lastCommentsInfo",length=1000)
	private String lastCommentsInfo;
	private String lastUpdateTime;
	private String infoSource;
