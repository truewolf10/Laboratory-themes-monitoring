package com.company.Repository;

import com.company.Domain.HasdId;
import com.company.Domain.IValidator;
import com.company.Domain.ValidatorException;

import java.util.*;
import java.util.function.Consumer;

public abstract class AbstractRepository<E extends HasdId<ID>, ID extends Comparable<ID> > implements IRepository<E, ID> {

    private IValidator<E> validator = null;
    protected TreeMap<ID, E> storage;

    public AbstractRepository(IValidator<E> validator){
        this.validator = validator;
        this.storage = new TreeMap<ID,E>(
                //new RepoComparator<ID>()
                Comparator.naturalOrder());
    }

//    private static class RepoComparator<Id extends Comparable<Id>> implements Comparator<Id> {
//        @Override
//        public int compare(Id o1, Id o2) {
//            return o1.compareTo(o2);
//        }
//    }

    @Override
    public long size() {
        return storage.size();
    }

    @Override
    public E add(E entity) throws ValidatorException {
        if (validator != null)
            validator.validate(Optional.ofNullable(entity));

        E temp =  storage.put(entity.getId(),entity);
        saveData();
        return temp;
    }

    @Override
    public E delete(ID id) {

        E temp = storage.remove(id);
        if (temp != null)
            saveData();

        return temp;
    }

    @Override
    public E findExistingObject(ID id) {

        return storage.get(id);
    }

    @Override
    public Iterable<E> findAll() {

        return storage.values();
    }

    @Override
public Iterator<E> iterator() {
    return storage.values().iterator();
}

    @Override
    public void forEach(Consumer<? super E> action) {
        storage.values().forEach(action);
    }

    @Override
    public Spliterator<E> spliterator() {
        return storage.values().spliterator();
    }
}
