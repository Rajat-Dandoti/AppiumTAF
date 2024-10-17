package org.example;

import org.example.base.AppiumServer;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

public class AppiumTest extends AppiumServer {
    @Test
    public void testSampleFeature() throws InterruptedException {
        driver.get("http://www.google.com");
        Thread.sleep(5000);
        driver.findElement(By.xpath("//input[@name='q']")).sendKeys("Appium");
        driver.findElement(By.xpath("//button[@aria-label='Google Search']")).click();
        Thread.sleep(5000);
    }
}