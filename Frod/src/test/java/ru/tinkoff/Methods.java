package ru.tinkoff;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.support.ui.ExpectedConditions.*;

public class Methods {
    public ChromeDriver driver;
    public WebDriverWait wait;

    @Before
    public void setUp()
    {
        driver=new ChromeDriver();
        wait = new WebDriverWait(driver, 20);
        driver.manage().window().maximize();
    }

    @After
    public void close()
    {
        driver.quit();

    }

    ExpectedCondition<Boolean> pageload= new ExpectedCondition<Boolean>()
    {
        @Override
        public Boolean apply(WebDriver webDriver) {
            JavascriptExecutor js= driver;
            return js.executeScript("return document.readyState").toString().equals("complete");
        }
    };

    public WebElement find_in_List(String locator, String attribute, String choice)
    {
        wait.until(pageload);
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        List<WebElement> elements = driver.findElements(By.cssSelector(locator));
        if (elements.size()!=0)
        {
        for (int i = 0; i < elements.size(); i++) {
            if (elements.get(i).getAttribute(attribute).compareTo(choice) == 0)
            {
                return elements.get(i);
            }
        }}
        elements.clear();
        return null;

    }

    public void select_city(String find_cityname, String cityname_for_comparison)
    {
        if (driver.findElement(By.cssSelector(".PaymentsCatalogHeader__regionSelect_3MRVi")).getAttribute("aria-label").compareTo(cityname_for_comparison)!=0)
        {
            wait.until(pageload);
            driver.findElement(By.cssSelector(".PaymentsCatalogHeader__regionSelect_3MRVi")).click();
            driver.findElement(By.linkText(find_cityname)).click();
            wait.until(textToBePresentInElement(driver.findElement(By.cssSelector(".PaymentsCatalogHeader__regionSelect_3MRVi")),cityname_for_comparison));
            Assert.assertTrue(driver.findElement(By.cssSelector(".PaymentsCatalogHeader__regionSelect_3MRVi")).getAttribute("aria-label").equals(cityname_for_comparison));
        }
    }

    public void click_and_check_title(String locator, String attribute, String choice,String title_after_click)
    {
        wait.until(pageload);
        wait.until(visibilityOf(find_in_List(locator,attribute,choice)));
        find_in_List(locator,attribute,choice).click();
        wait.until(titleIs(title_after_click));
    }

    public void check_validate_values_payerCode(String correct, String error_message)
    {
        switch (correct)
        {
            case "correct":

                long correct_number = (long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L;
                driver.findElement(By.cssSelector("#payerCode")).sendKeys(Long.toString(correct_number));
                Assert.assertTrue(find_in_List("[data-qa-file=UIFormRowError]","textContent",error_message)==null);
                driver.navigate().refresh();
                wait.until(pageload);
                break;

            case "incorrect":
                long incorrect_number = (long) Math.floor(Math.random() * 9_000_000L) + 1_000_000L;
                driver.findElement(By.cssSelector("#payerCode")).sendKeys(Long.toString(incorrect_number)+Keys.TAB);
                Assert.assertTrue(find_in_List("[data-qa-file=UIFormRowError]","textContent",error_message)!=null);
                driver.navigate().refresh();
                wait.until(pageload);
                break;
        }

    }

    public void check_validate_values_period(String correct, String error_message)
    {
        switch (correct)
        {
            case "correct":

                int correct_number1 = 1+(int) (Math.random() * 12);
                int correct_number2 = 1000+(int) (Math.random() * 9999);
                if(correct_number1>10)
                {
                driver.findElement(By.cssSelector("#period")).sendKeys(Integer.toString(correct_number1)+Integer.toString(correct_number2)+Keys.TAB);
                }
                else
                {
                driver.findElement(By.cssSelector("#period")).sendKeys("0"+Integer.toString(correct_number1)+Integer.toString(correct_number2)+Keys.TAB);
                }
                wait.until(pageload);
                Assert.assertTrue(find_in_List("[data-qa-file=UIFormRowError]","textContent",error_message)==null);
                driver.navigate().refresh();
                wait.until(pageload);
                break;

            case "incorrect":
                int incorrect_number1 = 12 + (int) (Math.random() * 90) ;
                int incorrect_number2 = 1000 + (int) (Math.random() * 9000);
                wait.until(pageload);
                driver.findElement(By.cssSelector("#period")).sendKeys(Integer.toString(incorrect_number1)+Integer.toString(incorrect_number2)+Keys.TAB);
                wait.until(pageload);
                Assert.assertTrue(find_in_List("[data-qa-file=UIFormRowError]","textContent",error_message)!=null);
                driver.navigate().refresh();
                wait.until(pageload);
                break;
        }
    }

    public void check_validate_values_insurance_sum(String correct, String error_message1, String error_message2,String error_message3) throws InterruptedException {
        switch (correct)
        {
            case "correct":
                List <WebElement> elements=driver.findElements(By.cssSelector(".Input__valueContent_1Os4v"));
                long correct_number1 = (long) Math.floor(Math.random() * 9_000_000_000_000_000_000L) + 1_000_000_000_000_000_000L;
                elements.get(0).sendKeys(Long.toString(correct_number1));
                Assert.assertTrue(find_in_List("[data-qa-file=UIFormRowError]","textContent",error_message1)==null);
                driver.navigate().refresh();
                wait.until(pageload);

                List <WebElement> elements1=driver.findElements(By.cssSelector(".Input__valueContent_1Os4v"));
                long correct_number2 = (long) Math.floor(Math.random() * 15000) + 10;
                elements1.get(1).sendKeys(Long.toString(correct_number2));
                Assert.assertTrue(find_in_List("[data-qa-file=UIFormRowError]","textContent",error_message2)==null);
                driver.navigate().refresh();
                wait.until(pageload);
                break;

            case "incorrect":
                List <WebElement> elements2=driver.findElements(By.cssSelector(".Input__valueContent_1Os4v"));
                elements2.get(0).sendKeys("1111111111111111111111111111111"+Keys.TAB);
                Assert.assertTrue(find_in_List("[data-qa-file=UIFormRowError]","textContent",error_message1)!=null);
                driver.navigate().refresh();
                wait.until(pageload);

                List <WebElement> elements3=driver.findElements(By.cssSelector(".Input__valueContent_1Os4v"));
                int incorrect_number2 = 15001 + (int) (Math.random() * 20000);
                elements3.get(1).sendKeys(Integer.toString(incorrect_number2)+Keys.TAB);
                Assert.assertTrue(find_in_List("[data-qa-file=UIFormRowError]","textContent",error_message2)!=null);
                driver.navigate().refresh();
                wait.until(pageload);

                List <WebElement> elements4=driver.findElements(By.cssSelector(".Input__valueContent_1Os4v"));
                elements4.get(0).sendKeys("20000"+Keys.TAB);
                elements4.get(1).sendKeys("10000"+Keys.TAB);
                Assert.assertTrue(find_in_List("[data-qa-file=UIFormRowError]","textContent",error_message3)!=null);
                driver.navigate().refresh();
                wait.until(pageload);
                break;
        }
    }

    public void check_required_fields()
    {
        Actions builder = new Actions(driver);
        builder.sendKeys(Keys.chord(Keys.ENTER)).perform();
        Assert.assertTrue(driver.findElements(By.cssSelector("[data-qa-file=UIFormRowError]")).size()==3);
    }
}
