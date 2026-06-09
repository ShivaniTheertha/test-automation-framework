package com.orangehrm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DashBoard extends BasePage {

    By dashboardHeader = By.xpath("//h6[contains(@class,'oxd-topbar-header-breadcrumb-module') and normalize-space()='Dashboard']");
    By sidebarMenu  = By.className("oxd-main-menu");


    public String verifyDashBoardHeader() {
        // Wait for URL, page load, then sidebar, then header
        wait.until(ExpectedConditions.urlContains("dashboard"));

        wait.until(driver -> ((JavascriptExecutor) driver)
                .executeScript("return document.readyState")
                .equals("complete"));

        wait.until(ExpectedConditions.presenceOfElementLocated(sidebarMenu));

        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(dashboardHeader)
        ).getText().trim();
    }

    public void selectHamburgerMenu(String menuName) {
        // Wait for sidebar to be present
        wait.until(ExpectedConditions.presenceOfElementLocated(sidebarMenu));

        By menuLocator = By.xpath(
                "//ul[@class='oxd-main-menu']" +
                        "//span[normalize-space()='" + menuName + "']"
        );

        WebElement menuElement = wait.until(
                ExpectedConditions.elementToBeClickable(menuLocator)
        );

        jsScrollIntoView(menuElement);

        try {
            menuElement.click();
        } catch (Exception e) {
            jsClick(menuElement);
        }
    }
}
