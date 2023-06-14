package com.zjj.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.entity.News;
import com.zjj.entity.User;
import com.zjj.mapper.NewsMapper;
import com.zjj.mapper.UserMapper;
import com.zjj.service.NewsService;
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
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
    @Autowired
    private NewsMapper newsMapper;
    @Override
    public IPage pageCC(Page<News> page, Wrapper wrapper) {
        return newsMapper.pageCC(page, wrapper);
    }
}
