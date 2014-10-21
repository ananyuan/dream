package com.dream;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.dream.model.Task;
import com.dream.model.User;
import com.dream.service.TaskService;
import com.dream.service.UserService;


public class TestUser {
    
    ApplicationContext context = null;
    UserService userService = null;
    TaskService taskService = null;
    
    
    @Before
    public void initContext(){
        this.context = new FileSystemXmlApplicationContext("WebRoot/WEB-INF/applicationContext.xml");
        this.userService = (UserService) context.getBean("userService");
        
        this.taskService = (TaskService)context.getBean("taskService");
    }
    
    
    @Test
    public void countAll(){
        System.out.println("数据库中的记录条数:"  + userService.countAll());
    }
    
    @Test
    public void insert(){
//        User user = new User();
//        user.setUsername("testUserName");
//        user.setPassword("passtest");
//        user.setEmail("xxx@163.com");
//        user.setSex("男");
//        user.setAge(23);
//        userService.insert(user);
        
        
        Task task = new Task();
        task.setTitle("第二条Spring");
        task.setDescp("jar包");
        task.setType(1);
        task.setStart("2014-10-21");
        task.setEnd("2014-10-21");
        
        taskService.insert(task);
    }
    
    @Test
    public void selectAll(){
        List<User> list = userService.selectAll();
        for(int i=0; i<list.size(); i++){
            User user = list.get(i);
            System.out.println("用户名:" + user.getUsername() + "\t密码:" + user.getPassword() + "\t邮箱：" + user.getEmail());
        }
    }
    
    @Test
    public void update(){
        User user = new User();
        user.setUsername("testUserName");
        user.setPassword("xxxxxxxx");
        user.setEmail("xxxxxx@163xxx");
        user.setSex("男");
        user.setAge(23);
        userService.update(user);
    }
    
    @Test
    public void delete(){
        userService.delete("testUserName");
    }
    
    @Test
    public void findByName(){
        User user = userService.findByUserName("testUserName");
        System.out.println("用户名:" + user.getUsername() + "\t密码:" + user.getPassword() + "\t邮箱：" + user.getEmail());

    }
}