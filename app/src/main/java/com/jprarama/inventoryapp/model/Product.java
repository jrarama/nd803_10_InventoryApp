package com.jprarama.inventoryapp.model;

import com.jprarama.inventoryapp.data.DbAnotations.Column;
import com.jprarama.inventoryapp.data.DbAnotations.Key;
import com.jprarama.inventoryapp.data.DbAnotations.Table;
import com.jprarama.inventoryapp.data.DbContract.ProductEntry;

/**
 * Created by joshua on 6/7/16.
 */
@Table(name = ProductEntry.TABLE_NAME)
public class Product {

    @Key
    @Column(name = ProductEntry._ID, type = "INTEGER")
    private Integer id;

    @Column(name = ProductEntry.COLUMN_TITLE)
    private String title;

    @Column(name = ProductEntry.COLUMN_IMAGE_PATH)
    private String imagePath;

    @Column(name = ProductEntry.COLUMN_PRICE, type = "FLOAT")
    private float price;

    @Column(name = ProductEntry.COLUMN_QUANTITY, type = "FLOAT")
    private float quantity;

    public Product() {
    }

    public Product(String title, String imagePath, float price, float quantity) {
        this.title = title;
        this.imagePath = imagePath;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getQuantity() {
        return quantity;
    }

    public void setQuantity(float quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", imagePath='" + imagePath + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                '}';
    }
}
