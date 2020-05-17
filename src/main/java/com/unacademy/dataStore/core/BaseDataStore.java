package com.unacademy.dataStore.core;

/*
   Any typical Data Store, T is the basic unit of Data
 */

public interface BaseDataStore<T> {

    /*
    Sets the value, for given key for ttl time
     */
    void set(String key, String value, long ttl) throws Exception;
    /*
    Gets the value for key, null if not present
     */
    String get(String key) throws Exception;
    /*
    Flushes the value for the given key and returns true, false if not present
     */
    void flush(String key) throws Exception;
    /*
    Bootstrap this node, with dump
     */
    void bootStrap(T dump) throws Exception;
    /*
    Get dump
     */
    T getDump() throws Exception;
}
