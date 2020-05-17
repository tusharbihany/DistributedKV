package com.unacademy.dataStore.core;

import com.google.inject.Inject;
import com.unacademy.dataStore.models.DataModel;
import com.unacademy.dataStore.scheduler.BackgroundScheduler;

import java.util.*;
import java.util.concurrent.Callable;

public class MapDataStore implements BaseDataStore<DataModel> {

    private Timer timer;
    protected volatile DataModel dataModel;

    @Inject
    public MapDataStore(DataModel dataModel) {
        timer = new Timer();
        BackgroundScheduler.scheduleTTLCleanUp(timer, getTTLEvaluatorCallable());
        this.dataModel = dataModel;
    }

    @Override
    public void set(String key, String value, long ttl) throws Exception {
        long expiryTime = ttl + System.currentTimeMillis();
        this.dataModel.getDataMap().put(key, value);
        this.dataModel.getTimeMap().put(key, expiryTime);
    }

    @Override
    public String get(String key) throws Exception {
        return this.dataModel.getDataMap().get(key);
    }

    @Override
    public void flush(String key) throws Exception {
        this.dataModel.getDataMap().remove(key);
        this.dataModel.getTimeMap().remove(key);
    }

    @Override
    public void bootStrap(DataModel dump) throws Exception {
        this.dataModel = dump;
    }

    @Override
    public DataModel getDump() throws Exception {
        return this.dataModel;
    }

    /*
    Checks if the current time >= ttl, if yes removes that key.
     */
    private Callable<Void> getTTLEvaluatorCallable() {
        Callable<Void> requiredCallable = new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                long currentTime = System.currentTimeMillis();
                Iterator<Map.Entry<String, Long>> itr = dataModel.getTimeMap().entrySet().iterator();
                while (itr.hasNext()) {
                    Map.Entry<String, Long> entry = itr.next();
                    if (entry.getValue() <= currentTime) {
                        itr.remove();
                        dataModel.getDataMap().remove(entry.getKey());
                    }
                }
                return null;
            }
        };
        return requiredCallable;
    }
}
