package com.tomtomscenery.scenery_backend.model.Impl;

public class PoiImpl {

     private String name;
     private long categoryId;
     private String address;
     private String localName;
     private double lon;
     private double lat;

     
     public PoiImpl(String namePoi, long categoryId, String addressPoi, String localNamePoi, double latPoi, double lonPoi)
     {
          this.name = namePoi;
          this.categoryId = categoryId;
          this.address = addressPoi;
          this.localName = localNamePoi;
          this.lat = latPoi;
          this.lon = lonPoi;
     }

     public PoiImpl() {
     }

     //Getters
     public String getName() {
          return name;
     }
     public String getAddress() {
          return address;
     }
     public String getLocalName() {
          return localName;
     }
     public double getLon() {
          return lon;
     }
     public double getLat() {
          return lat;
     }
     public long getCategoryId() {
          return categoryId;
     }


     @Override
     public String toString() {
          return         this.name +
                         this.categoryId +
                         this.address +
                         this.localName +
                         this.lat +
                         this.lon ;
     }
}