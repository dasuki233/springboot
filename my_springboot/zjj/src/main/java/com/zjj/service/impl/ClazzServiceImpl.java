package com.zjj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Clazz;
import com.zjj.entity.User;
import com.zjj.mapper.ClazzMapper;
import com.zjj.service.ClazzService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@Service
public class ClazzServiceImpl extends ServiceImpl<ClazzMapper, Clazz> implements ClazzService {
    @Autowired
    private ClazzMapper clazzMapper;

    @Override
    public IPage pageCC(Page<Clazz> page, Wrapper wrapper) {
        return clazzMapper.pageCC(page, wrapper);
    }

}
