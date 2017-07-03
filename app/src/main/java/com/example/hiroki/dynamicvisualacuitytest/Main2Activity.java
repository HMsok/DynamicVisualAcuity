package com.example.hiroki.dynamicvisualacuitytest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener{

    private List<Button> $buttons = new ArrayList<>();

    private static final int MAX_NUMBER = 25;

    private Map<Button, DynamicVisualAcuityDto> dtoMap = new HashMap<>();

    private int lastPushedNumber = 0;

    private TextView $timerText;

    private Timer timer;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        $buttons = this.makeButtonsList($buttons);
        Collections.shuffle($buttons);

        dtoMap = this.createDtoMap($buttons, MAX_NUMBER);
        this.setButtonTextAndEvent(dtoMap);
        $timerText = (TextView) findViewById(R.id.timerText);
        this.initTimerText();
    }

    private void initTimerText() {
        $timerText.setText("00:00.0");
    }

    private List<Button> makeButtonsList(List<Button> $buttons) {
        $buttons.add((Button) findViewById(R.id.button00));
        $buttons.add((Button) findViewById(R.id.button01));
        $buttons.add((Button) findViewById(R.id.button02));
        $buttons.add((Button) findViewById(R.id.button03));
        $buttons.add((Button) findViewById(R.id.button04));
        $buttons.add((Button) findViewById(R.id.button10));
        $buttons.add((Button) findViewById(R.id.button11));
        $buttons.add((Button) findViewById(R.id.button12));
        $buttons.add((Button) findViewById(R.id.button13));
        $buttons.add((Button) findViewById(R.id.button14));
        $buttons.add((Button) findViewById(R.id.button20));
        $buttons.add((Button) findViewById(R.id.button21));
        $buttons.add((Button) findViewById(R.id.button22));
        $buttons.add((Button) findViewById(R.id.button23));
        $buttons.add((Button) findViewById(R.id.button24));
        $buttons.add((Button) findViewById(R.id.button30));
        $buttons.add((Button) findViewById(R.id.button31));
        $buttons.add((Button) findViewById(R.id.button32));
        $buttons.add((Button) findViewById(R.id.button33));
        $buttons.add((Button) findViewById(R.id.button34));
        $buttons.add((Button) findViewById(R.id.button40));
        $buttons.add((Button) findViewById(R.id.button41));
        $buttons.add((Button) findViewById(R.id.button42));
        $buttons.add((Button) findViewById(R.id.button43));
        $buttons.add((Button) findViewById(R.id.button44));

        return $buttons;
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
            dtoMap.put($buttons.get(i - 1), dto);
        }
        return dtoMap;
    }

    private void setButtonTextAndEvent(Map<Button, DynamicVisualAcuityDto> dtoMap) {
        for (Map.Entry<Button, DynamicVisualAcuityDto> entry : dtoMap.entrySet()) {
            Button $btn = entry.getKey();
            $btn.setText(Integer.toString(entry.getValue().no));
            $btn.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v){
        DynamicVisualAcuityDto dto = dtoMap.get(v);
        if(dto.no == lastPushedNumber + 1) {
            dto.isPushed = true;
            lastPushedNumber++;

            if(dto.no == 1) {
                this.startGame();
            }

            // finished game
            if(dto.no == MAX_NUMBER) {
                this.finishGame();
            }
        } else {
            Toast.makeText(getApplicationContext(), "間違い", Toast.LENGTH_LONG).show();
        }
    }

    private void finishGame() {
        Toast.makeText(getApplicationContext(), "Game Clear!", Toast.LENGTH_LONG).show();
        Intent i = new Intent(getApplicationContext(), MainActivity.class);

//        resetTimer();
        stopTimer();
//        startActivity(i);
    }


    private void startGame() {
        this.resetTimer();

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

    private void resetTimer() {
        if (null != timer) {
            timer.cancel();
            timer = null;
            this.initTimerText();
            count = 0;
        }
    }

    private void stopTimer() {
        timer.cancel();
    }
}
