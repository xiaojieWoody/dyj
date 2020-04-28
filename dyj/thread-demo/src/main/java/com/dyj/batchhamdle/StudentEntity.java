package com.dyj.batchhamdle;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StudentEntity {

    private List<String> names;
    private List<String> alisNames;
    private String className;
}
