/**
 * Класc для получения запросов от пользователя и приведения их
 * к какому-то стандартному виду, принятому внутри программы
 *
 * @version 0.1
 * @package standard
 * @author Igor Budasov <igor.budasov@gmail.com>
 * @copyright Copyright (c) 2013, Igor Budasov
 */

package com.company;

import java.util.Scanner;
import java.util.StringTokenizer;

public class Request {

    private String result;
    private String[] command;

    /**
     * Конструктор объекта ответа.
     */
    public Request() {
    }

    /**
     * Зачитываем пользовательский ввод, сохраняем
     * его в объекте и возвращаем по запросу
     *
     * @return String
     */
    public String get() {
        Scanner in = new Scanner(System.in);
        this.set(in.nextLine());
        return this.result;
    }

    /**
     * Получение комманды, введенной пользователем.
     * Команда парсится в массив строк, где первая запись -- сама команда,
     * а остальные записи -- это параметры команды, в порядке ввода.
     *
     * @return String[]
     */
    public String[] getCommand() {
        String requestString = this.get();
        return this.command = requestString.split(" ");
    }

    /**
     * Устанавливаем объект запроса.
     * Метод приватный и может быть вызван только внутри класса
     *
     * @param result
     * @return
     */
    private String set(String result) {
        return this.result = result;
    }
}
