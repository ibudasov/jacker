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

        //String inputCommand = JOptionPane.showInputDialog  //получаем команду с помощью окна
        //        ("Start or Finish?");

        String[] inputCommand = req.getCommand("Для начала задачи -- start + название задачи; для окончания -- finish + id задачи.");

        if (inputCommand[0].equalsIgnoreCase("start")) {
            if (inputCommand.length == 1) {
                String inputTask = req.get("И всё-таки, что делаем?"); /** берём название задачи */
                Date openTime = new Date(); /** определяем время начала задачи */
                String startTime = formatTime(openTime); /** форматируем время начала задачи */
                /** сохранение стартовавшей задачи */
                String[] task = new String[]{inputTask, startTime, null};
                st.add(task);
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
                res.setAndSend("Окей, начали делать '" + inputTask + "' в " + startTime + ". Для завершения -- 'finish + id этой задачи: " + st.getIdByName(inputTask) + "'");

            }
            return;
        }


        if (inputCommand[0].equalsIgnoreCase("finish")) {
            boolean askId = true;
            if (inputCommand.length == 1) {
                String[][] data = st.getTasks();
                int j = 0;
                for (int i = 0; i < data.length; i++) {                 //Перебираются все элементы двумерного массива с задачами
                    if (data[i] != null) {                              //Выбираются только непустые элементы массива
                        if (data[i].length > 2) {                       //Отсекаются задачи, в которых нет поля для времени окончания
                            if (data[i][2] == null) {                   //Выбираются элементы, в которых не записано время окончания
                                System.out.println("ID: " + i + ", '" + data[i][0] + "'. Время начала: " + data[i][1]);
                                j++;
                            }
                        }
                    } else {
                        if (j == 0 & i == (data.length - 1)) {
                            System.out.println("Ура! Нет незавершённых задач.");   //Когда нет незавершённых задач
                            askId = false;
                        }

                    }
                }
                if (askId) {
                    int id = Integer.parseInt(req.get("Введите id задачи, которую хотите завершить: ")); /** берём id задачи */
                    String[] taskFromFile = st.get(id);
                    String startTime = taskFromFile[1];
                    Date closeTime = new Date(); /** определяем время окончания задачи */
                    String finishTime = formatTime(closeTime);
                    st.setFT(id, finishTime);
                    String[] closedTask = st.get(id);
                    res.setAndSend("Время работы над задачей '" + closedTask[0] + "': " + Interval(startTime, finishTime));
                }

            } else {
                int id = Integer.parseInt(inputCommand[1]);
                String[] taskFromFile = st.get(id);
                String startTime = taskFromFile[1];
                Date closeTime = new Date(); /** определяем время окончания задачи */
                String finishTime = formatTime(closeTime);
                st.setFT(id, finishTime);
                String[] closedTask = st.get(id);
                //res.setAndSend("Время начала и конца: " + startTime + " - " + finishTime);  // проверка   8
                res.setAndSend("Время работы задачи '" + closedTask[0] + "': " + Interval(startTime, finishTime));
            }
            return;
        }
        res.setAndSend("Неверный ввод команды");
        //@todo: требовать верный ввод или EXIT для выхода

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
