package ru.aristovo.framework.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * Страница, после поиска товаров, если найдено по заголовкам больше одного похожего наименования.
 */
public class ListProductPage {

    /**
     * Переменная для сохранения списка предлагаеемых сайтом товаров.
     * Полный список найденных товаров по критерию.
     */
    @FindBy(xpath = "//div[@data-id='product']")
    List<WebElement> listChoiceProduct;

    /**
     * Переменная для просмотра заголовков (наименований) товаров.
     */
    @FindBy(xpath = ".//a[@class='ui-link']")
    WebElement nameProductOnPage;

    /**
     * Конструктор.
     * @param driver - веб-драйвер в который передается стартовая страница.
     */
    public ListProductPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    /**
     * Метод поиска и открытия нужного товара.
     * @param productName - указываем нужный пользователю товар.
     */
    public void selectAvailableProduct(String productName) {
        for (WebElement webProduct: listChoiceProduct) {
            if (nameProductOnPage.getText().toLowerCase().contains(productName)) {
                nameProductOnPage.click();
                break;
            }
        }
    }
}
