package com.school.dao;
import com.school.entity.Student;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;


import java.util.List;
import java.util.Objects;

public class StudentDaoImpl implements GenericDao<Student, Long> {
    private final EntityManagerFactory emf;
    public StudentDaoImpl(EntityManagerFactory emf) {
        this.emf = emf;
    }
    @Override
    public void save(final Student student) {
        Objects.requireNonNull(student, "Parameter [student] must not be null!");
        EntityManager entityManager = this.emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(student);
            transaction.commit();
            System.out.println("Saved student %s ".formatted(student));
        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Cannot save student %s".formatted(student));
            throw ex;
        } finally {
            entityManager.close();
        }
    }
    @Override
    public Student update(Student student) {
        Objects.requireNonNull(student, "Student must not be null!");

        EntityManager entityManager = emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            Student updatedStudent = entityManager.merge(student);

            transaction.commit();

            System.out.println("Updated student: " + updatedStudent);

            return updatedStudent;

        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw ex;
        } finally {
            entityManager.close();
        }
    }
    @Override
    public Student findById(Long id) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager
                    .createQuery("FROM Student s WHERE s.id = :id", Student.class)
                    .setParameter("id", Objects.requireNonNull(id))
                    .getSingleResult();
        }
    }

    @Override
    public Student findByEmail(String email) {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager
                    .createQuery("FROM Student s WHERE s.email = :email", Student.class)
                    .setParameter("email", Objects.requireNonNull(email))
                    .getSingleResult();
        }
    }
    @Override
    public List<Student> findAll() {
        try (EntityManager entityManager = emf.createEntityManager()) {
            return entityManager
                    .createQuery("FROM Student", Student.class)
                    .getResultList();
        }
    }
    @Override
    public boolean deleteById(Long id) {
        Objects.requireNonNull(id, "Parameter [id] must not be null!");
        EntityManager entityManager = this.emf.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            Student student = entityManager.find(Student.class, id);

            if (student == null) {
                transaction.rollback();
                return false;
            }

            entityManager.remove(student);
            transaction.commit();
            System.out.println("Delete student %s".formatted(student));
            return true;

        } catch (Exception ex) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            System.out.println("Cannot delete student");
            throw ex;
        } finally {
            entityManager.close();
        }

    }

}