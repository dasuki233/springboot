package com.zjj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.common.Result;
import com.zjj.entity.Job;
import com.zjj.entity.User;
import com.zjj.mapper.JobMapper;
import com.zjj.mapper.UserMapper;
import com.zjj.service.JobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {
    @Autowired
    private JobMapper jobMapper;
    @Override
    public void F5Time() {
        jobMapper.F5Time();
    }

    @Override
    public boolean saveForm(Job job) {
        return jobMapper.saveForm(job);
    }

    @Override
    public IPage pageCC(Page<Job> page, Wrapper wrapper) {
        return jobMapper.pageCC(page, wrapper);
    }

}
