package com.tomtomscenery.scenery_backend.services;

import com.tomtomscenery.scenery_backend.model.Impl.PoiImpl;

import java.util.List;

public interface I_DataProcessorService
{
     List<PoiImpl> getPois();
     void setCounter(int counter);
     int getCounter();
}
