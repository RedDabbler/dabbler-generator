package ${basePackage}.dao
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.HibernateTemplate;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * @author ${author}
 * @create ${createDate?string("yyyy-MM-dd hh:mm:ss")}
 **/
@SuppressWarnings({"rawtypes", "unchecked"})
public abstract class HibernateBaseDao<T> {
    private Class<T> entityClass;
    protected HibernateTemplate hibernateTemplate;

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public HibernateBaseDao() {
        entityClass =(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    private String getEntityName(){
        return entityClass.getSimpleName();
    }

    public void insert(T entity) {
        hibernateTemplate.save(entity);
    }

    public void update(T entity) {
        hibernateTemplate.update(entity);
    }

    public void delete(T entity) {
        hibernateTemplate.delete(entity);
    }

    public T selectById(Serializable id) {
        return (T) hibernateTemplate.get(entityClass, id);
    }


    public List<T> findAll() {
        return (List<T>)hibernateTemplate.find("from " + getEntityName());
    }

    public int countAll() {
        List list = hibernateTemplate.find("select count(*) from " + getEntityName());
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
        List list = hibernateTemplate.findByCriteria(dc);
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
        return (List<T>) hibernateTemplate.findByCriteria(dc);
    }

    /**
    * @param pageNo
    * @param pageSize
    * @param fieldName 指定属性
    * @return
    */
    public List<T> findByPage(int pageNo, int pageSize,String fieldName) {
        Session session = hibernateTemplate.getSessionFactory() .getCurrentSession();
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
            return (List<T>) hibernateTemplate.findByCriteria(dc);
        }
        return (List<T>) hibernateTemplate.findByCriteria(dc, (pageNo - 1) * pageSize, pageSize);
    }

    public List<T> queryByhql(String hql) {
        return (List<T>) hibernateTemplate.find(hql);
    }

    public int getCount(String sql) {
        Session session = hibernateTemplate.getSessionFactory() .getCurrentSession();
        Query query = session.createSQLQuery(sql);
        query.setCacheable(true);
        return query.list().size();
    }

    /**
    * 依据SQL语句和参数，返回一个List<Map<String, Object>>集合
    */
    public List<Map<String, Object>> findBySqlGetListMap(String sql, Object... objects) {
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
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
        Session session = hibernateTemplate.getSessionFactory() .getCurrentSession();
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
        Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
        Query query = session.createSQLQuery(sql).setResultTransformer(Transformers.TO_LIST);
        List<Object> list = query.list();
        return list;
    }

    public void deleteAll(Collection<T> entities) {
        hibernateTemplate.deleteAll(entities);
    }

}

