
package it.adfor.rapportino.dao;

import java.lang.reflect.ParameterizedType;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;

@Repository("genericDao")
public class GenericDao<T> extends AbstractDao<T>{
    public GenericDao(Class c){
        this.parameterType=c;
    }
    public GenericDao(){
        
    }
    
    public void setClass(Class c){
        this.parameterType=c;
    }
}
