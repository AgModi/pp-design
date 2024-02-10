package com.example.ppdesign.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
public class EmployeeDto {

    @JsonProperty("id")
    @NotNull
    private int id;

    @JsonProperty("name")
    @NotBlank
    private String name;

    @JsonProperty("salary")
    @NotNull
    private int salary;

    public EmployeeDto(int id, String name, int salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }
}
