package com.zjj.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Job;
import com.zjj.entity.JoinJobVO;
import com.zjj.entity.Joinjob;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface JoinjobMapper extends BaseMapper<Joinjob> {

    //模糊查询
    IPage pageCC(Page<JoinJobVO> page, Integer tId, String jobname, String name);

    IPage pageCC1(Page<Job> page, @Param(Constants.WRAPPER) Wrapper wrapper);

    IPage joinjobStudent(Page<JoinJobVO> page, String jobname, Integer sid);
}
