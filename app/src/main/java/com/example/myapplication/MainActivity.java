package com.example.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityMainBinding;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngineCache;

import static com.example.myapplication.DemoApplication.FLUTTER_ENGINE_CACHE_KEY;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    public void startFlutterActivity(String route) {
        FlutterEngineCache.getInstance().get(FLUTTER_ENGINE_CACHE_KEY).getNavigationChannel().pushRoute(route);
        startActivity(FlutterActivity.withCachedEngine(FLUTTER_ENGINE_CACHE_KEY).build(this));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startFlutterActivity("/chat");
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();

        //regular app start not triggered from message
        if (getIntent().getExtras() == null) {
            return;
        }

        if (getIntent().getExtras().getBoolean("FLUTTER_BACK_TO_NATIVE")) {
            handleFlutterIntent(getIntent().getExtras());
            return;
        }
    }

    private void handleFlutterIntent(Bundle intentExtras) {
        Log.i("tag", "flutter back: ");
        String viewName = intentExtras.getString("VIEW_NAME");
        Uri u = Uri.parse(viewName);
        switch (u.getPath()) {
            case "/matches": {
                runOnUiThread(() -> handleDrawerItemClick(1));
                break;
            }
            default:
                Log.w("tag", "target not implemented: " + viewName);
                break;
        }
    }

    protected void handleDrawerItemClick(int position) {
        if (isFinishing())
            return;

        if (position == 0) {
            startFlutterActivity("/chat/");
        } else {
            Fragment fr = getSupportFragmentManager().getFragmentFactory().instantiate(getApplicationContext().getClassLoader(), FirstFragment.class.getName()); //
            setFragment(fr, 0);
        }
    }

    protected void setFragment(Fragment fragment, int animateFrom) {
        popBackstackFull();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.nav_host_fragment_content_main, fragment);
        ft.commitAllowingStateLoss();
    }

    private void popBackstackFull() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            getSupportFragmentManager().popBackStack(getSupportFragmentManager().getBackStackEntryAt(0).getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

}