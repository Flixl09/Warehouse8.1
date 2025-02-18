package com.flixl.warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping(path = "/warehouse")
@SpringBootApplication
public class WarehouseApplication {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @PostMapping("/addProduct")
    public @ResponseBody String addProduct(
            @RequestParam String name,
            @RequestParam String category,
            @RequestParam Integer quantity,
            @RequestParam String unit,
            @RequestParam Integer warehouseID
            ) {
        Optional<Warehouse> w = warehouseRepository.findById(warehouseID);
        if (w.isEmpty()) {
            return "Warehouse not found";
        }
        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setQuantity(quantity);
        product.setUnit(unit);
        product.setWarehouseID(w.get());
        productRepository.save(product);
        return "Product added with ID: " + product.getId();
    }

    @GetMapping("/getWarehouse")
    public @ResponseBody String getWarehouse(@RequestParam(required = false) Integer id) {
        if (id != null) {
            Optional<Warehouse> w = warehouseRepository.findById(id);
            if (w.isEmpty()) {
                return "Warehouse not found";
            }
            return w.get().toString();
        } else {
            StringBuilder sb = new StringBuilder();
            for (Warehouse w : warehouseRepository.findAll()) {
                sb.append(w.toString()).append("\n");
            }
            return sb.toString();
        }
    }

    @GetMapping("/getProduct")
    public @ResponseBody String getProduct(@RequestParam(required = false) Integer id) {
        if (id != null) {
            Optional<Product> p = productRepository.findById(id);
            if (p.isEmpty()) {
                return "Product not found";
            }
            return p.get().toString();
        } else {
            StringBuilder sb = new StringBuilder();
            for (Product p : productRepository.findAll()) {
                sb.append(p.toString()).append("\n");
            }
            return sb.toString();
        }
    }

    @GetMapping("/get")
    public @ResponseBody String get(@RequestParam Integer warehouseID, @RequestParam Integer productID) {
        Optional<Warehouse> w = warehouseRepository.findById(warehouseID);
        if (w.isEmpty()) {
            return "Warehouse not found";
        }
        Optional<Product> p = productRepository.findById(productID);
        if (p.isEmpty()) {
            return "Product not found";
        }
        if (!Objects.equals(p.get().getWarehouseID().getId(), w.get().getId())) {
            return "Product not found in warehouse";
        }
        return p.get().toString();
    }

    @PostMapping("/updateWarehouse")
    public @ResponseBody String updateWarehouse(
            @RequestParam Integer id,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String address,
            @RequestParam(required = false) Integer postalCode,
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String country
            ) {
        Optional<Warehouse> w = warehouseRepository.findById(id);
        if (w.isEmpty()) {
            return "Warehouse not found";
        }
        if (name == null && address == null && postalCode == null && city == null && country == null) {
            return "No fields to update";
        }
        if (name == null) {
            name = w.get().getName();
        } if (address == null) {
            address = w.get().getAddress();
        } if (postalCode == null) {
            postalCode = w.get().getPostalCode();
        } if (city == null) {
            city = w.get().getCity();
        } if (country == null) {
            country = w.get().getCountry();
        }
        w.get().setName(name);
        w.get().setAddress(address);
        w.get().setPostalCode(postalCode);
        w.get().setCity(city);
        w.get().setCountry(country);
        warehouseRepository.save(w.get());
        return "Warehouse updated";
    }

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }

}
