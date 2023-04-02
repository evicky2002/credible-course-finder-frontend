package com.example.credible_course_recommender.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.credible_course_recommender.R
import com.example.credible_course_recommender.models.Course

class CourseAdapter: RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    inner class CourseViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Course>(){
        override fun areItemsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem._id == newItem._id
        }

        override fun areContentsTheSame(oldItem: Course, newItem: Course): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        return CourseViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_course_preview,
            parent,false)
        )
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = differ.currentList[position]
        val tvCourseTitle: TextView = holder.itemView.findViewById(R.id.tvCourseTitle)
        val ivCourseImage: ImageView = holder.itemView.findViewById(R.id.ivCourseImage)
        tvCourseTitle.text =  course.name

        holder.itemView.apply {
            Glide.with(this)
                .load(course.imageUrl) // add your image url
                .into(ivCourseImage)
            setOnClickListener{
                onItemClickListener?.let {
                    it(course)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }
    private var onItemClickListener: ((Course)-> Unit)?=null

    fun setOnItemClickListener(listener: (Course)-> Unit){
        onItemClickListener = listener
    }

}