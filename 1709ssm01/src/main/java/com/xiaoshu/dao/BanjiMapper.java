package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Banji;
import com.xiaoshu.entity.BanjiExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BanjiMapper extends BaseMapper<Banji> {
    long countByExample(BanjiExample example);

    int deleteByExample(BanjiExample example);

    List<Banji> selectByExample(BanjiExample example);

    int updateByExampleSelective(@Param("record") Banji record, @Param("example") BanjiExample example);

    int updateByExample(@Param("record") Banji record, @Param("example") BanjiExample example);
}