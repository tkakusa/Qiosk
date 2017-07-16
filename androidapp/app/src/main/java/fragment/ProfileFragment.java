package fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.haileyhultquist.qiosk.AddJobActivity;
import com.example.haileyhultquist.qiosk.JobListingActivity;
import com.example.haileyhultquist.qiosk.R;
import com.example.haileyhultquist.qiosk.ServerRestClientUsage;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProfileFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // parameters
    private String key;
    private String userType;

    private ServerRestClientUsage serverRestClientUsage;

    private TextView nameTextView;
    private TextView balanceTextView;
    private TextView userTypeView;

    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameter.
     *
     * @param key key to be used.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String key, String userType) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString("key", key);
        args.putString("userType", userType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        nameTextView = (TextView) view.findViewById(R.id.name);
        balanceTextView = (TextView) view.findViewById(R.id.balance);
        userTypeView = (TextView) view.findViewById(R.id.userType);


        if (getArguments() != null) {
            key = getArguments().getString("key");
            userType = getArguments().getString("userType");

            serverRestClientUsage = new ServerRestClientUsage();
            serverRestClientUsage.getUserData(key, new ServerRestClientUsage.Callback<String>() {
                @Override
                public void onResponse(String s) throws JSONException {
                    JSONObject jsonObject = new JSONObject(s);
                    nameTextView.setText(jsonObject.getString("firstName") + " " + jsonObject.getString("lastName"));
                    balanceTextView.setText("Account balance: " + jsonObject.getString("accountBalance"));
                }
            });

            if (userType.equals("employer")) {
                userTypeView.setText("EMPLOYER");
            } else {
                userTypeView.setText("WORKER");
            }
        }
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
        */
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
