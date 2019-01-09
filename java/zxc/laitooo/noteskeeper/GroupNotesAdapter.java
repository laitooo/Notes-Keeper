package zxc.laitooo.noteskeeper;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by userr on 1/5/2019.
 */

public class GroupNotesAdapter extends RecyclerView.Adapter<GroupNotesAdapter.GroupNotesHolder>  {

    private ArrayList<GroupNotes> list;
    private Context context;

    /*private String TAG_CONTENT = "note_content";
    private String TAG_ID_GROUP = "id_group";
    private String TAG_ID_USER = "id_user";
    private String TAG_DATE = "note_date";
    private String TAG_USERNAME = "username";
    private String TAG_PROFILE = "profile";
    private String TAG_ID = "id_group";


    Content = i.getStringExtra(TAG_CONTENT);
        Username = i.getStringExtra(TAG_USERNAME);
        Date = i.getStringExtra(TAG_DATE);
        Profile = i.getStringExtra(TAG_PROFILE);
        ID = i.getIntExtra(TAG_ID,0);
        User_Id = i.getIntExtra(TAG_ID_USER,0);
        Group_Id = i.getIntExtra(TAG_ID_GROUP,0);

        Toast.makeText(getApplicationContext(),"id:" + ID + " username:" + Username + " user_id:"
                + User_Id + " group_id:" + Group_Id + " content" + Content + " date:" + Date
                + " profile:"+Profile
                ,Toast.LENGTH_LONG).show();
     */

    public GroupNotesAdapter(ArrayList<GroupNotes> l,Context m){
        this.list = l;
        this.context = m;
    }

    @Override
    public GroupNotesAdapter.GroupNotesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group_note,parent,false);
        return new GroupNotesAdapter.GroupNotesHolder(v);
    }

    @Override
    public void onBindViewHolder(final GroupNotesAdapter.GroupNotesHolder holder, final int position) {
        //holder.layout.
        holder.username.setText(list.get(position).getUserName());
        holder.content.setText(list.get(position).getContent());
        holder.date.setText(list.get(position).getDate());
        //if (list.get(position).isAdmin()) {
            //holder.id.setText("admin "+list.get(position).getId());
        //}else {
            //holder.id.setText(list.get(position).getId());
        //}
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent i = new Intent(context,SelectGroupActivity.class);
                i.putExtra(TAG_ID,list.get(position).getID());
                i.putExtra(TAG_CONTENT,list.get(position).getContent());
                i.putExtra(TAG_DATE,list.get(position).getDate());
                i.putExtra(TAG_ID_GROUP,list.get(position).getId_Group());
                i.putExtra(TAG_USERNAME,list.get(position).getUserName());
                i.putExtra(TAG_PROFILE,list.get(position).getProfile());
                i.putExtra(TAG_ID_USER,list.get(position).getId_User());
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);*/
                Toast.makeText(context ,"id:" + list.get(position).getID() + " username:" +
                        list.get(position).getUserName() + " user_id:" + list.get(position).getId_User()
                        + " group_id:" + list.get(position).getId_Group() + " content" +
                        list.get(position).getContent() + " date:" + list.get(position).getDate()
                        + " profile:" + list.get(position).getProfile()
                        ,Toast.LENGTH_LONG).show();

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

    public class GroupNotesHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        ImageView profile;
        TextView username;
        TextView content;
        TextView date;

        public GroupNotesHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout)itemView.findViewById(R.id.gr_no_relative);
            profile = (ImageView) itemView.findViewById(R.id.gr_no_image);
            username = (TextView)itemView.findViewById(R.id.gr_no_username);
            content = (TextView)itemView.findViewById(R.id.gr_no_content);
            date = (TextView)itemView.findViewById(R.id.gr_no_date);
        }
    }
}
