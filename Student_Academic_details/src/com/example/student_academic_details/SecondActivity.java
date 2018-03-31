package com.example.student_academic_details;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SecondActivity extends Activity implements OnClickListener {
	int a=0;
	int flag=0;
	Button btsave;
	TextView troll,tn,tmob,title;
	EditText eroll,en,emob;
	Intent i;
	String rollno,name,mob;
	private String tempr;
	SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        db=openOrCreateDatabase("Student", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS details(rollno TEXT,name TEXT,phone TEXT);");
        init_Compo();
    }

    private void init_Compo()
    {
		troll = (TextView) findViewById(R.id.txtenroll);
		tn = (TextView) findViewById(R.id.txtname);
		tmob = (TextView) findViewById(R.id.txtphone);
		title = (TextView) findViewById(R.id.det);
		title.setPaintFlags(title.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
		eroll = (EditText) findViewById(R.id.edenroll);
	    en = (EditText) findViewById(R.id.edname);
	    emob = (EditText) findViewById(R.id.edphone);
	    btsave = (Button)findViewById(R.id.btnsave);
	    btsave.setOnClickListener(this);
    }
    @Override
	public void onClick(View v) 
    {
		/*if(v==btnx)
		{
			if(flag==1){
			i= new Intent(SecondActivity.this,ThirdActivity.class);
			i.putExtra("phon", emob.getText().toString());
			i.putExtra("rollno", eroll.getText().toString());
			startActivity(i);
			clear();}
			else
			{
				showMessage("Error", "please Kindly provide all information");
			}
		}*/
		if(v==btsave)
		{
			
			if(eroll.getText().toString().trim().length()==0||en.getText().toString().trim().length()==0||emob.getText().toString().trim().length()==0)
		    {
		    	showMessage("Error", "Please enter all values");
		    	
		    	return;
		    }
			else{
				flag=1;
				getdata();
				a = Validate_rollno();
				if(a==0){
		    db.execSQL("INSERT INTO details VALUES('"+eroll.getText()+"','"+en.getText()+"','"+emob.getText()+"');");
		    ToastDis("Data added Successfully");
		    clear();}
		   
			}
		}
		
	}
    public void ToastDis(String msg)
	{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
    public void clear()
    {
    	eroll.setText("");
    	en.setText("");
    	emob.setText("");
    }
    
    public void getdata()
    {
    	rollno = eroll.getText().toString();
    	name = en.getText().toString();
        mob = emob.getText().toString();
    }
	private int Validate_rollno() 
	{
		
		tempr = rollno;
		int lenght = tempr.length();
		String s1 = tempr.substring(0,7);
		//ToastDis(s1);
		String no = tempr.substring(7,11);
		//ToastDis(no);
		if(lenght!=11)
		{
			showMessage("Error", "Length Should be proper");
			return 1;
		}
		if(!s1.equals("CSU17S-"))
		{
		   	showMessage("Error", "Format Incorrect");
		   	return 1;
		}
		if(no.length()!=4)
		{
			showMessage("Error", "Format Incorrect. Plaese Check Numerics");
			return 1;
		}
		return 0;
		
	}

	public void showMessage(String title,String message)
    {
    	Builder builder=new Builder(this);
    	builder.setCancelable(true);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.show();
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
