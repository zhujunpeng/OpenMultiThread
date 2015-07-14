package com.example.mutithreaddemo;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.HttpHandler;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;

public class MainActivity extends Activity {

    private EditText et_http;
	private TextView tv_info;
	private Button download;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        et_http = (EditText) findViewById(R.id.et_http);
        tv_info = (TextView) findViewById(R.id.tv_info);
        download = (Button) findViewById(R.id.download);
        download.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				download();
			}
		});
    }

	private void download(){
		String path = et_http.getText().toString().trim();
		HttpUtils http = new HttpUtils();
		HttpHandler handler = http.download(path,
			    "/sdcard/abc.zip",
			    true, // ���Ŀ���ļ����ڣ�����δ��ɵĲ��ּ������ء���������֧��RANGEʱ���������ء�
			    true, // ��������󷵻���Ϣ�л�ȡ���ļ�����������ɺ��Զ���������
			    new RequestCallBack<File>() {

			        @Override
			        public void onStart() {
			        	tv_info.setText("conn...");
			        }

			        @Override
			        public void onLoading(long total, long current, boolean isUploading) {
			        	tv_info.setText(current + "/" + total);
			        }

			        @Override
			        public void onSuccess(ResponseInfo<File> responseInfo) {
			        	tv_info.setText("downloaded:" + responseInfo.result.getPath());
			        }


			        @Override
			        public void onFailure(HttpException error, String msg) {
			        	tv_info.setText(msg);
			        }
			});
	}
}
