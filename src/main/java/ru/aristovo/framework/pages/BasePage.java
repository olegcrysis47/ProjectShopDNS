package ru.aristovo.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

/**
 * Базовый класс, который содержит общие методы для всех классов страниц сайта.
 */
public class BasePage {

    /**
     * Конструктор.
     * @param driver - веб-драйвер в который передается стартовая страница.
     */
    public BasePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Метод притормаживает тест, чтобы драйвер успел обновить данные на странице.
     * @param milliSeconds - задаем время ожидания в миллисекундах.
     */
    public void waitThread(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
