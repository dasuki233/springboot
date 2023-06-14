package com.zjj;

import org.junit.jupiter.api.Test;

/**
 * @auther zjj
 * @date 2023/3/7 4:09
 * @description:
 */
public class FileTest {
    @Test
    public void g(){
        String currentPath=System.getProperty("user.dir");
        currentPath = currentPath.replace("my_springboot\\zjj", "") + "zjj-web/src/assets/images/";
        System.out.println(currentPath);
    }
}
