package com.apartmentslt.apartments.owner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.models.Complaint;
import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.services.ApartmentsService;
import com.apartmentslt.apartments.tenant.activities.FilterDialog;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.net.URI;

public class AddApartmentActivity extends AppCompatActivity {
    public final static String APARTMENT_DATA_KEY = "com.apartmentslt.apartments.APARTMENT_DATA";
    private Apartment currentApartment;
    private int REQUEST_GET_SINGLE_FILE = 1;
    private static Uri imagePath;
    File finishedfile;
    ImageView apartmentImage;
    EditText address;
    EditText price ;
    EditText size;
    EditText description;
    EditText rooms;
    EditText name;
    EditText country;
    EditText city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.addBackButton();
        toolbar.show();
        boolean editMode = getIntent().getBooleanExtra("edit",false);
        this.currentApartment = getCurrentApartment();
        apartmentImage = findViewById(R.id.apartment_image);
        address = findViewById(R.id.address);
        price = findViewById(R.id.price);
        size = findViewById(R.id.size);
        description = findViewById(R.id.description);
        rooms = findViewById(R.id.rooms);
         name = findViewById(R.id.name);
         country = findViewById(R.id.country);
         city = findViewById(R.id.city);

        Button btn = findViewById(R.id.apartment_book);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPicture();
            }
        });
        
        if (this.currentApartment != null && editMode) {
            populateEdit(this.currentApartment);
        }

    }

    private void showPicture() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        //intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select Picture"),REQUEST_GET_SINGLE_FILE);
    }
    public static String getPath(final Context context, final Uri uri) {

// check here to KITKAT or new version
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

// DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/"
                            + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };

                return getDataColumn(context, contentUri, selection,
                        selectionArgs);
            }
        }
// MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {


            return getDataColumn(context, uri, null, null);
        }
// File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }
    public static String getDataColumn(Context context, Uri uri,
                                       String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };

        try {
            cursor = context.getContentResolver().query(uri, projection,
                    selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri
                .getAuthority());
    }
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri
                .getAuthority());
    }
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri
                .getAuthority());
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (resultCode == RESULT_OK) {
                if (requestCode == REQUEST_GET_SINGLE_FILE) {
                    Uri selectedImageUri = data.getData();
                    String src = getPath(getApplicationContext(), selectedImageUri);
                    finishedfile = new File (src);
                    // Get the path from the Uri
                    final String path = getPathFromURI(selectedImageUri);
                    if (path != null) {
                        File f = new File(path);
                        selectedImageUri = Uri.fromFile(f);
                        //finishedfile = new File(path);
                    }
                    imagePath = selectedImageUri;

                    // Set the image in ImageView
                    apartmentImage.setImageURI(selectedImageUri);
                }
            }
        } catch (Exception e) {
            Log.e("FileSelectorActivity", "File select error", e);
        }
    }
    public String getPathFromURI(Uri contentUri) {
        /*String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();*/
        String[] proj = { MediaStore.Images.Media.DATA };
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
        //return res;
    }
    //TODO: replace with working version
    void populateEdit(Apartment apartment) {
        EditText address = findViewById(R.id.address);
        EditText price = findViewById(R.id.price);
        EditText size = findViewById(R.id.size);
        EditText description = findViewById(R.id.description);
        EditText rooms = findViewById(R.id.rooms);
        EditText name = findViewById(R.id.name);
        EditText country = findViewById(R.id.country);
        EditText city = findViewById(R.id.city);

        Glide.with(getApplicationContext())
                .load(apartment.getNuotraukaUrl())
                .error(R.drawable.ic_error)
                .into(apartmentImage);
        address.setText(apartment.getAdresas());
        price.setText(apartment.getKainaUzNakti()+"");
        size.setText(apartment.getDydis()+"");
        description.setText(apartment.getAprasas());
        rooms.setText(apartment.getKambaruSkaicius()+"");
        name.setText(apartment.getPavadinimas());
        country.setText(apartment.getŠalis());
        city.setText(apartment.getMiestas());

    }
    private Apartment getCurrentApartment() {
        Intent intent = getIntent();
        Apartment apartment = null;
        if (intent.getExtras() != null) {
            apartment = ((Apartment) intent.getExtras().getSerializable(APARTMENT_DATA_KEY));
            if (apartment == null) {
                Toast.makeText(this, "Could not load apartment", Toast.LENGTH_LONG).show();
            }
        }

        return apartment;
    }
    /**
     * Inflates toolbar menu items for the toolbar
     * @param menu Menu
     * @return true if inflated successfully
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_apartment_menu, menu);
        return true;
    }


    /**
     * Binds apartments data to components
     *
     * @param apartment Apartment
     */
    /*private void bindData(Apartment apartment) {
        ((TextView) findViewById(R.id.address)).setText(apartment.getAddress());
        // TODO: Finish binding
//        ((TextView) findViewById(R.id.price)).setText();
    }*/

    /**
     * Menu items click listener
     * Shows filter dialog after pressing filter icon
     * @param item Selected menu item
     * @return true if commands initiated successfully
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (currentApartment == null) {
            currentApartment = new Apartment();
        }
        currentApartment.setAdresas(address.getText().toString());
        currentApartment.setKainaUzNakti(Double.parseDouble(price.getText().toString()));
        currentApartment.setDydis(Integer.parseInt(size.getText().toString()));
        currentApartment.setAprasas((description.getText().toString()));
        currentApartment.setKambaruSkaicius(Integer.parseInt(rooms.getText().toString()));
        currentApartment.setPavadinimas("test");
        currentApartment.setMiestas("Kaunas");
        currentApartment.setŠalis("Lietuva");
        boolean editMode = getIntent().getBooleanExtra("edit",false);
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();


        // Makes complaint object
        Apartment apartment = new Apartment(currentApartment.getDydis(),
                currentApartment.getKambaruSkaicius(), currentApartment.getKainaUzNakti(),
                currentApartment.getAdresas(), currentApartment.getAprasas(), currentApartment.getPavadinimas(),
                currentApartment.getMiestas(),currentApartment.getŠalis(),
                User.getInstance().getIdIsNaudotojas());
        //File originalFile =  new File(getPath(getApplicationContext(), imagePath));
        MediaType a = MediaType.parse(getContentResolver().getType(imagePath));

       // RequestBody apartmentPart = RequestBody.create(MultipartBody.FORM, apartment);
        String path = imagePath.getPath();
        Apartment ap = new Apartment();
        //finishedfile = new File(imagePath.getPath());
        RequestBody filePart = RequestBody.create(a, finishedfile);
        MultipartBody.Part file = MultipartBody.Part.createFormData("Nuotrauka", finishedfile.getName(), filePart);


        ApartmentsService apartmentsService = retrofit.create(ApartmentsService.class);
        final Call<RequestBody> request = apartmentsService.addApartment(apartment, file);
        request.enqueue(new Callback<RequestBody>() {

            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                if (response.isSuccessful()) {
                    RequestBody body = response.body();
                    if (body == null) {
                        Toast.makeText(getApplicationContext(), "Could not load save apartment", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(getApplicationContext(), "Apartment was successfully sent", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        if (item.getItemId() == R.id.action_done) {
            if (editMode) {
                Toast.makeText(getApplicationContext(),"Edited successfully!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Added successfully!", Toast.LENGTH_SHORT).show();

            }
            finish();
        }

        return true;
    }

}
