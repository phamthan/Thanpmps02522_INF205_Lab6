package edu.poly.myappasm;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    ArrayList<student> listsv=new ArrayList<student>();

    EditText edtId,edtTenSV;
    Button btn_them,btn_xoa,btn_sua;
    TextView tv,tv_sl;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "s8mDZgWG0wOnUFK9oKVeE1PMRLx4SpmOdObNekAr", "ifHbpC9Aurp5R0NhRjEknDXceE3kNkt9s7FUYoKD");


        edtId=(EditText) findViewById(R.id.editText1);
        edtTenSV=(EditText) findViewById(R.id.editText2);
        btn_them=(Button) findViewById(R.id.button1);
        lv=(ListView) findViewById(R.id.listView1);

        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  id = edtId.getText().toString();
                String ten = edtTenSV.getText().toString();
                ParseObject work = new ParseObject("CongViec");
                work.put("Id", id);
                work.put("TenCongViec", ten);
                work.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {

                        final ArrayList<String> mangCV = new ArrayList<String>();


                        //Parse , get CongViec
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("CongViec");
                        query.findInBackground(new FindCallback<ParseObject>() {
                            public void done(List<ParseObject> scoreList, ParseException e) {
                                if (e == null) {
                                    for (ParseObject cv : scoreList) {
                                        mangCV.add(cv.getString("Id"));
                                        mangCV.add(cv.getString("TenCongViec"));
                                    }
                                    ArrayAdapter adapter = new ArrayAdapter(
                                            MainActivity.this,
                                            android.R.layout.simple_list_item_1,
                                            mangCV
                                    );

                                    lv.setAdapter(adapter);
                                    Toast.makeText(MainActivity.this, "" + scoreList.size(), Toast.LENGTH_LONG).show();
                                } else {
                                    Log.d("score", "Error: " + e.getMessage());
                                }
                            }
                        });
                        Toast.makeText(
                                MainActivity.this,
                                "Them thanh cong !",
                                Toast.LENGTH_LONG
                        ).show();
                    }
                });
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
