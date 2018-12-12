package com.example.report.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AuditingMessageDTO {
    private List<Long> passList;
    private List<Long> notPassList;
    private int uid;
}
