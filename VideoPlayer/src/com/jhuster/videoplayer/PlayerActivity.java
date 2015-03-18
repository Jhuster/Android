package com.jhuster.videoplayer;

import com.jhuster.videoplayer.R;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.TextView;

public class PlayerActivity extends Activity {

    private TextView mPlayerTextView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        mPlayerTextView = (TextView)findViewById(R.id.PlayerTextView);        
        
        if( getIntent().getData() == null ) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        builder.append("Intent Information:\n\n\n");
        builder.append("[Action]: "+getIntent().getAction()+"\n\n");
        builder.append("[Uri]: "+getIntent().getData()+"\n\n");        
        builder.append("[Scheme]: "+getIntent().getData().getScheme()+"\n\n");
        builder.append("[MimeType]: "+getIntent().getType()+"\n\n");
        builder.append("\n");
        builder.append("[VideoPath]: "+getVideoPath(this,getIntent().getData())+"\n\n");
        
        mPlayerTextView.setText(builder.toString());        
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    
    public static String getVideoPath(Context context, Uri uri) {
        
        Uri videopathURI = uri;
        if (uri.getScheme().toString().compareTo("content") == 0 ) {      
            Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
            if (cursor.moveToFirst()) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                videopathURI = Uri.parse(cursor.getString(column_index));
                return videopathURI.getPath();
            }
        }
        else if (uri.getScheme().compareTo("file") == 0 ) {
            return videopathURI.getPath();
        }
        
        return videopathURI.toString();
    }
    
}
