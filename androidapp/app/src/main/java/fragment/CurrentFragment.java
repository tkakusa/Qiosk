package fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.haileyhultquist.qiosk.Job;
import com.example.haileyhultquist.qiosk.JobAdapter;
import com.example.haileyhultquist.qiosk.JobViewActivity;
import com.example.haileyhultquist.qiosk.R;
import com.example.haileyhultquist.qiosk.ServerRestClientUsage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CurrentFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link CurrentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CurrentFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String userType;
    private String key;
    private String status;

    private ListView ipJobsView;
    private ListView pendingJobsView;
    private ArrayList<Job> ipJobsArrayList;
    private ArrayList<Job> pendingJobsArrayList;

    private ServerRestClientUsage serverRestClientUsage;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public CurrentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CurrentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CurrentFragment newInstance(String param1, String param2) {
        CurrentFragment fragment = new CurrentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        View view = inflater.inflate(R.layout.fragment_current, container, false);


        ipJobsView = (ListView) view.findViewById(R.id.ip_job_list);
        pendingJobsView = (ListView) view.findViewById(R.id.pending_job_list);

        serverRestClientUsage = new ServerRestClientUsage();

        if (getArguments() != null) {
            key = getArguments().getString("key");
            userType = getArguments().getString("userType");
        }

        if (userType.equals("worker")) {
            status = "W";
        } else {
            status = "E";
            serverRestClientUsage.getJobs(key, new ServerRestClientUsage.Callback<String>() {
                @Override
                public void onResponse(String s) throws JSONException {
                    ipJobsArrayList = new ArrayList<Job>();
                    JSONArray jsonArray = new JSONArray(s);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject j = ((JSONObject)(jsonArray.get(i)));
                        if (!j.getString("status").equals("open")) {
                            continue;
                        }
                        ipJobsArrayList.add(new Job(j.getString("title"),
                                j.getString("description"),
                                j.getString("payment"),
                                j.getString("pk")));
                    }
                    JobAdapter adapter = new JobAdapter(getActivity(), ipJobsArrayList);
                    ipJobsView.setAdapter(adapter);
                }
            });
        }

        /*
        ipJobsArrayList = new ArrayList<Job>();
        ipJobsArrayList.add(new Job("Paint the wall", "My house", 19));
        ipJobsArrayList.add(new Job("Change my car's tire", "Building Q", 15));
        ipJobsArrayList.add(new Job("Mow my lawn", "The Villas", 5));
        JobAdapter adapter = new JobAdapter(this, ipJobsArrayList);
        ipJobsView.setAdapter(adapter);*/

        pendingJobsArrayList = new ArrayList<Job>();
        pendingJobsArrayList.add(new Job("Do my homework", "The library", "10", "10"));
        JobAdapter adapter2 = new JobAdapter(getActivity().getApplicationContext(), pendingJobsArrayList);
        pendingJobsView.setAdapter(adapter2);

        ipJobsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Job selectedJob = ipJobsArrayList.get(position);

                Intent detailIntent = new Intent(getActivity(), JobViewActivity.class);

                detailIntent.putExtra("title", selectedJob.getTitle());
                detailIntent.putExtra("userType", userType);
                status += "inprogress";
                detailIntent.putExtra("status", status);
                detailIntent.putExtra("previous", "user");
                detailIntent.putExtra("key", key);
                detailIntent.putExtra("pk", ipJobsArrayList.get(position).getPK());

                startActivity(detailIntent);
            }
        });

        pendingJobsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, android.view.View view, int position, long id) {
                Job selectedJob = pendingJobsArrayList.get(position);

                Intent detailIntent = new Intent(getActivity(), JobViewActivity.class);

                detailIntent.putExtra("title", selectedJob.getTitle());
                detailIntent.putExtra("userType", userType);
                status += "pending";
                detailIntent.putExtra("status", status);
                detailIntent.putExtra("previous", "user");
                detailIntent.putExtra("key", key);
                detailIntent.putExtra("pk", ipJobsArrayList.get(position).getPK());

                startActivity(detailIntent);
            }
        });
        return inflater.inflate(R.layout.fragment_current, container, false);
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
