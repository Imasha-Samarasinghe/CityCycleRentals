package com.sam.citycyclerentals;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    private RatingBar ratingBarFeedback;
    private EditText editTextFeedbackComment;
    private Button buttonSubmitFeedback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        ratingBarFeedback = findViewById(R.id.ratingBarFeedback);
        editTextFeedbackComment = findViewById(R.id.editTextFeedbackComment);
        buttonSubmitFeedback = findViewById(R.id.buttonSubmitFeedback);

        buttonSubmitFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                float rating = ratingBarFeedback.getRating();
                String comment = editTextFeedbackComment.getText().toString();

                if (rating == 0) {
                    Toast.makeText(FeedbackActivity.this, "Please provide a rating", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FeedbackActivity.this, "Feedback Submitted", Toast.LENGTH_SHORT).show();
                    ratingBarFeedback.setRating(0);
                    editTextFeedbackComment.setText("");
                }
            }
        });
    }
}
