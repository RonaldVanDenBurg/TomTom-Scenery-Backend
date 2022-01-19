package com.tomtomscenery.scenery_backend.services.impl;

import com.tomtomscenery.scenery_backend.services.I_UrlBuildService;
import org.springframework.stereotype.Service;

@Service
public class UrlBuildServiceImpl implements I_UrlBuildService
{
     private String urlSearchString;
     private int limit = 20;

     // This method gathers all the information from the GET-Request (String category, double lat, double lon),
     // to form a String (urlSearchString).
     // This String will be used to search for Points Of Interest via the TomTom API.
     @Override
     public void setUrlSearchString(String category, double lat, double lon)
     {
          String baseUrl      = "https://api.tomtom.com/search/2/categorySearch/";
          String key          = "akRRoJXWg8BsEebGgXquaebxtD95DZ2d";
          String lang         = "nl-NL";
          int radius          = 1000;
          String urlSearchString =  baseUrl + category + ".json?key=" + key + "&language=" + lang + "&lat=" + lat + "&lon=" + lon + "&radius=" + radius + "&limit=" + limit;
          this.urlSearchString = urlSearchString;
     }

     @Override
     public int getLimit() {
          return limit;
     }

     @Override
     public void setLimit(int limit) {
          this.limit = limit;
     }

     @Override
     public String getUrlSearchString() {
          return urlSearchString;
     }
}
