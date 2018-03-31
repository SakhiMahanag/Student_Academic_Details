package com.example.student_academic_details;

import android.support.v7.app.ActionBarActivity;
import android.telephony.SmsManager;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.*;


public class SendActivity extends Activity implements OnClickListener, OnItemSelectedListener {

	String[] rollnos;
	SQLiteDatabase db;
	Button btsc;
	Spinner sp1;
	TextView tv,tv2,tvtx1,tvtx2,tv3;
	Intent i;
	String getRollNo;String phoneno;
	int index_roll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.send_xml);
        db=openOrCreateDatabase("Student", Context.MODE_PRIVATE, null);
        initialize_compo();
    }

    private void initialize_compo() 
    {
    	sp1 = (Spinner) findViewById(R.id.edrollsend);
    	sp1.setOnItemSelectedListener(this);
    	tv = (TextView)findViewById(R.id.txenroll);
    	tv2 = (TextView)findViewById(R.id.edmsg);
    	tv3 = (TextView)findViewById(R.id.edno);
    	tvtx1 = (TextView) findViewById (R.id.tx1);
    	tvtx2 = (TextView) findViewById (R.id.tx2);
		btsc = (Button)findViewById(R.id.btnsend);
		btsc.setOnClickListener(this);
		init_roll();
	}
	private void init_roll()
	{
		Cursor c=db.rawQuery("SELECT rollno FROM details", null);
		if(c.getCount()==0)
		{
			showMessage("Error", "No records found");
			return;
		}
		StringBuffer buffer=new StringBuffer();
		while(c.moveToNext())
		{
			buffer.append(c.getString(0)+"\n");
		}
		String s = buffer.toString();
		rollnos = s.split("\n");
		StringBuffer buf = new StringBuffer();
		for(int i=0;i<rollnos.length;i++)
		{
			buf.append(rollnos[i]+"\n");
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,rollnos);
        sp1.setAdapter(adapter);

	}

	@Override
	public void onClick(View v) 
	{
		if(v==btsc)
		{
			//showMessage("Title", "hjsdhgf");
			Cursor c=db.rawQuery("SELECT * FROM academic WHERE roll='"+rollnos[index_roll]+"'", null);
    		if(c.getCount()==0)
    		{
    			showMessage("Error", "No records found");
    			return;
    		}
    		StringBuffer buffer=new StringBuffer();
    		while(c.moveToNext())
    		{
    			buffer.append("Rollno: "+c.getString(0)+"\n");
    			buffer.append("Sem: "+c.getString(1)+"\n");
    			buffer.append("Subject: "+c.getString(2)+"\n");
    			buffer.append("Test 1: "+c.getString(3)+"\n");
    			buffer.append("Test 2: "+c.getString(4)+"\n");
    		}
    		//showMessage("Student Details", buffer.toString());
    		//tv2.setText(buffer.toString());
    		
    		Cursor c1=db.rawQuery("SELECT phone FROM details WHERE rollno='"+rollnos[index_roll]+"'", null);
    		if(c1.getCount()==0)
    		{
    			showMessage("Error", "No records found");
    			return;
    		}
    		StringBuffer buff=new StringBuffer();
    		while(c1.moveToNext())
    		{
    			buff.append(c1.getString(0));
    		}
    	    phoneno=buff.toString();
    	  
    		tv2.setText(buffer.toString());
    		tv3.setText(phoneno);
    		//Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
    		sendSMS(phoneno,buffer.toString());
    		
    		
		}
	}
	private void sendSMS(String phone, String data) {
		// TODO Auto-generated method stub
		SmsManager sms = SmsManager.getDefault();
		sms.sendTextMessage(phone,null,data,null,null);
	}

	public void showMessage(String title,String message)
    {
    	Builder builder = new Builder(this);
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

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		index_roll = parent.getSelectedItemPosition();
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
