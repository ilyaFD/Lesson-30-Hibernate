package com.school.entity;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @OneToMany(
            mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private Set<Homework> homeworks = new HashSet<>();


    public Student() {

    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Homework> getHomeworks() {
        return homeworks;
    }
    public void setHomeworks(Set<Homework> homeworks) {
        this.homeworks = homeworks;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Student student = (Student) o;

        return id != null && Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }



    @Override
    public String toString() {
        return "Homework{" +
                "id=" + id +
                ", first name='" + firstName + '\'' +
                ", last name='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", homeworksCount=" + homeworks.size() +
                '}';


    }


    public void addHomework(final Homework homework) {
        homeworks.add(homework);
        homework.setStudent(this);
    }

    public void removeHomework(final Homework homework) {
        homeworks.remove(homework);
        homework.setStudent(null);
    }
}

