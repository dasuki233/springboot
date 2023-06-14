package com.zjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.common.QueryPageParam;
import com.zjj.common.Result;
import com.zjj.entity.*;
import com.zjj.service.ClazzService;
import com.zjj.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/student")
public class StudentController {
    @Autowired
    private StudentService studentService;
    @Autowired
    private ClazzService clazzService;

    //查询(模糊,匹配)
    @PostMapping("/listPageC1")
    public Result listPageC(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String clazzId = (String) param.get("clazzId");//班级
        String cid = (String) param.get("cid");//院系
        System.out.println("param: " + param);

        Page<Student> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name)) {
            lambdaQueryWrapper.like(Student::getName, name);
        }
        if (StringUtils.isNotBlank(cid)) {
            lambdaQueryWrapper.eq(Student::getCollegesid, cid);
        }
        if (StringUtils.isNotBlank(clazzId)) {
            lambdaQueryWrapper.eq(Student::getClzid, clazzId);
        }


//        IPage result = userService.pageC(page);
        IPage result = studentService.pageCC(page, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

    //查找账号唯一性
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        if (StringUtils.isBlank(no)) return Result.fail();
        List list = studentService.lambdaQuery().select(Student::getNo).eq(Student::getNo, no).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    //根据院系返回班级
    @GetMapping("findClazzList/{cid}")
    public Result findClazz(@PathVariable("cid") String cid) {
        if (StringUtils.isBlank(cid)) return Result.fail();
        return Result.suc(clazzService.lambdaQuery().select(Clazz::getClzid,Clazz::getClazz,Clazz::getCid).eq(Clazz::getCid, cid).list());
    }

    //返回所有班级
    @GetMapping("/findAllClazz")
    public Result findAllClazz() {
        return Result.suc(clazzService.lambdaQuery().select(Clazz::getClzid,Clazz::getClazz).list());
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody Student student) {
        try {
            if (student.getNo().length() < 6 || student.getNo().length() > 12) return Result.fail("账号长度需要大于6位小于12位");
            if (StringUtils.isBlank(student.getName())) return Result.fail("姓名不能为空");
//            if (StringUtils.isBlank(user.getAcademy())) return Result.fail("院系不能为空");
            if (StringUtils.isBlank(student.getPassword())) return Result.fail("密码不能为空");
            if (student.getSex() != 1 && student.getSex() != 0) return Result.fail("请选择正确的性别");
            if (student.getPhone().length() != 11) return Result.fail("请输入11位手机号");
            if (student.getAge() < 0 || student.getAge() > 150) return Result.fail("请输入正确年龄");
        } catch (Exception e) {
            return Result.fail();
        }
        return studentService.save(student) ? Result.suc() : Result.fail();
    }

    //修改update
    @PostMapping("/update")
    public Result update(@RequestBody Student student) {
        try {
            if (student.getNo().length() < 6 || student.getNo().length() > 12) return Result.fail("账号长度需要大于6位小于12位");
            if (StringUtils.isBlank(student.getName())) return Result.fail("姓名不能为空");
//            if (StringUtils.isBlank(user.getAcademy())) return Result.fail("院系不能为空");
            if (StringUtils.isBlank(student.getPassword())) return Result.fail("密码不能为空");
            if (student.getSex() != 1 && student.getSex() != 0) return Result.fail("请选择正确的性别");
            if (student.getPhone().length() != 11) return Result.fail("请输入11位手机号");
            if (student.getAge() < 0 || student.getAge() > 150) return Result.fail("请输入正确年龄");
        } catch (Exception e) {
            return Result.fail();
        }
        return studentService.updateById(student) ? Result.suc() : Result.fail();
    }

    //根据id删除
    @PostMapping("del/{id}")
    public Result delById(@PathVariable("id") String id) {
        return studentService.removeById(id) ? Result.suc() : Result.fail();
    }
}
