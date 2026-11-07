
package com.school;

import com.school.dao.StudentDaoImpl;
import com.school.entity.Homework;
import com.school.entity.Student;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.time.LocalDate;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        EntityManagerFactory emf =
                Persistence.createEntityManagerFactory("StudentEntityPostgres");

        try {
            StudentDaoImpl studentDao = new StudentDaoImpl(emf);

            // 1. SAVE
            Student student = new Student();

            student.setFirstName("Ivan");
            student.setLastName("Ivanov");
            student.setEmail("ivan@example.com");

            Homework homework = new Homework();
            homework.setDescription("Learn Hibernate");
            homework.setDeadline(LocalDate.now().plusDays(7));
            homework.setMark(10);

            student.addHomework(homework);

            studentDao.save(student);

            Long studentId = student.getId();

            System.out.println("Generated student ID: " + studentId);


            // 2. FIND BY ID
            Student foundById = studentDao.findById(studentId);

            System.out.println(
                    "Found by ID: " + foundById.getFirstName()
            );


            // 3. FIND BY EMAIL
            Student foundByEmail =
                    studentDao.findByEmail("ivan@example.com");

            System.out.println(
                    "Found by email: " + foundByEmail.getEmail()
            );


            // 4. FIND ALL
            List<Student> students = studentDao.findAll();

            System.out.println(
                    "Total students: " + students.size()
            );


            // 5. UPDATE
            foundByEmail.setFirstName("Petr");

            studentDao.update(foundByEmail);

            System.out.println(
                    "Updated first name: " + foundByEmail.getFirstName()
            );


            // 6. DELETE BY ID
            boolean deleted = studentDao.deleteById(studentId);

            System.out.println("Student deleted: " + deleted);

        } finally {
            emf.close();
        }
    }
}
