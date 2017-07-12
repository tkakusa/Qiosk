package com.example.haileyhultquist.qiosk;

import android.util.Log;

/**
 * Created by haileyhultquist on 7/7/17.
 */

public class Job {
    private String title;
    private String address;
    private String pay;
    private String jobID;

    public Job(String title, String address, String pay, String jobID) {
        this.title = title;
        this.address = address;
        this.pay = pay;
        this.jobID = jobID;

        Log.d("searchforthis", "pk is " + jobID);
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getPay() {
        return pay;
    }

    public String getPK() {
        return jobID;
    }
}
