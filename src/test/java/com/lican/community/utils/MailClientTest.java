package com.lican.community.utils;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;



@SpringBootTest
class MailClientTest {

    @Autowired
    private MailClient mailClient;

    @Autowired
    private TemplateEngine templateEngine;
    @Test
    void senMail() {
        mailClient.senMail("942433951@qq.com", "hello", "汪汪汪");
    }

    @Test
    void senMailWithHtml() {
        Context context = new Context();
        context.setVariable("username","kingtiger");
        String content = templateEngine.process("/mail/activation.html",context);
        mailClient.senMail("1445079235@qq.com", "汪汪汪", content);
    }
}