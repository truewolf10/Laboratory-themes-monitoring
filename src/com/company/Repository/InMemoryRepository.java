package com.company.Repository;


import com.company.Domain.HasdId;
import com.company.Domain.IValidator;

public class InMemoryRepository<E extends HasdId<ID>, ID extends Comparable<ID> >extends AbstractRepository<E, ID> {
    public InMemoryRepository(IValidator<E> validator) {
        super(validator);
    }

    @Override
    public void loadData() {
        return;
    }

    @Override
    public void saveData() {
        return;
    }
}
