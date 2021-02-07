package org.example.sberTest;

import org.example.base.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertAll;

public class SberTest extends BaseTest {
    @Test
    public void sberTest(){
        WebElement cardsButton = driver.findElement(By.xpath("//a[@aria-label='Карты']"));
        wait.until(ExpectedConditions.elementToBeClickable(cardsButton));
        cardsButton.click();

        WebElement debCardsButton = driver.findElement(By.xpath("//a[contains(text(),'Дебетовые карты')]"));
        wait.until(ExpectedConditions.elementToBeClickable(debCardsButton));
        debCardsButton.click();

        WebElement debHeader = driver.findElement(By.xpath("//h1[@data-test-id='Product_catalog_header']"));
        assertThat(debHeader.getText(),is("Дебетовые карты"));

        WebElement molodCard = driver.findElement(By.xpath("//a[@data-product='Молодёжная карта']/span[contains(text(),'Заказать онлайн')]"));
        scrollToElementJS(molodCard);
        wait.until(ExpectedConditions.elementToBeClickable(molodCard));
        molodCard.click();

        WebElement molodHeader = driver.findElement(By.xpath("//h1[contains(text(),'Молодёжная карта')]"));
        scrollToElementJS(molodHeader);
        assertThat(molodHeader.getText(),is("Молодёжная карта"));

        WebElement orderButton = driver.findElement(By.xpath("//a[@href='#order' and @data-test-id='PageTeaserDict_button']"));
        scrollToElementJS(molodHeader);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(orderButton));
        orderButton.click();

        String inputXPath = "//input[@data-name='%s']";

        WebElement inputField = driver.findElement(By.xpath(String.format(inputXPath,"lastName")));
        String value = "Иванов";
        fillInputField(inputField,value);
        assertThat(inputField.getAttribute("value"),is(value));

        inputField = driver.findElement(By.xpath(String.format(inputXPath,"firstName")));
        value = "Сергей";
        fillInputField(inputField,value);
        assertThat(inputField.getAttribute("value"),is(value));

        inputField = driver.findElement(By.xpath(String.format(inputXPath,"middleName")));
        value = "Петрович";
        fillInputField(inputField,value);
        assertThat(inputField.getAttribute("value"),is(value));

        inputField = driver.findElement(By.xpath(String.format(inputXPath,"cardName")));
        value="SERGEI IVANOV";
        assertThat(inputField.getAttribute("value"),is(value));

        inputField = driver.findElement(By.xpath(String.format(inputXPath,"birthDate")));
        value = "13.10.2000";
        fillInputField(inputField,value);
        assertThat(inputField.getAttribute("value"),is(value));

        inputField = driver.findElement(By.xpath(String.format(inputXPath,"email")));
        value = "s.ivanov@example.com";
        fillInputField(inputField,value);
        assertThat(inputField.getAttribute("value"),is(value));

        inputField = driver.findElement(By.xpath(String.format(inputXPath,"phone")));
        value = "9991423333";
        fillInputField(inputField,value);
        assertThat(inputField.getAttribute("value"),is("+7 ("+value.substring(0,3)+") "+value.substring(3,6)+"-"+value.substring(6,8)+"-"+value.substring(8)));

        WebElement continueButton = driver.findElement(By.xpath("//button[@data-step='2']"));
        wait.until(ExpectedConditions.elementToBeClickable(continueButton));
        continueButton.click();

        List<WebElement> inputErrorMessage = driver.findElements(By.xpath("//div[contains(text(),'Обязательное поле')]"));
        Assertions.assertEquals(3, inputErrorMessage.size(),"Найдено неверное количество сообщений ошибки заполнения данных");
        String errorFieldXPath = "//label[contains(text(),'%s')]/following-sibling::div[@class='odcui-error__text']";
        assertAll("Warnings assertion",()->Assertions.assertEquals("Обязательное поле", driver.findElement(By.xpath(String.format(errorFieldXPath,"Серия"))).getText()),
                ()->Assertions.assertEquals("Обязательное поле", driver.findElement(By.xpath(String.format(errorFieldXPath,"Номер"))).getText()),
                ()->Assertions.assertEquals("Обязательное поле", driver.findElement(By.xpath(String.format(errorFieldXPath,"Дата выдачи"))).getText()));
    }

    private void fillInputField(WebElement element,String value){
        scrollToElementJS(element);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
        element.sendKeys(value);
    }

    private void scrollToElementJS(WebElement element) {
        JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;
        javascriptExecutor.executeScript("arguments[0].scrollIntoView(true);", element);
    }
}
