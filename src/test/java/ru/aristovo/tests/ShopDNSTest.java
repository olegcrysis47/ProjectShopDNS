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
import ru.aristovo.framework.pages.*;

import java.util.List;

@DisplayName("Тестируем магазин DNS")
public class ShopDNSTest extends BaseTests {

    // НАПОМИНАЛКА: нужно будет сделать assert заголовок страниц при их открытии

    static int priceBasket;             // сумма корзины
    static int pricePSNotGur;           // цена PS без гарантии
    static int pricePSWithGur;          // цена PS с гарантией
    static int priceDiskDetroit;        // цена диска Детроит

    @Test
    @DisplayName("Автоматизированный тест покупки PlayStation и диска Detroit")
    void buyPlayStationAndGames() {

        StartPage startPage = new StartPage(driver);
        ListProductPage listProductPage = new ListProductPage(driver);
        ProductPage productPage = new ProductPage(driver);

        startPage.searchProductOnSite("playstation");
        listProductPage.selectAvailableProduct("playstation 4 slim black");
        productPage.setupGuaranteeOnProduct(2);
        productPage.clickButtonBuy();
        productPage.searchProductOnSite("Detroit");
        productPage.clickButtonBuy();



        waitThread(4000); // на всякий случай, ждем изменение корзины на экране

        // 11. проверить что цена корзины стала равна сумме покупок
        String sumBasketOnScreenXPath = "//a[@class='ui-link cart-link']//span[@class='cart-link__price']";
        WebElement sumBasketOnScreen = driver.findElement(By.xpath(sumBasketOnScreenXPath));
        //waitUtilElementToBeVisible(sumBasketOnScreen);

//        Assertions.assertEquals(priceBasket,
//                Integer.parseInt(sumBasketOnScreen.getText().replaceAll("\\W", "")),
//                "Сумма на экране не соответствует сумме добавленных товаров");

        // 12. перейти в корзину
        sumBasketOnScreen.click();

        // 13. проверить, что для приставки выбрана гарантия на 2 года
        String selectBasketProductXPath = "//div[@class='cart-items__product']";
        List<WebElement> selectBasketProduct = driver.findElements(By.xpath(selectBasketProductXPath));
        for (WebElement w : selectBasketProduct) {
            WebElement titleProduct = w.findElement(By.xpath(".//a[@class='cart-items__product-name-link']"));
            if (titleProduct.getText().toLowerCase().contains("playstation")) {
                WebElement checkGur =
                        w.findElement(By.xpath(".//div[@data-commerce-target='basket_additional_warranty_24']//span"));
                Assertions.assertEquals("base-ui-radio-button__icon base-ui-radio-button__icon_checked",
                        checkGur.getAttribute("class"),
                        "Гарантия не установлена на кнопке 24 мес. (2 года)");
                break;
            }
        }

        // 14. проверить цену каждого из товаров и сумму
        for (WebElement w : selectBasketProduct) {
            WebElement titleProduct = w.findElement(By.xpath(".//a[@class='cart-items__product-name-link']"));
            WebElement priceProduct = w.findElement(By.xpath(".//span[@class='price__current']"));
            switch (titleProduct.getText().trim()) {
                case "Игровая консоль PlayStation 4 Slim Black 1 TB + 3 игры":
                    Assertions.assertEquals(pricePSNotGur,
                            Integer.parseInt(priceProduct.getText().replaceAll("\\W", "")),
                            "Цена не соответствует товару");
                    break;
                case "Игра Detroit: Стать человеком (PS4)":
                    Assertions.assertEquals(priceDiskDetroit,
                            Integer.parseInt(priceProduct.getText().replaceAll("\\W", "")),
                            "Цена не соответствует товару");
                    break;
            }
        }

        // 15. удалить из корзины Detroit
        for (WebElement w : selectBasketProduct) {
            WebElement titleProduct = w.findElement(By.xpath(".//a[@class='cart-items__product-name-link']"));
            if (titleProduct.getText().toLowerCase().contains("Detroit".toLowerCase())) {
                WebElement buttonDeleteProduct = w.findElement(By.xpath(".//button[contains(.,'Удалить')]"));
                waitUtilElementToBeClickable(buttonDeleteProduct);
                buttonDeleteProduct.click();
            }
        }

        waitThread(4000); // для обновления суммы корзины

        // 16. проверить что Detroit нет больше в корзине и что сумма уменьшилась на цену Detroit
        selectBasketProduct = driver.findElements(By.xpath(selectBasketProductXPath));
        for (WebElement w : selectBasketProduct) {
            WebElement titleProduct = w.findElement(By.xpath(".//a[@class='cart-items__product-name-link']"));
            Assertions.assertNotEquals("Игра Detroit: Стать человеком (PS4)",
                    titleProduct.getText(),
                    "Товар не был удален из корзины");
        }

        waitThread(4000); // для обновления суммы корзины

        priceBasket -= priceDiskDetroit;
        sumBasketOnScreen = driver.findElement(By.xpath(sumBasketOnScreenXPath));
        Assertions.assertEquals(priceBasket,
                Integer.parseInt(sumBasketOnScreen.getText().replaceAll("\\W", "")),
                "Сумма на экране не соответствует сумме добавленных товаров");

        // 17. добавить еще 2 playstation (кнопкой +) и проверить что сумма верна (равна трем ценам playstation)
        selectBasketProduct = driver.findElements(By.xpath(selectBasketProductXPath));
        for (WebElement w : selectBasketProduct) {
            WebElement buttonPlusProduct = w.findElement(By.xpath(".//button[@data-commerce-action='CART_ADD']"));
            waitUtilElementToBeClickable(buttonPlusProduct);
            buttonPlusProduct.click();
            priceBasket += pricePSWithGur;
            waitThread(3000);
            buttonPlusProduct.click();
            priceBasket += pricePSWithGur;
        }
        waitThread(3000);

        String totalPriceBlockXPath = "//div[@class='total-amount-block total-amount__info-block']" +
                "//span[@class='price__current']";
        WebElement totalPriceBlock = driver.findElement(By.xpath(totalPriceBlockXPath));
        Assertions.assertEquals(priceBasket,
                Integer.parseInt(totalPriceBlock.getText().replaceAll("\\W", "")),
                "Сумма в корзине не равна трем PS с гарантией");

        // 18. нажать вернуть удаленный товар, проверить что Detroit появился в корзине и сумма увеличилась на его значение
        String returnDeletedProductXPath = "//div[@class='group-tabs-menu']//span[@class='restore-last-removed']";
        WebElement returnDeletedProduct = driver.findElement(By.xpath(returnDeletedProductXPath));
        waitUtilElementToBeClickable(returnDeletedProduct);
        returnDeletedProduct.click();

        priceBasket += priceDiskDetroit;
        waitThread(3000);
        Assertions.assertEquals(priceBasket,
                Integer.parseInt(totalPriceBlock.getText().replaceAll("\\W", "")),
                "Сумма в корзине не равна трем PS с гарантией");


        // временно, чтобы посмотреть запоминание переменных
        System.out.println("priceBasket = " + priceBasket);
        System.out.println("pricePSNotGur = " + pricePSNotGur);
        System.out.println("pricePSWithGur = " + pricePSWithGur);
        System.out.println("priceDiskDetroit = " + priceDiskDetroit);


        waitThread(3000);
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
