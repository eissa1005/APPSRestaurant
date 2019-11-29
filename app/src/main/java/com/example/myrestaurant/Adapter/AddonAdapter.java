package com.example.myrestaurant.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myrestaurant.Common.Common;
import com.example.myrestaurant.Model.EventBus.AddOnEventChange;
import com.example.myrestaurant.Model.Response.Addon;
import com.example.myrestaurant.R;
import org.greenrobot.eventbus.EventBus;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class AddonAdapter extends RecyclerView.Adapter<AddonAdapter.AddonHolder> {
   Context mContext;
   List<Addon> mAddonList;

    public AddonAdapter(Context mcontext, List<Addon> addonList) {
        this.mContext = mcontext;
        this.mAddonList = addonList;
    }

    @NonNull
    @Override
    public AddonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddonHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_addon,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddonHolder holder, int position) {
       holder.ckb_addon.setText(new StringBuilder(mAddonList.get(position).getName() + "")
               .append("( ").append(mContext.getString(R.string.money_sign))
               .append(mAddonList.get(position).getExtraPrice())
               .append(")"));

       holder.ckb_addon.setOnCheckedChangeListener(((buttonView, isChecked) -> {
           if(isChecked) {
               Common.addonList.add(mAddonList.get(position));
               EventBus.getDefault().postSticky(new AddOnEventChange(true, mAddonList.get(position)));
           }else{
                   Common.addonList.remove(mAddonList.get(position));
                   EventBus.getDefault().postSticky(new AddOnEventChange(false, mAddonList.get(position)));
               }
       }));
    }

    @Override
    public int getItemCount() {
        if(mAddonList == null) return  0;
        return mAddonList.size();
    }

    public class AddonHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ckb_addon)
        CheckBox ckb_addon;
        Unbinder unbinder;
        public AddonHolder(@NonNull View itemView) {
            super(itemView);
            unbinder=ButterKnife.bind(this, itemView);
        }
    }
}
