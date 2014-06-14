package mx.crro.suh.letsmeet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

@SuppressLint("ValidFragment")
public class ScheduleActivity extends FragmentActivity implements OnItemSelectedListener {
	private ArrayList<Date> _selected;
	private Button _startBtn;
	private Button _endBtn;
	private boolean _startBool;
	private boolean _endBool;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_schedule);
		//Creating the formatter
		final SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);
		
		_selected = new ArrayList<Date>();
		_startBtn = (Button) findViewById(R.id.startBtn);
		_endBtn = (Button) findViewById(R.id.endBtn);
		Button timeZoneBtn = (Button) findViewById(R.id.timeZoneBtn);
		timeZoneBtn.setText("Time Zone: "+ TimeZone.getDefault().getDisplayName(false, TimeZone.SHORT));
		//Used to determine what button to change
		_startBool = false;
		_endBool = false;
		
		//setting the listeners for the buttons
		_startBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_startBool = true;
				_endBool = false;
				DialogFragment newFragment = new TimePickerFragment();
			    newFragment.show(getSupportFragmentManager(), "timePicker");
			}
		});
		
		_endBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				_startBool = false;
				_endBool = true;
				DialogFragment newFragment = new TimePickerFragment();
			    newFragment.show(getSupportFragmentManager(), "timePicker");
			}
		});
		
		//Setting up the spinner
		Spinner spinner = (Spinner) findViewById(R.id.time_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.time_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setOnItemSelectedListener(this);
		
		//Setting up the fragment
		CaldroidFragment caldroidFragment = new CaldroidFragment();
		Bundle args = new Bundle();
		Calendar cal = Calendar.getInstance();
		args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
		args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
		caldroidFragment.setArguments(args);

		FragmentTransaction t = getSupportFragmentManager().beginTransaction();
		t.replace(R.id.calendar1, caldroidFragment);
		t.commit();
		
		//The listener of the Caldroid
		final CaldroidListener listener = new CaldroidListener() {

			@Override
		    public void onSelectDate(Date date, View view) {
		        Toast.makeText(getApplicationContext(), formatter.format(date),
		                Toast.LENGTH_SHORT).show();
		        //TODO: Stop the user form selecting dates on a wrong month.
		        //TODO: Make it stay even if the user changes month.
		        if (!_selected.contains(date)) {
		        	view.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
		        	_selected.add(date);
		        } else {
		        	view.setBackgroundColor(getResources().getColor(android.R.color.white));
		        	_selected.remove(date);
		        }
		    }

		    @Override
		    public void onChangeMonth(int month, int year) {
		        String text = "month: " + month + " year: " + year;
		        Toast.makeText(getApplicationContext(), text,
		                Toast.LENGTH_SHORT).show();
		    }

		    @Override
		    public void onLongClickDate(Date date, View view) {
		        Toast.makeText(getApplicationContext(),
		                "Long click " + formatter.format(date),
		                Toast.LENGTH_SHORT).show();
		    }

		    @Override
		    public void onCaldroidViewCreated() {
		        Toast.makeText(getApplicationContext(),
		                "Caldroid view is created",
		                Toast.LENGTH_SHORT).show();
		    }

		};
		caldroidFragment.setCaldroidListener(listener);
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.schedule, menu);
		return true;
	}
	//Dialog class used for the UI pickers needed in this Activity.
	public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// Do something with the time chosen by the user
			if (ScheduleActivity.this._startBool) {
				if (minute > 30) {
					if (hourOfDay == 24) {
						ScheduleActivity.this._startBtn.setText("Start\n " + 00 + ":" + "00");
					} else {
						ScheduleActivity.this._startBtn.setText("Start\n " + hourOfDay + 1+ ":" + "00");
					}
				} else {
					ScheduleActivity.this._startBtn.setText("Start\n " + hourOfDay+ ":" + "00");
				}
			} else {
				if (minute > 30) {
					if (hourOfDay == 24) {
						ScheduleActivity.this._endBtn.setText("Start\n " + "00" + ":" + "00");
					} else {
						ScheduleActivity.this._endBtn.setText("Start\n " + hourOfDay + 1 + ":" + "00");
					}
				} else {
					ScheduleActivity.this._endBtn.setText("End\n " + hourOfDay+ ":" + "00");
				}
				
			}
		}
	}
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
