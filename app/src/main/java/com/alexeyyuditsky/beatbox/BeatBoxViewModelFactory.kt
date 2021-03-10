package com.alexeyyuditsky.beatbox

import android.content.res.AssetManager
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

// Класс отвечает за создание BeatBoxViewModel, если есть необходимость передать в него свои аргументы
class BeatBoxViewModelFactory(private val assetManager: AssetManager) : ViewModelProvider.Factory {

    // Функция отвеает за создание экземпляра BeatBoxViewModel с аргументами
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(AssetManager::class.java).newInstance(assetManager)
    }
}