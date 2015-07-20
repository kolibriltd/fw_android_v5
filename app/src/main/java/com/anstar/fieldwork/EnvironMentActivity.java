package com.anstar.fieldwork;

import android.app.ActionBar;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.anstar.common.Const;
import com.anstar.model.helper.ServiceResponse;
import com.anstar.models.AppointmentInfo;
import com.anstar.models.ModelDelegates.UpdateInfoDelegate;
import com.anstar.models.list.AppointmentModelList;

public class EnvironMentActivity extends BaseActivity {

	int a_id;
	private EditText edtSquareFeet, edtWindSpeed, edtTemperature;
	private Spinner spnWindDirection;
	private Button btnSave;
	ActionBar action = null;
	AppointmentInfo appointmentInfo = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.environment);
		getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		// info = MainActivity.appointmentInfo;
		action = getActionBar();
		action.setTitle(Html.fromHtml("<font color='"
				+ getString(R.string.header_text_color)
				+ "'>Weather conditions</font>"));
		// action.setTitle("Appointment Info");
		action.setHomeButtonEnabled(true);
		action.setDisplayHomeAsUpEnabled(true);

		btnSave = (Button) findViewById(R.id.btnSave);
		spnWindDirection = (Spinner) findViewById(R.id.spnWindDirection);
		edtSquareFeet = (EditText) findViewById(R.id.edtSquareFeet);
		edtWindSpeed = (EditText) findViewById(R.id.edtWindSpeed);
		edtTemperature = (EditText) findViewById(R.id.edtTemperature);

		Bundle b = getIntent().getExtras();
		if (b != null) {
			a_id = b.getInt(Const.Appointment_Id);
		}

		appointmentInfo = AppointmentModelList.Instance().getAppointmentById(
				a_id);
		if (appointmentInfo != null) {
			if (appointmentInfo.square_feet != 0
					&& String.valueOf(appointmentInfo.square_feet) != null) {
				edtSquareFeet.setText(appointmentInfo.square_feet + "");
			}
			if (appointmentInfo.wind_speed != null
					&& !appointmentInfo.wind_speed.equalsIgnoreCase("null")) {
				edtWindSpeed.setText(appointmentInfo.wind_speed);
			}
			if (appointmentInfo.temperature != null
					&& !appointmentInfo.temperature.equalsIgnoreCase("null")) {
				edtTemperature.setText(appointmentInfo.temperature);
			}
			if (appointmentInfo.wind_direction != null
					&& !appointmentInfo.wind_direction.equalsIgnoreCase("null")) {
				int id = 0;
				String[] arr = getResources().getStringArray(
						R.array.environment_winddirection_array);
				for (int i = 0; i < arr.length; i++) {
					if (arr[i].equalsIgnoreCase(appointmentInfo.wind_direction)) {
						id = i;
					}
				}
				spnWindDirection.setSelection(id);
			}
		}

		btnSave.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				int squft = 0;
				if (edtSquareFeet.getText().toString().trim().length() > 0) {
					squft = Integer
							.parseInt(edtSquareFeet.getText().toString());
				}
				String wind_dir = spnWindDirection.getSelectedItem().toString();
				String wind_speed = edtWindSpeed.getText().toString();
				String temp = edtTemperature.getText().toString();

				if (appointmentInfo != null) {
					showProgress();
					AppointmentInfo.SaveEnvironMent(appointmentInfo.getID(),
							squft, wind_dir, wind_speed, temp,
							new UpdateInfoDelegate() {

								@Override
								public void UpdateSuccessFully(
										ServiceResponse res) {
									hideProgress();
									if (!res.isError()) {
										Toast.makeText(
												getApplicationContext(),
												"Environment data save successfully.",
												Toast.LENGTH_LONG).show();
										finish();
									} else {
										Toast.makeText(getApplicationContext(),
												res.getErrorMessage(),
												Toast.LENGTH_LONG).show();
									}
								}

								@Override
								public void UpdateFail(String ErrorMessage) {
									hideProgress();
									Toast.makeText(getApplicationContext(),
											ErrorMessage, Toast.LENGTH_LONG)
											.show();
								}
							});
				}
			}

		});

	}
}