package com.andlacerda.appfitness

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(), OnItemClickListener {
    private lateinit var rvMain: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val mainItems = mutableListOf<MainItem>()
        mainItems.add(
            MainItem(id = 1,
                drawableId = R.drawable.baseline_elevator_24,
                textStringId = R.string.label_imc,
                color = Color.GREEN
            )
        )
        mainItems.add(
            MainItem(id = 2,
                drawableId = R.drawable.baseline_remove_red_eye_24,
                textStringId = R.string.tmb,
                color = Color.YELLOW
            )
        )

        val adapter = MainAdapter(mainItems, this)
        rvMain = findViewById(R.id.rv_main)
        rvMain.adapter = adapter
        rvMain.layoutManager = GridLayoutManager(this, 2)
    }

    override fun onClick(id : Int) {
        when(id) {
            1 -> {
                val intent = Intent(this, imcActivity::class.java)
                startActivity(intent)
            }
            2 -> {

            }
        }
        //Toast.makeText(this,"Clicou $id",Toast.LENGTH_SHORT).show()
    }

    private inner class MainAdapter(
        private val mainItems : List<MainItem>,
        private val onItemClickListener : OnItemClickListener) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
        // 1 - Qual é o layout XML da célula especifica ( item )
        override fun onCreateViewHolder( parent: ViewGroup, viewType: Int): MainViewHolder {
            val view = layoutInflater.inflate(R.layout.main_item, parent, false)
            val viewHolder = MainViewHolder(view)
            return viewHolder
        }

        // 2 - disparado toda vez que houver uma rolagem na tela e for necessario trocar o conteudo da celula
        override fun onBindViewHolder( holder: MainViewHolder, position: Int) {
            val itemCurrent = mainItems[position]
            holder.bind(itemCurrent)
        }

        // 3 - informar quantas celulas essa listagem terá
        override fun getItemCount(): Int {
            return mainItems.size
        }

        private inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            fun bind(item: MainItem){
                val img: ImageView = itemView.findViewById(R.id.item_img_icon)
                val name: TextView = itemView.findViewById(R.id.item_txt_name)
                val container: LinearLayout = itemView.findViewById(R.id.item_container_imc)

                img.setImageResource(item.drawableId)
                name.setText(item.textStringId)
                container.setBackgroundColor(item.color)

                container.setOnClickListener {
                    onItemClickListener.onClick(item.id)
                }
            }
        }
    }

    // É a classe da célula em si!

}