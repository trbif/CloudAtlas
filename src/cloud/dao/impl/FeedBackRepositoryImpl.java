package cloud.dao.impl;

我:
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.nubia.bean.sql.CommentsBean;
import cn.nubia.bean.sql.FeedbackBean;
import cn.nubia.bean.sql.TestSqlBean;
import cn.nubia.crawler.parser.NubiaFeedbackParser;
import cn.nubia.dao.FeedBackRepository;
import cn.nubia.dao.TestRepository;

/**
 * @author zhangqi
 * @date 2017年12月11日 上午10:17:34
 * @version V1.0
 * @说明:
 */
@Repository
public class FeedBackRepositoryImpl implements FeedBackRepository{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedBackRepositoryImpl.class);
	
	@Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }
我:
	/* (non-Javadoc)
	 * @see cn.nubia.dao.BaseRepository#load(java.io.Serializable)
	 */
	@Override
	public FeedbackBean load(Long id) {
		// TODO Auto-generated method stub
        return (FeedbackBean)getCurrentSession().load(FeedbackBean.class,id);
	}

	/* (non-Javadoc)
	 * @see cn.nubia.dao.BaseRepository#get(java.io.Serializable)
	 */
	@Override
	public FeedbackBean get(Long id) {
		// TODO Auto-generated method stub
        return (FeedbackBean)getCurrentSession().get(FeedbackBean.class,id);
	}

	/* (non-Javadoc)
	 * @see cn.nubia.dao.BaseRepository#findAll()
	 */
	@Override
	public List<FeedbackBean> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
