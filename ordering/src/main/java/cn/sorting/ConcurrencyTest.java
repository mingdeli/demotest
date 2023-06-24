package cn.sorting;

import org.openjdk.jcstress.annotations.*;
import org.openjdk.jcstress.infra.results.II_Result;

/**
 * 高并发测试：期待的结果分别是1，4，另一种结果是0；验证31、32行代码指令重排序的问题；
 * 使用java -jar 来运行
 */

@JCStressTest
@Outcome(id = {"1" ,"4"}, expect = Expect.ACCEPTABLE, desc = "Default outcome.")
@Outcome(id = "0", expect = Expect.ACCEPTABLE_INTERESTING, desc = "ordering！！！")
@State
public class ConcurrencyTest {

    int num = 0;
    boolean ready = false;

    @Actor
    public void actor1(II_Result r) {
        if (ready) {
            r.r1 = num + num;
        } else {
            r.r1 = 1;
        }
    }

    @Actor
    public void actor2(II_Result r) {
        num = 2;
        ready = true;
    }

}
