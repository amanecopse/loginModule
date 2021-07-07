package com.amnapp.loginmodule.recyclerViews

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.amnapp.loginmodule.R
import com.amnapp.loginmodule.UserData
import kotlinx.android.synthetic.main.activity_sign_in.view.*
import kotlinx.android.synthetic.main.item_admin_page.view.*

class AdminPageRecyclerAdapter(var subUserlist: MutableList<UserData>): RecyclerView.Adapter<AdminPageRecyclerAdapter.ViewHolder>() {
    inner class ViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_admin_page, parent, false)
    ) {
        val userNameTv = itemView.userNameTv
        val militaryIdTv = itemView.militaryIdTv
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        subUserlist.get(position).let { item ->
            with(holder) {
                userNameTv.text = item.userName
                militaryIdTv.text = item.militaryId.toString()
            }
        }
    }

    override fun getItemCount(): Int {
        return subUserlist.size
    }
}