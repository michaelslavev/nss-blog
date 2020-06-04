/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package the.nss.boys.blog.dao;



import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * Basic Data access class every DAO class inherits from her
 * @param <T> Model class (article, user, topic, etc.)
 *
 * Contains EntityManager and Class type
 */
public abstract class BaseDao<T> implements GenericDao<T> {

    @PersistenceContext
    protected EntityManager em;

    protected final Class<T> type;

    protected BaseDao(Class<T> type) {
        this.type = type;
    }

    /**
     * Find specific entity by her id
     * @param id Identifier
     * @return
     */
    @Override
    public T find(Integer id) {
        Objects.requireNonNull(id);
        return em.find(type, id);
    }

    /**
     * Query for loading all entities
     * @return List of all entities
     */
    @Override
    public List<T> findAll() {
        try {
            return em.createQuery("SELECT e FROM " + type.getSimpleName() + " e", type).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param entity Entity to persist
     */
    @Override
    public void persist(T entity) {
        Objects.requireNonNull(entity);
        try {
            em.persist(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     *
     * @param entities Entities to persist
     */
    @Override
    public void persist(Collection<T> entities) {
        Objects.requireNonNull(entities);
        if (entities.isEmpty()) {
            return;
        }
        try {
            entities.forEach(this::persist);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     *
     * @param entity Entity to update
     * @return
     */
    @Override
    public T update(T entity) {
        Objects.requireNonNull(entity);
        try {
            return em.merge(entity);
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }

    /**
     *
     * @param entity Entity to remove
     */
    @Override
    public void remove(T entity) {
        Objects.requireNonNull(entity);
        try {
            final T toRemove = em.merge(entity);
            if (toRemove != null) {
                em.remove(toRemove);
            }
        } catch (RuntimeException e) {
            throw new PersistenceException(e);
        }
    }


    /**
     *
     * @param id Entity identifier
     * @return
     */
    @Override
    public boolean exists(Integer id) {
        return id != null && em.find(type, id) != null;
    }
}