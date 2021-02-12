package ru.aristovo.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.aristovo.base.BaseTests;

import java.util.List;

@DisplayName("Тестируем магазин DNS")
public class ShopDNSTest extends BaseTests {

    @Test
    @DisplayName("Тест покупки PlayStation и пары дисков с играми")
    void buyPlayStationAndGames() {

        // 2. в поиске найти playstation
        String fieldFoundOnSiteXPath =
                "//input[@class='ui-input-search__input " +
                        "ui-input-search__input_presearch' and @placeholder='Поиск по сайту']";

        WebElement fieldFoundOnSite = driver.findElement(By.xpath(fieldFoundOnSiteXPath));
        waitUtilElementToBeClickable(fieldFoundOnSite);
        fieldFoundOnSite.click();
        fieldFoundOnSite.sendKeys("playstation");
        Assertions.assertEquals("playstation", fieldFoundOnSite.getAttribute("value"),
                "Поле было заполнено не верно");
        fieldFoundOnSite.sendKeys(Keys.ENTER);

        // 3. кликнуть по playstation 4 slim black
        String selectAvailableProductXPath = "//div[@data-id='product']";
        List<WebElement> selectAvailableProduct = driver.findElements(By.xpath(selectAvailableProductXPath));
        for (WebElement w:selectAvailableProduct) {
            WebElement p = w.findElement(By.xpath(".//a[@class='ui-link']"));
            if (p.getText().toLowerCase().contains("playstation 4 slim black")) {
                waitUtilElementToBeClickable(p);
                p.click();
                break;
            }
        }

        waitThread(5000);
        /*
        4. запомнить цену
        5. Доп.гарантия - выбрать 2 года
        6. дождаться изменения цены и запомнить цену с гарантией
        7. Нажать Купить
        8. выполнить поиск Detroit
        9. запомнить цену
        10. нажать купить
        11. проверить что цена корзины стала равна сумме покупок
        12. перейри в корзину
        13. проверить, что для приставки выбрана гарантия на 2 года
        14. проверить цену каждого из товаров и сумму
        15. удалить из корзины Detroit
        16. проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
        17. добавить еще 2 playstation (кнопкой +) и проверить что сумма верна (равна трем ценам playstation)
        18. нажать вернуть удаленный товар, проверить что Detroit появился в корзине и сумма увеличилась на его значение
         */
    }

    void waitThread(int milliSeconds) {
        try {
            Thread.sleep(milliSeconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void scrollToElementJs(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }

    void waitUtilElementToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    void waitUtilElementToBeVisible(By locator) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    void waitUtilElementToBeVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
