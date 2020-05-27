package com.example.edittextspinner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.reginald.editspinner.EditSpinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "DemoActivity";
    private static final String PREF_KEY_NEW = "newUsers";

    EditSpinner mEditSpinner1;
    EditSpinner mEditSpinner2;
    Button btn_input;
    ArrayAdapter<String> adapter;

    SharedPreferences sharedPreferences;
    ArrayAdapter<String> arrayAdapter;
    Set<String> users;
    ArrayList<String> newUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        btn_input = findViewById(R.id.btn_input);
        btn_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addArrayList();
            }
        });

    }

    //新增帳密
    private void addArrayList() {
        //取得輸入值
        String newUser = mEditSpinner1.getText().toString();
        newUsers.add(newUser);
        if (newUsers.size() > 5) newUsers.remove(0); //如果裡面資料超過5筆,就刪除最開始的那筆
        adapter.notifyDataSetChanged();//調變器重新顯示
        sharedPreferences.edit().putString(PREF_KEY_NEW, new Gson().toJson(newUsers)).commit(); //儲存照密,將Json資料轉成String儲存
        Log.v("hank", "addArrayList:");
    }
    final static String USERS_PREF_NAME = "account1";
//    final static String PREF_KEY = "user";

//fromJson(String json, Type typeOfT):(回傳<T>資料結構)
    private void initViews() {

        //取得儲存帳密
        sharedPreferences = getSharedPreferences(USERS_PREF_NAME, MODE_PRIVATE);
        String json = sharedPreferences.getString(PREF_KEY_NEW, null);


        if (json == null) newUsers = new ArrayList<>();
        else newUsers = new Gson().fromJson(json, new TypeToken<ArrayList<String>>() { //如果有儲存帳號在裡面的話,將儲存帳號的String轉成Json結構
        }.getType());


        //調變器設定
        mEditSpinner1 = (EditSpinner) findViewById(R.id.edit_spinner_1);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, newUsers);//1.Context,2.Spinner元件,3.要呈現的資料List<>結構
        mEditSpinner1.setAdapter(adapter);

        //下拉選單關閉時
        mEditSpinner1.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Log.v("hank", "mEditSpinner1 popup window dismissed!");
            }
        });

        //選擇Item時,回傳Item的值
        mEditSpinner1.setItemConverter(new EditSpinner.ItemConverter() {
            @Override
            public String convertItemToString(Object selectedItem) {
                Log.v("hank", "convertItemToString :" + selectedItem.toString());
                return selectedItem.toString();
            }
        });


        //下拉選單打開時
        mEditSpinner1.setOnShowListener(new EditSpinner.OnShowListener() {
            @Override
            public void onShow() {
                // maybe you want to hide the soft input panel when the popup window is showing.
                hideSoftInputPanel();
                Log.v("hank", "setOnShowListener :");
            }
        });

//         other optional configurations:

//         use setEditable() to dynamically set whether it can be Edited.
//         Notice: it won't work if you set android:editable="false" in xml.
//         mEditSpinner1.setEditable(false);
//
////         set the dropdown drawable on the right of EditText and its size
//         mEditSpinner1.setDropDownDrawable(getResources().getDrawable(R.drawable.picker), 60, 60);
//
////         set the spacing bewteen Edited area and DropDown click area
//         mEditSpinner1.setDropDownDrawableSpacing(50);
//
////         set DropDown animation of the popup window
//         mEditSpinner1.setDropDownAnimationStyle(R.style.CustomPopupAnimation);
//
////         set DropDown popup window background
////         mEditSpinner1.setDropDownBackgroundResource(R.drawable.your_custom_dropdown_bkg);
//
////         set the selection
//         mEditSpinner1.selectItem(1);

//         see more in EditSpinner


//         EditSpinner 2:
//        final String[] stringArray2 = getResources().getStringArray(R.array.edits_array_2);
//        mEditSpinner2 = (EditSpinner) findViewById(R.id.edit_spinner_2);
//        mEditSpinner2.setDropDownDrawable(getResources().getDrawable(R.drawable.spinner), 25, 25);
//        mEditSpinner2.setDropDownDrawableSpacing(50);
//        mEditSpinner2.setAdapter(new BaseAdapter() {
//            @Override
//            public int getCount() {
//                return stringArray2.length;
//            }
//
//            @Override
//            public Object getItem(int position) {
//                return stringArray2[position];
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return position;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                if (convertView == null) {
//                    convertView = View.inflate(MainActivity.this, R.layout.layout_item, null);
//                }
//
//                ImageView icon = convertView.findViewById(R.id.item_icon);
//                TextView textView = convertView.findViewById(R.id.item_text);
//
//                String data = (String) getItem(position);
//
//                icon.setImageResource(R.mipmap.ic_launcher);
//                textView.setText(data);
//
//                return convertView;
//            }
//        });
//
//        // it converts the item in the list to a string shown in EditText.
//        mEditSpinner2.setItemConverter(new EditSpinner.ItemConverter() {
//            @Override
//            public String convertItemToString(Object selectedItem) {
//                if (selectedItem.toString().equals(stringArray2[stringArray2.length - 1])) {
//                    return "";
//                } else {
//                    return selectedItem.toString();
//                }
//            }
//        });
//
//        // triggered when one item in the list is clicked
//        mEditSpinner2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d(TAG, "onItemClick() position = " + position);
//                if (position == stringArray2.length - 1) {
//                    showSoftInputPanel(mEditSpinner2);
//                }
//            }
//        });
//
//        mEditSpinner2.setOnShowListener(new EditSpinner.OnShowListener() {
//            @Override
//            public void onShow() {
//                hideSoftInputPanel();
//            }
//        });
//
//        // select the first item initially
//        mEditSpinner2.selectItem(1);
//    }
//
//
//
//    private void showSoftInputPanel(View view) {
//        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//        if (imm != null) {
//            imm.showSoftInput(view, 0);
//        }
//    }

    }
    private void hideSoftInputPanel(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(mEditSpinner1.getWindowToken(), 0);
            Log.v("hank","hideSoftInputPanel");
        }
    }
}