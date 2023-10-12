package com.example.lungcancer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

public class Services extends AppCompatActivity {
    GridView gridview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);
        gridview=(GridView)findViewById(R.id.gridView);
        String[] Name = {"Quick Check","Chat","Scheduling","Nutrition",
                "Side Effects","Contact Us"};
        int[] Images = {R.drawable.one,R.drawable.two,
                R.drawable.three,R.drawable.four,R.drawable.five,R.drawable.six};

        gridadapter gridAdapter = new gridadapter(Services.this,Name,Images);
        gridview.setAdapter(gridAdapter);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Services.this,"You Clicked on "+ Name[position],Toast.LENGTH_SHORT).show();
                switch (position){
                    case 0:
                        Intent intent1=new Intent(Services.this, prediction.class);
                        startActivity(intent1);

                        break;
                    case 1:

                        Intent intent=new Intent(Services.this,chat.class);
                        startActivity(intent);

                        break;
                    case 2:
                        Intent intent3=new Intent(Services.this, Scheduling.class);
                        startActivity(intent3);
                        break;
                    case 3:
                        Intent intent4=new Intent(Services.this,nutration.class);
                        startActivity(intent4);
                        break;
                    case 4:
                        Intent intent5=new Intent(Services.this,sideeffects.class);
                        startActivity(intent5);
                        break;
                    default:
                        Intent intent8=new Intent(Services.this, contactus.class);
                        startActivity(intent8);

                }



            }
        });

    }
    }
