package com.zjj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.common.Result;
import com.zjj.entity.Job;
import com.zjj.entity.JoinJobVO;
import com.zjj.entity.Joinjob;
import com.zjj.mapper.JobMapper;
import com.zjj.mapper.JoinjobMapper;
import com.zjj.mapper.MessageMapper;
import com.zjj.service.JoinjobService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@Service
public class JoinjobServiceImpl extends ServiceImpl<JoinjobMapper, Joinjob> implements JoinjobService {

    @Autowired
    private JoinjobMapper joinjobMapper;


    @Override
    public IPage pageCC(Page<JoinJobVO> page, Integer tId, String jobname, String name) {
        return joinjobMapper.pageCC(page, tId, jobname, name);
    }

    @Override
    public IPage pageCC1(Page<Job> page, Wrapper wrapper) {
        return joinjobMapper.pageCC1(page, wrapper);
    }

    @Override
    public IPage joinjobStudent(Page<JoinJobVO> page,  String jobname, Integer sid) {
        return joinjobMapper.joinjobStudent(page, jobname, sid);
    }

}
