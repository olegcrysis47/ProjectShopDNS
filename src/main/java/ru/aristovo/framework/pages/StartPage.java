package ru.aristovo.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * StartPage - стартовая страница https://www.dns-shop.ru/
 */
public class StartPage {

    /**
     * Поле на сайте "Поиск по сайту"
     */
    @FindBy(xpath = "//input[@class='ui-input-search__input ui-input-search__input_presearch' and @placeholder='Поиск по сайту']")
    WebElement searchField;

    /**
     * Конструктор.
     * @param driver - веб-драйвер в который передается стартовая страница.
     */
    public StartPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Метод обращается к полю "Поиск по сайту" и вводит в него наименование искомого товара.
     * @param productName - пользователь вводит полное или частичное наименование товара, который его интересует.
     */
    public void searchProductOnSite(String productName) {
        searchField.click();
        searchField.clear();
        searchField.sendKeys(productName);
        Assertions.assertEquals(productName, searchField.getAttribute("value"),
                "Поле было заполнено не верно");
        searchField.sendKeys(Keys.ENTER);
    }
}
