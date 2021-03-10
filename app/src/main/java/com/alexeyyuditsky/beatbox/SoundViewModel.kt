package com.alexeyyuditsky.beatbox

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.alexeyyuditsky.beatbox.MainActivity.Companion.rate

// Класс модели представления
class SoundViewModel(private val beatBox: BeatBox) : BaseObservable() { // BaseObservable() - Этот класс, который реализует интерфейс Observable, который позволяет классу привязки(ListItemSoundBinding) установить слушателей для модели представления(SoundViewModel), чтобы он мог автоматически получать обратные вызовы при изменении полей

    // Свойство для хранения объекта Sound(String) с полем name в котором хранится название звука
    var sound: Sound? = null
        set(sound) {
            field = sound
            notifyChange() // Функция вызывается при каждом изменении значения свойства и оповещает класс привязки(ListItemSoundBinding) о том, что все Bindable-свойства этого экземпляра были обновлены. Класс привязки(ListItemSoundBinding) выполняет код внутри скобок {} для повторного заполнения представления. Таким образом, при установке значения звука объект ListItemSoundBinding получит уведомление и вызовет Button.setText(String), как указано в файле list_item_sound.xml
        }

    // Свойство для получения названия, которое отображается на кнопке
    @get:Bindable // Аннотация, которая говорит, что данное поле является наблюдаемым и генерирует класс BR внутри которого есть свойство с именем свойства которое аннотируется
    val title: String?
        get() = sound?.name


    // Функция используется для воспроизведения звука по нажатию на кнопку
    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it, rate)
        }
    }
}
