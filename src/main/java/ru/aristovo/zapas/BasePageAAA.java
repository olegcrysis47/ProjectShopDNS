package ru.aristovo.zapas;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BasePageAAA {

    @FindBy(xpath = "//input[@class='ui-input-search__input ui-input-search__input_presearch' and @placeholder='Поиск по сайту']")
    WebElement searchInput;

    public BasePageAAA(WebDriver driver){
        PageFactory.initElements(driver, this);
    }

    /*
    public ProductPage searchAndGetProduct(String value) {
        searchInput.sendKeys(value);
        searchInput.submit();
        return ProductPage;
    }

    public ListProductPage searchAndGetListProduct(String value) {
        searchInput.sendKeys(value);
        searchInput.submit();
        return ProductPage;
    }
    */

}
