package com.zjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.common.QueryPageParam;
import com.zjj.common.Result;
import com.zjj.entity.Job;
import com.zjj.entity.News;
import com.zjj.entity.Student;
import com.zjj.entity.User;
import com.zjj.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  新闻公告前端控制器
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@RestController
@RequestMapping("/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    //发布新闻 保存
    @PostMapping("/saveNews")
    public Result save(@RequestBody News news) {
        return newsService.save(news) ? Result.suc(news) : Result.fail();
    }

    //新闻模糊查询
    @PostMapping("/newsList")
    public Result newList(@RequestBody QueryPageParam query) {
//        System.out.println(query);
        HashMap param = query.getParam();
        String title = (String) param.get("title");

        Page<News> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<News> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(title)) {
            lambdaQueryWrapper.like(News::getTitle, title);
        }

        IPage result = newsService.pageCC(page, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

    //修改新闻
    @PostMapping("/update")
    public Result update(@RequestBody News news) {
        return newsService.updateById(news) ? Result.suc() : Result.fail();
    }


    //根据id删除新闻
    @PostMapping("/del")
    public Result delJobById(@RequestBody News news) {
        return newsService.removeById(news.getNewsid()) ? Result.suc() : Result.fail();
    }
}
