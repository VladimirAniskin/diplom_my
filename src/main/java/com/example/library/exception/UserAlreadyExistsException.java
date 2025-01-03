package com.example.library.exception;
/**
 * Исключение, выбрасываемое, когда попытка создать пользователя
 * завершается неудачей из-за того, что пользователь с такими данными уже существует.
 */
public class UserAlreadyExistsException extends Throwable {
    /**
     * Конструктор для создания исключения с заданным сообщением.
     *
     * @param message сообщение, описывающее причину исключения
     */
    public UserAlreadyExistsException(String s) {
    } // Передаем сообщение в родительский класс Throwable
}
