package com.example.app_cnpmnc_da_hethongatm.Activities;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_cnpmnc_da_hethongatm.Model.TheNganHang;
import com.example.app_cnpmnc_da_hethongatm.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class LockCardActivity extends AppCompatActivity  {


    Spinner spLoaiThe,spSoThe;
    Button btKhoaThe;
    List<TheNganHang> cards;
    String [] cardids;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_card);

        spLoaiThe=findViewById(R.id.spLoaiThe);
        spSoThe=findViewById(R.id.spSoThe);
        btKhoaThe=findViewById(R.id.btKhoaThe);
        firebaseDatabase = FirebaseDatabase.getInstance("https://systematm-aea2c-default-rtdb.asia-southeast1.firebasedatabase.app/");
        databaseReference=firebaseDatabase.getReference();


        cards=new List<TheNganHang>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(@Nullable Object o) {
                return false;
            }

            @NonNull
            @Override
            public Iterator<TheNganHang> iterator() {
                return null;
            }

            @NonNull
            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @NonNull
            @Override
            public <T> T[] toArray(@NonNull T[] ts) {
                return null;
            }

            @Override
            public boolean add(TheNganHang card) {
                return false;
            }

            @Override
            public boolean remove(@Nullable Object o) {
                return false;
            }

            @Override
            public boolean containsAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean addAll(@NonNull Collection<? extends TheNganHang> collection) {
                return false;
            }

            @Override
            public boolean addAll(int i, @NonNull Collection<? extends TheNganHang> collection) {
                return false;
            }

            @Override
            public boolean removeAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public boolean retainAll(@NonNull Collection<?> collection) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public TheNganHang get(int i) {
                return null;
            }

            @Override
            public TheNganHang set(int i, TheNganHang card) {
                return null;
            }

            @Override
            public void add(int i, TheNganHang card) {

            }

            @Override
            public TheNganHang remove(int i) {
                return null;
            }

            @Override
            public int indexOf(@Nullable Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(@Nullable Object o) {
                return 0;
            }

            @NonNull
            @Override
            public ListIterator<TheNganHang> listIterator() {
                return null;
            }

            @NonNull
            @Override
            public ListIterator<TheNganHang> listIterator(int i) {
                return null;
            }

            @NonNull
            @Override
            public List<TheNganHang> subList(int i, int i1) {
                return null;
            }
        };


        ReadData();
        inits();

        btKhoaThe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //startActivity(new Intent(LockCardActivity.this,ConfirmLockCardActiviti.class));
                ReadData();
            }
        });

    }


    public void ReadData(){

        databaseReference.child("TheNganHang").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cards.clear();
                for (DataSnapshot snap : snapshot.getChildren()){
                    TheNganHang card = snap.getValue(TheNganHang.class);
                    cards.add(card);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(LockCardActivity.this,"Failed ui",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void inits(){

        //Loai theeeeeeeeeeeee
        String[]item={"Tín dụng", "Ghi nợ"};
        ArrayAdapter<String>loaithe=new ArrayAdapter<String>(LockCardActivity.this, android.R.layout.simple_spinner_item,item);
        loaithe.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLoaiThe.setAdapter(loaithe);
        spLoaiThe.setPrompt("Loại thẻ");

        spLoaiThe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        //So theeeeee :{}
        cardids=new String[cards.size()];
        //So theeeeee :{}
      for(int i=0;i<cards.size();i++){
            //cardids[i]=""+cards.size();
            cardids[i].valueOf(cards.get(i).getMaSoThe());
       }
        ArrayAdapter<String>adapter=new ArrayAdapter<String>(LockCardActivity.this, android.R.layout.simple_spinner_item,cardids);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSoThe.setAdapter(adapter);
        spSoThe.setPrompt("Mã số thẻ");

        spSoThe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ReadData();
                String value=adapterView.getItemAtPosition(i).toString();

                 
                Toast.makeText(LockCardActivity.this,"Mã số thẻ"+value,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


}