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

import org.apache.xmlbeans.impl.xb.xsdschema.Public;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class Core {

    private Request req = new Request();
    private Response res = new Response();
    private Storage st = new Storage();

    public void Core() throws ParseException {
        //@todo: получать из хранилища незавершенную задачу, если возможно
        //@todo: показывать ее юзернейму, если возможно, типа: сейчас есть незавершенная задача

        //String inputCommand = JOptionPane.showInputDialog  //получаем команду с помощью окна
        //        ("Start or Finish?");
        String inputCommand = req.get("Введите 'start' для начала новой задачи, или 'finish' для окончания текущей.");
        //res.setAndSend("Значение inputCommand: " + inputCommand); /** проверка состояния переменной inputCommand*/

        //st.add(new String[] {"Название задачи", "2013-10-10 11:12:13", "2013-10-10 11:12:14"});
        //int id = st.getIdByName("Название задачи" );
        //String[] task = st.get(id);

        if (inputCommand.equalsIgnoreCase("start")) {
            //@todo: получать не только команду старта, а еще и парсить как-то и имя
            //String inputTask = JOptionPane.showInputDialog   //получаем имя задачи с помощью окна
            //     ("Что делаем?");
            Date openTime = new Date(); /** определяем время начала задачи */
            String startTime = formatTime(openTime); /** форматируем время начала задачи */
            String inputTask = req.get("Что делаем?"); /** берём название задачи */
            res.setAndSend("Окей, начали: '" + inputTask + "'. Для завершения -- 'finish'");
            /** сохранение стартовавшей задачи */
            String[] task = new String[]{inputTask, startTime};
            st.add(task);
            System.out.println(Arrays.toString(st.add(task)));   //проверка правильного выполнения двух строк выше

        }

        if (inputCommand.equalsIgnoreCase("finish")) {
            //@todo: выводить все незавершённые задачи


            String inputTask = req.get("какую задачу завершить?"); /** берём название задачи */
            int id = st.getIdByName(inputTask); /** берём ID задачи из файла по названию задачи */
            if (id != -1) {
                String[] taskFromFile = st.get(id);
                String startTime = taskFromFile[1];

                Date closeTime = new Date(); /** определяем время окончания задачи */
                String finishTime = formatTime(closeTime);
                res.setAndSend("Время начала и время конца: " + startTime + " - " + finishTime);  // проверка
                res.setAndSend("Время работы: " + Interval(startTime, finishTime));

            } else {
                res.setAndSend("Нет такой задачи");
            }

            return;
        }

        return;
    }

    public String Interval(String startTime, String finishTime) throws ParseException {
        /** это нужно чтобы SimpleDateFormat не добавлял +2 часа нашей таймзоны */
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+0"));

        /**
         * Присваиваем значение переменных String переменным класса Date
         * форматируем так же с помощью SimpleDateFormat в методе formatTime
         */
        SimpleDateFormat startTimeFormat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat finishTimeFormat = new SimpleDateFormat("HH:mm");
        Date beginTime = startTimeFormat.parse(startTime);
        Date endTime = finishTimeFormat.parse(finishTime);

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
        String result = timeFormat.format(timeToFormat);
        return result;


    }
}
