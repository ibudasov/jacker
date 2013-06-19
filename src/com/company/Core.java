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
 * @author Igor Budasov <igor.budasov@gmail.com>
 * @copyright Copyright (c) 2013, Igor Budasov
 */

package com.company;

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

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+0"));
        // приветствуем юзернейма
        res.setAndSend("Введите 'start' для начала новой задачи, или 'finish' для окончания текущей.");
        //@todo: получать из хранилища незавершенную задачу, если возможно
        //@todo: показывать ее юзернейму, если возможно, типа: сейчас есть незавершенная задача

        // получаем команду
        String input = req.get();

        if (input.equalsIgnoreCase("start")) {
            //@todo: получать не только команду старта, а еще и парсить как-то и имя
            //@todo: сохранение стартовавшей задачи
            res.setAndSend("Окей, начали. Для завершения -- 'finish'");
        }

        if (input.equalsIgnoreCase("finish")) {
            //@todo: получаем открытую задачу
            //@todo: считаем время

            /**
             * определяем текущее время
             */
            GregorianCalendar startTime = new GregorianCalendar();
            System.out.println("Start time: " + startTime.get(Calendar.HOUR_OF_DAY) + ":" + startTime.get(Calendar.MINUTE) + ":" + startTime.get(Calendar.SECOND));

            /**
             * добавляем час к текущему времени
             */
            GregorianCalendar endTime = new GregorianCalendar();
            endTime.add(Calendar.HOUR_OF_DAY, 1);
            System.out.println("End time: " + endTime.get(Calendar.HOUR_OF_DAY) + ":" + endTime.get(Calendar.MINUTE) + ":" + endTime.get(Calendar.SECOND));

            /**
             * определяем текущее время с помощью класса Date
             * и форматируем его исключительно для вывода в консоль
             */
            Date starttime = new Date();
            SimpleDateFormat startTimeFormat = new SimpleDateFormat("d.MM HH:mm:ss");
            System.out.println("Текущее время: " + startTimeFormat.format(starttime)); /** проверка */

            /**
             * используем переменную endTime, полученную через применение
             * класса GregorianCalendar и присваиваем её значение
             * переменной класса Date
             * форматируем так же с помощью SimpleDateFormat
             */
            Date pastTime = endTime.getTime();
            SimpleDateFormat endTimeFormat = new SimpleDateFormat("d.MM HH:mm:ss");
            System.out.println("Прошедшее время: " + endTimeFormat.format(pastTime));

            /**
             * вычисляем прошедшее время, отнимая starttime от pastTime
             */
            long resultTime = (pastTime.getTime() - starttime.getTime()); /** разница в милисекундах уже посчитана, теперь её нужно запихнуть в объект Date */
            SimpleDateFormat resultTimeFormat = new SimpleDateFormat("yyyy d.MM HH:mm:ss");
            //resultTimeFormat.setTimeZone(TimeZone.getTimeZone("GMT+0"));
            System.out.println("Значение resultTime: " + resultTime + " - " + resultTimeFormat.format(resultTime));
            Date finalResult = new Date(resultTime); /** поправка на часовой пояс (у нас он +2) */
            SimpleDateFormat finalTimeFormat = new SimpleDateFormat("HH:mm"); /** форматируем вывод времени */
            res.setAndSend("Отработано " + finalTimeFormat.format(finalResult));
            return;
        }

        return;
    }
}
