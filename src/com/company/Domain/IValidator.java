package com.company.Domain;

import java.util.Optional;

public interface IValidator<E> {
    public void validate(Optional<E> _elem) throws ValidatorException;
}
