package edu.cs.und.revenstad.finalproject;

import java.util.HashMap;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabContentFactory;

public class TabsFragmentActivity extends FragmentActivity implements TabHost.OnTabChangeListener {
 
    private TabHost mTabHost;
    private HashMap mapTabInfo = new HashMap();
    private TabInfo mLastTab = null;
    ActionBar ab;
    int place;
 
    private class TabInfo {
         private String tag;
         private Class clss;
         private Bundle args;
         private Fragment fragment;
         TabInfo(String tag, Class clazz, Bundle args) {
             this.tag = tag;
             this.clss = clazz;
             this.args = args;
         }
 
    }
 
    class TabFactory implements TabContentFactory {
 
        private final Context mContext;
 
        public TabFactory(Context context) {
            mContext = context;
        }
 
        public View createTabContent(String tag) {
            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);
            return v;
        }
 
    }

    public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
    	//menu.add(1, 1, 1, "Edit");
		menu.add(1, 2, 2, "Delete");
		return true;
	}
    private boolean handleMenuSelection(MenuItem item) {
		int menuItemId = item.getItemId();
		if (menuItemId == 1) {
			Toast.makeText(getApplicationContext(), "Edit", Toast.LENGTH_SHORT).show();
		}
		else if (menuItemId == 2) {
			//send bundle back to main telling to delete
			AlertDialog.Builder builder;
			AlertDialog alertDialog;
			builder = new AlertDialog.Builder(this);
			builder.setTitle("Are you sure you want to delete this project?");
			
			builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					Intent localIntent = getIntent();
					Bundle dataBundle = localIntent.getExtras();
					dataBundle.putInt("place", place);
					localIntent.putExtras(dataBundle);
					setResult(Activity.RESULT_OK, localIntent);
					stop();
				}
			});
			builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// do nothing
				}
			});
			builder.setCancelable(false);
			alertDialog = builder.create();
			alertDialog.show();
			
		}
		else if (menuItemId == android.R.id.home) {
			Toast.makeText(getApplicationContext(), "Up", Toast.LENGTH_SHORT).show();
			//if clock running ask to quit
			this.finish();
		}
		return false;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return (handleMenuSelection(item) || super.onOptionsItemSelected(item));
	}
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        	// Step 1: Inflate layout
        	setContentView(R.layout.projectmain);
        	// Step 2: Setup TabHost
        	initializeTabHost(savedInstanceState);
        	if (savedInstanceState != null) {
        		mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        	}
        	
        	Intent localIntent = getIntent();
    		Bundle dataBundle = localIntent.getExtras();
    		String name = dataBundle.getString("pName");
    		place = dataBundle.getInt("place");
        	
        	ab = getActionBar();
        	ab.setTitle(name);
        	ab.setDisplayHomeAsUpEnabled(true);
        	
    }
 
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", mTabHost.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }
 
    /**
     * Step 2: Setup TabHost
     */
    private void initializeTabHost(Bundle args) {
        mTabHost = (TabHost)findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo = null;
        TabsFragmentActivity.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab1").setIndicator("Time"), ( tabInfo = new TabInfo("Tab1", leftTab.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        TabsFragmentActivity.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab2").setIndicator("Project"), ( tabInfo = new TabInfo("Tab2", centertab.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        TabsFragmentActivity.addTab(this, this.mTabHost, this.mTabHost.newTabSpec("Tab3").setIndicator("Tasks"), ( tabInfo = new TabInfo("Tab3", righttab.class, args)));
        this.mapTabInfo.put(tabInfo.tag, tabInfo);
        // Default to first tab
        this.onTabChanged("Tab1");
        //
        mTabHost.setOnTabChangedListener(this);
    }
 
    private static void addTab(TabsFragmentActivity activity, TabHost tabHost, TabHost.TabSpec tabSpec, TabInfo tabInfo) {
        // Attach a Tab view factory to the spec
        tabSpec.setContent(activity.new TabFactory(activity));
        String tag = tabSpec.getTag();
 
        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        tabInfo.fragment = activity.getSupportFragmentManager().findFragmentByTag(tag);
        if (tabInfo.fragment != null && !tabInfo.fragment.isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(tabInfo.fragment);
            ft.commit();
            activity.getSupportFragmentManager().executePendingTransactions();
        }
 
        tabHost.addTab(tabSpec);
    }
 
    public void onTabChanged(String tag) {
        TabInfo newTab = (TabInfo) this.mapTabInfo.get(tag);
        if (mLastTab != newTab) {
            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            if (mLastTab != null) {
                if (mLastTab.fragment != null) {
                    ft.detach(mLastTab.fragment);
                }
            }
            if (newTab != null) {
                if (newTab.fragment == null) {
                    newTab.fragment = Fragment.instantiate(this,
                            newTab.clss.getName(), newTab.args);
                    ft.add(R.id.realtabcontent, newTab.fragment, newTab.tag);
                } else {
                    ft.attach(newTab.fragment);
                }
            }
 
            mLastTab = newTab;
            ft.commit();
            this.getSupportFragmentManager().executePendingTransactions();
        }
    }
    
    public void stop(){
    	this.finish();
    }
}
