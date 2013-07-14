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
        //String inputCommand = req.get("Введите 'start' для начала новой задачи, или 'finish' для окончания текущей.");
        //res.setAndSend("Значение inputCommand: " + inputCommand); /** проверка состояния переменной inputCommand*/

        String[] inputCommand = req.getCommand("Для начала задачи -- start + название задачи; для окончания -- finish + название задачи.");

        //if (input[0].equalsIgnoreCase("start")) {
        //res.setAndSend("Окей, начали делать '" + input[1] + "'. Для завершения -- 'finish " + input[1] + "'");

        // сохраняем задачу в хранилище
        //String task[] = new String[] {input[1], new GregorianCalendar().toString()};
        // st.add(task);
        //}

        //if (input[0].equalsIgnoreCase("finish")) {

        // получаем открытую задачу
        //String[] taskToFinish = st.get(st.getIdByName(input[1]));

        if (inputCommand[0].equalsIgnoreCase("start")) {
            if (inputCommand.length == 1) {
                String inputTask = req.get("И всё-таки, что делаем?"); /** берём название задачи */
                Date openTime = new Date(); /** определяем время начала задачи */
                String startTime = formatTime(openTime); /** форматируем время начала задачи */
                //res.setAndSend("Окей, начали делать '" + inputTask + "' в " + startTime + ". Для завершения -- 'finish " + inputTask + "'");
                /** сохранение стартовавшей задачи */
                String[] task = new String[]{inputTask, startTime, null};
                st.add(task);
                System.out.println(Arrays.toString(st.add(task)));   //проверка правильного выполнения двух строк выше
                res.setAndSend("Окей, начали делать '" + inputTask + "' в " + startTime + ". Для завершения -- 'finish + id этой задачи: " + st.getIdByName(inputTask) + "'");
            } else {
                //String inputTask = JOptionPane.showInputDialog   //получаем имя задачи с помощью окна
                //     ("Что делаем?");
                Date openTime = new Date(); /** определяем время начала задачи */
                String startTime = formatTime(openTime); /** форматируем время начала задачи */
                String inputTask = "";
                for (int i = 1; i < inputCommand.length; i++)
                    inputTask += (inputCommand[i] + " "); /** составление имени задачи из нескольких слов */
                /** сохранение стартовавшей задачи */
                String[] task = new String[]{inputTask, startTime, null};
                st.add(task);
                //System.out.println(Arrays.toString(st.add(task)));   //проверка правильного выполнения двух строк выше
                res.setAndSend("Окей, начали делать '" + inputTask + "' в " + startTime + ". Для завершения -- 'finish + id этой задачи: " + st.getIdByName(inputTask) + "'");

            }
            return;
        }


        if (inputCommand[0].equalsIgnoreCase("finish")) {
            if (inputCommand.length == 1) {
                //@TODO Сделать вывод всех задач

                int id = Integer.parseInt(req.get("Введите id задачи, которую хотите завершить: ")); /** берём id задачи */
                String[] taskFromFile = st.get(id);
                String startTime = taskFromFile[1];
                res.setAndSend(id + startTime);
                Date closeTime = new Date(); /** определяем время окончания задачи */
                String finishTime = formatTime(closeTime);
                res.setAndSend("Время начала и конца: " + startTime + " - " + finishTime);  // проверка
                res.setAndSend("Время работы над задачей " + taskFromFile[1] + ": " + Interval(startTime, finishTime));

            } else {
                int id = Integer.parseInt(inputCommand[1]);
                //@todo: выводить все незавершённые задачи

                String[] taskFromFile = st.get(id);
                String startTime = taskFromFile[1];
                Date closeTime = new Date(); /** определяем время окончания задачи */
                String finishTime = formatTime(closeTime);
                st.set(id, finishTime);
                String[] closedTask = st.get(id);
                res.setAndSend("Время начала и конца: " + startTime + " - " + finishTime);  // проверка
                res.setAndSend("Задача с временем окончания: " + Arrays.toString(closedTask));  // проверка
                res.setAndSend("Время работы: " + Interval(startTime, finishTime));
            }
            return;
        }
        res.setAndSend("Неверный ввод команды");

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
