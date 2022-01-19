package com.tomtomscenery.scenery_backend.services.impl;

import com.tomtomscenery.scenery_backend.exceptions.PoiNotFoundException;
import com.tomtomscenery.scenery_backend.model.Impl.PoiImpl;
import com.tomtomscenery.scenery_backend.services.I_DataProcessorService;
import com.tomtomscenery.scenery_backend.services.I_UrlBuildService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataProcessorServiceImpl implements I_DataProcessorService {
     private int counter;
     private final I_UrlBuildService urlBuildService;
     private List<PoiImpl> poiCollection;


     public DataProcessorServiceImpl(I_UrlBuildService urlBuildService) {
          this.urlBuildService = urlBuildService;
     }

     public void setPoiCollection(List<PoiImpl> poiCollection) {
          this.poiCollection = poiCollection;
     }

     public int getCounter() {
          return counter;
     }

     public void setCounter(int counter) {
          this.counter = counter;
     }


     // Read incoming data and store it into the StringBuilder object.
     private void read(BufferedReader bufferedReader, StringBuilder stringBuilder) throws IOException {
          String line;

          while ((line = bufferedReader.readLine()) != null) {
               stringBuilder.append(line);
          }
     }

     // Create an URL using the UrlSearchString from the UrlBuildService class.
     private URL getUrl() throws MalformedURLException {
          return new URL(urlBuildService.getUrlSearchString());
     }


     // This method checks if the length of the array is smaller than the limit (limited amount of results).
     // If the length of the array is smaller than the limit it will set the limit with the length of the
     // array to avoid arrayOutOfBoundExceptions.
     private void checkLengthResultArray(JSONArray resultArrayObj)
     {
          if (resultArrayObj.length() == 0) {
               throw new PoiNotFoundException("No content found.");
          }
          else if (resultArrayObj.length() < urlBuildService.getLimit()) {
               urlBuildService.setLimit(resultArrayObj.length());
          }
     }


     // This method filters the specific information out of the data coming from the resultsArray
     // and returns that information stored in a PoiImpl object.
     private PoiImpl getSpecificInformation(StringBuilder stringBuilderObject)
     {
          JSONObject jsonObj = new JSONObject(stringBuilderObject.toString());

          // JSONArray object to navigate to resultsArray.
          JSONArray resultArrayObj = jsonObj.getJSONArray("results");

          checkLengthResultArray(resultArrayObj);

          // JSONObject to navigate to the poi object within de resultsArray at a specific index(counter).
          JSONObject poi = resultArrayObj.getJSONObject(counter).getJSONObject("poi");
          // JSONArray object to navigate to categorySetArray within poi object.
          JSONArray categorySetArray = poi.getJSONArray("categorySet");
          // JSONObject to navigate to the address object within de resultsArray at a specific index(counter).
          JSONObject addressPOI = resultArrayObj.getJSONObject(counter).getJSONObject("address");
          // JSONObject to navigate to the position object within de resultsArray at a specific index(counter).
          JSONObject positionPoi = resultArrayObj.getJSONObject(counter).getJSONObject("position");

          String namePoi = poi.getString("name");
          long categoryIdPoi = categorySetArray.getJSONObject(0).getLong("id");
          String addressPoi = addressPOI.getString("freeformAddress");
          String localNamePoi = addressPOI.getString("localName");
          double latPoi = positionPoi.getDouble("lat");
          double lonPoi = positionPoi.getDouble("lon");

          return new PoiImpl(namePoi, categoryIdPoi, addressPoi, localNamePoi, latPoi, lonPoi);
     }


     // The method 'getPois' makes a connection the TomTom API and gets the data it requested via the
     // URL that has been build. Based on the amount of results it will loop through the data and adds
     // PoiImpl objects to a list and finally it returns a list to the controller.
     @Override
     public List<PoiImpl> getPois() throws PoiNotFoundException
     {
          try {
                    URL url = getUrl();
                    URLConnection urlConnection = url.openConnection();
                    List<PoiImpl> listOfPois = new ArrayList<>();

                    if (((HttpURLConnection) urlConnection).getResponseCode() == 200)
                    {
                         InputStream inputStream = urlConnection.getInputStream();
                         BufferedReader bufferedReaderObject = new BufferedReader(new InputStreamReader(inputStream));
                         StringBuilder stringBuilderObject = new StringBuilder();

                         read(bufferedReaderObject, stringBuilderObject);

                         for(int i = 0; i < urlBuildService.getLimit(); i++)
                         {
                              getSpecificInformation(stringBuilderObject);
                              listOfPois.add(getSpecificInformation(stringBuilderObject));
                              setCounter(i);
                         }
                         setPoiCollection(listOfPois);
                         return poiCollection;
                    }
               }
               catch (IOException e) {
                    e.printStackTrace();
               }
               return null;
     }
}
