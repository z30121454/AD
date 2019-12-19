package com.example.fan.sd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class MainActivity extends AppCompatActivity {
    //寫入按鈕與讀取按鈕
    private Button myBtIn, myBtOut;
    //輸入儲存文字的EditText
    private EditText myWriteEt;
    //顯示讀取文字的TextView
    private TextView myTvOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myBtIn = (Button) findViewById(R.id.myBtIn);
        myWriteEt = (EditText) findViewById(R.id.myWriteEt);
        myBtOut = (Button) findViewById(R.id.myBtOut);
        myTvOut = (TextView) findViewById(R.id.myTvOut);

        myBtIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    File mSDFile;
                    //檢查有沒有SD卡
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_REMOVED)) {
                        Toast.makeText(MainActivity.this, "請插入SD卡", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        //取得SD卡儲存路徑
                        mSDFile = Environment.getExternalStorageDirectory();
                    }
                    //建立儲存路徑
                    File mFile = new File(mSDFile.getParent() + "/" + mSDFile.getName() + "/MyAndroid");
                    //建立此檔案路徑
                    if (!mFile.exists()) {
                        mFile.mkdirs();
                    }
                    //取得mEdit文字並儲存寫入至SD卡文件裡
                    FileWriter mFileWriter = new FileWriter(mSDFile.getParent() + "/" + mSDFile.getName() + "/MyAndroid/File.txt");

                    mFileWriter.write(myWriteEt.getText().toString());
                    mFileWriter.close();
                    Toast.makeText(MainActivity.this, "已儲存文字", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                }
            }
        });

        myBtOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //取得SD卡儲存路徑
                    File mSDFile = Environment.getExternalStorageDirectory();
                    FileReader mFileReader = new FileReader(mSDFile.getParent() + "/" + mSDFile.getName() + "/MyAndroid/File.txt");
                    BufferedReader mBufferedReader = new BufferedReader(mFileReader);
                    String mReadText = "";
                    String mTextLine = mBufferedReader.readLine();

                    //取出文字字串裝入String裡
                    while (mTextLine != null) {
                        mReadText += mTextLine + "\n";
                        mTextLine = mBufferedReader.readLine();
                    }
                    //文字放入mText裡，並清空myWriteEt
                    myTvOut.setText(mReadText);
                    Toast.makeText(MainActivity.this, "已讀取文字", Toast.LENGTH_SHORT).show();
                    myWriteEt.setText("");
                } catch (Exception e) {
                }
            }
        });
    }
}