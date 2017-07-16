package fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.haileyhultquist.qiosk.Job;
import com.example.haileyhultquist.qiosk.JobAdapter;
import com.example.haileyhultquist.qiosk.JobViewActivity;
import com.example.haileyhultquist.qiosk.R;
import com.example.haileyhultquist.qiosk.ServerRestClientUsage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import activity.NavigationActivity;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link JobListFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link JobListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class JobListFragment extends Fragment {

    // parameters
    private String key;
    private String userType;

    private ArrayList<Job> jobsArrayList;

    private ServerRestClientUsage serverRestClientUsage;

    private ListView jobListingsView;

    private OnFragmentInteractionListener mListener;

    public JobListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment JobListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JobListFragment newInstance(String param1, String param2) {
        JobListFragment fragment = new JobListFragment();
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

        View view = inflater.inflate(R.layout.fragment_job_list, container, false);

        jobListingsView = (ListView) view.findViewById(R.id.job_listings);

        serverRestClientUsage = new ServerRestClientUsage();

        if (getArguments() != null) {
            key = getArguments().getString("key");
            userType = getArguments().getString("userType");

            serverRestClientUsage.getJobs(key, new ServerRestClientUsage.Callback<String>() {
                @Override
                public void onResponse(String s) throws JSONException {
                    jobsArrayList = new ArrayList<Job>();
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = ((JSONObject)(jsonArray.get(i)));
                        jobsArrayList.add(new Job(j.getString("title"),
                                j.getString("description"),
                                j.getString("payment"),
                                j.getString("pk")));
                    }
                    JobAdapter adapter = new JobAdapter(getActivity(), jobsArrayList);
                    jobListingsView.setAdapter(adapter);
                }
            });

            jobListingsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                    if (userType.equals("employer")) {
                        Toast.makeText(getActivity(), "ONLY EMPLOYEES CAN VIEW JOBS", Toast.LENGTH_LONG).show();
                    }
                    else {
                        Job selectedJob = jobsArrayList.get(position);

                        Intent detailIntent = new Intent(getActivity(), JobViewActivity.class);

                        detailIntent.putExtra("title", selectedJob.getTitle());
                        detailIntent.putExtra("userType", userType);
                        detailIntent.putExtra("status", "Wposted");
                        detailIntent.putExtra("previous", "job_board");
                        detailIntent.putExtra("key", key);
                        detailIntent.putExtra("pk", selectedJob.getPK());
                        Log.d("searchforthis", "the pk issss " + selectedJob.getPK());

                        startActivity(detailIntent);
                    }
                }
            });
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
