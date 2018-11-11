/**
 *
 * <h1>
        Elastisearch database
 * </h1>
 *
 *  <p>
 *     Description: <br>
 *         The purpose of this class is to interact with the Elastisearch database to store and
 *         retrieve data to be used within the application.
 *
 * </p>
 *
 * <p>
 *     References: <br>
 *
 *         Android Developer Guide, Determine and monitor the connectivity status
 *         https://developer.android.com/training/monitoring-device-state/connectivity-monitoring#java
 *         Last updated 2018-05-08, accessed 2018-11-09
 * </p>
 *
 * @author Tem Tamre, Thomas Roskewich
 * @contact ttamre@ualberta.ca, roskewic@ualbeta.ca
 * @see cs.ualberta.ca.medlog.helper.FileSaver
 */

package cs.ualberta.ca.medlog.helper;

import android.content.Context;
import android.util.Log;

import com.searchly.jestdroid.JestDroidClient;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import cs.ualberta.ca.medlog.entity.BodyLocation;
import cs.ualberta.ca.medlog.entity.MapLocation;
import cs.ualberta.ca.medlog.entity.Problem;
import cs.ualberta.ca.medlog.entity.user.CareProvider;
import cs.ualberta.ca.medlog.entity.user.Patient;

public class Database {
    public Context context;
    private String databaseAddress;  // "http://cmput301.softwareprocess.es:8080/cmput301f18t17"
    private int timeout = 10;
    private JestDroidClient client = null;


    public Context getDatabaseContext() {
        return context;
    }

    public String getDatabaseAddress() {
        return databaseAddress;
    }

    public int getTimeout() {
        return timeout;
    }


    public void setDatabaseContext(Context newContext){
        this.context = newContext;
    }

    public void setDatabaseAddress(String newDatabaseAddress) {
        this.databaseAddress = newDatabaseAddress;
    }

    public void setTimeout(int newTimeout) {
        this.timeout = newTimeout;
    }


    /**
     * <p>Get a patient from the database if a connection can be established, load from disc otherwise</p>
     * @return patient (Patient that was retrieved or loaded)
     */
    public Patient LoadPatient(){
        Patient patient = null;

        if (checkConnectivity()) {
            // Database operations
        } else {
            FileSaver saver = new FileSaver(context);
            patient = saver.loadPatient();
        }

        return patient;
    }


    /**
     * <p>Get a provider from the database if a connection can be established, load from disc otherwise</p>
     * @return provider (Provider that was retrieved or loaded)
     */
    public CareProvider LoadProvider(){
        CareProvider provider = null;

        if (checkConnectivity()) {
            // Database operations
        } else {
            FileSaver saver = new FileSaver(context);
            provider = saver.loadCareProvider();
        }

        return provider;
    }


    /**
     * <p>Push a patient to the database if a connection can be established, save to disc otherwise</p>
     * @param patient Patient to be saved
     */
    public void SavePatient(Patient patient){
        if (checkConnectivity()) {
            // Database operations
        } else {
            FileSaver saver = new FileSaver(context);
            saver.savePatient(patient);
        }
    }


    /**
     * <p>Push a provider to the database if a connection can be established, save to disc otherwise</p>
     * @param provider Provider to be saved
     */
    public void SaveProvider(CareProvider provider){
        if (checkConnectivity()) {
            // Database operations
        } else {
            FileSaver saver = new FileSaver(context);
            saver.saveCareProvider(provider);
        }
    }



    public ArrayList<Problem> SearchPatient(Patient patient, String keywords, MapLocation map, BodyLocation bod){
        return null;
    }

    public ArrayList<Problem> SearchProvider(CareProvider provider, String keyword, MapLocation map, BodyLocation body){
        return null;
    }


    /**
     * <p>Check if we can connect to the Elastisearch server</p>
     * @return True if a connection can be established, false otherwise
     */
    public boolean checkConnectivity() {
        try {
            URL url = new URL(databaseAddress);
            URLConnection connection = url.openConnection();
            connection.setConnectTimeout(timeout);
            connection.connect();
            return true;
        } catch (IOException e) {
            Log.d("Database", "IOException thrown in Database.checkConnectivity()");
            e.printStackTrace();
            return false;
        }
    }
}
