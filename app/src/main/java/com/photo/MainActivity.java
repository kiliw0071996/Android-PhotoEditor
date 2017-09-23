package com.photo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ColorOverlaySubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubfilter;
import com.zomato.photofilters.imageprocessors.subfilters.SaturationSubfilter;
/*
หลาย
 */

//เดียว

/**
 * doc
 */
public class MainActivity extends AppCompatActivity {
    static {
        System.loadLibrary("NativeImageProcessor");
    }

    private Intent intent;
    private ImageView imageView;
    private Button pButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");

        pButton = (Button) findViewById(R.id.photoButton);
    }

    public void clk(View view) {
        if (view.getId() == R.id.photoButton) {
            startActivityForResult(intent, 0);
            pButton.setEnabled(false);
        } else if (view.getId() == R.id.filterButton) {
            if (imageView != null) {
                filter(imageView);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pButton.setEnabled(true);
        if (requestCode == 0 && resultCode == RESULT_OK) {
            imageView = (ImageView) findViewById(R.id.imageView);
            imageView.setImageURI(data.getData());
        }
    }

    public void filter(ImageView imageView) {
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap immutableBitmap = Bitmap.createBitmap(drawable.getBitmap());
        Bitmap bitmap = immutableBitmap.copy(Bitmap.Config.ARGB_8888, true);

        Filter myFilter = new Filter();
        myFilter.addSubFilter(new BrightnessSubfilter(30));
//        myFilter.addSubFilter(new ContrastSubfilter(1.1f));
        bitmap = myFilter.processFilter(bitmap);
        Log.w(" ", "dddd");
        System.out.println("JJJJJ");
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.item1){
            Log.i("gg", "onOptionsItemSelected: dsds");
            Button button = (Button) findViewById(R.id.filterButton);
            button.setText("chao");
            String app = getResources().getString(R.string.app_name);
            TextView textView = (TextView) findViewById(R.id.textView);
            textView.setText(app);
        }
        return super.onOptionsItemSelected(item);
    }
}
