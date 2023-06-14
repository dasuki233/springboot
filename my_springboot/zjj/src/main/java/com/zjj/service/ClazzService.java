package com.zjj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Clazz;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjj.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
public interface ClazzService extends IService<Clazz> {

    IPage pageCC(Page<Clazz> page, Wrapper wrapper);
}
