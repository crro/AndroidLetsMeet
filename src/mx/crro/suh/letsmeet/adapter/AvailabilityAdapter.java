package mx.crro.suh.letsmeet.adapter;

import mx.crro.suh.letsmeet.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AvailabilityAdapter extends BaseAdapter {
	private String[] _times = { "9:00", "10:00", "11:00", "12:00", 
			   "13:00", "14:00", "15:00", "16:00", "17:00", "18:00", 
			   "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"};
	private Context _context;
	
	public AvailabilityAdapter(Context c) {
		_context = c;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return _times.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return _times[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public static class ViewHolder {
		TextView dateTitle;
		public boolean busy;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) _context.getSystemService(_context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.cell_layout, null);
			
			holder = new ViewHolder();
			holder.dateTitle = (TextView) convertView.findViewById(R.id.textCell);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		if ((position > 2) && (position < 6)) {
			convertView.setBackgroundColor(_context.getResources().getColor(android.R.color.holo_red_dark));
			holder.busy = true;
			holder.dateTitle.setText(_times[position] + " Event");
		} else {
			convertView.setBackgroundColor(_context.getResources().getColor(android.R.color.transparent));
			holder.dateTitle.setText(_times[position]);
		}
		return convertView;
	}

}
