package com.ishmeetgrewal.elements;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GameActivity extends Activity {


    int nextItem;
    int nextColor;
    int level = 0;
    ImageView nextItemView;
    boolean newGame = true;
    int[] tileArray = new int[49];
    int red = R.drawable.icon_fire;
    int green = R.drawable.icon_grass;
    int blue = R.drawable.icon_water;
    int blank = R.drawable.icon_blank;
    int score = 0;
    final Handler handler = new Handler();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ActionBar actionBar = getActionBar();
        actionBar.hide();

        nextItemView = (ImageView) findViewById(R.id.next_item_view);
        nextItemView.setImageResource(getNextItem());
        nextItemView.setBackgroundColor(nextColor);

        GridView gridView = (GridView) findViewById(R.id.grid_view);
        gridView.setBackgroundColor(Color.GRAY);
        gridView.setVerticalSpacing(1);
        gridView.setHorizontalSpacing(1);
        // Instance of ImageAdapter Class
        gridView.setAdapter(new GridAdapter(this));


        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ImageView imageView = (ImageView) view;
                if(tileArray[position]==0){
                    tileArray[position]= nextItem;
                    imageView.setImageResource(nextItem);
                    imageView.setBackgroundColor(nextColor);
                    checkNeighbors(position,nextItem);
                    nextItemView = (ImageView) findViewById(R.id.next_item_view);
                    nextItemView.setImageResource(getNextItem());
                    nextItemView.setBackgroundColor(nextColor);
                }else{
                    Toast.makeText(getApplicationContext(), "Not possible!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ImageButton button_fire_power = (ImageButton) findViewById(R.id.button_fire_power);
        ImageButton button_water_power = (ImageButton) findViewById(R.id.button_water_power);
        ImageButton button_grass_power = (ImageButton) findViewById(R.id.button_grass_power);

        button_fire_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                powerUp(red);
            }
        });
        button_water_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                powerUp(blue);
            }
        });
        button_grass_power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                powerUp(green);
            }
        });


    }

    public int getNextItem(){
        int random = (int) (Math.random() * 3 + 1);
        int item, color;
        if(random == 1){
            item = red;
            color = Color.rgb(235,193,55);
        }else if (random==2){
            item = green;
            color = Color.rgb(107,154,51);
        }else{
            item = blue;
            color = Color.rgb(0,143,213);
        }
        nextColor = color;
        nextItem = item;
        return item;
    }

    public boolean checkAdjacent(int pos){
        boolean result = false;
        int top,left,bottom,right;
        top = pos - 7;
        if(top<0){
            top = -1;
        }
        left = pos - 1;
        if(((left+1)%7==0)||(left<0)){
            left = -1;
        }
        right = pos + 1;
        if((right%7==0)||(right>48)){
            right = -1;
        }
        bottom = pos + 7;
        if (bottom>48){
            bottom = -1;
        }

        if(top>=0 && tileArray[top]!=0) {
            result = true;
        }
        else if(left>=0 && tileArray[left]!=0){
            result = true;
        }
        else if(right>=0 && tileArray[right]!=0){
            result = true;
        }
        else if(bottom>=0 && tileArray[bottom]!=0){
            result = true;
        }
        return result;
    }

    public void checkNeighbors(int pos, int currentItem){
        //Determine Neighbors
        int top,left,bottom,right,topLeft,topRight,botRight,botLeft;
        top = pos - 7;
        if(top<0){
            top = -2;
        }
        left = pos - 1;
        if(((left+1)%7==0)||(left<0)){
            left = -2;
        }
        right = pos + 1;
        if((right%7==0)||(right>48)){
            right = -2;
        }
        bottom = pos + 7;
        if (bottom>48){
            bottom = -2;
        }
        topLeft = top - 1;
        if((topLeft+1)%7==0 || topLeft<0){
            topLeft = -2;
        }
        topRight = top + 1;
        if ((topRight%7==0)){
            topRight = -2;
        }
        botLeft = bottom - 1;
        if((botLeft+1)%7==0){
            botLeft = -2;
        }
        botRight = bottom + 1;
        if((botRight%7==0) || botRight>48){
            botRight = -2;
        }
        boolean deleted;
        //Analysis
        if(currentItem == red){
            deleted = removeItems(green,top,bottom,left,right,topLeft,topRight,botLeft,botRight);
        }else if (currentItem == blue){
            deleted = removeItems(red,top,bottom,left,right,topLeft,topRight,botLeft,botRight);
        }else{
            deleted = removeItems(blue,top,bottom,left,right,topLeft,topRight,botLeft,botRight);
        }

        if(!deleted){
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    addRandomTiles();
                }
            }, 100);
        }
    }

    public boolean removeItems(int color, int top, int bottom, int left, int right, int topLeft,int topRight,int bottomLeft,int bottomRight){
        boolean result = false;
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        ImageView iv;

        if(top>=0 && tileArray[top]==color){
            tileArray[top]=0;
            iv = (ImageView) gridView.getChildAt(top);
            iv.setImageResource(blank);
            iv.setBackgroundColor(Color.rgb(238,238,238));
            updateScore(1);
            result = true;
        }
        if(left>=0 && tileArray[left]==color){
            tileArray[left]=0;
            iv = (ImageView) gridView.getChildAt(left);
            iv.setImageResource(blank);
            iv.setBackgroundColor(Color.rgb(238,238,238));
            updateScore(1);
            result = true;
        }
        if(right>=0 && tileArray[right]==color){
            tileArray[right]=0;
            iv = (ImageView) gridView.getChildAt(right);
            iv.setImageResource(blank);
            iv.setBackgroundColor(Color.rgb(238,238,238));
            updateScore(1);
            result = true;
        }
        if(bottom>=0 && tileArray[bottom]==color){
            tileArray[bottom]=0;
            iv = (ImageView) gridView.getChildAt(bottom);
            iv.setImageResource(blank);
            iv.setBackgroundColor(Color.rgb(238,238,238));
            updateScore(1);
            result = true;
        }
        if(topLeft>=0 && tileArray[topLeft]==color){
            tileArray[topLeft]=0;
            iv = (ImageView) gridView.getChildAt(topLeft);
            iv.setImageResource(blank);
            iv.setBackgroundColor(Color.rgb(238,238,238));
            updateScore(1);
            result = true;
        }
        if(topRight>=0 && tileArray[topRight]==color){
            tileArray[topRight]=0;
            iv = (ImageView) gridView.getChildAt(topRight);
            iv.setImageResource(blank);
            iv.setBackgroundColor(Color.rgb(238,238,238));
            updateScore(1);
            result = true;
        }
        if(bottomLeft>=0 && tileArray[bottomLeft]==color){
            tileArray[bottomLeft]=0;
            iv = (ImageView) gridView.getChildAt(bottomLeft);
            iv.setImageResource(blank);
            iv.setBackgroundColor(Color.rgb(238,238,238));
            updateScore(1);
            result = true;
        }
        if(bottomRight>=0 && tileArray[bottomRight]==color){
            tileArray[bottomRight]=0;
            iv = (ImageView) gridView.getChildAt(bottomRight);
            iv.setImageResource(blank);
            iv.setBackgroundColor(Color.rgb(238,238,238));
            updateScore(1);
            result = true;
        }
        return result;
    }

    public void updateScore(int pos){
        score+=pos;
        if(score/20 > level){
            level+=1;
        }
        TextView levelView = (TextView)findViewById(R.id.level_count);
        levelView.setText(Integer.toString(level+1));
        TextView scoreView = (TextView) findViewById(R.id.score_count);
        scoreView.setText(Integer.toString(score));
    }

    public void powerUp(int color){
        ImageView iv;
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        int i = 0;
        while(i<49){
            if(tileArray[i]==color){
                tileArray[i]=0;
                iv = (ImageView) gridView.getChildAt(i);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(1);
            }
            i++;
        }
    }

    public void addRandomTiles(){
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        ImageView iv;
        boolean run;
        int random, pos, color, item;
        for (int i=0; i<=level; i++){
            run = true;
            while(run){
                pos = (int) (Math.random()*49);
                if(tileArray[pos]==0){
                    random = (int) (Math.random()*3+1);
                    if(random == 1){
                        item = red;
                        color = Color.rgb(235,193,55);
                    }else if (random==2){
                        item = green;
                        color = Color.rgb(107,154,51);
                    }else{
                        item = blue;
                        color = Color.rgb(0,143,213);
                    }
                    iv = (ImageView) gridView.getChildAt(pos);
                    iv.setImageResource(item);
                    iv.setBackgroundColor(color);
                    tileArray[pos] = item;
                    run = false;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
