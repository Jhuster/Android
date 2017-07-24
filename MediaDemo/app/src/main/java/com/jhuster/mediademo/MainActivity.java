package com.jhuster.mediademo;

import android.app.Activity;
import android.media.MediaCodec;
import android.media.MediaCodec.BufferInfo;
import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.media.MediaMuxer;
import android.media.MediaMuxer.OutputFormat;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.nio.ByteBuffer;

public class MainActivity extends Activity {

    private static final String SDCARD_PATH = Environment.getExternalStorageDirectory().getPath();
    private static final String INPUT_FILEPATH = SDCARD_PATH + "/input.mp4";
    private static final String OUTPUT_FILEPATH = SDCARD_PATH + "/output.mp4";

    private TextView mLogView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLogView = (TextView) findViewById(R.id.LogView);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    transcode(INPUT_FILEPATH, OUTPUT_FILEPATH);
                } catch (IOException e) {
                    e.printStackTrace();
                    logout(e.getMessage());
                }
            }
        }).start();
    }

    protected boolean transcode(String input, String output) throws IOException {

        logout("start processing...");

        MediaMuxer muxer = null;

        MediaExtractor extractor = new MediaExtractor();
        extractor.setDataSource(input);

        logout("start demuxer: " + input);

        int mVideoTrackIndex = -1;
        for (int i = 0; i < extractor.getTrackCount(); i++) {
            MediaFormat format = extractor.getTrackFormat(i);
            String mime = format.getString(MediaFormat.KEY_MIME);
            if (!mime.startsWith("video")) {
                logout("mime not video, continue search");
                continue;
            }
            extractor.selectTrack(i);
            muxer = new MediaMuxer(output, OutputFormat.MUXER_OUTPUT_MPEG_4);
            mVideoTrackIndex = muxer.addTrack(format);
            muxer.start();
            logout("start muxer: " + output);
        }

        if (muxer == null) {
            logout("no video found !");
            return false;
        }

        BufferInfo info = new BufferInfo();
        info.presentationTimeUs = 0;
        ByteBuffer buffer = ByteBuffer.allocate(1024 * 1024 * 2);
        while (true) {
            int sampleSize = extractor.readSampleData(buffer, 0);
            if (sampleSize < 0) {
                logout("read sample data failed , break !");
                break;
            }
            info.offset = 0;
            info.size = sampleSize;
            info.flags = extractor.getSampleFlags();
            info.presentationTimeUs = extractor.getSampleTime();
            boolean keyframe = (info.flags & MediaCodec.BUFFER_FLAG_SYNC_FRAME) > 0;
            logout("write sample " + keyframe + ", " + sampleSize + ", " + info.presentationTimeUs);
            muxer.writeSampleData(mVideoTrackIndex, buffer, info);
            extractor.advance();
        }

        extractor.release();

        muxer.stop();
        muxer.release();

        logout("process success !");

        return true;
    }

    private void logout(final String content) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i("MediaDemo", content);
                mLogView.setText(mLogView.getText() + "\n" + content);
            }
        });
    }
}
