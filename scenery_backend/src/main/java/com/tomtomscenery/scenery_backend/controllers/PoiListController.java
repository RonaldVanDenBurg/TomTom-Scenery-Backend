package com.tomtomscenery.scenery_backend.controllers;

import com.tomtomscenery.scenery_backend.exceptions.PoiNotFoundException;
import com.tomtomscenery.scenery_backend.model.Impl.PoiImpl;
import com.tomtomscenery.scenery_backend.services.I_DataProcessorService;
import com.tomtomscenery.scenery_backend.services.I_UrlBuildService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PoiListController
{
     @Autowired
     I_DataProcessorService dataProcessorService ;

     @Autowired
     I_UrlBuildService urlBuildService;


     // The getPoiUser REST API returns a list of Points of interest to the frontend.
     @GetMapping("getpoi/{category}/{latitude}/{longitude}")
     public ResponseEntity<List<PoiImpl>> getPoiUser    (@PathVariable(name = "category") String category,
                                                         @PathVariable(name = "latitude") double lat,
                                                         @PathVariable(name = "longitude") double lon)
     {
          urlBuildService.setUrlSearchString(category, lat, lon);

          if (dataProcessorService.getPois() == null)
          {
               throw new PoiNotFoundException("No content");
          }
          HttpHeaders responseHeaders = new HttpHeaders();
          responseHeaders.set("Access-Control-Allow-Origin", "*");
          responseHeaders.set("Content-Type", "application/json; charset=UTF-8");
          return new ResponseEntity<>(dataProcessorService.getPois(),responseHeaders, HttpStatus.OK);
     }
}
