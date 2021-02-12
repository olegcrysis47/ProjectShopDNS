package ru.aristovo.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.aristovo.base.BaseTests;

@DisplayName("Тестируем магазин DNS")
public class ShopDNSTest extends BaseTests {

    @Test
    @DisplayName("Тест покупки PlayStation и пары дисков с играми")
    void buyPlayStationAndGames() {

        /*
        2. в поиске найти playstation
        3. кликнуть по playstation 4 slim black
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
}
