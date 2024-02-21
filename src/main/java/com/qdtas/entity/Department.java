package com.qdtas.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long deptId;
    @NotBlank(message = "Department name should not be blank")
    private String deptName;
    @JsonIgnore
    @OneToMany(mappedBy = "dept", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<User> employees;

}
