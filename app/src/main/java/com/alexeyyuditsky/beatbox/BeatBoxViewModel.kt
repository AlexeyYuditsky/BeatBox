package com.alexeyyuditsky.beatbox

import android.content.res.AssetManager
import androidx.lifecycle.ViewModel

class BeatBoxViewModel(assetManager: AssetManager) : ViewModel() {

    var beatBox: BeatBox = BeatBox(assetManager) // свойство передаваемое в MainActivity для инициализации в нём BeatBox(assetManager)

    // Функция вызывается перед тем когда Activity окончательно закрывается и перед тем как провайдер удаляет ViewModel
    override fun onCleared() {
        beatBox.release() // Функция удаляет все загруженные звуки из SoundPool и освобождает память. После вызова этого метода экземпляр класса SoundPool уже нельзя использовать. Метод используется при выходе из программы, чтобы освободить ресурсы.
    }
}
