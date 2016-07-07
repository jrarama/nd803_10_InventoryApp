package com.jprarama.inventoryapp.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by joshua on 6/7/16.
 */
public class Product implements Parcelable {

    private int id;

    private String title;

    private String imagePath;

    private float price;

    private float quantity;

    public Product() {
    }

    public Product(String title, String imagePath, float price, float quantity) {
        this.title = title;
        this.imagePath = imagePath;
        this.price = price;
        this.quantity = quantity;
    }

    protected Product(Parcel in) {
        id = in.readInt();
        title = in.readString();
        imagePath = in.readString();
        price = in.readFloat();
        quantity = in.readFloat();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(imagePath);
        parcel.writeFloat(price);
        parcel.writeFloat(quantity);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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
