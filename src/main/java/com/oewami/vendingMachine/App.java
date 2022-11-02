package com.oewami.vendingMachine;

import com.oewami.vendingMachine.controller.VendingMachineController;
import com.oewami.vendingMachine.service.InsufficientFundsException;
import com.oewami.vendingMachine.service.ItemOutOfStockException;
import com.oewami.vendingMachine.service.PersistenceException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.math.BigDecimal;

public class App {

    public static void main(String[] args) throws ItemOutOfStockException, InsufficientFundsException, PersistenceException {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.scan("com.oewami.vendingMachine");
        applicationContext.refresh();

        VendingMachineController controller = applicationContext.getBean("vendingMachineController", VendingMachineController.class);
        controller.run();


    }
}
