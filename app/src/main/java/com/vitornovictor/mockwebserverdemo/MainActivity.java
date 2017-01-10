package com.vitornovictor.mockwebserverdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
  private View mSearchUserFormView;
  private View mResultFormView;
  private EditText mUsernameView;
  private View mProgressView;
  private TextView mResultTextView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    mSearchUserFormView = findViewById(R.id.search_user_form);
    mUsernameView = (EditText) findViewById(R.id.username);
    View searchUserButton = findViewById(R.id.search_user_button);
    searchUserButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        searchGitHubUser();
      }
    });

    mProgressView = findViewById(R.id.login_progress);

    mResultFormView = findViewById(R.id.result_form);
    mResultTextView = (TextView) findViewById(R.id.result_label);
    View clearSearchButton = findViewById(R.id.clear_result_button);
    clearSearchButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        clearResult();
      }
    });
  }

  private void searchGitHubUser() {
    //TODO
  }

  private void clearResult() {
    showResultForm(false);
    showSearchForm(true);
  }

  private void showResultForm(boolean show) {
    mResultFormView.setVisibility(show ? View.VISIBLE : View.GONE);
  }

  private void showSearchForm(boolean show) {
    mSearchUserFormView.setVisibility(show ? View.VISIBLE : View.GONE);
  }

  private void showProgress(final boolean show) {
    int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

    mSearchUserFormView.setVisibility(show ? View.GONE : View.VISIBLE);
    mSearchUserFormView.animate()
                       .setDuration(shortAnimTime)
                       .alpha(show ? 0 : 1)
                       .setListener(new AnimatorListenerAdapter() {
                         @Override
                         public void onAnimationEnd(Animator animation) {
                           mSearchUserFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                         }
                       });

    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
    mProgressView.animate()
                 .setDuration(shortAnimTime)
                 .alpha(show ? 1 : 0)
                 .setListener(new AnimatorListenerAdapter() {
                   @Override
                   public void onAnimationEnd(Animator animation) {
                     mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                   }
                 });
  }

  private void showError() {
    Snackbar.make(findViewById(android.R.id.content), R.string.error_message, Snackbar.LENGTH_SHORT)
            .show();
  }

  private void setSearchResult(String result) {
    mResultTextView.setText(result);
  }
}

