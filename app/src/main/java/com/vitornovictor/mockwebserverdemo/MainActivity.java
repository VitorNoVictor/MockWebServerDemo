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
  private View searchUserPanel;
  private View resultPanel;
  private EditText username;
  private View progress;
  private TextView result;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    searchUserPanel = findViewById(R.id.search_user_panel);
    username = (EditText) findViewById(R.id.username);
    View searchUserButton = findViewById(R.id.search_user_button);
    searchUserButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        searchGitHubUser();
      }
    });

    progress = findViewById(R.id.progress);

    resultPanel = findViewById(R.id.result_panel);
    result = (TextView) findViewById(R.id.result);
    View clearSearchButton = findViewById(R.id.clear_result_button);
    clearSearchButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        clearResult();
      }
    });
  }

  private void searchGitHubUser() {
    String username = this.username.getText().toString();
    setSearchResult(username);
    showResultForm(true);
    showSearchForm(false);
  }

  private void clearResult() {
    username.setText(null);
    showResultForm(false);
    showSearchForm(true);
  }

  private void showResultForm(boolean show) {
    resultPanel.setVisibility(show ? View.VISIBLE : View.GONE);
  }

  private void showSearchForm(boolean show) {
    searchUserPanel.setVisibility(show ? View.VISIBLE : View.GONE);
  }

  private void showProgress(final boolean show) {
    int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

    searchUserPanel.setVisibility(show ? View.GONE : View.VISIBLE);
    searchUserPanel.animate()
                   .setDuration(shortAnimTime)
                   .alpha(show ? 0 : 1)
                   .setListener(new AnimatorListenerAdapter() {
                         @Override
                         public void onAnimationEnd(Animator animation) {
                           searchUserPanel.setVisibility(show ? View.GONE : View.VISIBLE);
                         }
                       });

    progress.setVisibility(show ? View.VISIBLE : View.GONE);
    progress.animate()
            .setDuration(shortAnimTime)
            .alpha(show ? 1 : 0)
            .setListener(new AnimatorListenerAdapter() {
                   @Override
                   public void onAnimationEnd(Animator animation) {
                     progress.setVisibility(show ? View.VISIBLE : View.GONE);
                   }
                 });
  }

  private void showError() {
    Snackbar.make(findViewById(android.R.id.content), R.string.error_message, Snackbar.LENGTH_SHORT)
            .show();
  }

  private void setSearchResult(String result) {
    this.result.setText(result);
  }
}

