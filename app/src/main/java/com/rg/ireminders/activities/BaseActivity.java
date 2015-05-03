package com.rg.ireminders.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import com.rg.ireminders.R;

public abstract class BaseActivity extends ActionBarActivity {
  protected Toolbar toolbar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(getLayoutResource());
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    if (toolbar != null) {
      setSupportActionBar(toolbar);
    }
  }

  protected abstract int getLayoutResource();

  protected void setActionBarIcon(int iconRes) {
    toolbar.setNavigationIcon(iconRes);
  }
}
