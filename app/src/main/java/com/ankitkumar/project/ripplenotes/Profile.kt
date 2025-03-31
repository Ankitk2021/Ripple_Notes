package com.ankitkumar.project.ripplenotes

import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat


class Profile(val bundle: Bundle?) : Fragment(R.layout.fragment_profile) {
    private val data: Bundle? = bundle
    private lateinit var user_name: String
    private lateinit var user_email: String



    //    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        view.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.app_name_color))
//        super.onViewCreated(view, savedInstanceState)
//    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        user_name = bundle?.getString("user_name","user-xyz-123").toString()
        user_email = bundle?.getString("user_email","user-123@OpenAi.com").toString()


        val closeBtn: ImageButton = view.findViewById(R.id.closeBtn_profile)


        closeBtn.setOnTouchListener(View.OnTouchListener { view, motionEvent ->
            Toast.makeText(view.context, "clicked", Toast.LENGTH_SHORT).show()
            //hide fragment
            requireActivity().supportFragmentManager.popBackStack()

            false
        })

        var list = view.findViewById<ListView>(R.id.list_profile)

        var items = mutableListOf<String>()
        items.add("Log out")
        items.add("Security")
        items.add("About")


        val adap = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, items)
        list.adapter = adap

        list.setOnItemClickListener { adapterView, view, i, l ->
            when (i) {
                0 -> {
                    startActivity(Intent(view.context, log_signup::class.java))
                }

                1 -> {
                    //privacy policy
                    startActivity(Intent(view.context, Security::class.java))


                }

                2 -> {
                    //open my github.
                    val url = "https://www.github.com/Ankitk2021";
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    startActivity(intent)
                }
            }
        }


        val owner_name = view.findViewById<TextView>(R.id.owner_name)
        owner_name.text = user_name

        val paint = owner_name.paint
        val width = paint.measureText(owner_name.text.toString())
        val colors = intArrayOf(
            0xFF4285F4.toInt(),
            0xFF34A853.toInt(),
            0xFFFBBC05.toInt()
        )

        val animator = ValueAnimator.ofFloat(0f, width)
        animator.duration = 9000
        animator.repeatCount = ValueAnimator.INFINITE
        animator.repeatMode = ValueAnimator.REVERSE
        animator.addUpdateListener { animation ->
            val animationValue = animation.animatedValue as Float
            val shader2 = LinearGradient(
                animationValue,
                0f,
                animationValue + width,
                owner_name.textSize,
                colors,
                null,
                Shader.TileMode.CLAMP
            )
            owner_name.paint.shader = shader2
            owner_name.invalidate()

        }
        animator.start()

        super.onViewCreated(view, savedInstanceState)
    }
}