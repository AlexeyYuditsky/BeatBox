package com.alexeyyuditsky.beatbox

private const val WAV = ".wav"

// Класс для хранения имени файла, и всей остальной информации, относящейся к данному звуку
class Sound(val assetPath: String, var soundId: Int? = null) { // assetPath - путь к файлу со звуком, например: "sample_sounds/65_cjipie.wav", soundId - идентификатор звука, который можно использовать для воспроизведения или выгрузки звука

    // split(String) - возвращает List<String> разделённый на элементы через разделитель "/". last() - возвращает последний элемент списка List<String>. removeSuffix(".wav") - возвращает строку с удалённой частью указанной в аргументе(если указанное в аргументе находится в конце строки иначе ничего не удаляет)
    val name = assetPath.split("/").last().removeSuffix(WAV) // на выходе получаем: Sound().name = 65_cjipie, Sound().name = 66_indios и т.д.
}