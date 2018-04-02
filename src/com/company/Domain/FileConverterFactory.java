package com.company.Domain;

@FunctionalInterface
public  interface FileConverterFactory<E> {
    E create(String string);
}
