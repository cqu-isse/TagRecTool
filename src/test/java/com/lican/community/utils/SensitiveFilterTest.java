package com.lican.community.utils;


import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
class SensitiveFilterTest {
    @Autowired
    private SensitiveFilter sensitiveFilter;

    @Test
    void filter() {
        String text = "喝酒后，我想嫖***娼";
        System.out.println(sensitiveFilter.filter(text));
    }
}