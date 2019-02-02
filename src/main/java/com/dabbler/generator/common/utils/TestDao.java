package com.dabbler.generator.common.utils;



import com.dabbler.generator.common.HibernateBaseDao;
import com.dabbler.generator.common.Test;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.List;
import java.util.Map;

/**
 * @author RedDabbler
 * @create 2019-02-02 9:44
 **/
public class TestDao extends HibernateBaseDao<Test> {

    public Test getById(Integer id){
        return super.findById(id);
    }


    public List<Test> listByName(String  name){
        Criteria criterion = getSession().createCriteria(Test.class).add(Restrictions.eq("name", name));
        return criterion.list();
    }



    public void save(Test testSave){
        if(testSave.getId()==null){
            insert(testSave);
            return;
        }
        update(testSave);
    }

    public void deleteById(Integer id){
        super.deleteById(id);
    }

    public void deleteByName(String name){
        List<Test> testList =  listByName(name);
        super.deleteAll(testList);
    }




    public List<Test> getByParam(Map<String,Object> parameter){
        Integer id = (Integer)parameter.get("id");
        String name = (String)parameter.get("name");
        Criteria criterion = getSession().createCriteria(Test.class);
        if (StringUtils.isNotBlank(name)){
            criterion.add(Restrictions.eq("name",name));
        }

        if (id!=null){
            criterion.add(Restrictions.eq("name",name));
        }
        return criterion.list();
    }

}
