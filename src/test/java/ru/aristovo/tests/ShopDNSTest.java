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

    // НАПОМИНАЛКА: нужно будет сделать assert заголовок страниц при их открытии

    static int priceBasket;             // сумма корзины
    static int pricePSNotGur;           // цена PS без гарантии
    static int pricePSWithGur;          // цена PS с гарантией
    static int priceDiskDetroit;        // цена диска Детроит

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

        // 4. запомнить цену
        String pricePlayStationNotGuaranteeXPath = "//div[@class='product-card-price__current-wrap']";
        WebElement pricePlayStationNotGuarantee = driver.findElement(By.xpath(pricePlayStationNotGuaranteeXPath));
        waitUtilElementToBeVisible(pricePlayStationNotGuarantee);
        pricePSNotGur = Integer.parseInt(pricePlayStationNotGuarantee.getText().replaceAll("\\W", ""));

        // 5. Доп.гарантия - выбрать 2 года
        String selectuaranteeTwoYearXPath = "//select//option[@value='1']";
        WebElement selectGuaranteeTwoYear = driver.findElement(By.xpath(selectuaranteeTwoYearXPath));
        waitUtilElementToBeClickable(selectGuaranteeTwoYear);
        selectGuaranteeTwoYear.click();

        waitThread(500); // на всякий случай, ждем изменение цены

        // 6. дождаться изменения цены и запомнить цену с гарантией
        String pricePlayStationWithGuaranteeXPath = "//div[@class='product-card-price__current-wrap']";
        WebElement pricePlayStationWithGuarantee = driver.findElement(By.xpath(pricePlayStationWithGuaranteeXPath));
        waitUtilElementToBeVisible(pricePlayStationWithGuarantee);
        pricePSWithGur = Integer.parseInt(pricePlayStationWithGuarantee.getText().replaceAll("\\W", ""));

        Assertions.assertNotEquals(pricePSNotGur, pricePSWithGur,
                "После подключения гарантии сумма не изменилась");

        // 7. Нажать Купить
        String buttonBuyPSXPath = "//button[contains(.,'Купить')]";
        WebElement buttonBuyPS = driver.findElement(By.xpath(buttonBuyPSXPath));
        waitUtilElementToBeVisible(buttonBuyPS);
        waitUtilElementToBeClickable(buttonBuyPS);
        buttonBuyPS.click();

        priceBasket += pricePSWithGur;

        waitThread(500); // на всякий случай, ждем изменение корзины на экране

        // 8. выполнить поиск Detroit
        String fieldFoundDetroitXPath =
                "//input[@class='ui-input-search__input " +
                        "ui-input-search__input_presearch' and @placeholder='Поиск по сайту']";
        WebElement fieldFoundDetroit = driver.findElement(By.xpath(fieldFoundDetroitXPath));
        waitUtilElementToBeClickable(fieldFoundDetroit);
        fieldFoundDetroit.click();
        fieldFoundDetroit.clear();
        fieldFoundDetroit.sendKeys("Detroit");
        Assertions.assertEquals("Detroit", fieldFoundDetroit.getAttribute("value"),
                "Поле было заполнено не верно");
        fieldFoundDetroit.sendKeys(Keys.ENTER);

        // 9. запомнить цену Detroit
        String priceDetroitXPath = "//div[@class='product-card-price__current-wrap']";
        WebElement priceDetroit = driver.findElement(By.xpath(priceDetroitXPath));
        waitUtilElementToBeVisible(priceDetroit);
        priceDiskDetroit = Integer.parseInt(priceDetroit.getText().replaceAll("\\W", ""));

        // 10. нажать купить Detroit
        String buttonBuyDetroitXPath = "//button[contains(.,'Купить')]";
        WebElement buttonBuyDetroit = driver.findElement(By.xpath(buttonBuyDetroitXPath));
        waitUtilElementToBeVisible(buttonBuyDetroit);
        waitUtilElementToBeClickable(buttonBuyDetroit);
        buttonBuyDetroit.click();

        priceBasket += priceDiskDetroit;

        waitThread(500); // на всякий случай, ждем изменение корзины на экране

        // временно, чтобы посмотреть запоминание переменных
        System.out.println("priceBasket = " + priceBasket);
        System.out.println("pricePSNotGur = " + pricePSNotGur);
        System.out.println("pricePSWithGur = " + pricePSWithGur);
        System.out.println("priceDiskDetroit = " + priceDiskDetroit);


        waitThread(3000);
        /*
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
