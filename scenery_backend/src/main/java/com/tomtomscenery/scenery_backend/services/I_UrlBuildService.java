package com.tomtomscenery.scenery_backend.services;

public interface I_UrlBuildService {
     String getUrlSearchString();
     void setUrlSearchString(String category, double lat, double lon);
     int getLimit();
     void setLimit(int limit);
}