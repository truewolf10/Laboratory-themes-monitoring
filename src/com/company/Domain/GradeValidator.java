package com.company.Domain;

import java.util.ArrayList;
import java.util.Optional;

public class GradeValidator implements  IValidator<Grade>{
    @Override
    public void validate(Optional<Grade> _elem2) throws ValidatorException {
        ArrayList<String> errors = new ArrayList<>();

        if (!_elem2.isPresent()){
            errors.add("null is not valid");
            throw new ValidatorException(errors);
        }
        Grade _elem = _elem2.get();
        if (_elem.getValoare()!= null) {


            if (_elem.getValoare() < 0.0)
                errors.add("Value must be positive");
            if (_elem.getValoare() > 10.0)
                errors.add("The value must be in [1,10]");
        }
        if (_elem.getDate() != null) {
            if (_elem.getDate() < 0)
                errors.add("The date cannot be negative!");
        }
        if(!errors.isEmpty())
            throw new ValidatorException(errors);
    }
}
