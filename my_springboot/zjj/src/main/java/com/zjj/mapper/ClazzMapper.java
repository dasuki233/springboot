package com.zjj.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Clazz;
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
public interface ClazzMapper extends BaseMapper<Clazz> {

    IPage pageCC(Page<Clazz> page, @Param(Constants.WRAPPER) Wrapper wrapper);
}
