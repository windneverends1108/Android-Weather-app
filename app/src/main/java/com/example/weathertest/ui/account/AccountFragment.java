package com.example.weathertest.ui.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.weathertest.MainActivity;
import com.example.weathertest.R;
import com.google.android.material.navigation.NavigationView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class AccountFragment extends Fragment {

    private TextView title_account;
    private EditText account_name;
    private Button Bcapture;
    private ImageView icon_account;
    public File capFile;
    private static int RESULT_LOAD_IMG = 1;
    private static final int CAPTURE_IMAGE_REQUEST=100;
    public static final String GALIM_CONVERT= "GALIM_CONVERT";
    public static final String IMAGE_CONVERT= "IMAGE_CONVERT";
    public String capIm="";
    public byte[] imageBytes;
    private Button BSave;
    public static final  String SHARED_PREFS="sharedPrefs";
    public static final String default_name="Default Name";
    private String name;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account,container,false);
        return  view;
    }
    public void onViewCreated(View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        title_account=view.findViewById(R.id.text_account);

        account_name=view.findViewById(R.id.account_name);
        Bcapture=view.findViewById(R.id.B_capture_account);
        icon_account=view.findViewById(R.id.icon_account);
        BSave=view.findViewById(R.id.B_save_account);
        Bcapture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(MainActivity.this,PrimaryActivity.class));
                captureImage();
            }
        });
        BSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(account_name.getText()!=null) {
                    title_account.setText("account name :"+account_name.getText().toString());
                    saveData();
                }
                else{
                    Toast.makeText(getActivity(),"can't be void",Toast.LENGTH_SHORT).show();
                }
            }
        });

        loadData();
        upDateView();
    }
    public void loadData(){
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(AccountFragment.SHARED_PREFS, getActivity().MODE_PRIVATE);
        name=sharedPreferences.getString(AccountFragment.default_name,"");

    }
    public void upDateView(){
        title_account.setText("account name :"+name);

    }

    public void saveData(){
        SharedPreferences sharedPreferences =getActivity().getSharedPreferences(SHARED_PREFS,getActivity().MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString(default_name,account_name.getText().toString());
        if(editor.commit()){
        Toast.makeText(getActivity(),"Your account information is saved"+account_name.getText().toString(),Toast.LENGTH_SHORT).show();
    }
        else
            Toast.makeText(getActivity(),"Failed",Toast.LENGTH_SHORT).show();
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = AccountFragment.this.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        capIm = image.getAbsolutePath();
        Log.i("InCreateFile",image.getAbsolutePath());
        return image;
    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(AccountFragment.this.getContext().getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                capFile = createImageFile();
                displayMessage(getActivity().getBaseContext(),capFile.getAbsolutePath());
                Log.i("InTry",capFile.getAbsolutePath());
                // Continue only if the File was successfully created
                if (capFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getActivity(),
                            "com.example.accesscam.fileprovider",
                            capFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
                }
            } catch (Exception ex) {
                // Error occurred while creating the File
                displayMessage(getActivity().getBaseContext(),ex.getMessage().toString());
                Log.i("InCatch",ex.getMessage().toString());
            }
        }else
        {
            displayMessage(getActivity().getBaseContext(),"Nullll");
        }
        //Previous Version
        //Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //startActivityForResult(takePictureIntent, CAPTURE_IMAGE_REQUEST);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==CAPTURE_IMAGE_REQUEST)
        {
            //Previous Version
                /*Bundle bundle =data.getExtras();
                Bitmap imageBitmap=(Bitmap)bundle.get("data");
                imgView.setImageBitmap(imageBitmap);*/
            Bitmap myBitmap = BitmapFactory.decodeFile(capFile.getAbsolutePath());
            icon_account.setImageBitmap(myBitmap);

        }
        else if (resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Toast.makeText(getActivity(), imageUri.getPath(), Toast.LENGTH_LONG).show();
            try {
                Bitmap bitmap =  MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                //imgView.setImageBitmap(bitmap);
                //encode image to base64 string
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                imageBytes  = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //decode base64 string to image
                imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                icon_account.setImageBitmap(decodedImage);
                //galIm = Base64.encodeToString(imageBytes, Base64.DEFAULT);

            } catch (IOException e) {
                e.printStackTrace();
                Log.e("TryUploadGal",e.toString());
            }
        }
        else {
            Toast.makeText(getActivity(), "You haven't picked Image", Toast.LENGTH_LONG).show();
        }
    }
    private void displayMessage(Context context, String message)
    {
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }}