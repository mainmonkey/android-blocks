package org.javia.blocks;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;
import android.view.Menu;
import android.view.SubMenu;
import android.view.MenuItem;

public class Blocks extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
	return true;	
    }

    public boolean onOptionsItemSelected(MenuItem item) {
	return false;
    }
}
