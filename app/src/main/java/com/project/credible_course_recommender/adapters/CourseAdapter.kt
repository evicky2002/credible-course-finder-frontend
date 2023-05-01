package com.project.credible_course_recommender.adapters

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.project.credible_course_recommender.R
import com.project.credible_course_recommender.models.Course
import java.math.RoundingMode
import java.text.DecimalFormat


class CourseAdapter : RecyclerView.Adapter<CourseAdapter.CourseViewHolder>() {
    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Course>() {
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
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_course_preview,
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val course = differ.currentList[position]
        val tvCourseTitle: TextView = holder.itemView.findViewById(R.id.tvCourseTitle)
        val tvRatings: TextView = holder.itemView.findViewById(R.id.tvRatings)
        val ivCourseImage: ImageView = holder.itemView.findViewById(R.id.ivCourseImage)
        val ivCourseProvider: ImageView = holder.itemView.findViewById(R.id.ivCourseProvider)
        val tvAuthor: TextView = holder.itemView.findViewById(R.id.tvAuthor)
        val tvDuration: TextView = holder.itemView.findViewById(R.id.tvDuration)
        val btnDetails: MaterialCardView = holder.itemView.findViewById(R.id.btnDetails)
        val btnVisit: MaterialCardView = holder.itemView.findViewById(R.id.btnVisit)
        val tvPricing: TextView = holder.itemView.findViewById(R.id.tvPricing)

        tvCourseTitle.text = course.name
        tvAuthor.text = "Author"
        tvDuration.text = course.productDurationEnum
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.CEILING
        tvRatings.text = df.format(course.avgProductRating).toDouble().toString()


        holder.itemView.apply {
            tvAuthor.text= course.partners[0]
            if (course.isCourseFree == 0) {
                tvPricing.text = "FREE"
            } else {
                tvPricing.text = "PAID"
            }
            when (course.course_provider) {
                "Coursera" -> ivCourseProvider.setImageResource(R.drawable.coursera)
                "Udemy" -> ivCourseProvider.setImageResource(R.drawable.udemy)
                "EDX" -> ivCourseProvider.setImageResource(R.drawable.edx)

            }
            btnDetails.setOnClickListener{
                val bundle = Bundle().apply {
                    putSerializable("course",course)
                }

                findNavController(it).navigate(
                    R.id.action_searchFragment_to_courseFragment,
                    bundle
                )
            }
            btnVisit.setOnClickListener{
                it.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(course.objectUrl)))
            }



            Glide.with(this)
                .load(course.imageUrl) // add your image url
                .into(ivCourseImage)
            setOnClickListener {
                onItemClickListener?.let {
                    it(course)
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Course) -> Unit)? = null

    fun setOnItemClickListener(listener: (Course) -> Unit) {
        onItemClickListener = listener
    }

}