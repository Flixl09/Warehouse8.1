package com.flixl.warehouse;

import jakarta.persistence.*;

@Entity
@Table(name = "products", schema = "Warehuse")
public class Product {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit", nullable = false)
    private String unit;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "warehouse_id", nullable = false)
    private com.flixl.warehouse.Warehouse warehouseID;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public com.flixl.warehouse.Warehouse getWarehouseID() {
        return warehouseID;
    }

    public void setWarehouseID(com.flixl.warehouse.Warehouse warehouseID) {
        this.warehouseID = warehouseID;
    }


    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", category='" + category + '\'' +
                ", quantity=" + quantity +
                ", unit='" + unit + '\'' +
                ", warehouseID=" + warehouseID +
                '}';
    }
}