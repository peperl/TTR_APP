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

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.health.openscale.R;
import com.health.openscale.core.OpenScale;
import com.health.openscale.core.datatypes.ScaleMeasurement;
import com.health.openscale.core.datatypes.ScaleUser;
import com.health.openscale.core.utils.Converters;
import com.health.openscale.core.utils.PolynomialFitter;
import com.health.openscale.gui.activities.DataEntryActivity;
import com.health.openscale.gui.views.BMRMeasurementView;
import com.health.openscale.gui.views.FloatMeasurementView;
import com.health.openscale.gui.views.MeasurementView;
import com.health.openscale.gui.views.MeasurementViewSettings;
import com.health.openscale.gui.views.WeightMeasurementView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Stack;

import lecho.lib.hellocharts.formatter.SimpleLineChartValueFormatter;
import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SelectedValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.Viewport;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;


public class GraphFragment extends Fragment implements FragmentUpdateListener {

    LineChart grafica;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_avances, container, false);
        grafica = (LineChart) view.findViewById(R.id.linechartA);
        grafica.getAxisLeft().setEnabled(true);
        grafica.getAxisRight().setEnabled(false);
        grafica.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        grafica.getXAxis().setDrawAxisLine(false);
        grafica.getXAxis().setDrawGridLines(false);
        grafica.getXAxis().setTextSize(15);
        grafica.getAxisLeft().setTextSize(15);
        Description des = new Description();
        des.setText("Datos en kg");
        grafica.setDescription(des);
        Legend legend = grafica.getLegend();
        legend.setTextSize(20);
        legend.setForm(Legend.LegendForm.LINE);
        legend.setXEntrySpace(20f);



        List<Entry> peso = new ArrayList<Entry>();
        peso.add(new Entry(0,80));
        peso.add(new Entry(1,75));
        peso.add(new Entry(2,70));
        peso.add(new Entry(3,75));
        LineDataSet dataS1 = new LineDataSet(peso,"Peso");
        dataS1.setLineWidth(5f);
        dataS1.setCircleRadius(7f);
        dataS1.setColor(Color.parseColor("#1BB8D1"));
        dataS1.setCircleColor(Color.parseColor("#199096"));
        dataS1.setValueTextSize(17);
        dataS1.setValueTextColor(Color.parseColor("#199096"));

        List<Entry> grasa = new ArrayList<Entry>();
        grasa.add(new Entry(0,20));
        grasa.add(new Entry(1,18));
        grasa.add(new Entry(2,17));
        grasa.add(new Entry(3,19));
        LineDataSet dataS2 = new LineDataSet(grasa,"Grasa");
        dataS2.setLineWidth(5f);
        dataS2.setCircleRadius(7f);
        dataS2.setColor(Color.parseColor("#E72264"));
        dataS2.setCircleColor(Color.parseColor("#A91B52"));
        dataS2.setValueTextSize(17);
        dataS2.setValueTextColor(Color.parseColor("#A91B52"));

        List<Entry> masaM = new ArrayList<Entry>();
        masaM.add(new Entry(0,60));
        masaM.add(new Entry(1,61));
        masaM.add(new Entry(2,62));
        masaM.add(new Entry(3,61));
        LineDataSet dataS3 = new LineDataSet(masaM,"Masa Muscular");
        dataS3.setLineWidth(5f);
        dataS3.setCircleRadius(7f);
        dataS3.setColor(Color.parseColor("#833D90"));
        dataS3.setCircleColor(Color.parseColor("#552B60"));
        dataS3.setValueTextSize(17);
        dataS3.setValueTextColor(Color.parseColor("#552B60"));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(dataS1);
        dataSets.add(dataS2);
        dataSets.add(dataS3);
        LineData lineData = new LineData(dataSets);
        grafica.animateX(500);
        grafica.setData(lineData);
        grafica.invalidate();
        return view;
    }

    @Override
    public void updateOnView(List<ScaleMeasurement> scaleMeasurementList) {

    }
}