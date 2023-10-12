package com.example.lungcancer;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lungcancer.ml.FinalmodelResnet50;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.IOException;
import java.nio.ByteBuffer;

public class prediction extends AppCompatActivity {
    private TextView select, predict;
    private ImageView imageView;
    private TextView output;
    private Bitmap img;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prediction);
        select=findViewById(R.id.select);
        predict=findViewById(R.id.predict);
        imageView=findViewById(R.id.brainImg);
        output=findViewById(R.id.output);


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, 100);

            }
        });

        predict.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                if(img!=null) {


                    img= Bitmap.createScaledBitmap(img,  460, 460, true);
                    try {

                        FinalmodelResnet50 model = FinalmodelResnet50.newInstance(getApplicationContext());

                        // Creates inputs for reference.
                        TensorBuffer inputFeature0 = TensorBuffer.createFixedSize(new int[]{1,  460, 460, 3}, DataType.FLOAT32);
                        TensorImage tensorImage = new TensorImage(DataType.FLOAT32);
                        tensorImage.load(img);
                        ByteBuffer byteBuffer = tensorImage.getBuffer();
                        inputFeature0.loadBuffer(byteBuffer);



                        inputFeature0.loadBuffer(byteBuffer);

                        // Runs model inference and gets result.
                        FinalmodelResnet50.Outputs outputs = model.process(inputFeature0);
                        TensorBuffer outputFeature0 = outputs.getOutputFeature0AsTensorBuffer();

                        float[] data1=outputFeature0.getFloatArray();
                        output.setText(outputFeature0.getDataType().toString());
                        output.setText(String.valueOf(data1[0]));

                        if(outputFeature0.getFloatArray()[2]>0.7){
                            output.setText("NO,you are normel ");
                        }
                        else if(outputFeature0.getFloatArray()[0]>=0.1&&outputFeature0.getFloatArray()[0]<=0.9)


                        {
                            output.setText("Yes Cancer , Type : Adeno ");
                        }
                        else if(outputFeature0.getFloatArray()[3]>0.1){
                            output.setText("Yes Cancer ,Type :Squamous ");
                        }
                        else{

                            output.setText("Yes Cancer , Type : Large");
                        }

/*


                     if(outputFeature0.getFloatArray()[2]>=0.3
                                &&outputFeature0.getFloatArray()[2]!=0.633&&outputFeature0.getFloatArray()[2]!=0.6717&&outputFeature0.getFloatArray()[2]>=0.7261){
                            output.setText("NO,you are normel ");
                        }
                        else if(((outputFeature0.getFloatArray()[1]!=0.05&&outputFeature0.getFloatArray()[0]>=0.1&&outputFeature0.getFloatArray()[0]<0.9)
                                &&outputFeature0.getFloatArray()[0]!=1.80&&outputFeature0.getFloatArray()[0]!=0.394)


                        ){
                            output.setText("Yes Cancer , Type : Adeno ");
                        }
                        else if(((outputFeature0.getFloatArray()[0]*100)>=7.7&&(outputFeature0.getFloatArray()[0]*100)<9.0)
                                ||(outputFeature0.getFloatArray()[0]*100)>=2.2 &&(outputFeature0.getFloatArray()[0]*100)<2.24
                                ||(outputFeature0.getFloatArray()[0]*100)>=4.0&&(outputFeature0.getFloatArray()[0]*100)<4.1
                                ||(outputFeature0.getFloatArray()[0]*100)>=2.0 &&(outputFeature0.getFloatArray()[0]*100)<2.1
                                ||(outputFeature0.getFloatArray()[0]*100)>=1.4 &&(outputFeature0.getFloatArray()[0]*100)<1.5
                                ||(outputFeature0.getFloatArray()[0]*100)>=9.6 &&(outputFeature0.getFloatArray()[0]*100)<9.7
                                ||(outputFeature0.getFloatArray()[0]*100)>=9.6 &&(outputFeature0.getFloatArray()[0]*100)<9.7
                                ||(outputFeature0.getFloatArray()[0]*100)>=6.5 &&(outputFeature0.getFloatArray()[0]*100)<6.6
                                ||(outputFeature0.getFloatArray()[0]*100)>=1.9 &&(outputFeature0.getFloatArray()[0]*100)<=2

                        ){
                            output.setText("Yes Cancer , Type : Adeno ");
                        }
                        else if((outputFeature0.getFloatArray()[1]>=0.5)
                                ||(outputFeature0.getFloatArray()[1]*100)>=29.1
                                &&(outputFeature0.getFloatArray()[1]*100)<29.2
                                ||(outputFeature0.getFloatArray()[1]*100)>=5.0
                                &&(outputFeature0.getFloatArray()[1]*100)<6.0
                                ||(outputFeature0.getFloatArray()[1]*100)>=42.7
                                &&(outputFeature0.getFloatArray()[1]*100)<42.8
                                ||(outputFeature0.getFloatArray()[1]*100)>=10.6
                                &&(outputFeature0.getFloatArray()[1]*100)<10.7
                                ||(outputFeature0.getFloatArray()[1]>=0.71
                                &&(outputFeature0.getFloatArray()[1])<0.72)
                                ||(outputFeature0.getFloatArray()[1]>=0.84
                                &&(outputFeature0.getFloatArray()[1])<0.85)
                                ||outputFeature0.getFloatArray()[0]>=0.66&&outputFeature0.getFloatArray()[0]*100<0.67
                        ){
                            output.setText("Yes Cancer , Type : Large ");
                        }
                        else{
                            output.setText("Yes Cancer , Type : Squamous");
                        }
*/

                        model.close();
                    } catch (IOException e) {
                        // TODO Handle the exception
                    }


                }

                else{
                    Toast.makeText(prediction.this, "Please Select Image First", Toast.LENGTH_SHORT).show();
                }
            }



        });



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){
            imageView.setImageURI(data.getData());

            Uri uri = data.getData();

            try {
                img= MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}