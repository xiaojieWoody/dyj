package com.dyj.invokeall.student;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentEntity {

    private List<String> names;
    private List<Integer> ages;
    private String className;
}
