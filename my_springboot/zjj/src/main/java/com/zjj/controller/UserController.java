package com.zjj.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjj.common.QueryPageParam;
import com.zjj.common.Result;
import com.zjj.entity.*;
import com.zjj.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zjj
 * @since 2023-02-16
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private AdminService adminService;
    @Autowired
    private CollegesService collegesService;


    //menu转换
    public List menus(String roleId) {
        //父级
        List<Menu> menuList = menuService.lambdaQuery().eq(Menu::getMenuright, roleId)
//                .notLike(Menu::getMenulevel, "0")
                .notLike(Menu::getMenulevel, null).list();
//        子
        List<Menu> rootMenu = menuService.lambdaQuery().eq(Menu::getMenuright, roleId)
//                .notLike(Menu::getMenuparentcode, "0")
                .isNotNull(Menu::getMenuparentcode).list();

        List list = new ArrayList();
        for (int i = 0; i < menuList.size(); i++) {
            menuList.get(i).setChildListsss(getChild(menuList.get(i).getMenulevel(), rootMenu));
            list.add(menuList.get(i));
        }
        return list;
    }

    public List getChild(String level, List<Menu> menu) {
        List<Menu> l = new ArrayList<>();
        for (int i = 0; i < menu.size(); i++) {
            if (menu.get(i).getMenuparentcode().equals(level))
                l.add(menu.get(i));
        }
        return l;
    }


    //登录
    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> map) {
        //非空判断
        if (StringUtils.isBlank(map.get("no"))) return Result.fail();
        if (StringUtils.isBlank(map.get("password"))) return Result.fail();
        //判断是否管理员登录
        if (map.get("roleId").equals("0")) {
            List list1 = adminService.lambdaQuery().eq(Admin::getNo, map.get("no"))
                    .eq(Admin::getPassword, map.get("password")).list();
            System.out.println("list" + list1);
            if (list1.size() > 0) {
                HashMap res = new HashMap();
                res.put("user", list1);
                res.put("menu", menus("0"));
                return Result.suc(res);
            }

//            //是否教师登录
        } else if (map.get("roleId").equals("1")) {
            List list2 = userService.lambdaQuery().eq(User::getNo, map.get("no"))
                    .eq(User::getPassword, map.get("password")).list();
            if (list2.size() > 0) {
                HashMap res = new HashMap();
                res.put("user", list2);
                res.put("menu", menus("1"));
                return Result.suc(res);
            }
            //是否学生登录
        } else if (map.get("roleId").equals("2")) {
            List list3 = studentService.lambdaQuery().select(Student::getNo, Student::getPassword, Student::getPassword, Student::getName,Student::getSid).eq(Student::getNo, map.get("no"))
                    .eq(Student::getPassword, map.get("password")).list();
            if (list3.size() > 0) {
                HashMap res = new HashMap();
                res.put("user", list3);
                res.put("menu", menus("2"));
                return Result.suc(res);
            }
        }
        return Result.fail();
    }

    //查找id唯一性
    @GetMapping("/findByNo")
    public Result findByNo(@RequestParam String no) {
        if (StringUtils.isBlank(no)) return Result.fail();
        List list = userService.lambdaQuery().eq(User::getNo, no).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

    @PostMapping("/list")
    public Result list() {
        return Result.suc(userService.list());
    }

    //根据id删除
    @PostMapping("del/{id}")
    public Result delById(@PathVariable("id") String id) {
        return userService.removeById(id) ? Result.suc() : Result.fail();
    }

    //新增
    @PostMapping("/save")
    public Result save(@RequestBody User user) {
        try {
            if (user.getNo().length() < 6 || user.getNo().length() > 12) return Result.fail("账号长度需要大于6位小于12位");
            if (StringUtils.isBlank(user.getName())) return Result.fail("姓名不能为空");
//            if (StringUtils.isBlank(user.getAcademy())) return Result.fail("院系不能为空");
            if (StringUtils.isBlank(user.getPassword())) return Result.fail("密码不能为空");
            if (user.getSex() != 1 && user.getSex() != 0) return Result.fail("请选择正确的性别");
            if (user.getPhone().length() != 11) return Result.fail("请输入11位手机号");
            if (user.getAge() < 0 || user.getAge() > 150) return Result.fail("请输入正确年龄");
        } catch (Exception e) {
            return Result.fail();
        }
        return userService.save(user) ? Result.suc() : Result.fail();
    }

    //修改
    @PostMapping("/mod")
    public boolean mod(@RequestBody User user) {
        return userService.updateById(user);
    }

    //新增或修改
    @PostMapping("/saveOrMod")
    public boolean saveOrMod(@RequestBody User user) {
        return userService.saveOrUpdate(user);
    }

    //修改update
    @PostMapping("/update")
    public Result update(@RequestBody User user) {
        try {
            if (user.getNo().length() < 6 || user.getNo().length() > 12) return Result.fail("账号长度需要大于6位小于12位");
            if (StringUtils.isBlank(user.getName())) return Result.fail("姓名不能为空");
//            if (StringUtils.isBlank(user.getAcademy())) return Result.fail("院系不能为空");
            if (StringUtils.isBlank(user.getPassword())) return Result.fail("密码不能为空");
            if (user.getSex() != 1 && user.getSex() != 0) return Result.fail("请选择正确的性别");
            if (user.getPhone().length() != 11) return Result.fail("请输入11位手机号");
            if (user.getAge() < 0 || user.getAge() > 150) return Result.fail("请输入正确年龄");
        } catch (Exception e) {
            return Result.fail();
        }
        return userService.updateById(user) ? Result.suc() : Result.fail();
    }

    //删除
    @PostMapping("/delete")
    public boolean delete(Integer id) {
        return userService.removeById(id);
    }

    //查询(模糊,匹配)
    @PostMapping("/listPageC")
    public List listPage(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(User::getName, name);

//        IPage result = userService.pageC(page);
        IPage result = userService.pageCC(page, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return result.getRecords();
    }

    //查询(模糊,匹配)
    @PostMapping("/listPageC1")
    public Result listPageC(@RequestBody QueryPageParam query) {
        HashMap param = query.getParam();
        String name = (String) param.get("name");
        String academyid = (String) param.get("academyid");
        System.out.println("name==" + (String) param.get("name"));

        Page<User> page = new Page();
        page.setCurrent(query.getPageNum());
        page.setSize(query.getPageSize());

        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(name) && !"null".equals(name)) {
            lambdaQueryWrapper.like(User::getName, name);
        }
        if (StringUtils.isNotBlank(academyid)) {
            lambdaQueryWrapper.eq(User::getAcademyid, academyid);
        }


//        IPage result = userService.pageC(page);
        IPage result = userService.pageCC(page, lambdaQueryWrapper);
        System.out.println("totol===" + result.getTotal());

        return Result.suc(result.getRecords(), result.getTotal());
    }

    //group by 院系, 搜索框的options
    @GetMapping("/academyList")
    public Result findAcademyList() {
        return Result.suc(collegesService.lambdaQuery().groupBy(Colleges::getCname).list());
    }

    //修改个人信息 教师
    @PostMapping("/updateForSomeone")
    public Result updateForSomeone(@RequestBody User user) {
        return userService.updateById(user) ? Result.suc() : Result.fail();
    }

    //找个人信息 教师
    @PostMapping("/findForSomeone")
    public Result findForSomeone(@RequestBody User user) {
        Integer tid = user.getId();
        List list = userService.lambdaQuery().eq(User::getId, tid).list();
        return list.size() > 0 ? Result.suc(list) : Result.fail();
    }

}
