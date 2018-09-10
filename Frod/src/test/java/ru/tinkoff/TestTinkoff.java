package ru.tinkoff;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

/**
 * Created by Амир on 08.09.2018.
 */
public class TestTinkoff extends Methods {

    @Test
    public void tinkoff_test() throws InterruptedException {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.tinkoff.ru/");
        wait.until(titleIs("Кредит наличными и кредитные карты онлайн"));
        click_and_check_title(".Footer-common__link_3mHJX","textContent","Платежи","Tinkoff.ru: платежи и переводы денег");
        click_and_check_title(".PaymentsCategoryItem__wrapper_1bbEL","textContent","ЖКХ","Оплата ЖКХ без комиссии. Коммунальные платежи онлайн");

        select_city("г. Москва","Москве");

        String first_provider_name= driver.findElement(By.cssSelector("[data-qa-file='UIMenuItemProvider']")).getAttribute("textContent");
        Assert.assertTrue(driver.findElement(By.cssSelector("[data-qa-file='UIMenuItemProvider']")).getAttribute("textContent").equals("ЖКУ-Москва"));

        click_and_check_title("[data-qa-file='UIMenuItemProvider']","textContent","ЖКУ-Москва","ЖКУ-Москва | Онлайн-проверка задолженностей ЖКУ-Москва");
        click_and_check_title("[class='Tab__tab_2Ylcg']","textContent","Оплатить ЖКУ в Москве","ЖКУ-Москва | Онлайн-оплата ЖКУ-Москва без комиссии");

        check_validate_values_payerCode("correct",null);
        check_validate_values_payerCode("incorrect","Поле неправильно заполнено");
        check_validate_values_period("correct",null);
        check_validate_values_period("incorrect","Поле заполнено некорректно");

        check_validate_values_insurance_sum("correct",null,null,null);
        check_validate_values_insurance_sum("incorrect","Поле заполнено неверно","Максимум — 15 000","Сумма добровольного страхования не может быть больше итоговой суммы.");
        check_required_fields();

        click_and_check_title(".footer__Footer-common__link_383TZ","textContent","Платежи","Tinkoff.ru: платежи и переводы денег");

        driver.findElement(By.cssSelector("[data-qa-file='SearchInput']")).clear();
        driver.findElement(By.cssSelector("[data-qa-file='SearchInput']")).sendKeys(first_provider_name);

        Assert.assertTrue(driver.findElement(By.cssSelector(".Text__text_sizeDesktop_17_2KUMp")).getAttribute("textContent").equals(first_provider_name));

        driver.findElement(By.cssSelector(".Text__text_sizeDesktop_17_2KUMp")).click();

        click_and_check_title("[class='Tab__tab_2Ylcg']","textContent","Оплатить ЖКУ в Москве","ЖКУ-Москва | Онлайн-оплата ЖКУ-Москва без комиссии");
        click_and_check_title(".footer__Footer-common__link_383TZ","textContent","Платежи","Tinkoff.ru: платежи и переводы денег");
        find_in_List(".PaymentsCategoryItem__wrapper_1bbEL","textContent","ЖКХ").click();
        select_city("г. Санкт-Петербург","Санкт-Петербурге");

        List<WebElement> elements= driver.findElements(By.cssSelector("[data-qa-file='UIMenuItemProvider']"));
        for(int i=0;i<elements.size();i++)
        {
            Assert.assertFalse(elements.get(i).getAttribute("textContent").equals(first_provider_name));
        }
    }

}
