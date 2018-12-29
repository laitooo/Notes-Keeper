package zxc.laitooo.noteskeeper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by userr on 12/22/2018.
 */

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesHolder> {


    private ArrayList<Note> list;
    private Context context;

    private String TAG_TITLE = "edit_title";
    private String TAG_CONTENT = "edit_content";
    private String TAG_ID = "id";

    public NotesAdapter(ArrayList<Note> l,Context m){
        this.list = l;
        this.context = m;
    }

    @Override
    public NotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.note,parent,false);
        return new NotesHolder(v);
    }

    @Override
    public void onBindViewHolder(final NotesHolder holder, final int position) {
        //holder.layout.
        holder.title.setText(list.get(position).getTitle());
        holder.content.setText(list.get(position).getContent());
        holder.date.setText(list.get(position).getDate());
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,EditNoteActivity.class);
                i.putExtra(TAG_ID,list.get(position).getID());
                i.putExtra(TAG_TITLE,list.get(position).getTitle());
                i.putExtra(TAG_CONTENT,list.get(position).getContent());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public int getItemCount() {
            return list.size();
        }

    public class NotesHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        TextView title;
        TextView content;
        TextView date;

        public NotesHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout)itemView.findViewById(R.id.note_rv);
            title = (TextView)itemView.findViewById(R.id.note_ti);
            content = (TextView)itemView.findViewById(R.id.note_co);
            date = (TextView)itemView.findViewById(R.id.note_da);
        }
    }
}
