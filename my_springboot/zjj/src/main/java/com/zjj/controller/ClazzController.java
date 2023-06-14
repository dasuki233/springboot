package com.zjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.common.QueryPageParam;
import com.zjj.common.Result;
import com.zjj.entity.Clazz;
import com.zjj.entity.News;
import com.zjj.entity.Student;
import com.zjj.entity.User;
import com.zjj.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author zjj
 * @since 2023-02-17
 */
@RestController
@RequestMapping("/clazz")
public class ClazzController {
    @Autowired
    private ClazzService clazzService;


    //查询(模糊,匹配)
    @PostMapping("/listPageC")
    public Result listPageC(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String clazz = (String) param.get("clazz");
        String clzId = (String) param.get("clzId");
        String collegesId = (String) param.get("collegesId");

        Page<Clazz> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Clazz> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(clazz) && !"null".equals(clazz)) {
            lambdaQueryWrapper.like(Clazz::getClazz, clazz);
        }
        if (StringUtils.isNotBlank(clzId) && !"null".equals(clzId)) {
            lambdaQueryWrapper.like(Clazz::getClzid, clzId);
        }
        if (StringUtils.isNotBlank(collegesId)) {
            lambdaQueryWrapper.eq(Clazz::getCollegesid, collegesId);
        }

        IPage result = clazzService.pageCC(page, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

    //查找班级唯一性
    @PostMapping("/findByClazz")
    public Result findByNo(@RequestBody Clazz clazz) {
        String clazz1 = clazz.getClazz();
        System.out.println(clazz1);
        List<Clazz> list = new ArrayList<>();
        if (StringUtils.isNotBlank(clazz1))
            list = clazzService.lambdaQuery().select(Clazz::getClazz).eq(Clazz::getClazz, clazz1).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    //修改班级信息
    @PostMapping("/update")
    public Result update(@RequestBody Clazz clazz) {
        return clazzService.updateById(clazz) ? Result.suc() : Result.fail();
    }


    //根据id删除班级
    @PostMapping("/del")
    public Result delJobById(@RequestBody Clazz clazz) {
        return clazzService.removeById(clazz.getClzid()) ? Result.suc() : Result.fail();
    }

    //发布新闻 保存
    @PostMapping("/saveClazz")
    public Result save(@RequestBody Clazz clazz) {
        return clazzService.save(clazz) ? Result.suc(clazz) : Result.fail();
    }
}
