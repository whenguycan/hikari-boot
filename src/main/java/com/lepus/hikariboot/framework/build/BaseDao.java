package com.lepus.hikariboot.framework.build;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;

/**
 * 
 * @author wangchenyu
 * @date 2017-11-3
 */
@Repository
public class BaseDao extends HibernateDaoSupport{

	protected SessionFactory sessionFactory;
	
	@Resource
	public void setMySessionFactory(SessionFactory sessionFactory){
		this.sessionFactory = sessionFactory;
		super.setSessionFactory(sessionFactory);
	}
	
	private Session getSession() {
		return sessionFactory.openSession();
	}
	
	public <T> T fetch(Class<T> clazz, String id, boolean bornIfNull){
		try {
			T t = null;
			if(id != null && !"".equals(id))
				t = getHibernateTemplate().get(clazz, id);
			return bornIfNull?t==null?clazz.newInstance():t:t;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public <T> T save(T t){
		getHibernateTemplate().save(t);
		return t;
	}
	
	public void save(List<?> t){
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		try {
			int idx = 0;
			for(Object o : t){
				session.save(o);
				idx++;
				if(idx % 50 == 0){
					session.flush();
					session.clear();
				}
			}
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tx.rollback();
			session.close();
		}
	}
	
	public <T> T update(T t){
		getHibernateTemplate().update(t);
		return t;
	}
	
	public <T> void delete(T t){
		getHibernateTemplate().delete(t);
	}
	
	public <T> void delete(Class<T> clazz, String id){
		T t = getHibernateTemplate().get(clazz, id);
		getHibernateTemplate().delete(t);
	}
	
	public <T> List<T> findList(Hql hql){
		Session session = getSession();
		List<T> list = session.createQuery(hql.getQueryHql()).list();
		session.close();
		return list;
	}
	
	public <T> Page<T> findPage(Hql hql, Page<T> page){
		Session session = getSession();
		int count = ((Number)session.createQuery(hql.getCountHql()).uniqueResult()).intValue();
		int first = (page.getPageNo() - 1) * page.getPageSize();
		List<T> list = session.createQuery(hql.getQueryHql()).setFirstResult(first).setMaxResults(page.getPageSize()).list();
		page.setList(list);
		page.setRowCount(count);
		page.setPageCount(count%page.getPageSize()==0?count/page.getPageSize():count/page.getPageSize()+1);
		page.setPre(page.getPageNo()==1?1:page.getPageNo()-1);
		page.setAfter(page.getPageNo()<page.getPageCount()?page.getPageNo()+1:page.getPageCount());
		session.close();
		return page;
	}
	
}
