package com.sree.school;

public class Car {

        private String model;
        private int year;
        private String manufacturer;
        private String color;
        private String id;
		private int price;
		private boolean sold;
		private String brand;
        
        public Car(String model, int year, String manufacturer, String color) {
                this.model = model;
                this.year = year;
                this.manufacturer = manufacturer;
                this.color = color;
        }

        public Car(String randomId, String randomBrand, int randomYear, String randomColor, int randomPrice,
				boolean randomSoldState) {
			this.setId(randomId);
			this.setBrand(randomBrand);
			this.year = randomYear;
			this.color = randomColor;
			this.setPrice(randomPrice);
			this.setSold(randomSoldState);
		}

		public String getModel() {
                return model;
        }

        public void setModel(String model) {
                this.model = model;
        }

        public int getYear() {
                return year;
        }

        public void setYear(int year) {
                this.year = year;
        }

        public String getManufacturer() {
                return manufacturer;
        }

        public void setManufacturer(String manufacturer) {
                this.manufacturer = manufacturer;
        }

        public String getColor() {
                return color;
        }

        public void setColor(String color) {
                this.color = color;
        }

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public int getPrice() {
			return price;
		}

		public void setPrice(int price) {
			this.price = price;
		}

		public boolean isSold() {
			return sold;
		}

		public void setSold(boolean soldState) {
			this.sold = soldState;
		}

		public String getBrand() {
			return brand;
		}

		public void setBrand(String brand) {
			this.brand = brand;
		}
}