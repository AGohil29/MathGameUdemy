package com.example.mathgameudemy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class StartGame extends AppCompatActivity
{
    private int opt1 = 0, opt2 = 0, correctAnswer = 0, incorrectAnswer = 0;
    private TextView tvTimer, tvPoints, tvSum, tvResult;
    Button btn0, btn1, btn2, btn3;
    private CountDownTimer countDownTimer;
    private long millisUntilFinished = 30100;
    private int points = 0, numberOfQuestions = 0;
    private Random random;
    private int[] btnIds;
    private int correctAnswerPosition = 0;
    private ArrayList<Integer> incorrectAnswers;
    private String[] operatorArray;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_game);
        if (getSupportActionBar() != null)
            getSupportActionBar().hide();

        tvTimer = findViewById(R.id.tvTimer);
        tvPoints = findViewById(R.id.tvPoints);
        tvSum = findViewById(R.id.tvSum);
        tvResult = findViewById(R.id.tvResult);
        btn0 = findViewById(R.id.btn0);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3   );

        random = new Random();
        btnIds = new int[]{R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3};
        incorrectAnswers = new ArrayList<>();
        operatorArray = new String[]{"+", "-", "*", "÷"};
        startGame();
    }

    private void startGame()
    {
        tvTimer.setText("" + millisUntilFinished / 1000 + "s");
        tvPoints.setText("" + points + "/" + numberOfQuestions);
        generateQuestions();

        countDownTimer = new CountDownTimer(millisUntilFinished, 1000)
        {
            @Override
            public void onTick(long millisUntilFinished)
            {
                tvTimer.setText("" + (millisUntilFinished / 1000) + "s");
            }

            @Override
            public void onFinish()
            {
                tvTimer.setText("0s");
                btn0.setClickable(false);
                btn1.setClickable(false);
                btn2.setClickable(false);
                btn3.setClickable(false);

                Intent intent = new Intent(StartGame.this, GameOver.class);
                intent.putExtra("points", points);
                startActivity(intent);
                finish();
            }
        }.start();
    }

    private void generateQuestions()
    {
        numberOfQuestions++;
        opt1 = random.nextInt(10);
        opt2 = 1 + random.nextInt(9);
        String selectedOperator = operatorArray[random.nextInt(4)];
        correctAnswer = getAnswer(selectedOperator);
        tvSum.setText(opt1 + " " + selectedOperator + " " + opt2 + " = ");
        correctAnswerPosition = random.nextInt(4);
        ((Button) findViewById(btnIds[correctAnswerPosition])).setText("" + correctAnswer);

        while (true)
        {
            if (incorrectAnswers.size() > 3)
                break;
            opt1 = random.nextInt(10);
            opt2 = 1 + random.nextInt(9);
            selectedOperator = operatorArray[random.nextInt(4)];
            incorrectAnswer = getAnswer(selectedOperator);
            if (incorrectAnswer == correctAnswer)
            {
                continue;
            }
            incorrectAnswers.add(incorrectAnswer);
        }

        for (int i = 0; i<3; i++)
        {
            if (i == correctAnswerPosition)
            {
                continue;
            }
            ((Button) findViewById(btnIds[i])).setText("" + incorrectAnswers.get(i));
        }
        incorrectAnswers.clear();
    }

    private int getAnswer(String selectedOperator)
    {
        int answer = 0;
        switch (selectedOperator)
        {
            case "+":
                answer = opt1 + opt2;
                break;
            case "-":
                answer = opt1 - opt2;
                break;
            case "*":
                answer = opt1 * opt2;
                break;
            case "÷":
                answer = opt1 / opt2;
                break;
        }
        return answer;
    }

    public void chooseAnswer(View view)
    {
        int answer = Integer.parseInt(((Button) view).getText().toString());
        if (answer == correctAnswer)
        {
            points++;
            tvResult.setText("Correct!");
        }
        else
        {
            tvResult.setText("Wrong!");
        }
        tvPoints.setText(points + "/" + numberOfQuestions);
        generateQuestions();
    }
}
