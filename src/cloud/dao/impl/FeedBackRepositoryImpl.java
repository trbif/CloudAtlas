package cloud.dao.impl;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cloud.bean.sql.FeedbackBean;
import cloud.dao.FeedbackRepository;

/**
 * @author zhangqi
 * @date 2017年12月11日 上午10:17:34
 * @version V1.0
 * @说明:
 */
@Repository
public class FeedBackRepositoryImpl implements FeedbackRepository{
	
    private static final Logger LOGGER = LoggerFactory.getLogger(FeedBackRepositoryImpl.class);
	
	@Autowired
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return this.sessionFactory.openSession();
    }
    
	@Override
	public FeedbackBean load(Long id) {
		// TODO Auto-generated method stub
        return (FeedbackBean)getCurrentSession().load(FeedbackBean.class,id);
	}

	@Override
	public FeedbackBean get(Long id) {
		// TODO Auto-generated method stub
        return (FeedbackBean)getCurrentSession().get(FeedbackBean.class,id);
	}

	@Override
	public List<FeedbackBean> findAll() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<FeedbackBean> findList(String hql, int start, int size) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public FeedbackBean find() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void persist(FeedbackBean entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(FeedbackBean entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public Long save(FeedbackBean entity) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Long saveBatch(List<FeedbackBean> entityList) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void saveOrUpdate(FeedbackBean entity) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void flush() {
		// TODO Auto-generated method stub
		
	}
}
