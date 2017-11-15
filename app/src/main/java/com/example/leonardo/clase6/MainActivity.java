package com.example.leonardo.clase6;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText mItemInput;
    private FloatingActionButton mAddButton;
    private ListView mDynamicListView;
    private List<String> mItemsList;
    private ArrayAdapter<String> mItemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mItemInput = (EditText) findViewById(R.id.itemEditText);
        mAddButton = (FloatingActionButton) findViewById(R.id.add_item_button);
        mDynamicListView = (ListView) findViewById(R.id.itemListView);

        // Initialize items ArrayList and add a default item,
        // so that we always see one item when app is launched
        mItemsList = new ArrayList<>();
        mItemsList.add("Android ATC");

        // Initialize ArrayAdapter that holds the mapping of,
        // Strings in ArrayList, to, textView in listView items
        mItemListAdapter = new ArrayAdapter<>(
                MainActivity.this,
                R.layout.list_individual_item,
                R.id.listItemText,
                mItemsList);

        // Setting the ArrayAdapter to the listView
        mDynamicListView.setAdapter(mItemListAdapter);

        // Add item to the listView on click floating action button (mAddButton)
        mAddButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String todoItem = mItemInput.getText().toString();
                if (todoItem.length() > 0)
                {
                    // Add editText's input to the item list
                    mItemsList.add(todoItem);

                    // Apply Changes on the ArrayAdapter to refresh the listView
                    mItemListAdapter.notifyDataSetChanged();

                    // Clear the editText
                    mItemInput.setText("");
                }
            }
        });

        // Delete mItemInput on the long click on the mItemInput
        mDynamicListView.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener()
                {
                    @Override
                    public boolean onItemLongClick(
                            AdapterView<?> parent,
                            View view,
                            int position,
                            long id)
                    {
                        // Remove the mItemInput from mItemsList
                        mItemsList.remove(position);
                        mItemListAdapter.notifyDataSetChanged();
                        return true;
                    }
                });

        mDynamicListView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                // Animate the floating action button out of view
                mAddButton.hide();

                /*
                "transitionName" is just a string that is shared between the 2 or more views,
                that are to be animated across activity transitions
                 */
                String transitionName = getString(R.string.transition_name);

                View sharedView = view.findViewById(R.id.listItemText);

                Intent intent = new Intent(MainActivity.this, DetailsActivity.class);

                /*
                 Pass in the text of the item that is clicked,
                 so that the text in Details view can be populated accordingly
                  */
                String textOfClickedItem = mItemListAdapter.getItem(position);
                intent.putExtra("Name", textOfClickedItem);

                ActivityOptions transitionActivityOptions = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, sharedView, transitionName);
                    startActivity(intent, transitionActivityOptions.toBundle());
                }else {
                    startActivity(intent);
                }


            }
        });
    }
    @Override
    protected void onResume()
    {
        super.onResume();

        /*
         Animate the floating action button into view

         NOTE - Adding the show() to the onResume() lifeCycle event of the activity ensures that,
         every time that the activity shows up on the device screen
         which could be a fresh start of the app, or when the user clicked back from the Details Activity,
         the floating action button will always animate into view
          */
        mAddButton.show();
    }
}
