/**
 * Класс ядра программы.
 * Сама программа должна принимать от юзера команду старта,
 * и записывать в хранилище время старта.
 * Дальше, при следующем запуске, проверять хранилище на незавершенные задачи,
 * и показывать сколько юзер проработал,
 * и предлагать завершить. Дальше - сохранение и конец.
 *
 * @version 0.1
 * @package standard
 * @author Taras Narizhniy <maximnow@gmail.com>
 * @copyright Copyright (c) 2013, Taras Narizhniy
 */

package com.company;

import javax.swing.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;


public class Core {

    private Request req = new Request();
    private Response res = new Response();
    private Storage st = new Storage();

    public void Core() {

        //TimeZone.setDefault(TimeZone.getTimeZone("GMT+0")); /** это нужно чтобы SimpleDateFormat не добавлял +2 часа нашей таймзоны */

        //@todo: получать из хранилища незавершенную задачу, если возможно
        //@todo: показывать ее юзернейму, если возможно, типа: сейчас есть незавершенная задача

        // приветствуем юзернейма
        // получаем команду Start или Finish
        //String inputCommand = JOptionPane.showInputDialog  //получаем команду с помощью окна
        //        ("Start or Finish?");
        String inputCommand = req.get("Введите 'start' для начала новой задачи, или 'finish' для окончания текущей.");
        res.setAndSend("Значение inputCommand: " + inputCommand); /** проверка состояния переменной inputCommand*/

        //st.add(new String[] {"Название задачи", "2013-10-10 11:12:13", "2013-10-10 11:12:14"});
        //int id = st.getIdByName("Название задачи" );
        //String[] task = st.get(id);

        if (inputCommand.equalsIgnoreCase("start")) {
            //@todo: получать не только команду старта, а еще и парсить как-то и имя
            //String inputTask = JOptionPane.showInputDialog   //получаем имя задачи с помощью окна
            //     ("Что делаем?");
            Date openTime = new Date();
            String opTime = formatTime(openTime);
            //String opTime = openTime.toString();
            res.setAndSend("Значение opTime: " + opTime); /** проверка состояния переменной opTime*/
            ///String[] task = new String[]{inputCommand, opTime};
            String inputTask = req.get("Что делаем?");
            //@todo: сохранение стартовавшей задачи
            res.setAndSend("Окей, начали: '" + inputTask + "'. Для завершения -- 'finish'");
            ///st.add(task);
            ///int id = st.getIdByName(inputCommand);
            ///String[] taskFromFile = st.get(id);
            ///res.setAndSend("Задача из файла: " + taskFromFile);

        }

        if (inputCommand.equalsIgnoreCase("finish")) {
            //@todo: получаем открытую задачу

            /**
             * Определяем текущее время с помощью класса GregorianCalendar
             * и форматируем его исключительно для вывода в консоль
             */
            //openTime = new GregorianCalendar();  - openTime получаем из открытой задачи

            /** Добавляем час к текущему времени */
            GregorianCalendar closeTime = new GregorianCalendar();
            closeTime.add(Calendar.HOUR_OF_DAY, 1);

            /** Возращаем посчитаный интервал времени */
            //res.setAndSend("Время работы: " + Interval(openTime, closeTime));
            return;
        }

        return;
    }

    public String Interval(GregorianCalendar startTime, GregorianCalendar finishTime) {

        /**
         * Присваиваем значение переменных GregorianCalendar
         * переменным класса Date форматируем так же с помощью SimpleDateFormat в методе formatTime
         */
        Date beginTime = startTime.getTime();
        Date endTime = finishTime.getTime();

        /** Вычисляем прошедшее время, отнимая beginTime от endTime */
        long interval = (endTime.getTime() - beginTime.getTime()); /** считается разница в милисекундах */
        Date resultTime = new Date(interval);
        return formatTime(resultTime);
    }

    /**
     * Метод который форматирует миллисекунды в часы, минуты и т.д.
     */
    public String formatTime(Date timeToFormat) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        return timeFormat.format(timeToFormat);

    }
}
