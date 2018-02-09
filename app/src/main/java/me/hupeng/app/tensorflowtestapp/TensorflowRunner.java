package me.hupeng.app.tensorflowtestapp;

import android.content.Context;

import org.tensorflow.contrib.android.TensorFlowInferenceInterface;

import android.os.Handler;
import android.os.Looper;

/**
 * Created by HUPENG on 2018/2/10.
 */

public class TensorflowRunner {
    private static final String MODEL_FILE="file:///android_asset/1.pb";
    private Context mContext;
    private TensorFlowInferenceInterface inferenceInterface;
    private Handler handler = new Handler(Looper.getMainLooper());

    public static interface TensorflowRunnerListener{
        public void callback(float data);
    }

    private TensorflowRunnerListener tensorflowRunnerListener;

    public TensorflowRunner(Context context, TensorflowRunnerListener tensorflowRunnerListener){
        this.mContext = context;
        this.tensorflowRunnerListener = tensorflowRunnerListener;
        inferenceInterface = new TensorFlowInferenceInterface(this.mContext.getAssets(), MODEL_FILE);
    }

    public void add(final float data){
        new Thread(new Runnable() {
            @Override
            public void run() {
                inferenceInterface.feed("input", new float[]{data}, 1, 1);
                inferenceInterface.run(new String[]{"output"});
                final float[] out = new float[1];
                inferenceInterface.fetch("output",out);
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tensorflowRunnerListener.callback(out[0]);
                    }
                });
            }
        }).start();
    }
}
