package com.example.myapplication;

import android.app.Application;
import android.util.Log;

import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.embedding.engine.FlutterEngineCache;
import io.flutter.embedding.engine.dart.DartExecutor;

/**
 * @author bwahlen
 * Created 2/2/24
 */
public class DemoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initFluter();
    }

    public static String FLUTTER_ENGINE_CACHE_KEY = "ecift_flutter_engine";

    private void initFluter(){
        Log.w("TTT", "---init-flutter---");
        // create, pre-warm and put flutter-engine in cache
        FlutterEngine flutterEngine = new FlutterEngine(getApplicationContext());
        flutterEngine.getDartExecutor().executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault());
        FlutterEngineCache.getInstance().put(FLUTTER_ENGINE_CACHE_KEY, flutterEngine);
    }
}
