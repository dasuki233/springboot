package com.zjj.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Saraly;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjj.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@Mapper
public interface SaralyMapper extends BaseMapper<Saraly> {

    IPage pageCC(Page<Saraly> page, @Param(Constants.WRAPPER) Wrapper wrapper);
    IPage PaysalaryList(Page<Saraly> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    boolean pay(Double money,Integer saralyid);

    IPage studentSalary(Page<Saraly> page, Integer sid, @Param(Constants.WRAPPER) Wrapper wrapper);
}
