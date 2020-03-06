package com.example.mathgameudemy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity
{
    private int points;
    private TextView tvPoints;
    private SharedPreferences sharedPreferences;
    private ImageView ivHighScore;
    private TextView tvHighScore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_over);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        if (getIntent().getExtras() != null)
        {
            points = getIntent().getExtras().getInt("points");
        }

        tvPoints = findViewById(R.id.tvScore);
        ivHighScore = findViewById(R.id.ivHighScore);
        tvHighScore = findViewById(R.id.tvHighScore);

        sharedPreferences = getSharedPreferences("pref", 0);
        int poinstSP = sharedPreferences.getInt("pointsSP", 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (points > poinstSP)
        {
            poinstSP = points;
            editor.putInt("pointsSP", poinstSP);
            editor.commit();
            ivHighScore.setVisibility(View.VISIBLE);
        }
        tvPoints.setText("" + points);
        tvHighScore.setText("" + poinstSP);
    }

    public void restart(View view)
    {
        Intent intent = new Intent(GameOver.this, StartGame.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view)
    {
        finish();
    }
}
