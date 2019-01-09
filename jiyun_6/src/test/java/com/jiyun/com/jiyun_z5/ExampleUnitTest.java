package com.jiyun.com.jiyun_z5;

import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void testRadom(){
        Random random = new Random();//指定种子数字
        int index = random.nextInt(100);

        System.out.println("xx="+index);
    }
}