package com.example.ayhan.myapplication;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;

import com.example.ayhan.myapplication.pos.PosApi;

import static android.app.PendingIntent.getActivity;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        getContext();
    }

    @SmallTest
    public void testLogin() {
        PosApi p = new PosApi();
        p.login("osbwin", "Alphatwo2");
        System.out.println("fsewewfwfe");
    }
}