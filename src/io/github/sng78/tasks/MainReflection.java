package io.github.sng78.tasks;

import io.github.sng78.webapp.model.Resume;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Resume r = new Resume("New name");
        Field field = r.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        System.out.println("Получаем имя первого поля - " + field.getName());
        System.out.println("Получаем его значение - " + field.get(r));
        System.out.println("\nМеняем значение поля на другое");
        field.set(r, "new_uuid");
        System.out.println("Новое значение - " + r.getUuid());

        Method methodToString = r.getClass().getMethod("toString");
        System.out.println("\nВызываем toString через Reflections - " + methodToString.invoke(r));
    }
}
