package com.example.student_academic_details;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends ActionBarActivity implements OnClickListener {
	Button bts,bex,bta,btssnd;
	Intent i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize_compo();
    }
    private void initialize_compo() 
    {
		bts = (Button) findViewById(R.id.btnst);
		bts.setOnClickListener(this);
		bex = (Button) findViewById(R.id.exit);
		bex.setOnClickListener(this);
		bta = (Button) findViewById(R.id.acad);
		bta.setOnClickListener(this);
		btssnd = (Button) findViewById(R.id.send_sms);
		btssnd.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) 
	{
		if(v==bts)
		{
			i = new Intent(MainActivity.this,SecondActivity.class);
			startActivity(i);
		}
		if(v==bta)
		{
			i = new Intent(MainActivity.this,ThirdActivity.class);
			startActivity(i);
		}
		if(v==btssnd)
		{
			i = new Intent(MainActivity.this,SendActivity.class);
			startActivity(i);
		}
		if(v==bex)
		{
			System.exit(0);
		}
	}
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
