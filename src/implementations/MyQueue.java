package implementations;

import exceptions.EmptyQueueException;
import utilities.Iterator;
import utilities.QueueADT;
import implementations.MyDLL;

public class MyQueue<E> implements QueueADT<E> {

    private MyDLL<E> list; // 使用双向链表存储队列数据

    // 构造方法
    public MyQueue() {
        list = new MyDLL<>();
    }

    @Override
    public void enqueue(E toAdd) throws NullPointerException {
        if (toAdd == null) {
            throw new NullPointerException("Cannot add null to the queue.");
        }
        list.add(toAdd); // 添加到队列尾部
    }

    @Override
    public E dequeue() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        return list.remove(0); // 移除队列头部元素
    }

    @Override
    public E peek() throws EmptyQueueException {
        if (isEmpty()) {
            throw new EmptyQueueException();
        }
        return list.get(0); // 返回队列头部元素
    }

    @Override
    public void dequeueAll() {
        list.clear(); // 清空队列
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty(); // 检查队列是否为空
    }

    @Override
    public boolean contains(E toFind) throws NullPointerException {
        if (toFind == null) {
            throw new NullPointerException("Cannot search for null in the queue.");
        }
        return list.contains(toFind); // 使用 MyDLL 的 contains 方法
    }

    @Override
    public int search(E toFind) {
    	if (toFind == null) {
            throw new NullPointerException("The element to find cannot be null.");
        }

        Iterator<E> iterator = list.iterator(); // 使用 MyDLL 的迭代器遍历
        int index = 1; // 队列的 1-based 索引

        while (iterator.hasNext()) {
            if (toFind.equals(iterator.next())) {
                return index; // 找到时返回索引
            }
            index++;
        }

        return -1; // 未找到时返回 -1
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator(); // 使用 MyDLL 的迭代器
    }

    @Override
    public boolean equals(QueueADT<E> that) {
        if (that == null || that.size() != this.size()) {
            return false;
        }
        Iterator<E> thisIter = this.iterator();
        Iterator<E> thatIter = that.iterator();
        while (thisIter.hasNext() && thatIter.hasNext()) {
            if (!thisIter.next().equals(thatIter.next())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Object[] toArray() {
        return list.toArray(); // 返回对象数组
    }

    @Override
    public E[] toArray(E[] holder) throws NullPointerException {
        if (holder == null) {
            throw new NullPointerException("Array cannot be null.");
        }
        return list.toArray(holder); // 调用 MyDLL 的 toArray 方法
    }

    @Override
    public boolean isFull() {
        return false; // 队列基于链表实现，无容量限制
    }

    @Override
    public int size() {
        return list.size(); // 返回队列的大小
    }
}
