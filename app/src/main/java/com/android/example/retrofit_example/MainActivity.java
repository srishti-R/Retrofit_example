package com.android.example.retrofit_example;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.Year;
import java.util.Calendar;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
String Api_key="BM2lQ99gjhQqkujoOBBeFdD26LXgtg5y9lXR5dKW";
TextView textView;
TextView textViewex;
ImageView imageView;
String dateQuery;
DatePicker datePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView=findViewById(R.id.title);
        textViewex=findViewById(R.id.explain);
        imageView=findViewById(R.id.image_view);
       // datePicker=findViewById(R.id.picker);


    /*    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {


            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.getDefault());

               datePicker.init(year,month, dayOfMonth, null);

            }
        };
        new DatePickerDialog(this, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
*/

    }
  /*  private class DownloadImage extends AsyncTask<String, Void, Bitmap>{
        ImageView view;
        public DownloadImage(ImageView imageView1) {
            view=imageView1;
        }

        @Override
        protected Bitmap doInBackground(String... strings) {
            String imageURL = strings[0];
            Bitmap bimage = null;
            try {
                InputStream in = new java.net.URL(imageURL).openStream();
                bimage = BitmapFactory.decodeStream(in);

            } catch (Exception e) {
                Log.e("Error Message", e.getMessage());
                e.printStackTrace();
            }
            return bimage;
        }

        protected void onPostExecute(Bitmap result) {
            view.setImageBitmap(result);

        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.retro_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       if(item.getItemId()==R.id.date) selectDate();
        return super.onOptionsItemSelected(item);

    }
    public void selectDate(){

        final Calendar myCalendar = Calendar.getInstance();
        int mYear= myCalendar.get(Calendar.YEAR);
        int mMonth= myCalendar.get(Calendar.MONTH);
        int mDay= myCalendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dialog = new DatePickerDialog(MainActivity.this,
                new mDateSetListener(), mYear, mMonth, mDay);
        dialog.show();
    }

    class mDateSetListener implements DatePickerDialog.OnDateSetListener {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            // getCalender();
            int mYear = year;
            int mMonth = monthOfYear;
            int mDay = dayOfMonth;

            String formattedDay = (String.valueOf(mDay));
            String formattedMonth = (String.valueOf(mMonth));

            if(mDay < 10)
            {
                formattedDay = "0" + mDay;
            }

            if(mMonth+1 < 10)
            {
                formattedMonth = "0" + mMonth+1;
            }


          dateQuery=new StringBuilder().append(mYear).append("-").append(formattedMonth).append("-").append(formattedDay).toString();

            queryRetrofit(dateQuery);

        }
    }
    public void queryRetrofit(String date){

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://api.nasa.gov/planetary/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NasaApi nasaApi=retrofit.create(NasaApi.class);
        Call<Picture> picture = nasaApi.getPicture(date);
        picture.enqueue(new Callback<Picture>() {
            @Override
            public void onResponse(Call<Picture> call, Response<Picture> response) {
                if(!response.isSuccessful()){
                    textViewex.setText(response.code());
                }
                Picture picture1=response.body();
                textView.setText(picture1.getTitle());
                textViewex.setText(picture1.getBody());
                Picasso.get().load(Uri.parse(picture1.getImageUrl().toString())).into(imageView);
                // imageView.setBackgroundResource(picture1.getImageUrl());
                // new DownloadImage(imageView).execute(picture1.getImageUrl().toString());

            }

            @Override
            public void onFailure(Call<Picture> call, Throwable t) {
                textViewex.setText(t.getMessage());
            }
        });

    }
}
