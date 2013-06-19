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

public class Core {

    private Request req = new Request();
    private Response res = new Response();
    private Storage st = new Storage();

    public void Core() {

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

            /** тут уже должен быть мой код! */

            res.setAndSend("Отработано " + 12 + " минут");
            return;
        }

        return;
    }
}
