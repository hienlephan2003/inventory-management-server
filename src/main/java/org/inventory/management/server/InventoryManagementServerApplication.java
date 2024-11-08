package org.inventory.management.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InventoryManagementServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryManagementServerApplication.class, args);
        System.out.println("Inventory Management Server Application Started with http://localhost:8086/swagger-ui/index.html");
    }
}
