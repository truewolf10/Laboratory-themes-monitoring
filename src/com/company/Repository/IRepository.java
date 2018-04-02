package com.company.Repository;

public interface IRepository<E,ID> extends Iterable<E> {
    long size();
    E add(E entity) throws Exception;
    E delete(ID id);
    E findExistingObject(ID id);
    Iterable<E> findAll();

    void loadData();
    void saveData();

}
