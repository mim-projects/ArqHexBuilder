package ${package};

import ${packageBase}.application.usecases.UseCaseCrud;
import ${packageBase}.domain.repository.EntityRepository;

<#if useJakarta>
import jakarta.ejb.Stateless;
</#if>
<#if !useJakarta>
import javax.ejb.Stateless;
</#if>

import java.util.ArrayList;
import java.util.List;

@Stateless(name = "<#if module??>${module}_</#if>use_case_crud_impl")
public abstract class UseCaseCrudImpl<TypeId, Entity, Dto> implements UseCaseCrud<TypeId, Dto> {
    protected abstract EntityRepository<TypeId, Entity> getRepository();
    protected abstract Dto toDto(Entity entity);
    protected abstract Entity toEntity(Dto dto);

    @Override
    public Dto findById(TypeId typeId) {
        if (typeId == null) return null;
        return toDto(getRepository().findById(typeId));
    }

    @Override
    public List<Dto> findAll() {
        List<Dto> list = new ArrayList<>();
        for (Entity item : getRepository().findAll()) {
            list.add(toDto(item));
        }
        return list;
    }

    @Override
    public Dto save(Dto entity) {
        if (entity == null) return null;
        Entity entityToSave = toEntity(entity);
        getRepository().save(entityToSave);
        return toDto(entityToSave);
    }

    @Override
    public Dto update(Dto entity) {
        if (entity == null) return null;
        Entity entityToUpdate = toEntity(entity);
        getRepository().update(entityToUpdate);
        return toDto(entityToUpdate);
    }

    @Override
    public void delete(Dto entity) {
        if (entity == null) return;
        getRepository().delete(toEntity(entity));
    }
}