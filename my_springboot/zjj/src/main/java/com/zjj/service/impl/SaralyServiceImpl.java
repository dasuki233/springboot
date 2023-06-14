package com.zjj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.Job;
import com.zjj.entity.Saraly;
import com.zjj.entity.User;
import com.zjj.mapper.SaralyMapper;
import com.zjj.service.SaralyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@Service
public class SaralyServiceImpl extends ServiceImpl<SaralyMapper, Saraly> implements SaralyService {
    @Autowired
    private SaralyMapper saralyMapper;

    @Override
    public IPage pageCC(Page<Saraly> page, Wrapper wrapper) {
        return saralyMapper.pageCC(page, wrapper);
    }

    @Override
    public IPage studentSalary(Page<Saraly> page, Integer sid, Wrapper wrapper) {
        return saralyMapper.studentSalary(page, sid, wrapper);
    }


    @Override
    public IPage PaysalaryList(Page<Saraly> page, Wrapper wrapper) {
        return saralyMapper.PaysalaryList(page, wrapper);
    }

    @Override
    public boolean pay(Double money,Integer saralyid) {
        return saralyMapper.pay(money,saralyid);
    }
}
