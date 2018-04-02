//package teste;
//
//import com.company.Domain.LaboratoryValidator;
//import com.company.Domain.TemaLab;
//import com.company.Domain.ValidatorException;
//import com.company.Repository.AbstractRepository;
//import com.company.Repository.InMemoryRepository;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//class AbstractRepositoryTest {
//
//    AbstractRepository<TemaLab,Integer> repository;
//
//    void setUp() {
//        repository = new InMemoryRepository<>(new LaboratoryValidator());
//        try {
//            repository.add(new TemaLab(2,"ceva greu", 4));
//            repository.add(new TemaLab(3,"ceva greu", 4));
//            repository.add(new TemaLab(4,"ceva greu", 4));
//        } catch (ValidatorException e) {
//            fail(e.getMessage());
//        }
//    }
//
//    void size() {
//        assertEquals(repository.size(), 3);
//        try {
//            repository.add(new TemaLab(5, "ceva greu", 4));
//        } catch (ValidatorException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(repository.size(), 4);
//        repository.delete(3);
//        repository.delete(4);
//        assertEquals(repository.size(), 2);
//        try {
//            repository.add(new TemaLab(5, "ceva greu", 4));
//        } catch (ValidatorException e) {
//            fail(e.getMessage());
//        }
//        assertEquals(repository.size(), 2);
//        repository.delete(4);
//        assertEquals(repository.size(), 2);
//    }
//
//
//    void add() {
//        try {
//            assertEquals(repository.add(new TemaLab(5, "ceva greu", 4)), null);
//        } catch (ValidatorException e) {
//            fail(e.getMessage());
//        }
//        try {
//            TemaLab temp = repository.findExistingObject(5);
//            assertEquals(repository.add(new TemaLab(5, "ceva greu", 4)), temp);
//        } catch (ValidatorException e) {
//            fail(e.getMessage());
//        }
//        assertThrows(ValidatorException.class, ()->repository.add(null));
//    }
//
//
//    void delete() {
//        assertEquals(repository.delete(5),null);
//        TemaLab temp = repository.findExistingObject(3);
//        assertEquals(repository.delete(3), temp);
//    }
//
//    void findExistingObject() {
//        assertEquals(repository.findExistingObject(5), null);
//        assertNotEquals(repository.findExistingObject(3), null);
//        TemaLab TemaLab = repository.findExistingObject(2);
//        assertEquals((int)TemaLab.getId(), 2);
//    }
//
//}