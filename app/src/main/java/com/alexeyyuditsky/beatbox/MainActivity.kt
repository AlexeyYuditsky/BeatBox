package com.alexeyyuditsky.beatbox

import android.os.Bundle
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alexeyyuditsky.beatbox.databinding.ActivityMainBinding
import com.alexeyyuditsky.beatbox.databinding.ListItemSoundBinding


class MainActivity : AppCompatActivity() {

    private lateinit var beatBox: BeatBox
    private lateinit var seekBar: SeekBar

    private val beatBoxViewModelFactory: BeatBoxViewModelFactory by lazy { BeatBoxViewModelFactory(assets) } // Инициализация свойства объектом фабрики
    private val beatBoxViewModel: BeatBoxViewModel by lazy { ViewModelProvider(this, beatBoxViewModelFactory).get(BeatBoxViewModel::class.java) } // Инициализация свойства объектом BeatBoxViewModel созданным с помощью фабрики

    // статическая переменная которая хранит скорость воспроизведения звука
    companion object {
        var rate = 1.0f
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        beatBox = beatBoxViewModel.beatBox // Инициализация свойства beatBox класса MainActivity свойством из класса BeatBoxViewModel

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(context, 3)
            adapter = SoundAdapter(beatBox.sounds)
        }


        // SeekBar
        seekBar = findViewById(R.id.seek_bar)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            // Функция которая уведомляет об изменении уровня прогресса в SeekBar
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                rate = when (progress) {
                    0 -> 1.0f
                    1 -> 1.5f
                    else -> 2.0f
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {} // Функция уведомляет о том, что пользователь начал перемещать ползунок
            override fun onStopTrackingTouch(seekBar: SeekBar?) {} // Функция уведомляет о том, что пользователь закончил перемещать ползунок
        })
    }

    private inner class SoundHolder(private val binding: ListItemSoundBinding) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.viewModel = SoundViewModel(beatBox)
        }

        fun bind(sound: Sound) {
            binding.apply {
                viewModel?.sound = sound
                executePendingBindings()
            }
        }
    }

    private inner class SoundAdapter(private val sounds: List<Sound>) : RecyclerView.Adapter<SoundHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SoundHolder {
            val binding = DataBindingUtil.inflate<ListItemSoundBinding>(layoutInflater, R.layout.list_item_sound, parent, false)

            return SoundHolder(binding)
        }

        override fun onBindViewHolder(holder: SoundHolder, position: Int) {
            val sound = sounds[position]
            holder.bind(sound)
        }

        override fun getItemCount() = sounds.size
    }
}