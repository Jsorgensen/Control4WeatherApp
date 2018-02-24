package jsorgensen.com.control4weatherapp.Views;


import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import jsorgensen.com.control4weatherapp.R;

public class FilterSpinner<T> extends ConstraintLayout{

    FilterSpinner(Context context){
        super(context);

        LayoutInflater.from(context).inflate(R.layout.filter_spinner, this);

        editText = findViewById(R.id.filterSpinnerEditText);
        button = findViewById(R.id.filterSpinnerButton);
        listView = new ListView(context);


        listView.setBackgroundColor(Color.WHITE);

        button.setOnClickListener(
            new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (popupWindow != null && popupWindow.isShowing()) {
                        hideListView();
                    } else {
                        showListView();
                    }
                }
            }
        );

        adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1, focusedItems);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(
            new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    selectedItem = focusedItems.get(position);

                    hideListView();
                }
            }
        );

        editText.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        focusedItems.clear();
                        focusedItems.addAll(filterItems());
                        showListView();

                        for(T t: items){
                            if(t.toString().toLowerCase().equals(getText().toLowerCase())){
                                if(selectedItem != t)
                                    selectedItem = t;
                                if(focusedItems.size() == 1)
                                    hideListView();
                                return;
                            }
                        }

                        selectedItem = null;
                        adapter.notifyDataSetChanged();
                    }
                    @Override
                    public void afterTextChanged(Editable s) {}
                }
        );
    }

    EditText editText;
    Button button;
    ListView listView;
    private PopupWindow popupWindow;
    private ArrayAdapter<T> adapter;
    private T selectedItem;
    public T getSelectedItem(){ return selectedItem; }
    public void setSelectedItem(T value) {
        selectedItem = value;
        if (value != null && !getText().equals(value.toString())){
            setText(value.toString());
            editText.setSelection(getText().length());
        }
    }
    public String getText(){ return editText.getText().toString(); }
    public void setText(String text){ editText.setText(text); }
    private ArrayList<T> items = new ArrayList<>();
    public void setItems(ArrayList<T> newItems){
        items.clear();
        items.addAll(newItems);
        focusedItems.clear();
        focusedItems.addAll(filterItems());
        adapter.notifyDataSetChanged();
    }
    private ArrayList<T> focusedItems = new ArrayList<>();

    private ArrayList<T> filterItems(){
        ArrayList<T> filteredItems = new ArrayList<>();

        for(T item : items){
            if(item.toString().toLowerCase().contains(getText().toLowerCase()))
                filteredItems.add(item);
        }
        Collections.sort(filteredItems, new ItemComparator());

        return filteredItems;
    }

    private class ItemComparator implements Comparator<T>{
        @Override
        public int compare(T o1, T o2) {
            return o1.toString().toLowerCase().compareTo(o2.toString().toLowerCase());
        }
    }

    private void showListView(){
        if(popupWindow == null)
            createPopupWindow();

        if(focusedItems.size() == 0)
            return;

        popupWindow.showAsDropDown(this);
    }

    private void hideListView(){
        popupWindow.dismiss();
    }

    private void createPopupWindow(){
        if(getWidth() == 0)
            return;

        popupWindow = new PopupWindow(listView, getWidth(), FrameLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setFocusable(false);
        popupWindow.setOutsideTouchable(false);
    }
}