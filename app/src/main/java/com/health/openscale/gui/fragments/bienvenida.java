package com.health.openscale.gui.fragments;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.OnLifecycleEvent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.health.openscale.R;
import com.health.openscale.constants.Constantes;

import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link bienvenida.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link bienvenida#newInstance} factory method to
 * create an instance of this fragment.
 */
public class bienvenida extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_bienvenida, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        String result = Utils.getSharePreference(this.getActivity(), Constantes.paciente);
        JSONObject paciente = null;
        try {
            paciente = new JSONObject(result);
            Utils.showLog("llegó a bienvenida");
            Utils.showLog(paciente.toString());

            TextView name = (TextView) this.getActivity().findViewById(R.id.textView2);
            name.setText(paciente.getString("nombre"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
