package com.example.report.service;

import com.example.report.helper.Result;

import java.util.List;
import java.util.Map;

/**
 * Created by aimu on 2018/12/11.
 */
public interface LeaderService {
    Result showAllProject(Map<String, String> paramMap);

    List<Map<String, String>> showAllMember();

    Result cheakMemberIsExist(Map<String, String> addMemberParamMap);

    Result delUser(Map<String, String> delMemberParamMap);
}
