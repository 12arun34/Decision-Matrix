package com.example.decisionmatrix;

//import static androidx.appcompat.graphics.drawable.DrawableContainerCompat.Api21Impl.getResources;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class DecisionAdapter extends RecyclerView.Adapter<DecisionAdapter.ViewHolder> {

    private static ArrayList<ArrayList<String>> decisionList;
    private static ArrayList<String> decisionNames;
    private static SharedViewModel viewModel;
    @SuppressLint("StaticFieldLeak")
    private static Context HomeActivityContext;

    public DecisionAdapter(ArrayList<ArrayList<String>> decisionList, ArrayList<String>decisionNames , Context context, SharedViewModel sharedViewModel) {

        DecisionAdapter.decisionList = decisionList;
        DecisionAdapter.decisionNames = decisionNames;
        HomeActivityContext  = context;
        viewModel = sharedViewModel;
//        decisionList.add("String 1");
//        decisionList.add("String 2");
//        decisionList.add("String 3");
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_decision, parent, false);
//        SharedViewModel viewModel;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
//        String decisionName = decisionList.get(position);
        int size = decisionList.size();
//        holder.textViewSerialNo.setText(String.valueOf(size - position));

//        if(position==0){
//            holder.textViewDecisionName.setText("Optimised Priority" +(size - position )+" (New)");
//        }
//        else{
//            holder.textViewDecisionName.setText("Optmised Priority" +(size -position ));
//        }
         if(position==0){
            holder.textViewDecisionName.setText(decisionNames.get(size-1)+" (New)");
        }
        else{
            holder.textViewDecisionName.setText(decisionNames.get(size - position -1));
        }
        // Set other views as needed
    }

    @Override
    public int getItemCount() {
        return decisionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView textViewSerialNo;
        TextView textViewDecisionName;
        ImageView imageViewMore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
//            textViewSerialNo = itemView.findViewById(R.id.textViewSerialNo);
            textViewDecisionName = itemView.findViewById(R.id.textViewDecisionName);
            imageViewMore = itemView.findViewById(R.id.imageViewMore);

            // Set OnClickListener for the image view
            imageViewMore.setOnClickListener(this);
            textViewDecisionName.setOnClickListener(this);
            // Initialize other views here
            // Add bottom margin to create a gap between rows
            ViewGroup.MarginLayoutParams layoutParams = (ViewGroup.MarginLayoutParams) itemView.getLayoutParams();
            layoutParams.bottomMargin = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.row_bottom_margin);
            int horizontalPadding = itemView.getContext().getResources().getDimensionPixelSize(R.dimen.horizontal_padding);
            layoutParams.leftMargin = horizontalPadding;
            layoutParams.rightMargin = horizontalPadding;
            itemView.setLayoutParams(layoutParams);
        }

        @Override
        public void onClick(View v) {
            // to show taken decision
            if (v.getId() == R.id.textViewDecisionName) {
                // Show custom AlertDialog
                Context context = itemView.getContext();
                View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_layout, null);

                // Get the RecyclerView from the custom layout
                RecyclerView recyclerView = dialogView.findViewById(R.id.recyclerView);
//                Drawable customBackground = HomeActivityContext.getResources().getDrawable(R.drawable.custom_edittext);

                // Set the background to the RecyclerView
//                recyclerView.setBackground(customBackground);
                // Create and set the custom adapter with the list of strings
//                List<String> stringList = Arrays.asList("String 1", "String 2", "String 3"); // Sample string list
                int size = decisionList.size();
                List<String> stringList = decisionList.get(size -1 -  getAdapterPosition() );
                ShowListAdapter adapter = new ShowListAdapter(stringList);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(adapter);
                // Set the title of the AlertDialog
                int position = getAdapterPosition() + 1; // Add 1 to match the row number (1-indexed)
                String title = decisionNames.get(size- position );
                // Create AlertDialog using custom style
                AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.CustomAlertDialogStyle)
                        .setView(dialogView)
                        .setPositiveButton("OK", null)
                        .create();

                // Set custom title TextView
                View titleView = LayoutInflater.from(context).inflate(R.layout.custom_alert_dialog_title, null);
                TextView titleTextView = titleView.findViewById(R.id.titleTextView);
                titleTextView.setText(title);
                alertDialog.setCustomTitle(titleView);
                // Get a reference to the button
                alertDialog.setOnShowListener(dialog -> {
                    // Get a reference to the button after the dialog is shown
                    Button okButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                    if (okButton != null) {
                        // Set the text color of the button
                        okButton.setTextColor(Color.BLACK);
                    }
                });
                // Show the AlertDialog after setting the custom title
                alertDialog.show();
            }


            if (v.getId() == R.id.imageViewMore) {
                Context context = itemView.getContext();
                PopupMenu popupMenu = new PopupMenu(context, imageViewMore);
                popupMenu.inflate(R.menu.popup_menu); // Inflate the menu resource file

                MenuItem renameItem = popupMenu.getMenu().findItem(R.id.action_rename);
                renameItem.setOnMenuItemClickListener(item -> {
                    // Show a dialog to allow the user to enter text
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Enter New Name");

                    // Set up the input
                    final EditText input = new EditText(context);
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                    builder.setView(input);

                    // Set up the buttons
                    builder.setPositiveButton("OK", (dialog, which) -> {
                        // Get the text entered by the user
                        String newName = input.getText().toString();

                        // Check if the new name is empty
                        if (!newName.trim().isEmpty()) {
                            // Update the second text view of the row with the new name
                            textViewDecisionName.setText(newName);
                            int size = decisionList.size();
                            int position = size - getAdapterPosition() -1;
                            if(decisionList.size() == decisionNames.size()){
                                Toast.makeText(context, "samelength", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(context, "Unequal Legnth", Toast.LENGTH_SHORT).show();
                            }
                            viewModel.updateDecisionName(HomeActivityContext,position,newName);
                        } else {
                            // Show a prompt message if the new name is empty
                            Toast.makeText(context, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                        }
                    });
                    builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());

                    builder.show();
                    return true;
                });

                MenuItem deleteItem = popupMenu.getMenu().findItem(R.id.action_delete);
                deleteItem.setOnMenuItemClickListener(item -> {
                    Toast.makeText(context, "Delete clicked", Toast.LENGTH_SHORT).show();
                    int size = decisionList.size();
                    int position = size - getAdapterPosition() -1;
                    viewModel.deleteDecision(context,position);
                    // Notify adapter that an item has been removed
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), decisionList.size());
                    return true;
                });

                popupMenu.show();
            }
        }



    }



}



