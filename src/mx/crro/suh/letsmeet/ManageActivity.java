package mx.crro.suh.letsmeet;

import java.util.ArrayList;

import mx.crro.suh.letsmeet.adapter.SyncedScrollListener;
import mx.crro.suh.letsmeet.fragment.AvailabilityFragment;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class ManageActivity extends FragmentActivity{
	private ViewPager pager = null;
	private SampleAdapter _adapter;

	  @Override
	  public void onCreate(Bundle savedInstanceState) {
		  super.onCreate(savedInstanceState);
		  setContentView(R.layout.manage_layout);

		  pager = (ViewPager)findViewById(R.id.pager);
		  //Get the number of pages and pass them.
		  pager.setAdapter(_adapter = new SampleAdapter(getSupportFragmentManager(), 9));
		  pager.setOffscreenPageLimit(6);
	  }
	  
	  public SampleAdapter getAdapter() {
		  return _adapter;
	  }
	  
	  public class SampleAdapter extends FragmentPagerAdapter {
		  private ArrayList<AvailabilityFragment> _frags;
		  
		  public SampleAdapter(FragmentManager fm, int number) {
			  super(fm);
			  // TODO Auto-generated constructor stub
			  _frags = new ArrayList<AvailabilityFragment>();
			  for (int i = 0; i < number; i++) {
				  _frags.add(new AvailabilityFragment(ManageActivity.this, i, _frags));
			  }
			  
		  }

		  @Override
		  public int getCount() {
			  //Will change depending on how many pages we need
			  return(_frags.size());
		  }

		  @Override
		  public float getPageWidth(int position) {
			  return (float) (1.0/3.0);
		  }

		  @Override
		  public Fragment getItem(int pos) {
			  // TODO Auto-generated method stub
			  return _frags.get(pos);
		  }

	  }

}
