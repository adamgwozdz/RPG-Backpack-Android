package com.wodu.mobile.rpg_backpack;

import static androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import com.wodu.mobile.rpg_backpack.viewmodels.LoginActivityViewModel;
import com.wodu.mobile.rpg_backpack.views.LoginActivity;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private LoginActivityViewModel viewModel = new LoginActivityViewModel();
    private String email = "ap1@qa.qa";
    private String password = "Qwerty1!";

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.wodu.mobile.rpg_backpack", appContext.getPackageName());
    }
}