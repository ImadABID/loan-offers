package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void testContext(){
        // Just testing if the context can be built properly
    }

    @Test
    void testMainMethod(){
        DemoApplication.main(new String[0]);
    }

}
