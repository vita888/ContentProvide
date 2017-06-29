package com.example.vita.contentprovide;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.FileDescriptor;
import java.io.IOException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private  final  String TAG ="#######";
    private Button mButtonforDocument;
    private ContentResolver mContentResolver;
    private TextView mTextView;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButtonforDocument = (Button)findViewById(R.id.photo);
        mTextView = (TextView)findViewById(R.id.result);

        mButtonforDocument.setOnClickListener(this);
        mImageView = (ImageView)findViewById(R.id.image);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = mContentResolver.query(uri,null,null,null, null);
        mTextView.setText("");
        while (cursor.moveToNext()){
            String cName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String cNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Log.i(TAG, "name: "+cName+";number:"+cNum);
            Log.i(TAG,"=================");
            mTextView.append("name: "+cName+";number:"+cNum+"\n");

        }
        cursor.close();



    }


    private  void  queryContact(String name){
//        Uri uriNum = Uri.parse("content://com.android.contacts/data/phones/filter/" + name);
//
//        Cursor cursor = mContentResolver.query(uriNum,new String[]{"display_number"},null
//        ,null,null);
//
//        String number;
//
//        if (cursor.moveToFirst()){
//            number = cursor.getString(0);
//            Log.i(TAG, " "+name+"的电话号码是"+number);
//            mTextView.append(" "+name+"的电话号码是"+number);
//        }
//        cursor.close();
    
    }
    private void Opendocument(){
        Log.i(TAG, "Opendocument111: ");
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,null),2);
        Log.i(TAG, "Opendocument: ");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri uri;
        if (requestCode==2&&resultCode== Activity.RESULT_OK){

            if (data!=null){
                uri = data.getData();
                Log.i(TAG, "onActivityResult: URI:"+uri);
                if (getBitmapFromUri(uri)!=null) {
                    mImageView.setImageBitmap(getBitmapFromUri(uri));
                }
            }
        }


    }

    private Bitmap getBitmapFromUri(Uri uri){
        Bitmap image=null;
            ContentResolver contentResolver = getContentResolver();
            Cursor mCursor = contentResolver.query(uri,null,null,null,null);
            while (mCursor.moveToNext()) {
                String path = mCursor.getString(mCursor
                        .getColumnIndex(MediaStore.Images.Media.DATA));

                image = BitmapFactory.decodeFile(path);
            }
            return image;
    }

    @Override
    public void onClick(View v) {
       switch(v.getId()) {

           case  R.id.photo:
               Opendocument();
               break;
       }
    }
}
