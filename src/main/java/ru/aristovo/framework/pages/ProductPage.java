package ru.aristovo.framework.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

/**
 * Класс страницы с товаром, смена параметра товара, просмотр цены, покупка.
 */
public class ProductPage {

    /**
     * Переменная для определения текущей цены.
     */
    @FindBy(xpath = "//div[@class='product-card-price__current-wrap']")
    WebElement priceProduct;

    /**
     * Переменная для гарантийного обслуживания - 1 год.
     */
    @FindBy(xpath = "//select//option[@value='0']")
    WebElement guaranteeOneYear;

    /**
     * Переменная для гарантийного обслуживания - 2 года.
     */
    @FindBy(xpath = "//select//option[@value='1']")
    WebElement guaranteeTwoYear;

    /**
     * Переменная для кнопки "Купить".
     */
    @FindBy(xpath = "//button[contains(.,'Купить')]")
    WebElement buttonBuy;

    /**
     * Поле на сайте "Поиск по сайту"
     */
    @FindBy(xpath = "//input[@class='ui-input-search__input ui-input-search__input_presearch' and @placeholder='Поиск по сайту']")
    WebElement searchField;

    /**
     * Конструктор.
     * @param driver - веб-драйвер в который передается стартовая страница.
     */
    public ProductPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Метод запоминает цену товара, устанавливает гарантию, запоминает новую цену товара с гарантией.
     * Затем проверяет, что цена изменилась при включении гарантии.
     * @param year - пользователь вводит количество лет гарантийного обслуживания.
     */
    public void setupGuaranteeOnProduct(int year) {
        int priceNotGuar = getPriceProduct(priceProduct);
        choiceGuaranteeTwoYear(year);
        waitThread(500);
        int priceWithGuar = getPriceProduct(priceProduct);
        Assertions.assertNotEquals(priceNotGuar, priceWithGuar, "При включении гарантии цена не изменилась!");
    }

    /**
     * Метод извлекает текущую цену товара, показанную на экране.
     * @param priceProduct - передаем веб-элемент, содержащий цену товара.
     * @return - возвращает фактическую цену товара.
     */
    public int getPriceProduct(WebElement priceProduct) {
        return Integer.parseInt(priceProduct.getText().replaceAll("\\W", ""));
    }

    /**
     * Метод выбирает срок гарантии на товар.
     * @param year - пользователь вводит количество лет гарантийного обслуживания.
     */
    public void choiceGuaranteeTwoYear(int year) {
        switch (year) {
            case 0:
                break;
            case 1:
                guaranteeOneYear.click();
                break;
            case 2:
                guaranteeTwoYear.click();
                break;
        }
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

    public void clickButtonBuy() {
        buttonBuy.click();
        waitThread(500);
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
