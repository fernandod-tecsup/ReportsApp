package com.diaz.reportsapp.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.diaz.reportsapp.R;
import com.diaz.reportsapp.activities.DetailActivity;
import com.diaz.reportsapp.models.Note;
import com.diaz.reportsapp.repositories.NoteRepository;
import com.github.curioustechizen.ago.RelativeTimeTextView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    private static final String TAG = NoteAdapter.class.getSimpleName();
    private List<Note> notes;


    public NoteAdapter(){
        this.notes = new ArrayList<>();
    }



    public void setNotes(List<Note> notes) {
        this.notes = notes;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView pictureImage;
        public TextView titleText;
        public TextView contentText;
        public RelativeTimeTextView dateText;
        public CheckBox favoriteStar;
        public ImageButton menuButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pictureImage = (ImageView) itemView.findViewById(R.id.picture_image);
            titleText = (TextView) itemView.findViewById(R.id.title_text);
            contentText = (TextView) itemView.findViewById(R.id.content_text);
            dateText = (RelativeTimeTextView) itemView.findViewById(R.id.date_text);
            favoriteStar = (CheckBox) itemView.findViewById(R.id.favorite_star);
            menuButton = (ImageButton) itemView.findViewById(R.id.menu_button);

        }
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int position) {

        final Note note = this.notes.get(position);
        int color = ColorGenerator.MATERIAL.getColor(note.getTitle());
        TextDrawable drawable = TextDrawable.builder().buildRect(note.getTitle().substring(0, 1), color);
        viewHolder.pictureImage.setImageDrawable(drawable);

        viewHolder.titleText.setText(note.getTitle());
        viewHolder.contentText.setText(note.getContent());
        // https://github.com/curioustechizen/android-ago
        viewHolder.dateText.setReferenceTime(note.getDate().getTime());

        viewHolder.favoriteStar.setOnCheckedChangeListener(null);

        viewHolder.favoriteStar.setChecked(note.getFavorite());
        viewHolder.favoriteStar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonButton, boolean b) {
                note.setFavorite(b);
            }
        });
        viewHolder.menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                PopupMenu popup = new PopupMenu(v.getContext(),v);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.remove_button:
                                NoteRepository.remove(note.getId());
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position,notes.size());
                                Toast.makeText(v.getContext(), "Nota eliminada correctamente", Toast.LENGTH_LONG).show();
                                break;
                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), DetailActivity.class);
                intent.putExtra("ID", note.getId());
                view.getContext().startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return this.notes.size();
    }

}
