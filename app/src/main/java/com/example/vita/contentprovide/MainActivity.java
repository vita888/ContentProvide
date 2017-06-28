package com.example.vita.contentprovide;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private  final  String TAG ="#######";
    private Button mButton;
    private EditText mEditText;
    private ContentResolver mContentResolver;
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mContentResolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = mContentResolver.query(uri,null,null,null, null);
        while (cursor.moveToNext()){
            String cName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String cNum = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

            Log.i(TAG, "name: "+cName+";number:"+cNum);
            Log.i(TAG,"=================");
        }
        cursor.close();


        mButton = (Button)findViewById(R.id.searchbtn);
        mEditText = (EditText)findViewById(R.id.searchet);
        mTextView = (TextView)findViewById(R.id.result);

        mButton.setOnClickListener(this);

    }

    private  void  queryContact(String name){
        Uri uriNum = Uri.parse("content://com.android.contacts/data/phones/filter/" + name);

        Cursor cursor = mContentResolver.query(uriNum,new String[]{"display_number"},null
        ,null,null);

        String number;

        if (cursor.moveToFirst()){
            number = cursor.getString(0);
            Log.i(TAG, " "+name+"的电话号码是"+number);
            mTextView.append(" "+name+"的电话号码是"+number);
        }
        cursor.close();
    
    }
    @Override
    public void onClick(View v) {
       switch(v.getId()) {
           case R.id.searchbtn:
               String name = mEditText.getText().toString();
               queryContact(name);
       }
    }
}
