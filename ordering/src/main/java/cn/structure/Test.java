package cn.structure;

import cn.design.Cheese;
import cn.design.NYChessFactory;
import cn.design.PizzaIngredientFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;

import java.lang.reflect.Proxy;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;


@Slf4j
public class Test {
    String str = "good";
    char[] ch = {'t', 'e', 's', 't'};

    public void change(String str, char[] c) {
        str = "nihao";
        c[0] = 'b';
    }

    public static void main(String[] args) {
        Test test = new Test();
        test.change(test.str, test.ch);
        System.out.println(test.str);
        System.out.println(test.ch);

        //动态代理：
        NYChessFactory ny = new NYChessFactory();
        PizzaIngredientFactory pp = (PizzaIngredientFactory) Proxy.newProxyInstance(ny.getClass().getClassLoader(), ny.getClass().getInterfaces(),
                (proxy, method, args1) -> {

                    Object invoke = null;
                    if (args1 != null && "createCheese".equals(method.getName())) {
                        int a = (int) args1[0];
                        invoke = method.invoke(ny, a+3);
                    }
                    return invoke;
                });
        Cheese cheese = pp.createCheese(58);
        System.out.println(cheese);

        //CGlibdail
        NYChessFactory nyf = (NYChessFactory) Enhancer.create(ny.getClass(), (MethodInterceptor) (o, method, objects, methodProxy) -> {
            Object invoke = null;
            if (objects != null && "createCheese".equals(method.getName())) {
                int a = (int) objects[0];
                invoke = method.invoke(ny, a + 8);
            }
            return invoke;
        });
        Cheese c = nyf.createCheese(58);
        System.out.println(c);
//        timeTest();
//        testDateFormater();
    }

    @org.junit.Test
    public void dff() {
        log.info("fdff");
        System.out.println("fdffdfffff");
    }

    //不可变和线程安全
//    DateTimeFormatter
    public static void testDateFormater() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                System.out.println(df.parse("2022.05.06"));
            }).start();
        }
    }


    //这个方法不是线程安全的
    private static void timeTest() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < 100; i++) {
            int a = i;
            new Thread(() -> {
//                synchronized (Test.class) {
                try {
                    System.out.println(a + " " + sdf.parse("2020-12-23"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
//                }
            }).start();
        }
    }
}
