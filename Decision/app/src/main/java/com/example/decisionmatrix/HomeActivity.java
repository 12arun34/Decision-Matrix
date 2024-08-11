package com.example.decisionmatrix;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.navigation.NavDirections;
import com.example.decisionmatrix.databinding.HomeActivityBinding;


import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private HomeActivityBinding binding; // Update the binding variable
    private  ArrayList<String> sortedOptions = new ArrayList<>();
    private ArrayList<ArrayList<String>>listOfSortedOptions = new ArrayList<>();
    private ArrayList<String> decisionNames = new ArrayList<>();
    private SharedViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        viewModel = new ViewModelProvider(this).get(SharedViewModel.class);
        DecisionMatrixApplication decisionMatrixApp = (DecisionMatrixApplication) getApplication();
        viewModel = decisionMatrixApp.getSharedViewModel();

//        viewModel = ((DecisionMatrixApplication) getApplication()).getSharedViewModel();
            sortedOptions = new ArrayList<>();
        if (savedInstanceState == null) {
            System.out.println("Blunder BLunder ALERT: data from preference STORED");
            listOfSortedOptions = SharedPreferencesHelper.getSortedOptions(this);
            SharedPreferencesHelper.clearSortedOptions(this);
            viewModel.clearSortedOptions();
            // for decsion ==============
            decisionNames = SharedPreferencesHelper.getDecisionNames(this);
            SharedPreferencesHelper.clearDecisionNames(this);
            viewModel.clearDecisionNames();
        }
        // Get the sorted options ArrayList from the intent extras
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            sortedOptions = intent.getStringArrayListExtra("sortedOptions");
            if (sortedOptions != null) {
                System.out.println("Arun, correct pass of result");
                for (String val : sortedOptions) {
                    System.out.println(val);
                }
             } else {

                System.out.println("Arun, wrong result passed");
            }
        }
        System.out.println("Print1");
//        sortedOptions.add("name1");
//        sortedOptions.add("name2");
//        sortedOptions.add("name3");
        // Retrieve sorted options from SharedPreferences if app starts fresh
        if(!listOfSortedOptions.isEmpty() ){
//            listOfSortedOptions.add(sortedOptions);
            System.out.println("BLunder ALERT: data from preference Added");
            viewModel.addSortedOptions(this,listOfSortedOptions);
            viewModel.addDecisionNames(this,decisionNames);
//            System.out.println("Arun Blunder1 added");(NO change expected)
        }
        if(!sortedOptions.isEmpty() ){
            ArrayList<ArrayList<String>> empty = new ArrayList<>();
            ArrayList<String>empty2 = new ArrayList<>();
            if( !sortedOptions.isEmpty()){
                System.out.println("RED Alert: Entered in HOme activity (with Changes)");
                System.out.println("New Decision taken");
                empty.add(sortedOptions);
                viewModel.addSortedOptions(this,empty);
//               Giving default decision name
                MutableLiveData<ArrayList<String>> currentDecisionData= viewModel.getDecisionNames();
                ArrayList<String> currentDecision = currentDecisionData.getValue();
                int size=0;
                if(currentDecision!=null){
                    size = size + currentDecision.size();
                }
                String name = "Decision "+ (size+1) ;
                empty2.add(name);
                viewModel.addDecisionNames(this,empty2);

            }
            else{
                System.out.println("NOrmally entered in HOme activity (NO change expected)");
            }
            sortedOptions = new ArrayList<>();
        }

        System.out.println("Arun insie HOme Activity here 1");
        binding = HomeActivityBinding.inflate(getLayoutInflater()); // Update the binding initialization
        setContentView(binding.getRoot());
        System.out.println("Arun insie HOme Activity here 2");
        setSupportActionBar(binding.appBarMain.toolbar);
        // Set up navigation controller
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment_content_main2);
        navController.setGraph(navController.getGraph());

        // Pass sortedOptions to HomeFragment using NavDirections
        // Assuming you have NavController initialized
        //        NavDirections action = MainActivity3Directions.actionMainToHomeFragment(sortedOptions);
        //        navController.navigate(action);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setOpenableLayout(binding.drawerLayout)
                .build();
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main2);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
