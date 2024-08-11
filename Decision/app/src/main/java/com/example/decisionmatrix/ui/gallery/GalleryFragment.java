package com.example.decisionmatrix.ui.gallery;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.decisionmatrix.HomeActivity;
import com.example.decisionmatrix.R;
import com.example.decisionmatrix.SplashActivity;
import com.example.decisionmatrix.databinding.FragmentGalleryBinding;
import com.example.decisionmatrix.ui.home.HomeFragment;
import com.example.decisionmatrix.utils.FireBaseUtil;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        showLogoutConfirmationDialog();
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Confirm Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Implement logout functionality here
                FireBaseUtil.logout();
                startActivity(new Intent(requireContext(), SplashActivity.class));
                // Finish current activity
                requireActivity().finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Cancel logout

                startActivity(new Intent(requireContext(), SplashActivity.class));
//                // Finish current activity
                requireActivity().finish();
//                switchToHomeFragment();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCancelable(false); // Prevent dismissal by tapping outside
        dialog.show();
    }
    private void switchToHomeFragment() {
        // Replace GalleryFragment with HomeFragment
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.nav_home, new HomeFragment());
        fragmentTransaction.commit();
    }
}
