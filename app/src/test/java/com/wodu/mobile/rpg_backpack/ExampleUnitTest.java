package com.wodu.mobile.rpg_backpack;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;

import static org.junit.Assert.*;

import android.util.Log;
import android.widget.Toast;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;

import com.wodu.mobile.rpg_backpack.viewmodels.LoginActivityViewModel;
import com.wodu.mobile.rpg_backpack.views.LoginActivity;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private LoginActivityViewModel viewModel = new LoginActivityViewModel();
    private String email = "ap1@qa.qa";
    private String password = "Qwerty1!";

    @Rule
    public TestRule rule = new InstantTaskExecutorRule();

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void successLoginResponse() {
        viewModel.login(email, password).observe(mockLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String response) {
                assertEquals(response.length(), 191);
            }
        });
    }

    private static LifecycleOwner mockLifecycleOwner() {
        LifecycleOwner owner = Mockito.mock(LifecycleOwner.class);
        LifecycleRegistry lifecycle = new LifecycleRegistry(owner);
        lifecycle.handleLifecycleEvent(Lifecycle.Event.ON_RESUME);
        Mockito.when(owner.getLifecycle()).thenReturn(lifecycle);
        return owner;
    }

    @Test
    public void errorUnauthorized() {

    }
}