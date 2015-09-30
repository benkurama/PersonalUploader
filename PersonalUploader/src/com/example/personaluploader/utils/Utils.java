package com.example.personaluploader.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.example.personaluploader.R;
import com.example.personaluploader.fragments.MainMenuFragment;
import com.example.personaluploader.implementation.ProcessDialog;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.widget.Toast;

public enum Utils {
	me;
	
	public void MessageBoxOK(Context core, String msg){
		//
		new AlertDialog.Builder(core)
		.setTitle(msg)
		.setNeutralButton("OK", null)
		.show();
	}
	
	public void CopyStream(InputStream is, OutputStream os){
		//
		final int buffer_size=1024;
        try
        {
            byte[] bytes=new byte[buffer_size];
            for(;;)
            {
              int count=is.read(bytes, 0, buffer_size);
              if(count==-1)
                  break;
              os.write(bytes, 0, count);
            }
        }
        catch(Exception ex){}
	}
	
	@SuppressWarnings("resource")
	public byte[] imageToBytes(String imagePath){
		//
		File file = new File(imagePath);
		byte[] bytes = null;
		
		try{
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			
			byte[] buf = new byte[1024];
			
			for(int readNum; (readNum = fis.read(buf)) != -1;){
				bos.write(buf, 0, readNum);
			}
			bytes = bos.toByteArray();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bytes;
	}
	
	@SuppressLint("HandlerLeak")
	public void ProgressDialog(ProcessDialog coreInterface, Context core, String msg){
		//
		final ProcessDialog process = coreInterface;
		//
		final ProgressDialog dialog = ProgressDialog.show(core, "Please wait...", msg);
		final Handler handler = new Handler(){
			public void handleMessage(Message msg){
				dialog.dismiss();
				//
				process.finishProcess();
			}
		};
		Thread thread = new Thread(){
			public void run(){
				//
				process.startProcess();
				//
				handler.sendEmptyMessage(0);
			}
		};
		thread.start();
	}
	
	public void MessageToast(Context core, String msg){
		//
		Toast.makeText(core, msg, Toast.LENGTH_LONG).show();
	}
	
}
