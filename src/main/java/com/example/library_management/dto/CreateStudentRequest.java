package com.example.library_management.dto;

import com.example.library_management.model.Student;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateStudentRequest {
    @NotBlank
    private String name;
    @NotBlank
    private String email;
    @NotBlank
    private String rollNumber;
    @NotNull
    private Integer age;

    public Student toStudent(){
         return Student.builder()
                 .name(this.name)
                 .email(this.email)
                 .rollNumber(this.rollNumber)
                 .age(this.age).build();

    }
}
