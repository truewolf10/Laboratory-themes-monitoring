package com.company.Domain;

import java.util.ArrayList;
import java.util.Optional;

public class StudentValidator implements IValidator<Student> {
    @Override
    public void validate(Optional<Student> _elem2) throws ValidatorException {

        ArrayList<String> errors = new ArrayList<>();

        if(!_elem2.isPresent()){
            errors.add("null is not valid!");
            throw new ValidatorException(errors);
        }
        Student _elem = _elem2.get();
        if(_elem.getId() < 0)
            errors.add("Id must have a positive value! ");

        if(_elem.getNume().equals(""))
            errors.add("You should have a name");

        if (!errors.isEmpty())
            throw new ValidatorException(errors);
       // if(_elem.ge)

    }

}
