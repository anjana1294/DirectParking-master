package com.directparking.app.ui.login.model;

import java.io.Serializable;

public class CarData  implements Serializable {

    private String color;
    private String model;
    private String plateNumber;
    private String licenceNumber;
    private String carMake;

    public CarData(String color, String model, String plateNumber, String licenceNumber,
                   String carMake) {
        this.color = color;
        this.model = model;
        this.plateNumber = plateNumber;
        this.licenceNumber = licenceNumber;
        this.carMake = carMake;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public void setLicenceNumber(String licenceNumber) {
        this.licenceNumber = licenceNumber;
    }

    public void setCarMake(String carMake) {
        this.carMake = carMake;
    }

    public String getColor() {
        return color;
    }

    public String getModel() {
        return model;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public String getCarMake() {
        return carMake;
    }

    public static class Builder {

        private String color;
        private String model;
        private String plateNumber;
        private String licenceNumber;
        private String carMake;

        public Builder setColor(String color) {
            this.color = color;
            return this;
        }

        public Builder setModel(String model) {
            this.model = model;
            return this;
        }

        public Builder setPlateNumber(String plateNumber) {
            this.plateNumber = plateNumber;
            return this;
        }

        public Builder setLicenceNumber(String licenceNumber) {
            this.licenceNumber = licenceNumber;
            return this;
        }

        public Builder setCarMake(String carMake) {
            this.carMake = carMake;
            return this;
        }

        public CarData build() {
            return new CarData(color, model, plateNumber, licenceNumber, carMake);
        }
    }
}