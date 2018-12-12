package com.example.report.domain.DTO;

import lombok.Data;

import java.util.List;

@Data
public class AuditingMessageDTO {
    private List<String> passList;
    private List<String> notPassList;
    private int uid;
}
