package com.zjj.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Job;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjj.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@Mapper
public interface JobMapper extends BaseMapper<Job> {

    //更新过期状态
    void F5Time();
    //保存
    boolean saveForm(Job job);
    //模糊查询
    IPage pageCC(Page<Job> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
