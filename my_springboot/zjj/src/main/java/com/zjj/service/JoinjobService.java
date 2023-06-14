package com.zjj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Job;
import com.zjj.entity.JoinJobVO;
import com.zjj.entity.Joinjob;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
public interface JoinjobService extends IService<Joinjob> {
    IPage pageCC(Page<JoinJobVO> page, Integer tId, String jobname, String name);

    IPage pageCC1(Page<Job> page, Wrapper wrapper);
    IPage joinjobStudent(Page<JoinJobVO> page, String jobname, Integer sid);
}
