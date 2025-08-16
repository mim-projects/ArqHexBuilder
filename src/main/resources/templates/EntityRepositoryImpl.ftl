package ${package};

import ${packageBase}.domain.repository.EntityRepository;

<#if useJakarta>
import jakarta.ejb.Stateless;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
</#if>
<#if !useJakarta>
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
</#if>

import java.util.List;

@Stateless(name = "<#if module??>${module}_</#if>entity_repository_impl")
public abstract class EntityRepositoryImpl<TypeId, T> implements EntityRepository<TypeId, T> {
    @PersistenceContext
    private EntityManager entityManager;

    protected abstract Class<T> getEntityClass();

    public EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public T findById(TypeId id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    @Override
    public List<T> findAll() {
        return getEntityManager().createQuery("from " + getEntityClass().getName()).getResultList();
    }

    @Override
    public T save(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        getEntityManager().merge(entity);
        return entity;
    }

    @Override
    public void delete(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
}