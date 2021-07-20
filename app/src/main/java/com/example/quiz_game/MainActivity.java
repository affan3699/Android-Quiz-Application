package com.example.quiz_game;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.CalendarContract;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.graphics.Color;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView apple1, apple2, apple3, apple4, apple5, apple6, apple7, apple8, apple9;
    private ImageView number0, number1, number2, number3, number4, number5, number6, number7, number8, number9;
    private ImageView playAgain;
    private ImageView plate;
    private ImageView star1, star2, star3, star4;
    private TextView numberTextView1, numberTextView2, questionSignTextView, equalSignTextView, plusSignTextView;
    private TextView answerTextView1, answerTextView2, answerTextView3, answerPlusSign, answerEqualSign;
    private View finishingView, upperView;
    private int correctAnswer = -1;
    private ScaleAnimation scale;
    private TranslateAnimation trans;
    private RotateAnimation rotate;
    private AlphaAnimation alpha;
    private DisplayMetrics displaymetrics;
    private float screenHeight, screenWidth;
    private float[] appleCoordinates;
    private Vibrator vibrator;
    private MediaPlayer mediaPlayer;
    private Toast toast;
    private FloatingActionButton randomButton;

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        getCoordinates();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        apple1 = findViewById(R.id.apple1);
        apple2 = findViewById(R.id.apple2);
        apple3 = findViewById(R.id.apple3);
        apple4 = findViewById(R.id.apple4);
        apple5 = findViewById(R.id.apple5);
        apple6 = findViewById(R.id.apple6);
        apple7 = findViewById(R.id.apple7);
        apple8 = findViewById(R.id.apple8);
        apple9 = findViewById(R.id.apple9);

        number0 = findViewById(R.id.number0);
        number1 = findViewById(R.id.number1);
        number2 = findViewById(R.id.number2);
        number3 = findViewById(R.id.number3);
        number4 = findViewById(R.id.number4);
        number5 = findViewById(R.id.number5);
        number6 = findViewById(R.id.number6);
        number7 = findViewById(R.id.number7);
        number8 = findViewById(R.id.number8);
        number9 = findViewById(R.id.number9);

        numberTextView1 = findViewById(R.id.numberTextView1);
        numberTextView2 = findViewById(R.id.numberTextView2);
        plusSignTextView = findViewById(R.id.plusSignTextView);
        equalSignTextView = findViewById(R.id.equalSignTextView);
        questionSignTextView = findViewById(R.id.questionSignTextView);
        answerTextView1 = findViewById(R.id.answerTextVew1);
        answerTextView2 = findViewById(R.id.answerTextVew2);
        answerTextView3 = findViewById(R.id.answerTextVew3);
        answerPlusSign = findViewById(R.id.answerPlusSign);
        answerEqualSign = findViewById(R.id.answerEqualSign);

        randomButton = findViewById(R.id.randomButton);

        apple1.setOnTouchListener(handleTouch);
        apple2.setOnTouchListener(handleTouch);
        apple3.setOnTouchListener(handleTouch);
        apple4.setOnTouchListener(handleTouch);
        apple5.setOnTouchListener(handleTouch);
        apple6.setOnTouchListener(handleTouch);
        apple7.setOnTouchListener(handleTouch);
        apple8.setOnTouchListener(handleTouch);
        apple9.setOnTouchListener(handleTouch);

        finishingView = findViewById(R.id.finishingView);
        upperView = findViewById(R.id.upperView);

        playAgain = findViewById(R.id.playAgain);
        plate = findViewById(R.id.plate);

        star1 = findViewById(R.id.star1);
        star2 = findViewById(R.id.star2);
        star3 = findViewById(R.id.star3);
        star4 = findViewById(R.id.star4);

        displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        screenHeight = displaymetrics.heightPixels; // getting phone screen height
        screenWidth = displaymetrics.widthPixels; // getting phone screen width

        vibrator = (Vibrator)getSystemService(VIBRATOR_SERVICE); // for vibration
        toast = Toast.makeText(this, "Wrong Answer", Toast.LENGTH_SHORT);

        RandomQuestion();
    }

    private View.OnTouchListener handleTouch = new View.OnTouchListener()
    {
        int lastAction = 0;
        float dX, dY;

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent)
        {
            float newX = 0, newY = 0;

            switch (motionEvent.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    dX = view.getX() - motionEvent.getRawX();
                    dY = view.getY() - motionEvent.getRawY();
                    lastAction = MotionEvent.ACTION_DOWN;
                    break;
                case MotionEvent.ACTION_MOVE:
                    newX = motionEvent.getRawX() + dX;
                    newY = motionEvent.getRawY() + dY;

                    // checking if the apple is out of screen
                    if ((newX <= 0 || newX >= screenWidth-view.getWidth()) || (newY <= 408 || newY >= screenHeight-view.getHeight()))
                    {
                        lastAction = MotionEvent.ACTION_MOVE;
                        break;
                    }

                    view.animate()
                            .x(newX)
                            .y(newY)
                            .setDuration(0)
                            .start();

                    lastAction = MotionEvent.ACTION_MOVE;
                    break;
                default:
                    return false;
            }
            return true;
        }
    };

    public void answerCheck(View myView)
    {
        boolean flag = false;

        if(myView.getId() == number0.getId() && correctAnswer == 0)
        {
            System.out.println("Correct Answer0");
            flag = true;
        }

        else if(myView.getId() == number1.getId() && correctAnswer == 1)
        {
            System.out.println("Correct Answer1");
            flag = true;
        }

        else if(myView.getId() == number2.getId() && correctAnswer == 2)
        {
            System.out.println("Correct Answer2");
            flag = true;
        }

        else if(myView.getId() == number3.getId() && correctAnswer == 3)
        {
            System.out.println("Correct Answer3");
            flag = true;
        }

        else if(myView.getId() == number4.getId() && correctAnswer == 4)
        {
            System.out.println("Correct Answer4");
            flag = true;
        }

        else if(myView.getId() == number5.getId() && correctAnswer == 5)
        {
            System.out.println("Correct Answer5");
            flag = true;
        }

        else if(myView.getId() == number6.getId() && correctAnswer == 6)
        {
            System.out.println("Correct Answer6");
            flag = true;
        }

        else if(myView.getId() == number7.getId() && correctAnswer == 7)
        {
            System.out.println("Correct Answer7");
            flag = true;
        }

        else if(myView.getId() == number8.getId() && correctAnswer == 8)
        {
            System.out.println("Correct Answer8");
            flag = true;
        }

        else if(myView.getId() == number9.getId() && correctAnswer == 9)
        {
            System.out.println("Correct Answer9");
            flag = true;
        }

        if(flag == false)
        {
            toast.show();
            vibrator.vibrate(200);
        }
        else
        {
            playSound();
            showFinishingView(myView);
        }

        generateRandomColours();
    }

    private void RandomQuestion()
    {
        Random r = new Random();

        int number = r.nextInt(5);
        int number2 = r.nextInt(4);

        correctAnswer = number + number2;

        numberTextView1.setText(Integer.toString(number));
        numberTextView2.setText(Integer.toString(number2));
        answerTextView1.setText(Integer.toString(number));
        answerTextView2.setText(Integer.toString(number2));
        answerTextView3.setText(Integer.toString(correctAnswer));
    }

    public void showFinishingView(View myView)
    {
        upperView.setVisibility(View.INVISIBLE); // hiding upper view

        /* hiding apples */
        apple1.setVisibility(View.INVISIBLE);
        apple2.setVisibility(View.INVISIBLE);
        apple3.setVisibility(View.INVISIBLE);
        apple4.setVisibility(View.INVISIBLE);
        apple5.setVisibility(View.INVISIBLE);
        apple6.setVisibility(View.INVISIBLE);
        apple7.setVisibility(View.INVISIBLE);
        apple8.setVisibility(View.INVISIBLE);
        apple9.setVisibility(View.INVISIBLE);

        /* hiding numbers */
        number0.setVisibility(View.INVISIBLE);
        number1.setVisibility(View.INVISIBLE);
        number2.setVisibility(View.INVISIBLE);
        number3.setVisibility(View.INVISIBLE);
        number4.setVisibility(View.INVISIBLE);
        number5.setVisibility(View.INVISIBLE);
        number6.setVisibility(View.INVISIBLE);
        number7.setVisibility(View.INVISIBLE);
        number8.setVisibility(View.INVISIBLE);
        number9.setVisibility(View.INVISIBLE);

        /* hinding question */
        numberTextView1.setVisibility(View.INVISIBLE);
        numberTextView2.setVisibility(View.INVISIBLE);
        plusSignTextView.setVisibility(View.INVISIBLE);
        equalSignTextView.setVisibility(View.INVISIBLE);
        questionSignTextView.setVisibility(View.INVISIBLE);

        plate.setVisibility(View.INVISIBLE); // hiding plate
        randomButton.setVisibility(View.INVISIBLE);

        finishingView.setVisibility(View.VISIBLE);
        playAgain.setVisibility(View.VISIBLE);

        star1.setVisibility(View.VISIBLE);
        star2.setVisibility(View.VISIBLE);
        star3.setVisibility(View.VISIBLE);
        star4.setVisibility(View.VISIBLE);

        answerTextView1.setVisibility(View.VISIBLE);
        answerTextView2.setVisibility(View.VISIBLE);
        answerTextView3.setVisibility(View.VISIBLE);
        answerPlusSign.setVisibility(View.VISIBLE);
        answerEqualSign.setVisibility(View.VISIBLE);

        startAnimation();
    }

    public void hideFinishingView(View myView)
    {
        finishingView.setVisibility(View.INVISIBLE);
        playAgain.setVisibility(View.INVISIBLE);
        star1.setVisibility(View.INVISIBLE);
        star2.setVisibility(View.INVISIBLE);
        star3.setVisibility(View.INVISIBLE);
        star4.setVisibility(View.INVISIBLE);

        /* showing apples */
        apple1.setVisibility(View.VISIBLE);
        apple2.setVisibility(View.VISIBLE);
        apple3.setVisibility(View.VISIBLE);
        apple4.setVisibility(View.VISIBLE);
        apple5.setVisibility(View.VISIBLE);
        apple6.setVisibility(View.VISIBLE);
        apple7.setVisibility(View.VISIBLE);
        apple8.setVisibility(View.VISIBLE);
        apple9.setVisibility(View.VISIBLE);

        /* showing numbers */
        number0.setVisibility(View.VISIBLE);
        number1.setVisibility(View.VISIBLE);
        number2.setVisibility(View.VISIBLE);
        number3.setVisibility(View.VISIBLE);
        number4.setVisibility(View.VISIBLE);
        number5.setVisibility(View.VISIBLE);
        number6.setVisibility(View.VISIBLE);
        number7.setVisibility(View.VISIBLE);
        number8.setVisibility(View.VISIBLE);
        number9.setVisibility(View.VISIBLE);

        /* showing question */
        numberTextView1.setVisibility(View.VISIBLE);
        numberTextView2.setVisibility(View.VISIBLE);
        plusSignTextView.setVisibility(View.VISIBLE);
        equalSignTextView.setVisibility(View.VISIBLE);
        questionSignTextView.setVisibility(View.VISIBLE);

        upperView.setVisibility(View.VISIBLE); // showing upper view

        plate.setVisibility(View.VISIBLE); // showing plate
        randomButton.setVisibility(View.VISIBLE);

        answerTextView1.setVisibility(View.INVISIBLE);
        answerTextView2.setVisibility(View.INVISIBLE);
        answerTextView3.setVisibility(View.INVISIBLE);
        answerPlusSign.setVisibility(View.INVISIBLE);
        answerEqualSign.setVisibility(View.INVISIBLE);

        toast.cancel();
        stopAnimations();
        setApples();
        RandomQuestion();
    }

    private void startAnimation()
    {
        scale = new ScaleAnimation(0, 2, 0, 2);
        scale.setDuration(9000);
        scale.setRepeatCount(Animation.INFINITE);
        star1.startAnimation(scale);

        rotate = new RotateAnimation(0, 300);
        rotate.setDuration(9000);
        rotate.setRepeatCount(Animation.INFINITE);
        star2.startAnimation(rotate);

        trans = new TranslateAnimation(0, 200, 0, 100);
        trans.setDuration(9000);
        trans.setRepeatCount(Animation.INFINITE);
        star3.startAnimation(trans);

        alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(9000);
        star4.startAnimation(alpha);
    }

    private void stopAnimations()
    {
        star1.clearAnimation();
        star2.clearAnimation();
        star3.clearAnimation();
        star4.clearAnimation();
    }

    private void getCoordinates()
    {
        appleCoordinates = new float[18];

        appleCoordinates[0] = apple1.getX();
        appleCoordinates[1] = apple1.getY();
        appleCoordinates[2] = apple2.getX();
        appleCoordinates[3] = apple2.getY();
        appleCoordinates[4] = apple3.getX();
        appleCoordinates[5] = apple3.getY();
        appleCoordinates[6] = apple4.getX();
        appleCoordinates[7] = apple4.getY();
        appleCoordinates[8] = apple5.getX();
        appleCoordinates[9] = apple5.getY();
        appleCoordinates[10] = apple6.getX();
        appleCoordinates[11] = apple6.getY();
        appleCoordinates[12] = apple7.getX();
        appleCoordinates[13] = apple7.getY();
        appleCoordinates[14] = apple8.getX();
        appleCoordinates[15] = apple8.getY();
        appleCoordinates[16] = apple9.getX();
        appleCoordinates[17] = apple9.getY();
    }

    private void setApples()
    {
        apple1.setX(appleCoordinates[0]);
        apple1.setY(appleCoordinates[1]);

        apple2.setX(appleCoordinates[2]);
        apple2.setY(appleCoordinates[3]);

        apple3.setX(appleCoordinates[4]);
        apple3.setY(appleCoordinates[5]);

        apple4.setX(appleCoordinates[6]);
        apple4.setY(appleCoordinates[7]);

        apple5.setX(appleCoordinates[8]);
        apple5.setY(appleCoordinates[9]);

        apple6.setX(appleCoordinates[10]);
        apple6.setY(appleCoordinates[11]);

        apple7.setX(appleCoordinates[12]);
        apple7.setY(appleCoordinates[13]);

        apple8.setX(appleCoordinates[14]);
        apple8.setY(appleCoordinates[15]);

        apple9.setX(appleCoordinates[16]);
        apple9.setY(appleCoordinates[17]);
    }

    private void playSound()
    {
        mediaPlayer = MediaPlayer.create(this, R.raw.correct);
        mediaPlayer.start();
    }

    private void generateRandomColours()
    {
        Random random = new Random();
        int color = Color.argb(255, random.nextInt(256), random.nextInt(256), random.nextInt(256));
        numberTextView1.setTextColor(color);
        numberTextView2.setTextColor(color);
        questionSignTextView.setTextColor(color);
        equalSignTextView.setTextColor(color);
        plusSignTextView.setTextColor(color);
    }

    public void onClickRandomButton(View myView)
    {
        RandomQuestion();
        vibrator.vibrate(200);
        setApples();
    }
}