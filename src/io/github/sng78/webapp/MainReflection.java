package io.github.sng78.webapp;

import io.github.sng78.webapp.model.Resume;

import java.lang.reflect.Field;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException {
        Resume r = new Resume();
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println("Получаем имя первого поля - " + field.getName());
        System.out.println("Получаем его значение - " + field.get(r));
        System.out.println("\nМеняем значение поля на другое");
        field.set(r, "new_uuid");
        System.out.println("Новое значение - " + r);
    }
}
