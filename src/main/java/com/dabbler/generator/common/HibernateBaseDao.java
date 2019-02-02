package com.dabbler.generator.common;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @author RedDabbler
 * @create 2019-01-30 15:36
 **/

@SuppressWarnings({"rawtypes", "unchecked"})
// 为保证正常获取泛型参数，必须有子类实现
public abstract class HibernateBaseDao<T> extends HibernateDaoSupport {
    private Class<T> entityClass;


    @Resource
    public void setSessionFacotry(SessionFactory sessionFacotry) {
        super.setSessionFactory(sessionFacotry);
    }

    private HibernateTemplate getTemplate(){
        return super.getHibernateTemplate();
    }

    public HibernateBaseDao() {
        entityClass =(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String getEntityName(){
        return entityClass.getSimpleName();
    }

    public void insert(T entity) {
        getTemplate().save(entity);
    }

    public void update(T entity) {
        getTemplate().update(entity);
    }

    public void delete(T entity) {
        getTemplate().delete(entity);
    }

    protected void deleteById(Serializable id){
       T data =  findById(id);
       delete(data);
    }


    protected T findById(Serializable id) {
        return (T) getTemplate().get(entityClass, id);
    }


    public List<T> findAll() {
        return (List<T>)getTemplate().find("from " + getEntityName());

    }

    public int countAll() {
        List list = getTemplate().find("select count(*) from " + getEntityName());
        if (list != null) {
            Long count = (Long)list.get(0);
            return count.intValue();
        }
        return 0;

    }

    public int conditionCount(Criterion... cs) {
        DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
        for (int i = 0; i < cs.length; i++) {
            dc.add(cs[i]);
        }
        dc.setProjection(Projections.rowCount());
        List list = getTemplate().findByCriteria(dc);
        if (list != null) {
            Long l = (Long) list.get(0);
            return l.intValue();
        }
        return 0;
    }

    public List<T> findBy(Criterion... cs) {
        DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
        for (int i = 0; i < cs.length; i++) {
            dc.add(cs[i]);
        }
        return (List<T>) getTemplate().findByCriteria(dc);
    }

    /**
     * @param pageNo
     * @param pageSize
     * @param fieldName 指定属性
     * @return
     */
    public List<T> findByPage(int pageNo, int pageSize,String fieldName) {
        Session session = getTemplate().getSessionFactory()
                .getCurrentSession();
        Criteria criteria = session.createCriteria(entityClass);
        return criteria.addOrder(Order.asc(fieldName))
                .setFirstResult((pageNo - 1) * pageSize)
                .setMaxResults(pageSize).list();
    }

    public List<T> findByPage(int pageNo, int pageSize, Criterion... cs) {
        DetachedCriteria dc = DetachedCriteria.forClass(entityClass);
        for (int i = 0; i < cs.length; i++) {
            dc.add(cs[i]);
        }
        if (pageSize == 0 && pageNo < 1) {
            return (List<T>) getTemplate().findByCriteria(dc);
        }
        return (List<T>) getTemplate().findByCriteria(dc, (pageNo - 1) * pageSize, pageSize);
    }

    public List<T> queryByhql(String hql) {
        return (List<T>) getTemplate().find(hql);
    }

    public int getCount(String sql) {
        Session session = getTemplate().getSessionFactory()
                .getCurrentSession();
        Query query = session.createSQLQuery(sql);
        query.setCacheable(true);
        return query.list().size();
    }

    /**
     * 依据SQL语句和参数，返回一个List<Map<String, Object>>集合
     */
    public List<Map<String, Object>> findBySqlGetListMap(String sql, Object... objects) {
        Session session = getTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        for (int i = 0; i < objects.length; i++) {
            query.setString(i, objects[i].toString());
        }
        List<Map<String, Object>> maps = query.list();
        return maps;
    }

    /**
     * 依据SQL语句和参数，返回一个List<Object>集合
     */
    public List<Object> findBySql(String sql, Object... objects) {
        Session session = getTemplate().getSessionFactory()
                .getCurrentSession();
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.TO_LIST);
        for (int i = 0; i < objects.length; i++) {
            query.setString(i, objects[i].toString());
        }
        List<Object> list = query.list();
        return list;
    }

    /**
     * 依据SQL语句，返回一个List<Object>集合
     */
    public List<Object> findBySql(String sql) {
        Session session = getTemplate().getSessionFactory().getCurrentSession();
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.TO_LIST);
        List<Object> list = query.list();
        return list;
    }

    /**
     * 批量删除
     * @param entities
     */
    public void deleteAll(Collection<T> entities) {
        getTemplate().deleteAll(entities);
    }

}

