package task.homework4;

/**
 * @outher Mr.Wang
 * @project Core_java
 * @date 2025/7/15 015
 */
public class Test4 {
    public static void main(String[] args) {
        Home home=new Home();
        home.setFlag(1);
        home.setNum(5);
        new Thread(()->{
            home.product("生产超级兵",1,2);
        },"生产超级兵线程开始").start();
        new Thread(()->{
            home.product("移动超级兵",2,3);
        },"移动超级兵线程开始").start();
        new Thread(()->{
            home.product("攻击防御塔",3,1);
        },"攻击防御塔线程开始").start();
    }
}
class Home{
    private int flag;
    private int num;

    public Home() {
    }

    public Home(int flag, int num) {
        this.flag = flag;
        this.num = num;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }
    public void product(String opName,int waitFlag,int nextFlag){
        for (int i = 1; i <= num; i++) {
            synchronized (this){
                while (flag!=waitFlag){
                    try {
                        this.wait();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
                System.out.println( Thread.currentThread().getName()+opName);
                flag=nextFlag;
                this.notifyAll();
            }
        }
    }
}