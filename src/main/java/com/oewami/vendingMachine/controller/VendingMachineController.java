package com.oewami.vendingMachine.controller;

import com.oewami.vendingMachine.dto.Item;
import com.oewami.vendingMachine.service.*;
import com.oewami.vendingMachine.ui.VendingMachineView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VendingMachineController {

    @Autowired
    private VendingMachineView view;

    @Autowired
    private VendingMachineService service;

    public VendingMachineController() {
        this.view = new VendingMachineView();
        this.service = new VendingMachineServiceImpl();
    }

    public VendingMachineController(VendingMachineView view, VendingMachineService service) {
        this.view = view;
        this.service = service;
    }

    public void run() throws InsufficientFundsException, PersistenceException {
        boolean isContinuing = true;


        while(isContinuing) {
            String input = view.getMenu();
            view.displayBalance(service.getBalance());

            switch(input) {
                case "1":
                    addMoney();
                    break;
                case "2":
                    showItems();
                    break;
                case"3":
                    buyItem();
                    break;
                case "4":
                    isContinuing = false;
                    break;
                default:
                    view.displayInvalidInput();
            }
        }
        service.exit();
        view.displayRefund(service.getBalance());
        service.adjustBalance(service.getBalance());
        view.displayExit();
    }

    public void addMoney() {
        service.addFunds(view.addMoney());
    }

    public void showItems() {
        List<Item> items = service.listAllItems();
        view.displayItems(items);
    }

    public void buyItem() throws InsufficientFundsException, PersistenceException {
        view.displayItems(service.listAllItems());
        String input = view.buyItem();
        Item item = service.getItem(input);
        if (item != null) {
            service.sellItem(item);
            view.displayPurchasedItem(item);
            view.displayChange(service.getChange());
        } else view.displayInvalidInput();
    }
}
