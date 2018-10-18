package com.migo.task;

import java.lang.reflect.Method;

/**
 * @author jerry.chen
 * @date 2018/9/3 14:45
 **/

public class Test {


    private void test(String a) {
        System.out.println("..............."+a);
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.test("2233");

        for (Method method : test.getClass().getMethods()) {
            System.out.println(test.getClass().getName()+method);
        }
    }
}
