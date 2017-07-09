package com.example.hiroki.dynamicvisualacuitytest;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity {

    // This will display when max number is clicked
    private Button $nextGameButton;
    private TableLayout $tableLayout;
    private TextView $timerText;
    private TextView $countDownTimerText;
    private TextView $nextNumberDisplay;
    private List<Button> buttons = new ArrayList<>();
    private int lastPushedNumber = 0;
    private Map<Button, DynamicVisualAcuityDto> dtoMap = new HashMap<>();
    private Timer timer;
    private CountDownTimer countDownTimer;
    private int count = 0;

    private static final int MAX_NUMBER = 25;
    private static final long START_COUNT = 4000; // msec
    private static final long INTERVAL_COUNT = 1000; // msec

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.decorateInternal();
        this.setInitializeView();
        this.bindEvents();
        countDownTimer.start();
    }

    private void decorateInternal() {
        $tableLayout = (TableLayout) findViewById(R.id.tableRayout);
        $timerText = (TextView) findViewById(R.id.timerText);
        $nextNumberDisplay = (TextView) findViewById(R.id.nextNumber);
        $nextGameButton = (Button) findViewById(R.id.nextGame);
        $countDownTimerText = (TextView) findViewById(R.id.countDownTimerText);
        buttons = this.makeButtonsList(buttons);
        Collections.shuffle(buttons);
        dtoMap = this.createDtoMap(buttons, MAX_NUMBER);
        countDownTimer = new CountDownTimer(this, START_COUNT, INTERVAL_COUNT);
    }

    private void setInitializeView() {
        $timerText.setText("00:00.0");
        $countDownTimerText.setText(countDownTimer.START_COUNT);

        for(Map.Entry<Button, DynamicVisualAcuityDto> entry : dtoMap.entrySet()) {
            Button $btn = entry.getKey();
            DynamicVisualAcuityDto dto = entry.getValue();
            $btn.setText(Integer.toString(dto.no));
        }

        $nextNumberDisplay.setText(Integer.toString(lastPushedNumber + 1));
    }

    private void bindEvents() {
        // set nextGameButton
        $nextGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNextGameBtn();
            }
        });

        for (Map.Entry<Button, DynamicVisualAcuityDto> entry : dtoMap.entrySet()) {
            Button $btn = entry.getKey();
            DynamicVisualAcuityDto dto = entry.getValue();
            if (dto.no == this.MAX_NUMBER) {
                $btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        finishGame();
                    }
                });
            } else {
                $btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onClickButton(view);
                    }
                });
            }
        }
    }

    private void onClickButton(View v) {
        DynamicVisualAcuityDto dto = dtoMap.get(v);
        if(dto.no == lastPushedNumber + 1) {
            dto.isPushed = true;
            lastPushedNumber++;
            $nextNumberDisplay.setText(Integer.toString(lastPushedNumber + 1));
            $nextNumberDisplay.setTextColor(Color.BLACK);
        } else {
            $nextNumberDisplay.setText(Integer.toString(lastPushedNumber + 1) + "!");
            $nextNumberDisplay.setTextColor(Color.RED);
        }
    }

    private void finishGame() {
        $nextGameButton.setVisibility(View.VISIBLE);
        $nextGameButton.setText("ホームに戻る");
        Toast.makeText(getApplicationContext(), "Game Clear!", Toast.LENGTH_LONG).show();
        timer.cancel();
    }

    public void onClickNextGameBtn() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
//        resetTimer();
        startActivity(i);
    }


    private List<Button> makeButtonsList(List<Button> buttons) {
        buttons.add((Button) findViewById(R.id.button00));
        buttons.add((Button) findViewById(R.id.button01));
        buttons.add((Button) findViewById(R.id.button02));
        buttons.add((Button) findViewById(R.id.button03));
        buttons.add((Button) findViewById(R.id.button04));
        buttons.add((Button) findViewById(R.id.button10));
        buttons.add((Button) findViewById(R.id.button11));
        buttons.add((Button) findViewById(R.id.button12));
        buttons.add((Button) findViewById(R.id.button13));
        buttons.add((Button) findViewById(R.id.button14));
        buttons.add((Button) findViewById(R.id.button20));
        buttons.add((Button) findViewById(R.id.button21));
        buttons.add((Button) findViewById(R.id.button22));
        buttons.add((Button) findViewById(R.id.button23));
        buttons.add((Button) findViewById(R.id.button24));
        buttons.add((Button) findViewById(R.id.button30));
        buttons.add((Button) findViewById(R.id.button31));
        buttons.add((Button) findViewById(R.id.button32));
        buttons.add((Button) findViewById(R.id.button33));
        buttons.add((Button) findViewById(R.id.button34));
        buttons.add((Button) findViewById(R.id.button40));
        buttons.add((Button) findViewById(R.id.button41));
        buttons.add((Button) findViewById(R.id.button42));
        buttons.add((Button) findViewById(R.id.button43));
        buttons.add((Button) findViewById(R.id.button44));
        return buttons;
    }

    public Map<Button ,DynamicVisualAcuityDto> createDtoMap(List<Button> $shuffledButtons, int maxNumber) {
        Map<Button, DynamicVisualAcuityDto> dtoMap = new HashMap<>();

        if($shuffledButtons.size() != maxNumber) {
            return Collections.emptyMap();
        }

        for(int i = 1; i <= maxNumber; i++) {
            DynamicVisualAcuityDto dto = new DynamicVisualAcuityDto();
            dto.no = i;
            dto.isPushed = false;
            dtoMap.put(buttons.get(i - 1), dto);
        }
        return dtoMap;
    }

    public void startGame() {
        $tableLayout.setVisibility(View.VISIBLE);
        $countDownTimerText.setVisibility(View.INVISIBLE);

//        this.resetTimer();
        this.startTimer();
    }

//    private void resetTimer() {
//        if (null != timer) {
//            timer.cancel();
//            timer = null;
//            count = 0;
//        }
//    }

    private void startTimer() {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                count++;
                long mm = count * 100 / 1000 / 60;
                long ss = count * 100 / 1000 % 60;
                long ms = (count * 100 - ss * 1000 - mm * 1000 * 60) / 100;
                $timerText.setText(String.format("%1$02d:%2$02d.%3$01d", mm, ss, ms));
            }
        },0 , 100);
    }

    class CountDownTimer extends android.os.CountDownTimer {

        private Main2Activity outerClass;

        final String START_COUNT = Integer.toString(3);

        CountDownTimer(Main2Activity main2Activity, long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
            this.outerClass = main2Activity;
        }
        @Override
        public void onTick(long l) {
            String countDownTimerText = Long.toString(l / 1000 % 60);
            outerClass.$countDownTimerText.setText(countDownTimerText);
        }

        @Override
        public void onFinish() {
            outerClass.startGame();
        }
    }
}
