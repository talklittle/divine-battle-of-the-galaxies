/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package facade;

import java.util.List;
import javax.persistence.EntityManager;

/**
 * Abstract class for all facades doing lookup and modifications.
 * @author _yy
 */
public abstract class AbstractFacade<T> {
    private Class<T> entityClass;

    /**
     * constructor used by the subclasses
     * @param entityClass
     */
    public AbstractFacade(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    /**
     * get the entity manager for managing entities
     * @return
     */
    protected abstract EntityManager getEntityManager();

    /**
     * persist a new entity of type T
     * @param entity
     */
    public void create(T entity) {
        getEntityManager().persist(entity);
    }

    /**
     * persist an existing entity of type T
     * @param entity
     */
    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    /**
     * delete an existing entity of type T
     * @param entity
     */
    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    /**
     * lookup an existing entity of type T
     * @param id
     * @return
     */
    public T find(Object id) {
        return getEntityManager().find(entityClass, id);
    }

    /**
     * lookup all existing entities of type T
     * @return
     */
    public List<T> findAll() {
        javax.persistence.criteria.CriteriaQuery cq
                = getEntityManager().getCriteriaBuilder()
                                    .createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    /**
     * find the entities between range[0] and range[1]
     * @param range
     * @return
     */
    public List<T> findRange(int[] range) {
        javax.persistence.criteria.CriteriaQuery cq
                = getEntityManager().getCriteriaBuilder()
                                    .createQuery();
        cq.select(cq.from(entityClass));
        javax.persistence.Query q
                = getEntityManager().createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    /**
     * get the number of entities
     * @return
     */
    public int count() {
        javax.persistence.criteria.CriteriaQuery cq
                = getEntityManager().getCriteriaBuilder()
                                    .createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q
                = getEntityManager().createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}
