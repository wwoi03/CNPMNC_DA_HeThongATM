package com.example.app_cnpmnc_da_hethongatm.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.app_cnpmnc_da_hethongatm.Adapter.UserAdapter;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ApoimentActivity extends AppCompatActivity {
    private EditText edtId, edtName,tvdvdangky,tvSourcett, etDateLimit;
    private Button btnAddUser;
    private RecyclerView rcvUsers;
    private UserAdapter mUserAdapter;

    private List<UserActivity> mListUsers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apoiment);

        initUi();

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
          public void onClick(View view) {

               String name= edtName.getText().toString().trim();
               String loaiDichVu = tvdvdangky.getText().toString().trim();
               String chiNhanhKey = tvSourcett.getText().toString().trim();
               String ngayDenHen= etDateLimit.getText().toString().trim();

                UserActivity user = new UserActivity(name,loaiDichVu, chiNhanhKey,ngayDenHen);
                onClickPushData(user);

            }
        });
        getListUsersFormRealtimeDatabase();
    }

    private void initUi() {
        edtId = findViewById(R.id.edt_id);
        edtName = findViewById(R.id.edt_name);
        btnAddUser = findViewById(R.id.btn_add_user);
        tvdvdangky = findViewById(R.id.tvdvdangky);
        tvSourcett = findViewById(R.id.tvSourcett);
        etDateLimit = findViewById(R.id.etDateLimit);
        rcvUsers = findViewById(R.id.rcv_users);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvUsers.setLayoutManager(linearLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvUsers.addItemDecoration(dividerItemDecoration);

        mListUsers = new ArrayList<>();
        mUserAdapter = new UserAdapter(mListUsers);

        rcvUsers.setAdapter(mUserAdapter);
    }


    private void onClickPushData(UserActivity user) {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_users");

        String pathObject = String.valueOf(user.getSoDienThoai());
        myRef.child(pathObject).setValue(user, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ApoimentActivity.this, "",Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void clickAddAllUser(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_users");

        List<UserActivity> list=new ArrayList<>();
        list.add(new UserActivity("User 1","090969693","chiNhanh", "_ _ /_ _ /_ _ _ _"));
        list.add(new UserActivity("User 2","025442575","chiNhanh", "_ _ /_ _ /_ _ _ _"));
        list.add(new UserActivity("User 3","558885654","chiNhanh", "_ _ /_ _ /_ _ _ _"));

        myRef.setValue(list, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                Toast.makeText(ApoimentActivity.this, "",Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getListUsersFormRealtimeDatabase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("list_users");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    UserActivity user = dataSnapshot.getValue(UserActivity.class);
                    mListUsers.add(user);
                }
                mUserAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ApoimentActivity.this, "Bạn chưa đặt lịch hẹn", Toast.LENGTH_SHORT).show();

            }
        });
    }
}