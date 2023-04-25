package org.example.tgbd.queue;

import org.example.tgbd.dto.TimeDto;

// Класс для представления queue
public class Queue
{
    private TimeDto[] arr;      // массив для хранения элементов queue
    private int front;      // front указывает на передний элемент в queue
    private int rear;       // задняя часть указывает на последний элемент в queue
    private int capacity;   // максимальная емкость queue
    private int count;      // текущий размер queue

    // Конструктор для инициализации queue
    public Queue(int size)
    {
        arr = new TimeDto[size];
        capacity = size;
        front = 0;
        rear = -1;
        count = 0;
    }

    // Вспомогательная функция для удаления переднего элемента из очереди
    public TimeDto dequeue()
    {
        // проверка на опустошение queue
        if (isEmpty())
        {
            System.out.println("Underflow\nProgram Terminated");
            System.exit(-1);
        }

        TimeDto x = arr[front];

        System.out.println("Removing " + x);

        front = (front + 1) % capacity;
        count--;

        return x;
    }

    // Вспомогательная функция для добавления элемента в queue
    public void enqueue(TimeDto item)
    {
        // проверка на переполнение queue
        if (isFull())
        {
            System.out.println("Overflow\nProgram Terminated");
            System.exit(-1);
        }

        System.out.println("Inserting " + item);

        rear = (rear + 1) % capacity;
        arr[rear] = item;
        count++;
    }

    // Вспомогательная функция для возврата первого элемента queue
    public TimeDto peek()
    {
        if (isEmpty())
        {
            System.out.println("Underflow\nProgram Terminated");
            System.exit(-1);
        }
        TimeDto el = arr[front];
        dequeue();
        return el;
    }

    // Вспомогательная функция для возврата размера queue
    public int size() {
        return count;
    }

    // Вспомогательная функция для проверки, пуста ли queue или нет
    public boolean isEmpty() {
        return (size() == 0);
    }

    // Вспомогательная функция для проверки того, заполнена ли queue или нет
    public boolean isFull() {
        return (size() == capacity);
    }
}

