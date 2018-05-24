package ru.sergeykamyshov.splashscreen.ui.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import ru.sergeykamyshov.splashscreen.ui.ui.splash.SplashFragment1;
import ru.sergeykamyshov.splashscreen.ui.ui.splash.SplashFragment2;
import ru.sergeykamyshov.splashscreen.ui.ui.splash.SplashFragment3;

public class SplashAdapter extends FragmentPagerAdapter {

    public SplashAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return SplashFragment1.newInstance();
            case 1:
                return SplashFragment2.newInstance();
            case 2:
                return SplashFragment3.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
