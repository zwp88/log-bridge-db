package task.homework2;

import java.util.concurrent.TimeUnit;

/**
 * @outher Mr.Wang
 * @project Core_java
 * @date 2025/7/15 015
 */
public class Test2 {
    public static void main(String[] args) {
        PassCave cave=new PassCave();
        new Thread(cave::pass,"李白").start();
        new Thread(cave::pass,"杜甫").start();
        new Thread(cave::pass,"白居易").start();
    }
}
class PassCave{
    public synchronized void pass(){
        new Thread(()->{});
        System.out.println(Thread.currentThread().getName()+"进入山洞");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Thread.currentThread().getName()+"走出山洞");
    }
}

