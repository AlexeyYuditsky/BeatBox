package com.alexeyyuditsky.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException
import java.io.Serializable

private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5

// Класс который отвечает за управление активами
class BeatBox(private val assets: AssetManager) : Serializable { // Класс AssetManager - используется для обращения к активам

    val sounds: List<Sound> // Свойство для хранения инициализированного списка объектов Sound(String) с полем name = 65_cjipie и т.д.

    // Класс SoundPool управляет аудиоресурсами для приложений и воспроизводит их. Конструктор Builder() используется для создания экземпляра SoundPool. Функция setMaxStreams(Int) - устанавливает максимальное количество потоков, которые могут воспроизводиться одновременно
    val soundPool = SoundPool.Builder().setMaxStreams(MAX_SOUNDS).build()

    // блок init выполняется при инициализации объекта сразу же после вызова конструктора
    init {
        sounds = loadSounds() // Инициализируем свойство sounds списком объектов Sound(String), у которых свойство name ининциализировано имёнами файлов в папке assets/sample_sounds без расширения
    }

    // Функция возвращает список объектов Sound(String), у которых свойство name ининциализировано именами файлов в папке assets/sample_sounds без расширения
    private fun loadSounds(): List<Sound> {

        // Получаем массив имен файлов, содержащихся в папке assets/sample_sounds(65_cjipie.wav, 66_indios.wav и т.д.)
        val soundNames: Array<String> = assets.list(SOUNDS_FOLDER)!! // Функция AssetManager.list(String) возвращает массив имен файлов, содержащихся в папке assets/sample_sounds. Передав в аргументе путь к папке со звуками, мы получаем информацию обо всех файлах .wav в этой папке

        val sounds = mutableListOf<Sound>() // Список хранящий все инициализированные объекты Sound(String)

        // Выполняем проход по массиву soundNames с именами файлов звуков таких как: 65_cjipie.wav, 66_indios.wav и т.д., создаём объекты Sound(String) и кладём их в список sounds
        soundNames.forEach { fileName ->
            val assetPath = "$SOUNDS_FOLDER/$fileName" // 1 проход: assetPath = sample_sounds/65_cjipie.wav; 2 проход: assetPath = sample_sounds/66_indios.wav и т.д.
            val sound = Sound(assetPath) // Создаём объект Sound(String) с полем name = 65_cjipie, передав в аргументе путь к файлу (sample_sounds/65_cjipie.wav), след проход: (sample_sounds/66_indios.wav) и т.д.

            try {
                load(sound) // Загружаем звук в SoundPool и присваиваем ID этого звука полю soundId класса Sound
                sounds.add(sound) // Добавляем объект Sound(String) с полем name = 65_cjipie в список sounds, след проход: добавляем объект Sound(String) с полем name = 66_indios в список sounds и т.д.
            } catch (ieo: IOException) {
                Log.d("SoundViewModel", "asdsad")
            }
        }

        return sounds
    }

    // Функция отвечает за загрузку звука в SoundPool() и присваение идентификатора звука в поле soundId класса Sound
    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath) // Функция openFd(String) - возвращает объект AssetFileDescriptor - который обеспечивает доступ к файлам помещенным в каталог "assets"
        val soundId = soundPool.load(afd, 1) // Функция load(AssetFileDescriptor, Int) - загружает файл в SoundPool для последующего воспроизведения и возвращает звуковой идентификатор, который можно использовать для воспроизведения или выгрузки звука
        sound.soundId = soundId // Присваиваем ID, загруженного звука в SoundPool, полю soundId класса Sound(String, Int). Теперь каждый объект Sound хранит определенный звук
    }

    // Функция отвечает за воспроизведение загруженного звука в объект Sound
    fun play(sound: Sound, rate: Float = 1.0f) {
        sound.soundId?.let {
            soundPool.play(it, 1.0f, 1.0f, 1, 0, rate) // Функция play(int, float, float, int, int, float). Параметры содержат соответственно: идентификатор звука, громкость слева, громкость справа, приоритет, признак циклического воспроизведения и скорость воспроизведения
        }
    }

    // Функция удаляет все загруженные звуки из SoundPool и освобождает память. После вызова этого метода экземпляр класса SoundPool уже нельзя использовать. Метод используется при выходе из программы, чтобы освободить ресурсы.
    fun release() {
        soundPool.release()
    }
}