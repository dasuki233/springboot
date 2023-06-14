package com.zjj.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.News;
import com.zjj.entity.Saraly;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
public interface SaralyService extends IService<Saraly> {
    IPage pageCC(Page<Saraly> page, Wrapper wrapper);
    IPage studentSalary(Page<Saraly> page, Integer sid, Wrapper wrapper);
    IPage PaysalaryList(Page<Saraly> page, Wrapper wrapper);

    boolean pay(Double money,Integer saralyid);
}
