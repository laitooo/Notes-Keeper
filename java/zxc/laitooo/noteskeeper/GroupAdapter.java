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
 * Created by userr on 1/2/2019.
 */

public class GroupsAdapter  extends RecyclerView.Adapter<GroupsAdapter.GroupsHolder>  {

    private ArrayList<Group> list;
    private Context context;

    private String TAG_TITLE = "groupname";
    private String TAG_LINK = "link_group";
    private String TAG_ADMIN = "id_admin";
    private String TAG_ID = "id_group";

    public GroupsAdapter(ArrayList<Group> l,Context m){
        this.list = l;
        this.context = m;
    }

    @Override
    public GroupsAdapter.GroupsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.group,parent,false);
        return new GroupsAdapter.GroupsHolder(v);
    }

    @Override
    public void onBindViewHolder(final GroupsAdapter.GroupsHolder holder, final int position) {
        //holder.layout.
        holder.title.setText(list.get(position).getTitle());
        //holder.link.setText(list.get(position).getLink());
        if (list.get(position).isAdmin()) {
            holder.id.setText("admin "+list.get(position).getId());
        }else {
            holder.id.setText(list.get(position).getId());
        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context,SelectGroupActivity.class);
                i.putExtra(TAG_ID,list.get(position).getId());
                i.putExtra(TAG_TITLE,list.get(position).getTitle());
                i.putExtra(TAG_ADMIN,list.get(position).getAdmin_Id());
                i.putExtra(TAG_LINK,list.get(position).getLink());
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

    public class GroupsHolder extends RecyclerView.ViewHolder {
        RelativeLayout layout;
        TextView title;
        //TextView link;
        TextView id;

        public GroupsHolder(View itemView) {
            super(itemView);
            layout = (RelativeLayout)itemView.findViewById(R.id.group_rv);
            title = (TextView)itemView.findViewById(R.id.group_ti);
            //link = (TextView)itemView.findViewById(R.id.group_li);
            id = (TextView)itemView.findViewById(R.id.group_id);
        }
    }
}
