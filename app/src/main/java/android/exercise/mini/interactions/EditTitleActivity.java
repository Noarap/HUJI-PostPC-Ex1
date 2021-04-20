package android.exercise.mini.interactions;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EditTitleActivity extends AppCompatActivity {
  private boolean isEditing = false;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_edit_title);

    // find all views
    FloatingActionButton fabStartEdit = findViewById(R.id.fab_start_edit);
    FloatingActionButton fabEditDone = findViewById(R.id.fab_edit_done);
    TextView textViewTitle = findViewById(R.id.textViewPageTitle);
    EditText editTextTitle = findViewById(R.id.editTextPageTitle);

    // setup - start from static title with "edit" button
    fabStartEdit.setVisibility(View.VISIBLE);
    fabEditDone.setVisibility(View.GONE);
    textViewTitle.setText("Page title here");
    textViewTitle.setVisibility(View.VISIBLE);
    editTextTitle.setText("Page title here");
    editTextTitle.setVisibility(View.GONE);

    // handle clicks on "start edit"
    fabStartEdit.setOnClickListener(v -> {
      fabStartEdit.animate()
              .alpha(0f)
              .setDuration(200L)
              .withEndAction(new Runnable() {
                @Override
                public void run() {
                  fabEditDone.setVisibility(View.VISIBLE);
                  fabEditDone.animate()
                          .alpha(1f)
                          .setDuration(300L);
                }
              })
              .start();
      textViewTitle.setVisibility(View.GONE);
      editTextTitle.setVisibility(View.VISIBLE);
      isEditing = true;
    });

    // handle clicks on "done edit"
    fabEditDone.setOnClickListener(v -> {
      fabEditDone.animate()
              .alpha(0f)
              .setDuration(200L)
              .withEndAction(new Runnable() {
                @Override
                public void run() {
                  fabStartEdit.setVisibility(View.VISIBLE);
                  fabStartEdit.animate()
                          .alpha(1f)
                          .setDuration(300L);
                }
              })
              .start();
      closeKeyboard();
      CharSequence txt = editTextTitle.getText();
      textViewTitle.setText(txt);
      editTextTitle.setVisibility(View.GONE);
      textViewTitle.setVisibility(View.VISIBLE);
      isEditing = false;
    });
  }

  @Override
  public void onBackPressed() {
    // find all views
    FloatingActionButton fabStartEdit = findViewById(R.id.fab_start_edit);
    FloatingActionButton fabEditDone = findViewById(R.id.fab_edit_done);
    TextView textViewTitle = findViewById(R.id.textViewPageTitle);
    EditText editTextTitle = findViewById(R.id.editTextPageTitle);

    if (isEditing) {
      editTextTitle.setVisibility(View.GONE);
      editTextTitle.getText().clear();
      textViewTitle.setVisibility(View.VISIBLE);
      fabEditDone.animate()
              .alpha(0f)
              .setDuration(200L)
              .withEndAction(new Runnable() {
                @Override
                public void run() {
                  fabStartEdit.animate()
                          .alpha(1f)
                          .setDuration(300L);
                }
              })
              .start();
      fabEditDone.setVisibility(View.GONE);
      fabStartEdit.setVisibility(View.VISIBLE);
      isEditing = false;
    }
    else {
      super.onBackPressed();
      isEditing = false;
    }
  }

  private void closeKeyboard() {
    View view = this.getCurrentFocus();
    if (view != null) {
      InputMethodManager manager =
              (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
      manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
  }
}