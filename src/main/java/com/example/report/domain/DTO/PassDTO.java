package com.example.report.domain.DTO;

import lombok.Data;

import java.util.List;


@Data
public class PassDTO {
    private List<Long> passList;
    private int uid;
}
