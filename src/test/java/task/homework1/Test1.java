package task.homework1;

import java.util.concurrent.TimeUnit;

/**
 * @outher Mr.Wang
 * @project Core_java
 * @date 2025/7/15 015
 */
public class Test1 {
    public static void main(String[] args) {
        Student student=new Student();
        Thread studentThread=new Thread(student,"学生");
        Teacher teacher=new Teacher(studentThread);
        Thread teacherThread=new Thread(teacher,"老师");
        studentThread.start();
        teacherThread.start();
    }

}
class Student implements Runnable{

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"开始睡觉");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName()+"被吵醒");
        }
        System.out.println(Thread.currentThread().getName()+"开始上课");
    }
}
class Teacher implements Runnable{
    private Thread thread;

    public Teacher(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName()+"进入教室上课");
        for (int i = 1; i <= 3; i++) {
            System.out.println("上课");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        thread.interrupt();
    }
}
