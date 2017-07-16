package fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.haileyhultquist.qiosk.JobAddedActivity;
import com.example.haileyhultquist.qiosk.R;
import com.example.haileyhultquist.qiosk.ServerRestClientUsage;

import org.json.JSONException;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CreateFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {

    private Button addButton;
    private ServerRestClientUsage serverRestClientUsage;

    private String key;
    private String userType;

    private EditText title;
    private EditText description;
    private EditText pay;


    private OnFragmentInteractionListener mListener;

    public CreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFragment newInstance(String param1, String param2) {
        CreateFragment fragment = new CreateFragment();
        Bundle args = new Bundle();
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

        View view = inflater.inflate(R.layout.fragment_create, container, false);

        serverRestClientUsage = new ServerRestClientUsage();

        if (getArguments() != null) {
            key = getArguments().getString("key");
            userType = getArguments().getString("userType");
        }

        addButton = (Button) view.findViewById(R.id.submit);

        title = (EditText) view.findViewById(R.id.JobTitle);
        description = (EditText) view.findViewById(R.id.JobAddress);
        pay = (EditText) view.findViewById(R.id.JobPay);

        addButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                String t = title.getText().toString();
                String d = description.getText().toString();
                String p = pay.getText().toString();

                serverRestClientUsage.postJob(getActivity(), key, t, d, p, new ServerRestClientUsage.Callback<String>() {
                    @Override
                    public void onResponse(String s) throws JSONException {
                        Intent detailIntent = new Intent(getActivity(), JobAddedActivity.class);
                        detailIntent.putExtra("key", key);
                        startActivity(detailIntent);
                    }
                });

            }
        });
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
