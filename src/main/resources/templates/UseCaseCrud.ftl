package ${package};

import java.util.List;

public interface UseCaseCrud<TypeId, T> {
    T findById(TypeId id);
    List<T> findAll();
    T save(T entity);
    T update(T entity);
    void delete(T entity);
}