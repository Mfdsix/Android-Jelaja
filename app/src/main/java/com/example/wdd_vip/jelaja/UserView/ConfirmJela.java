package com.example.wdd_vip.jelaja.UserView;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.opengl.GLES10;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wdd_vip.jelaja.Client.Api;
import com.example.wdd_vip.jelaja.Client.ApiJelapay;
import com.example.wdd_vip.jelaja.Client.Retro;
import com.example.wdd_vip.jelaja.Model.AuthModel;
import com.example.wdd_vip.jelaja.R;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ConfirmJela extends AppCompatActivity {

    Button btn_upload;
    ImageView imgHolder;
    String part_image;
    ProgressDialog pd;
    TextView jml_topup;
    final int REQUEST_EXTERNAL_STORAGE = 0;
    final int REQUEST_GALLERY = 200;
    private static String[] PERMISSION_EXTERNAL_STORAGE = {Manifest.permission.READ_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_jela);

        int id_ptransaction = getIntent().getIntExtra("id_ptransaction", 0);
        String amount = getIntent().getStringExtra("amount");

        jml_topup = (TextView) findViewById(R.id.jml_topup);
        imgHolder = (ImageView) findViewById(R.id.imgHolder);
        btn_upload = (Button) findViewById(R.id.btn_upload);
        pd = new ProgressDialog(ConfirmJela.this);
        pd.setMessage("Sedang Memproses");
        pd.setCancelable(false);
        jml_topup.setText(amount);

        getSupportActionBar().setTitle("Konfirmasi Pembayaran");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imgHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                permission
                if(ActivityCompat.checkSelfPermission(ConfirmJela.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
                {
                    ActivityCompat.requestPermissions(ConfirmJela.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                }
                else
                {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_GALLERY);
                }
//                end permission
            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.show();
                File imagefile = new File(part_image);
                RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-file"), imagefile);
                RequestBody req_id = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(id_ptransaction));
                MultipartBody.Part part = MultipartBody.Part.createFormData("rich_photo", imagefile.getName(), requestBody);

                ApiJelapay apiJelapay = Retro.createJelapayApi();
                apiJelapay.uploadRich(part, req_id).enqueue(new Callback<AuthModel>() {
                    @Override
                    public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                        pd.dismiss();
                        if(response.body().getMessage().equals("200"))
                        {
                            Snackbar.make(v, "Bukti Sukses Di upload", Snackbar.LENGTH_SHORT).show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    startActivity(new Intent(ConfirmJela.this, JelaPay.class));
                                }
                            },2000);
                        }
                        else
                        {
                            Snackbar.make(v, response.message(), Snackbar.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthModel> call, Throwable t) {
                        pd.dismiss();
                        Snackbar.make(v, "Periksa koneksi internet", Snackbar.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK)
        {
            if(requestCode == REQUEST_GALLERY)
            {
                Cursor cursor = ConfirmJela.this.getContentResolver().query(data.getData(), null,null,null,null);
                if(cursor==null)
                {
                    part_image = data.getData().getPath();
                }
                else
                {
                    cursor.moveToFirst();
                    int imgIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
                    part_image = cursor.getString(imgIndex);
                }
                if(part_image!=null)
                {
                    File imageFile = new File(part_image);
                    Bitmap bitmap =  BitmapFactory.decodeFile(imageFile.getAbsolutePath());

                    int nh = (int) (bitmap.getHeight() * (512.0 / bitmap.getWidth()));
                    bitmap = Bitmap.createScaledBitmap(bitmap, 512, nh, true);

                    imgHolder.setImageBitmap(bitmap);
                    btn_upload.setEnabled(true);
                }
                else
                {
                    Toast.makeText(ConfirmJela.this, "Kesalahan saat mengambil file", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1)
        {
            if(grantResults.length > 0)
            {
                StringBuffer stringBuffer = new StringBuffer();
                int grantResult = grantResults[0];
                if(grantResult==PackageManager.PERMISSION_GRANTED)
                {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, REQUEST_GALLERY);
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home :
                super.onBackPressed();
                return true;
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
