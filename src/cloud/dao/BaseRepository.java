package cloud.dao;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhangqi
 * @date 2017��12��11�� ����10:15:53
 * @version V1.0
 * @˵��:
 */
public interface BaseRepository<T,PK extends Serializable>{
	T load(PK id);

	T get(PK id);

	List<T> findAll();
	
	List<T> findList(String hql,int start,int size);
	
	T find();

	void persist(T entity);
	
	void update(T entity);

	PK save(T entity);
	
	PK saveBatch(List<T> entityList);
	
	void saveOrUpdate(T entity);
	
	void delete(PK id);
	
	void flush();
}