package task.homework5;

import java.util.LinkedList;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @outher Mr.Wang
 * @project Core_java
 * @date 2025/7/15 015
 */
public class Test5 {
    public static void main(String[] args) {
        MessageQueue queue=new MessageQueue(2);
        for (int i = 1; i <=3 ; i++) {
            new Thread(()->{
                String id=IdUtil.getId();
                Message message=new Message(id,"消息信息"+id);
                queue.put(message);
            },"生产者："+i).start();
        }
        new Thread(()->{
            while (true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                    queue.take();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        },"消费者").start();
    }
}
class Message{
    private String id;
    private Object value;

    public Message() {
    }

    public Message(String id, Object value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id='" + id + '\'' +
                ", value=" + value +
                '}';
    }
}
class MessageQueue{
    private LinkedList<Message> list;
    private static final Object LOCK=new Object();
    private int capacity;
    public MessageQueue(int capacity){
        this.capacity=capacity;
        list=new LinkedList<>();
    }
    public Message take(){
        synchronized (LOCK){
            while (list.isEmpty()){
                System.out.println("消息队列无内容请等待");
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            Message message = list.removeFirst();
            LOCK.notifyAll();
            System.out.println("获取一个消息："+message);
            return message;
        }
    };
    public void put(Message message){
        synchronized (LOCK){
            while (list.size()==capacity){
                System.out.println("消息队列已满请等待");
                try {
                    LOCK.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            list.addLast(message);
            System.out.println(Thread.currentThread().getName()+"生产了一个消息："+message);
            LOCK.notifyAll();
        }
    }
}
class IdUtil{
    public static String getId(){
        return UUID.randomUUID().toString().replace("-","");
    }
}