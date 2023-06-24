package com.ldm.testplugin.controller;

import com.ldm.hello.service.Helloservice;
import com.ldm.testplugin.config.CarProperties;
import com.ldm.testplugin.po.MyUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class SayHello {

    @Autowired
    Helloservice helloservice;

    @Autowired
    CarProperties carProperties;

    @GetMapping("/hi")
    public String sayhello(String name ){
        String s = helloservice.sayHello(name);
        System.out.println(s);
        return s;
    }

    @GetMapping("/getProperty")
    public CarProperties getProperties(@RequestParam(value = "num", required = false, defaultValue = "20") int num){
        List list = new ArrayList();
        for (int i = 0; i < num; i++) {
            HashMap<Object, Object> m = new HashMap<>();
            m.put("a", new MyUser("name-",3,"addr-"));
            list.add(m);
        }
        log.debug("wrwrrrr");
//        carProperties.setList(list);
        System.out.println("已经完成。。。"+num);
        return carProperties;
    }

    @GetMapping("/print")
    public void printHi() throws InterruptedException {

        while (true) {
            run();
            TimeUnit.SECONDS.sleep(1);
        }
    }
    private static Random random = new Random();

    private int illegalArgumentCount = 0;


    public void run() {
        try {
            int number = random.nextInt() / 10000;
            List<Integer> primeFactors = primeFactors(number);
            print(number, primeFactors);

        } catch (Exception e) {
            System.out.println(String.format("illegalArgumentCount:%3d, ", illegalArgumentCount) + e.getMessage());
        }
    }

    public static void print(int number, List<Integer> primeFactors) {
        StringBuffer sb = new StringBuffer(number + "=");
        for (int factor : primeFactors) {
            sb.append(factor).append('*');
        }
        if (sb.charAt(sb.length() - 1) == '*') {
            sb.deleteCharAt(sb.length() - 1);
        }
        System.out.println(sb);
    }

    public List<Integer> primeFactors(int number) {
        if (number < 2) {
            illegalArgumentCount++;
            throw new IllegalArgumentException("number is: " + number + ", need >= 2");
        }

        List<Integer> result = new ArrayList<Integer>();
        int i = 2;
        while (i <= number) {
            if (number % i == 0) {
                result.add(i);
                number = number / i;
                i = 2;
            } else {
                i++;
            }
        }

        return result;
    }
}

