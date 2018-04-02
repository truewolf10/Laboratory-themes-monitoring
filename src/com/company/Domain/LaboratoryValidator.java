package com.company.Domain;

import java.util.ArrayList;
import java.util.Optional;

public class LaboratoryValidator implements IValidator<TemaLab> {
    @Override
    public void validate(Optional<TemaLab> _elem2) throws ValidatorException {
        ArrayList<String> errors = new ArrayList<>();

        if(!_elem2.isPresent()){
            errors.add("null is not valid!");
            throw new ValidatorException(errors);
        }
        TemaLab _elem = _elem2.get();
        if(_elem.getId() < 0)
            errors.add("Id must have a positive value! ");

        if(_elem.getDescriere().equals(""))
            errors.add("You should have a name");

        if (!errors.isEmpty())
            throw new ValidatorException(errors);
    }
}
