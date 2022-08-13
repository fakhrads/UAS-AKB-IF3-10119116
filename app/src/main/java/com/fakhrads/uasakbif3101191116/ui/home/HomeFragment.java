package com.fakhrads.uasakbif3101191116.ui.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.fakhrads.uasakbif3101191116.DatabaseAcces;
import com.fakhrads.uasakbif3101191116.EditActivity;
import com.fakhrads.uasakbif3101191116.MainActivity;
import com.fakhrads.uasakbif3101191116.Notes;
import com.fakhrads.uasakbif3101191116.R;
import com.fakhrads.uasakbif3101191116.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private DatabaseAcces databaseAcces;
    private List<Notes> memos;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        databaseAcces = DatabaseAcces.getInstance(getActivity());
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        listView = (ListView) root.findViewById(R.id.listView);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        databaseAcces.open();
        this.memos = databaseAcces.getAllMemos();
        databaseAcces.close();
        MemoAdapter adapter = new MemoAdapter(getActivity(),memos);
        listView.setAdapter(adapter);
    }


    private class MemoAdapter extends ArrayAdapter<Notes> {

        MemoAdapter(@NonNull Context context, List<Notes> objects) {
            super(context, 0,  objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            if (convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.layout_list_item, parent, false);
            }

            Button btnEdit = convertView.findViewById(R.id.btnEdit);
            Button btnDelete = convertView.findViewById(R.id.btnDelete);

            TextView textTitle =  convertView.findViewById(R.id.tvTitle);
            TextView txtMemo = convertView.findViewById(R.id.txtMemo);
            TextView txtCat =  convertView.findViewById(R.id.txtCat);
            TextView txtDate =  convertView.findViewById(R.id.txtDate);
            FloatingActionButton fab = getActivity().findViewById(R.id.floatingActionButton);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getActivity(), EditActivity.class));
                }
            });
            final Notes memo = memos.get(position);
            textTitle.setText(memo.getCategory());
            txtCat.setText(memo.getTtitle());
            txtDate.setText(memo.getDate());
            txtMemo.setText(memo.getShortText());

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getContext(), EditActivity.class);
                    intent.putExtra("MEMO", memo);
                    startActivity(intent);
                }
            });

            btnDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    databaseAcces.open();
                    databaseAcces.delete(memo);
                    databaseAcces.close();

                    ArrayAdapter<Notes> adapter = (ArrayAdapter<Notes>) listView.getAdapter();
                    adapter.remove(memo);
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }
    }
}