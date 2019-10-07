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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;

import com.health.openscale.R;
import com.health.openscale.core.datatypes.ScaleMeasurement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.health.openscale.gui.views.MeasurementView.MeasurementViewMode.STATISTIC;

public class RegistraComidaFragment extends Fragment implements FragmentUpdateListener {

    private ExpandableListView listView;
    private ExpandableListAdapter listAdapter;
    private List<String> listDataHeader;
    private HashMap<String,List<String>> listHash;
    private EditText com;
    private Button btn;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_registra_comida, container, false);
        listView = (ExpandableListView)v.findViewById(R.id.lvExp1);
        initData();
        listAdapter = new ExpandableListAdapter(v.getContext(),listDataHeader,listHash);
        listView.setAdapter(listAdapter);
        com = v.findViewById(R.id.editTextComida);
        btn = v.findViewById(R.id.buttonComida);

        Button button5 = v.findViewById(R.id.button5);

        return v;
    }

    private void initData() {
        listDataHeader = new ArrayList<>();
        listHash = new HashMap<>();

        listDataHeader.add("7:00 Desayuno");

        List<String> desayuno = new ArrayList<>();
        desayuno.add("1 Vaso de Leche Entera");
        desayuno.add("1 Pieza de Pan");
        desayuno.add("2 Huevos");

        listHash.put(listDataHeader.get(0),desayuno);
    }

    @Override
    public void updateOnView(List<ScaleMeasurement> scaleMeasurementList) {

    }
}
