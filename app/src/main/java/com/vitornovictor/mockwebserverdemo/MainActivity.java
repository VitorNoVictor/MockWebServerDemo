package com.vitornovictor.mockwebserverdemo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.vitornovictor.mockwebserverdemo.api.GitHubApi;
import com.vitornovictor.mockwebserverdemo.api.GitHubUser;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
  private View searchUserPanel;
  private View resultPanel;
  private EditText username;
  private TextView result;
  private GitHubApi gitHubApi;

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

    resultPanel = findViewById(R.id.result_panel);
    result = (TextView) findViewById(R.id.result);
    View clearSearchButton = findViewById(R.id.clear_result_button);
    clearSearchButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        clearResult();
      }
    });

    gitHubApi = ((GitHubIdFinderApplication) getApplication()).getGitHubApi();
  }

  private void searchGitHubUser() {
    String username = this.username.getText().toString();
    gitHubApi.getUser(username).enqueue(new Callback<GitHubUser>() {
      @Override
      public void onResponse(
          Call<GitHubUser> call, Response<GitHubUser> response) {
        if (response.isSuccessful()) {
          GitHubUser user = response.body();
          setSearchResult(user.toString());
        } else {
          setSearchResult(getString(R.string.user_not_found));
        }

        showResultForm(true);
      }

      @Override
      public void onFailure(Call<GitHubUser> call, Throwable t) {
        showError();
      }
    });
  }

  private void clearResult() {
    username.setText(null);
    showResultForm(false);
  }

  private void showResultForm(boolean show) {
    resultPanel.setVisibility(show ? View.VISIBLE : View.GONE);
    searchUserPanel.setVisibility(show ? View.GONE : View.VISIBLE);
  }

  private void showError() {
    Snackbar.make(findViewById(android.R.id.content), R.string.error_message, Snackbar.LENGTH_SHORT)
            .show();
  }

  private void setSearchResult(String result) {
    this.result.setText(result);
  }
}

