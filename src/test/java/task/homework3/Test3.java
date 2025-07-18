package task.homework3;

/**
 * @outher Mr.Wang
 * @project Core_java
 * @date 2025/7/15 015
 */
public class Test3 {
    static final Object LOCK=new Object();
    static boolean a=false;
    public static void main(String[] args) {
        Thread attack = new Thread(()->{
            synchronized (LOCK){
                while (!a){
                    System.out.println("没有产生超级兵");
                    try {
                        LOCK.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println(Thread.currentThread().getName()+"攻击防御塔");
            }
        },"攻击防御塔线程开始");
        Thread produce=new Thread(()->{
            synchronized (LOCK){
                System.out.println(Thread.currentThread().getName()+"生产超级兵");
                a=true;
                LOCK.notify();
            }
        },"生产超级兵线程开始");
        attack.start();
        produce.start();
    }
}
