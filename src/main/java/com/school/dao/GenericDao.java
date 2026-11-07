package com.school.dao;
import com.school.entity.Student;
import java.util.List;

public interface GenericDao<T, ID> {
    void save(T entity);
    T update(T entity);
    T findById(ID id);
    T findByEmail(String email);
    List<T> findAll();
    boolean deleteById(ID id);
}

