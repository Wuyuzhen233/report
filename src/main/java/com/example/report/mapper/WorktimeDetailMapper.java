package com.example.report.mapper;

import com.example.report.domain.WorktimeDetail;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

public interface WorktimeDetailMapper extends Mapper<WorktimeDetail> {
    void saveDetail(@Param("wid") long wid,@Param("detail") String detail);

}
