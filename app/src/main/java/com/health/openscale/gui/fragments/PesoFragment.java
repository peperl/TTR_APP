/* Copyright (C) 2014  olie.xdev <olie.xdev@googlemail.com>
*
*    This program is free software: you can redistribute it and/or modify
*    it under the terms of the GNU General Public License as published by
*    the Free Software Foundation, either version 3 of the License, or
*    (at your option) any later version.
*
*    This program is distributed in the hope that it will be useful,
*    but WITHOUT ANY WARRANTY; without even the implied warranty of
*    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*    GNU General Public License for more details.
*
*    You should have received a copy of the GNU General Public License
*    along with this program.  If not, see <http://www.gnu.org/licenses/>
*/
package com.health.openscale.gui.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.health.openscale.R;
import com.health.openscale.core.OpenScale;
import com.health.openscale.core.datatypes.ScaleMeasurement;
import com.health.openscale.gui.activities.DataEntryActivity;
import com.health.openscale.gui.views.MeasurementView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.util.TypedValue.COMPLEX_UNIT_DIP;

public class PesoFragment extends Fragment implements FragmentUpdateListener {

    private Context context;

    public PesoFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_registra_peso, container, false);
        context = v.getContext();

        Button button = (Button) v.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditText pesoText = v.findViewById(R.id.pesoText);
                ModelServices modelServices = new ModelServices(getActivity().getApplicationContext());
                SharedPreferences mPreferences;
                String sharedPrefFile = "com.example.josueduran.yumk1.constants";
                mPreferences = context.getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
                JSONObject result = null;
                try {
                    result = new JSONObject(mPreferences.getString("paciente",null));
                    modelServices.registraPeso(result.getInt("idpaciente") + "",pesoText.getText().toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //openScale.invokeConnectToBluetoothDevice();
            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        OpenScale.getInstance().unregisterFragment(this);
        super.onDestroyView();
    }


    private int pxImageDp(float dp) {
        return (int)(dp * getResources().getDisplayMetrics().density + 0.5f);
    }

    @Override
    public void updateOnView(List<ScaleMeasurement> scaleMeasurementList) {

    }

    private class MeasurementsAdapter extends RecyclerView.Adapter<MeasurementsAdapter.ViewHolder> {
        public static final int VIEW_TYPE_MEASUREMENT = 0;
        public static final int VIEW_TYPE_YEAR = 1;

        public class ViewHolder extends RecyclerView.ViewHolder {
            public LinearLayout measurementView;
            public ViewHolder(LinearLayout view) {
                super(view);
                measurementView = view;
            }
        }

        private List<MeasurementView> visibleMeasurements;
        private List<ScaleMeasurement> scaleMeasurements;

        public void setMeasurements(List<MeasurementView> visibleMeasurements,
                                    List<ScaleMeasurement> scaleMeasurements) {
            this.visibleMeasurements = visibleMeasurements;
            this.scaleMeasurements = new ArrayList<>(scaleMeasurements.size() + 10);

            Calendar calendar = Calendar.getInstance();
            if (!scaleMeasurements.isEmpty()) {
                calendar.setTime(scaleMeasurements.get(0).getDateTime());
            }
            calendar.set(calendar.get(Calendar.YEAR), 0, 1, 0, 0, 0);
            calendar.set(calendar.MILLISECOND, 0);

            // Copy all measurements from input parameter to member variable and insert
            // an extra "null" entry when the year changes.
            Date yearStart = calendar.getTime();
            for (int i = 0; i < scaleMeasurements.size(); ++i) {
                final ScaleMeasurement measurement = scaleMeasurements.get(i);

                if (measurement.getDateTime().before(yearStart)) {
                    this.scaleMeasurements.add(null);

                    Calendar newCalendar = Calendar.getInstance();
                    newCalendar.setTime(measurement.getDateTime());
                    calendar.set(Calendar.YEAR, newCalendar.get(Calendar.YEAR));
                    yearStart = calendar.getTime();
                }

                this.scaleMeasurements.add(measurement);
            }

            notifyDataSetChanged();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LinearLayout row = new LinearLayout(getContext());
            row.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));

            final int screenSize = getResources().getConfiguration()
                    .screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;
            final boolean isSmallScreen =
                    screenSize != Configuration.SCREENLAYOUT_SIZE_XLARGE
                            && screenSize != Configuration.SCREENLAYOUT_SIZE_LARGE;

            final int count = viewType == VIEW_TYPE_YEAR ? 1 : visibleMeasurements.size();
            for (int i = 0; i < count; ++i) {
                TextView column = new TextView(getContext());
                column.setLayoutParams(new LinearLayout.LayoutParams(
                        0, ViewGroup.LayoutParams.WRAP_CONTENT, 1));

                if (viewType == VIEW_TYPE_MEASUREMENT) {
                    column.setMinLines(2);
                    column.setGravity(Gravity.CENTER_HORIZONTAL);

                    if (isSmallScreen) {
                        column.setTextSize(COMPLEX_UNIT_DIP, 9);
                    }
                }
                else {
                    column.setPadding(0, 10, 0, 10);
                    column.setGravity(Gravity.CENTER);
                    column.setTextSize(COMPLEX_UNIT_DIP, 16);
                }

                row.addView(column);
            }

            return new ViewHolder(row);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            LinearLayout row = holder.measurementView;

            final ScaleMeasurement measurement = scaleMeasurements.get(position);
            if (measurement == null) {
                ScaleMeasurement nextMeasurement = scaleMeasurements.get(position + 1);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(nextMeasurement.getDateTime());

                TextView column = (TextView) row.getChildAt(0);
                column.setText(String.format("%d", calendar.get(Calendar.YEAR)));
                return;
            }

            ScaleMeasurement prevMeasurement = null;
            if (position + 1 < scaleMeasurements.size()) {
                prevMeasurement = scaleMeasurements.get(position + 1);
                if (prevMeasurement == null) {
                    prevMeasurement = scaleMeasurements.get(position + 2);
                }
            }

            // Fill view with data
            for (int i = 0; i < visibleMeasurements.size(); ++i) {
                final MeasurementView view = visibleMeasurements.get(i);
                view.loadFrom(measurement, prevMeasurement);

                SpannableStringBuilder string = new SpannableStringBuilder();
                string.append(view.getValueAsString(false));
                view.appendDiffValue(string, true);

                TextView column = (TextView) row.getChildAt(i);
                column.setText(string);
            }

            row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), DataEntryActivity.class);
                    intent.putExtra(DataEntryActivity.EXTRA_ID, measurement.getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return scaleMeasurements == null ? 0 : scaleMeasurements.size();
        }

        @Override
        public int getItemViewType(int position) {
            return scaleMeasurements.get(position) != null ? VIEW_TYPE_MEASUREMENT : VIEW_TYPE_YEAR;
        }
    }
}
