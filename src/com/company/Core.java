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

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+0")); /** это нужно чтобы SimpleDateFormat не добавлял +2 часа нашей таймзоны */

        // приветствуем юзернейма
        res.setAndSend("Введите 'start' для начала новой задачи, или 'finish' для окончания текущей.");
        //@todo: получать из хранилища незавершенную задачу, если возможно
        //@todo: показывать ее юзернейму, если возможно, типа: сейчас есть незавершенная задача

        // получаем команду
        String[] input = req.getCommand();

        if (input[0].equalsIgnoreCase("start")) {
            res.setAndSend("Окей, начали делать '" + input[1] + "'. Для завершения -- 'finish " + input[1] + "'");

            // сохраняем задачу в хранилище
            String task[] = new String[] {input[1], new GregorianCalendar().toString()};
            st.add(task);
        }

        if (input[0].equalsIgnoreCase("finish")) {

            // получаем открытую задачу
            String[] taskToFinish = st.get(st.getIdByName(input[1]));

            /**
             * Определяем текущее время с помощью класса GregorianCalendar
             * и форматируем его исключительно для вывода в консоль
             */
            GregorianCalendar openTime = new GregorianCalendar();

            // добавляем час к текущему времени
            GregorianCalendar closeTime = new GregorianCalendar();
            closeTime.add(Calendar.HOUR_OF_DAY, 1);

            // возращаем посчитаный интервал времени
            res.setAndSend("Время работы: " + Interval(openTime, closeTime));
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
