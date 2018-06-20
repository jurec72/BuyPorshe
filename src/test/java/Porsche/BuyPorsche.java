package Porsche;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import io.github.bonigarcia.wdm.WebDriverManager;

public class BuyPorsche {
	// method that takes string and return digits
	public static int digitFromString(String a) {
		String b = "";
		for (int i = 0; i < a.length(); i++) {
			char c7 = a.charAt(i);
			if (a.charAt(i) == '.')
				break;
			if (Character.isDigit(c7)) {
				b += c7;
			}
		}
		int t = Integer.parseInt(b);
		return t;
	}

	// start
	// =======================================================================================================
	public static void main(String[] args) throws InterruptedException {

		WebDriverManager.chromedriver().setup();// Set up chromedriver path
		WebDriver d = new ChromeDriver();
		d.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		d.manage().window().fullscreen(); // makes browser fullscreen
		String url = "https://www.porsche.com/usa/modelstart/";
		d.get(url);

		d.findElement(By.xpath("//img[@alt='Porsche - 718']")).click();

		// ===============================================================================================================

		// test 4
		String basePricebefore = d
				.findElement(By.xpath("//div[@id='model-result-row']/div[1]/div/div[1]/div[2]/div[2]")).getText();

		int priceBaseBefore = digitFromString(basePricebefore);

		// ===============================================================================================================
		//test 5
		d.findElement(By.linkText("Build & Price")).click();// click on the button
		// change to another browser window
		String parentWindow = d.getWindowHandle();
		Set<String> handles = d.getWindowHandles();
		for (String windowHandle : handles) {
			if (!windowHandle.equals(parentWindow)) {
				d.switchTo().window(windowHandle);
			}
		}
		// ===============================================================================================================
		// test 6
		// base price
		String basePriceAfter = d.findElement(By.xpath("//section[@id='s_price']/div[1]/div[1]/div[2]")).getText();

		int priceBaseAfter = digitFromString(basePriceAfter);

		if (priceBaseBefore == priceBaseAfter) {
			System.out.println("Pass.price the same - test 6");
		} else {
			System.out.println("Fail.Price is different - test 6");
		}

		// ===============================================================================================================
		// test 7
		// equipment price
		String equipmPr = d.findElement(By.xpath("//section[@id='s_price']/div[1]/div[2]")).getText();

		int actualEqPrice = digitFromString(equipmPr); // actual equipment price
		int expectedEqPrice = 0;

		if (actualEqPrice == expectedEqPrice) {
			System.out.println("Pass.Price of Equipment is correct - test 7");
		} else {
			System.out.println("Fail.Price of Equipment is not correct - test 7");
		}

		// ===============================================================================================================
		// test 8
		// delivery price
		String delivery = d.findElement(By.xpath("//section[@id='s_price']/div[1]/div[3]")).getText();
		int priceDelivery = digitFromString(delivery);

		// ===============================================================================================================

		// totalprice 1
		String lastPrice = d.findElement(By.xpath("//section[@id='s_price']/div[1]/div[4]")).getText();

		int totalPrice8 = digitFromString(lastPrice);

		if (totalPrice8 == priceDelivery + priceBaseAfter) {
			System.out.println("Pass.Total price is correct - test 8");
		} else {
			System.out.println("Fail.Total price is not correct - test 8");
		}
		// ===============================================================================================================
		// test 9
		// click on blue color
		WebElement doublCl = d.findElement(By.xpath("//span[@style='background-color: rgb(0, 120, 138);']"));

		Actions action = new Actions(d);// create Action object for mouse

		action.moveToElement(doublCl).doubleClick().build().perform();

		// ===============================================================================================================
		// test 10
		// equipment total price
		String equipmPrColor = d.findElement(By.xpath("//section[@id='s_price']/div[1]/div[2]")).getText();
		int priceEquipmentTotal = digitFromString(equipmPrColor);

		// color price
		String exPri = d.findElement(By.xpath("//div[@id='s_exterieur_x_IAF']/div[2]/div[1]/div[1]/div[2]")).getText();
		int priceColor = digitFromString(exPri);

		if (priceEquipmentTotal == priceColor) {
			System.out.println("Pass.Price of Equipment is correct! After adding color - test 10");
		} else {
			System.out.println("Fail.Price of Equipment is not correct! After adding color - test 10");
		}

		// ===============================================================================================================
		// test 11
		// last total price
		String totalPrice11 = d.findElement(By.xpath("//section[@id='s_price']/div[1]/div[4]")).getText();

		int priceTotal11 = digitFromString(totalPrice11);

		if (priceTotal11 == priceEquipmentTotal + priceBaseAfter + priceDelivery) {
			System.out.println("Pass.Total price is correct - test 11");
		} else {
			System.out.println("Fail.Total price is not correct - test 11");
		}

		// ===============================================================================================================
		// test 12
		// select wheels
		JavascriptExecutor jse = (JavascriptExecutor) d;
		jse.executeScript("scroll(0, 400)");

		WebElement wheel = d.findElement(By.xpath("//div[@id='ARA']/ul/li[8]/span/span"));
		Thread.sleep(2000);
		action.moveToElement(wheel).click().build().perform();

		// =================================================================================================================

		// //test 13
		// //equipment price with wheel

		String totalPriceEquipment = d
				.findElement(By.xpath("//div[@id='main']/div[4]/section[2]/section[2]/div[1]/div[2]/div[2]")).getText();
		int priceTotalEquipment13 = digitFromString(totalPriceEquipment);

		List<WebElement> a4 = d.findElements(By.xpath("//div[@class='tt_price tt_cell']"));
		String wheelPrice1 = a4.get(1).getText();
		int priceWheel = digitFromString(wheelPrice1);

		if (priceTotalEquipment13 == priceWheel + priceEquipmentTotal) {
			System.out.println("Pass.Total equipment price is correct with wheel and color - test 13");
		} else {
			System.out.println("Fail.Total equipment price is not correct with wheel and color - test 13");
			System.out.println("Total price - " + priceTotalEquipment13);
			System.out.println("Wheel price - " + priceWheel);
			System.out.println("Color price - " + priceEquipmentTotal);
		}

		// =================================================================================================================
		// test 14
		// Verify that total price

		String totalPrice5 = d
				.findElement(By.xpath("//div[@id='main']/div[4]/section[2]/section[2]/div[1]/div[4]/div[2]")).getText();
		int priceTotal14 = digitFromString(totalPrice5);

		if (priceTotal14 == priceBaseAfter + priceTotalEquipment13 + priceDelivery) {
			System.out.println("Pass. All correct. - test 14");
		} else {
			System.out.println("Fail.Price is not correct - test 14");
			System.out.println("Total price - " + priceTotal14);
			System.out.println("Equipment price - " + priceTotalEquipment13);
			System.out.println("Delivery price - " + priceDelivery);
		}
		// =================================================================================================================
		// test 15
		// Select seats ‘Power Sport Seats (14-way) with Memory Package’
		Thread.sleep(2000);
		jse.executeScript("scroll(0, 1400)");
		Thread.sleep(3000);
		WebElement radio = d.findElement(By.xpath("//div[@id='seats_73']/div[2]/span"));
		action.moveToElement(radio).click().build().perform();

		// ====================================================================================================================\
		// test 16
		String seat = d.findElement(By.xpath("//div[@id='seats_73']/div[2]/div[1]/div[3]/div[1]")).getText();
		int priceSeat = digitFromString(seat);
		//
		String totalPriceEquipment16 = d
				.findElement(By.xpath("//div[@id='main']/div[4]/section[2]/section[2]/div[1]/div[2]/div[2]")).getText();
		int priceTotalEquipment16 = digitFromString(totalPriceEquipment16);

		if (priceTotalEquipment16 == priceSeat + priceWheel + priceColor) {
			System.out.println("Pass. Total equipment price with color,wheel,seat are correct - test 16");
		} else {
			System.out.println("Fail. Total equipment price with color,wheel,seat are not correct - test 16");
			System.out.println("Total price is - " + priceTotalEquipment16 + " Seat price is -" + priceSeat
					+ " Wheel price is " + priceWheel + " Color price is - " + priceColor);

		}
		// =================================================================================================================
		// test 17
		String totalPrice7 = d
				.findElement(By.xpath("//div[@id='main']/div[4]/section[2]/section[2]/div[1]/div[4]/div[2]")).getText();
		int priceTotal17 = digitFromString(totalPrice7);
		if (priceTotal17 == priceBaseAfter + priceDelivery + priceTotalEquipment16) {
			System.out.println("Pass.Total price is correct - test 17");
		} else {
			System.out.println("Fail. Total price is not correct - test 17");
		}
		// =================================================================================================================
		// test 18

		Thread.sleep(1000);
		jse.executeScript("scroll(0, 2200)");// scroll to down
		WebElement click1 = d.findElement(By.xpath("//div[@id='IIC_wrapper']/div/div"));
		Thread.sleep(1000);
		// d.findElement(By.xpath("//div[@id='IIC_subHdl']']")).click();
		action.moveToElement(click1).click().build().perform();

		// =================================================================================================================
		// test 19
		WebElement carbon = d.findElement(By.xpath("//span[@id='vs_table_IIC_x_PEKH_x_c01_PEKH']"));
		Thread.sleep(1000);
		action.moveToElement(carbon).click().build().perform();
		// =================================================================================================================
		// test 20
		// Verify that Price for Equipment is the sum of Miami Blue price
		// interior price
		String intPrice = d.findElement(By.xpath("//div[@id='vs_table_IIC_x_PEKH']/div[1]/div[2]/div[1]")).getText();
		int priceInterior = digitFromString(intPrice);
		// last total price equipment
		String totalPriceEquipment1 = d
				.findElement(By.xpath("//div[@id='main']/div[4]/section[2]/section[2]/div[1]/div[2]/div[2]")).getText();
		int priceTotalEquipment20 = digitFromString(totalPriceEquipment1);

		if (priceTotalEquipment20 == priceInterior + priceSeat + priceWheel + priceColor) {
			System.out.println("Pass. All prices are correct - test 20");
		} else {
			System.out.println("Fail. Prices are not correct - test 20");

		}
		// =================================================================================================================
		// test 21
		/*
		 * Verify that total price is the sum of base price + Price for Equipment +
		 * Delivery,Processing and Handling Fee
		 */
		String totalPrice21 = d
				.findElement(By.xpath("//div[@id='main']/div[4]/section[2]/section[2]/div[1]/div[4]/div[2]")).getText();
		int priceTotal21 = digitFromString(totalPrice21);

		if (priceTotal21 == priceBaseAfter + priceTotalEquipment20 + priceDelivery) {
			System.out.println("Pass - test 21");
		} else {
			System.out.println("Fail - test 21");
		}
		// =================================================================================================================
		// test 22
		// Click on Performance
		Thread.sleep(1000);
		d.findElement(By.xpath("//div[@id='IMG_subHdl']")).click();
		// =================================================================================================================
		// test 23
		// Select 7-speed Porsche Doppelkupplung (PDK)
		Thread.sleep(1000);
		d.findElement(By.xpath("//span[@id='vs_table_IMG_x_M250_x_c11_M250']")).click();
		// =================================================================================================================
		// test 24
		// Select Porsche Ceramic Composite Brakes (PCCB)
		Thread.sleep(1000);

		jse.executeScript("scroll(0, 2800)");

		Thread.sleep(1000);
		d.findElement(By.xpath("//span[@id='vs_table_IMG_x_M450_x_c91_M450']")).click();
		// =================================================================================================================
		// test 25

		// price 7 speed
		String SevenSpeed = d.findElement(By.xpath("//div[@id='vs_table_IMG_x_M250']/div[1]/div[2]/div")).getText();
		int priceSevenSpeed = digitFromString(SevenSpeed);

		// price ceramic brakes
		String brakes = d.findElement(By.xpath("//div[@id='vs_table_IMG_x_M450']/div/div[2]/div")).getText();
		int priceBrakes = digitFromString(brakes);

		String totalPriceEquipment25 = d
				.findElement(By.xpath("//div[@id='main']/div[4]/section[2]/section[2]/div[1]/div[2]/div[2]")).getText();
		int priceTotalEquipment25 = digitFromString(totalPriceEquipment25);

		if (priceTotalEquipment25 == priceColor + priceSeat + priceWheel + priceInterior + priceSevenSpeed
				+ priceBrakes) {
			System.out.println("Pass - test 25");
		} else {
			System.out.println("Fail - test 25");
		}
		// =================================================================================================================
		// test 26
		String totalPrice26 = d
				.findElement(By.xpath("//div[@id='main']/div[4]/section[2]/section[2]/div[1]/div[4]/div[2]")).getText();
		int priceTotal26 = digitFromString(totalPrice26);

		if (priceTotal26 == priceTotalEquipment25 + priceBaseAfter + priceDelivery) {
			System.out.println("Pass - test 26");
		} else {
			System.out.println("Fail - test 26");
		}

		// =================================================================================================================
		// // d.switchTo().window(parentWindow); // return to parent window
		Thread.sleep(2000);
		d.quit();
	}

}
