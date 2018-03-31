package com.example.student_academic_details;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.test.suitebuilder.annotation.SmallTest;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class ThirdActivity extends Activity implements OnClickListener, OnItemSelectedListener {
    SmsManager sms;
	Button btsave;
	TextView troll,tse,tsub,tts1,tts2,ti;
	Spinner sub,sem,tst1,tst2;
	Spinner e1;
	int index_e1;
	String rollnos[];
	SQLiteDatabase db;
    String data ="";
    String i;
	String phone ;
	String[] subject,sem_ar,test1,test2;
	int index_sub,index_sem,index_tst1,index_tst2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        db=openOrCreateDatabase("Student", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS academic(roll TEXT,sem TEXT,subject TEXT,test1 TEXT,test2 TEXT);");
		/*i = getIntent().getExtras().getString("phon");
    	phone = i.toString();*/
		init_Compo();
    }

    private void init_Compo()
    {
    	ti = (TextView)findViewById(R.id.det);
    	ti.setPaintFlags(ti.getPaintFlags()| Paint.UNDERLINE_TEXT_FLAG);
		troll = (TextView) findViewById(R.id.txtenroll2);
		tse = (TextView) findViewById(R.id.txtsem);
		tsub = (TextView) findViewById(R.id.txtsubjct);
		tts1 = (TextView) findViewById(R.id.txttest1);
		tts2 = (TextView) findViewById(R.id.txttest2);
		
		e1 = (Spinner) findViewById(R.id.edroll);
		e1.setOnItemSelectedListener(this);
		sub = (Spinner) findViewById(R.id.ssub);
	    sem = (Spinner) findViewById(R.id.ssem);
	    tst1 = (Spinner) findViewById(R.id.stest1);
	    tst2 = (Spinner) findViewById(R.id.stest2);
	    
	    btsave = (Button)findViewById(R.id.btnsave2);
	    btsave.setOnClickListener(this);
	    
	    subject = getResources().getStringArray(R.array.subject);
        sem_ar  =  getResources().getStringArray(R.array.sem);
        test1 =  getResources().getStringArray(R.array.test1);
        test2 =  getResources().getStringArray(R.array.test2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,subject);
        sub.setAdapter(adapter);
        sub.setOnItemSelectedListener(this);
        sem.setAdapter( new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,sem_ar));
        sem.setOnItemSelectedListener(this);
        tst1.setAdapter( new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,test1));
        tst1.setOnItemSelectedListener(this);
        tst2.setAdapter( new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,test2));
        tst2.setOnItemSelectedListener(this);
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
        e1.setAdapter(adapter);

	}

    @Override
	public void onClick(View v) 
    {
    	/*switch(v.getId())
    	{
    		case R.id.btnsave2 :
    			dataDisplay();
    			data = "Enrollnment No: "+e1.getText()+"\nSem : "+sem_ar[index_sem]+"\nsubject : "+subject[index_sub]+"\nTest 1 : "+test1[index_tst1]+"\nTest 2 : "+test2[index_tst2];
    			showMessage("Data", data);
    			break;
    			
    		case R.id.btnexit :
    			sendSMS(phone,data);
    			 clear();
    			//showMessage("Alert","SMS Sent");
    			break;
    	}*/
    	if(v==btsave)
    	{
    		dataDisplay();
			data = "Enrollnment No: "+rollnos[index_e1]+"\nSem : "+sem_ar[index_sem]+"\nsubject : "+subject[index_sub]+"\nTest 1 : "+test1[index_tst1]+"\nTest 2 : "+test2[index_tst2];
			showMessage("Data", data);
    	}
		
	}
    
    private void sendSMS(String phone2, String data2) 
    {
	   sms = SmsManager.getDefault();
	   sms.sendTextMessage(phone2,null,data2, null, null);
		
	}

	private void dataDisplay()
    {
	    db.execSQL("INSERT INTO academic VALUES('"+rollnos[index_e1]+"','"+sem_ar[index_sem]+"','"+subject[index_sub]+"','"+test1[index_tst1]+"','"+test2[index_tst2]+"');");
	    ToastDis("Data added Successfully");
		clear();
	}

	public void clear()
    {
    	e1.setSelection(0);
       /* sub.setText("");
        sem.setText("");
        tst1.setText("");
        tst2.setText("");*/
    	sub.setSelection(0);
    	sem.setSelection(0);
    	tst1.setSelection(0);
    	tst2.setSelection(0);
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

    public void showMessage(String title,String message)
    {
    	Builder builder=new Builder(this);
    	builder.setCancelable(true);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.show();
	}
	public void ToastDis(String msg)
	{
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		switch(parent.getId())
		{
		    case R.id.edroll :
		    	index_e1 = parent.getSelectedItemPosition();
		    	break;
			case R.id.ssub :
				index_sub =  parent.getSelectedItemPosition();
				//ToastDis(subject[index_sub]);
				break;
				
			case R.id.ssem :
				index_sem = parent.getSelectedItemPosition();
				//ToastDis(sem_ar[index_sem]);
				break;
				
			case R.id.stest1:
				index_tst1 = parent.getSelectedItemPosition();
				//ToastDis(test1[index_tst1]);
				break;
				
			case R.id.stest2:
				index_tst2 = parent.getSelectedItemPosition();
				//ToastDis(test2[index_tst2]);
				break;
			
		}
		
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
}
