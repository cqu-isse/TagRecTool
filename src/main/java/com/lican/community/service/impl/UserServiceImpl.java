package com.lican.community.service.impl;

import com.lican.community.entity.UserEntity;
import com.lican.community.mapper.UserMapper;
import com.lican.community.service.UserService;
import com.lican.community.utils.CommunityConstant;
import com.lican.community.utils.CommunityUtils;
import com.lican.community.utils.MailClient;
import org.apache.commons.lang3.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService, CommunityConstant {
    //private Logger logger = LoggerFactory.getLogger(UserService.class);
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;


//    @Value("${community.path.domain}")
//    private String domain;
//
//    @Value("${server.servlet.context-path}")
//    private String contextPath;

    @Override
    public UserEntity findUserById(int id) {
        return userMapper.selectById(id);
    }

    public Map<String, Object> register(UserEntity u){
        //检查参数是否合法
        Map<String, Object> map = new HashMap<>();
        if(u==null){
            map.put("msg","参数不能为空!");
            //throw new IllegalArgumentException("参数不能为空!");
            return map;
        }

        if(StringUtils.isAllBlank(u.getUserName(),u.getPassword(),u.getEmail())){
            ////logger.info("参数不能为空，用户名，密码，邮箱不能为空为空，userName  ::: {}, password ::: {}, email ::: {}", u.getUserName(), u.getPassword(), u.getEmail());
            map.put("msg","用户名，密码，邮箱不能为空!");
            return map;
        }

        //验证参数是否已经存在
        //验证用户名
        UserEntity user1 = userMapper.selectByName(u.getUserName());
        System.out.println(user1);
        if(user1!=null) {
            //logger.info("userName ::: {} 已经存在!", u.getUserName());
            map.put("msg",String.format("userName ::: %s 已经存在!", u.getUserName()));
            return map;
        }

        //验证邮箱
        UserEntity user2 = userMapper.selectByEmail(u.getEmail());
        if(user2!=null) {
            //logger.info("email ::: {} 已经存在!", u.getEmail());
            map.put("msg",String.format("email ::: %s 已经存在!", u.getEmail()));
            return map;
        }

        //普通用户
        u.setSalt(CommunityUtils.generateUUID().substring(0,5));
        u.setPassword(CommunityUtils.md5(u.getPassword()+u.getSalt()));
        u.setType(0);
        u.setStatus(0);
        u.setActivationCode(CommunityUtils.generateUUID());
        u.setHeaderUrl(String.format("http://images.nowcoder.com/head/%dt.png",new Random().nextInt(1000)));
        userMapper.insertUser(u);

//        String url = domain + contextPath + "/activation/" + u.getId() + "/" + u.getActivationCode();
//        Context context = new Context();
//        context.setVariable("url",url);
//        String content = templateEngine.process("/mail/activation",context);
//        mailClient.senMail(u.getEmail(),"账户激活",content);

        return map;
    }

    public int activation(int userId, String code){
        UserEntity u = userMapper.selectById(userId);
        if(u.getStatus() == 1){
            return ACTIVATE_REPEAT;
        }else if(u.getActivationCode().equals(code)){
            userMapper.updateStatus(userId, 1);
            return ACTIVATE_SUCCESS;
        }else{
            return ACTIVATE_FAILED;
        }
    }
}
