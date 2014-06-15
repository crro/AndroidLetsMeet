package mx.crro.suh.letsmeet.fragment;

import java.util.ArrayList;

import mx.crro.suh.letsmeet.ManageActivity;
import mx.crro.suh.letsmeet.R;
import mx.crro.suh.letsmeet.adapter.AvailabilityAdapter;
import mx.crro.suh.letsmeet.adapter.AvailabilityAdapter.ViewHolder;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ValidFragment")
public class AvailabilityFragment extends Fragment {
	
	private ListView _lV;
	private ManageActivity _mA;
	private int _index;
	private ArrayList<AvailabilityFragment> _frags;
	
	@SuppressLint("ValidFragment")
	public AvailabilityFragment(ManageActivity mA, int index, ArrayList<AvailabilityFragment> frags) {
		_mA = mA;
		_index = index;
		_frags = frags; 
	}
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.day_layout, null);
		
		_lV = (ListView) v.findViewById(R.id.list);
		_lV.setAdapter(new AvailabilityAdapter(getActivity()));
		
		TextView tv = (TextView) v.findViewById(R.id.text);
		tv.setText("9/" + (Integer.valueOf(14 + _index)).intValue());
		/*Another attempt that almost works!!!!
		if (_index > 1 && _index < _frags.size()) {
			_lV.setOnScrollListener(new SyncedScrollListener(_frags.get(_index-1).getListView()));
			_frags.get(_index-1).getListView().setOnScrollListener(new SyncedScrollListener(_frags.get(_index-2).getListView()));
		}*/
		/* Attempt to make everything scroll together
		_lV.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}
			
			@Override
		    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
				if (_index > 0) {
					ListView lv = _frags.get(_index-1).getListView();
					lv.setSelectionFromTop(firstVisibleItem, view.getChildAt(0).getTop());
				}
		    }
		});*/
		
		_lV.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view, int position,
	                long id) {
				Toast.makeText(getActivity(), "Clicked", 
						   Toast.LENGTH_LONG).show();
				if (((ViewHolder) parent.getChildAt(position).getTag()).busy) {
					parent.getChildAt(position).setBackgroundColor(getResources().getColor(android.R.color.transparent));
					((ViewHolder) parent.getChildAt(position).getTag()).busy = false;
				} else {
					parent.getChildAt(position).setBackgroundColor(getResources().getColor(android.R.color.holo_orange_dark));
					((ViewHolder) parent.getChildAt(position).getTag()).busy = true;
				}
	        }
		});
		
		return v;
	}
	
	public ListView getListView() {
		return _lV;
	}
	
}
