// Generated by view binder compiler. Do not edit!
package com.example.decisionmatrix.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.decisionmatrix.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityOtpBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView gotToLogin;

  @NonNull
  public final ProgressBar loginProgressBar;

  @NonNull
  public final TextView loginText;

  @NonNull
  public final EditText otp;

  @NonNull
  public final Button otpNextButton;

  @NonNull
  public final TextView resendOtpTextview;

  private ActivityOtpBinding(@NonNull LinearLayout rootView, @NonNull TextView gotToLogin,
      @NonNull ProgressBar loginProgressBar, @NonNull TextView loginText, @NonNull EditText otp,
      @NonNull Button otpNextButton, @NonNull TextView resendOtpTextview) {
    this.rootView = rootView;
    this.gotToLogin = gotToLogin;
    this.loginProgressBar = loginProgressBar;
    this.loginText = loginText;
    this.otp = otp;
    this.otpNextButton = otpNextButton;
    this.resendOtpTextview = resendOtpTextview;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityOtpBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityOtpBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_otp, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityOtpBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.gotToLogin;
      TextView gotToLogin = ViewBindings.findChildViewById(rootView, id);
      if (gotToLogin == null) {
        break missingId;
      }

      id = R.id.login_progress_bar;
      ProgressBar loginProgressBar = ViewBindings.findChildViewById(rootView, id);
      if (loginProgressBar == null) {
        break missingId;
      }

      id = R.id.loginText;
      TextView loginText = ViewBindings.findChildViewById(rootView, id);
      if (loginText == null) {
        break missingId;
      }

      id = R.id.otp;
      EditText otp = ViewBindings.findChildViewById(rootView, id);
      if (otp == null) {
        break missingId;
      }

      id = R.id.otpNextButton;
      Button otpNextButton = ViewBindings.findChildViewById(rootView, id);
      if (otpNextButton == null) {
        break missingId;
      }

      id = R.id.resend_otp_textview;
      TextView resendOtpTextview = ViewBindings.findChildViewById(rootView, id);
      if (resendOtpTextview == null) {
        break missingId;
      }

      return new ActivityOtpBinding((LinearLayout) rootView, gotToLogin, loginProgressBar,
          loginText, otp, otpNextButton, resendOtpTextview);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
