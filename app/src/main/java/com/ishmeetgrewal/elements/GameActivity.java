package com.ishmeetgrewal.elements;

import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class GameActivity extends Activity {


    int nextItem;
    int nextColor;
    ImageView nextItemView;
    boolean newGame = true;
    int[] hasItemArray = new int[49];
    int red = R.drawable.icon_fire;
    int green = R.drawable.icon_grass;
    int blue = R.drawable.icon_water;
    int blank = R.drawable.icon_blank;
    int score = 0;

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
                if(newGame){
                    newGame = false;
                    hasItemArray[position]= nextItem;
                    imageView.setImageResource(nextItem);
                    imageView.setBackgroundColor(nextColor);
                    checkNeighbors(position,nextItem);
                    nextItemView = (ImageView) findViewById(R.id.next_item_view);
                    nextItemView.setImageResource(getNextItem());
                    nextItemView.setBackgroundColor(nextColor);
                    updateScore(1);
                }
                else if(hasItemArray[position]==0 && checkAdjacent(position)){
                    hasItemArray[position]= nextItem;
                    imageView.setImageResource(nextItem);
                    imageView.setBackgroundColor(nextColor);
                    checkNeighbors(position,nextItem);
                    nextItemView = (ImageView) findViewById(R.id.next_item_view);
                    nextItemView.setImageResource(getNextItem());
                    nextItemView.setBackgroundColor(nextColor);
                    updateScore(1);
                }else{
                    Toast.makeText(getApplicationContext(), "Not possible!", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public int getNextItem(){
        int random = (int) (Math.random() * 3 + 1);
        int item, color;
        if(random == 1){
            item = R.drawable.icon_fire;
            color = Color.rgb(235,193,55);
        }else if (random==2){
            item = R.drawable.icon_grass;
            color = Color.rgb(107,154,51);
        }else{
            item = R.drawable.icon_water;
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

        if(top>=0 && hasItemArray[top]!=0) {
            result = true;
        }
        else if(left>=0 && hasItemArray[left]!=0){
            result = true;
        }
        else if(right>=0 && hasItemArray[right]!=0){
            result = true;
        }
        else if(bottom>=0 && hasItemArray[bottom]!=0){
            result = true;
        }
        return result;
    }

    public void checkNeighbors(int pos, int currentItem){
        //Determine Neighbors
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

        GridView gridView = (GridView) findViewById(R.id.grid_view);
        //Analysis
        if(currentItem == red){
            if(top>=0 && hasItemArray[top]==green){
                hasItemArray[top]=0;
                ImageView iv = (ImageView) gridView.getChildAt(top);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
            if(left>=0 && hasItemArray[left]==green){
                hasItemArray[left]=0;
                ImageView iv = (ImageView) gridView.getChildAt(left);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
            if(right>=0 && hasItemArray[right]==green){
                hasItemArray[right]=0;
                ImageView iv = (ImageView) gridView.getChildAt(right);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
            if(bottom>=0 && hasItemArray[bottom]==green){
                hasItemArray[bottom]=0;
                ImageView iv = (ImageView) gridView.getChildAt(bottom);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
        }else if (currentItem == blue){
            if(top>=0 && hasItemArray[top]==red){
                hasItemArray[top]=0;
                ImageView iv = (ImageView) gridView.getChildAt(top);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
            if(left>=0 && hasItemArray[left]==red){
                hasItemArray[left]=0;
                ImageView iv = (ImageView) gridView.getChildAt(left);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
            if(right>=0 && hasItemArray[right]==red){
                hasItemArray[right]=0;
                ImageView iv = (ImageView) gridView.getChildAt(right);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
            if(bottom>=0 && hasItemArray[bottom]==red){
                hasItemArray[bottom]=0;
                ImageView iv = (ImageView) gridView.getChildAt(bottom);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
        }else{
            if(top>=0 && hasItemArray[top]==blue){
                hasItemArray[top]=0;
                ImageView iv = (ImageView) gridView.getChildAt(top);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
            if(left>=0 && hasItemArray[left]==blue){
                hasItemArray[left]=0;
                ImageView iv = (ImageView) gridView.getChildAt(left);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
            if(right>=0 && hasItemArray[right]==blue){
                hasItemArray[right]=0;
                ImageView iv = (ImageView) gridView.getChildAt(right);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
            if(bottom>=0 && hasItemArray[bottom]==blue){
                hasItemArray[bottom]=0;
                ImageView iv = (ImageView) gridView.getChildAt(bottom);
                iv.setImageResource(blank);
                iv.setBackgroundColor(Color.rgb(238,238,238));
                updateScore(3);
            }
        }
    }

    public void updateScore(int pos){
        score+=pos;
        TextView scoreView = (TextView) findViewById(R.id.score_count);
        scoreView.setText(Integer.toString(score));
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
