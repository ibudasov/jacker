/**
 * Класс для сохранения данных
 *
 * @version 0.1
 * @package standard
 * @author Roman Malinkin <zingnet86@gmail.com>
 * @copyright Copyright (c) 2013, Roman Malinkin
 */

package com.company;

import java.io.*;

public class Storage {

    /**
     * Двумерный массив с задачами.
     * Ключ записи - <i> задачи.
     * Поля записи - <a> старта и финиша, имя задачи
     */
    public String[][] tasks;


    public Storage() {
        this.tasks = new String[100][];
    }


    /**
     * Добавление задачи
     */
    public String[] add(String[] task) {

        String[][] data = getTasks();

        for (int i = 0; i < data.length; i++) {
            if (data[i] == null) {
                data[i] = task;
                break;
            }
        }

        store(data);

        return task;
    }

    /**
     * Изменение задачи
     */
    public void set(int id, String finishTime) {

        String[][] data = getTasks();
        data[id][2] = finishTime;
        store(data);

        return;
    }


    /**
     * Воpвращает задачу по айдишнику
     */
    public String[] get(Integer id) {

        String[][] data = getTasks();
        System.out.println(data[0][0]);
        return data[id];
    }

    //@todo: Сделать метод getAndClose - для взятия задачи иприсваивания ей метки завершённости

    /**
     * Воpвращает id задачи по имени,
     * если нет задачи с заданным именем -1
     */
    public int getIdByName(String name) {

        String[][] data = getTasks();

        for (int i = 0; i < data.length; i++)

            if (data[i] != null) {

                if (data[i][0].equalsIgnoreCase(name)) {
                    return i;
                }
            }

        return -1;
    }

    /**
     * Удаление задачи по id
     */
    public boolean delete(Integer id) {

        String[][] data = getTasks();

        tasks[id] = new String[]{};

        store(data);

        return true;
    }

    /**
     * Удаление всех задач
     */
    public boolean truncate() {

        store(new String[100][]);

        return true;
    }


    /**
     * Загрузка данных
     */
    public String[][] getTasks() {

        try {
            FileInputStream fis = new FileInputStream("storage.dat");

            ObjectInputStream iis = new ObjectInputStream(fis);

            tasks = (String[][]) iis.readObject();

            fis.close();

            iis.close();

        } catch (Exception e) {

        }

        return tasks;
    }

    /**
     * Сохранение данных
     */
    public void store(String[][] data) {

        try {

            FileOutputStream fos = new FileOutputStream("storage.dat");

            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(data);

            fos.close();

            oos.close();

        } catch (Exception e) {

        }

        data = this.tasks;
    }


    //public String taskToString(String[] task) {

    //    String result = new String();

    //    for (int i = 0; i < task.length; i++) {
    //        result = task[i] + " ";
    //    }

    //    return result;
    //}
}