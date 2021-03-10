package com.alexeyyuditsky.beatbox

import org.hamcrest.core.Is.`is`
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class SoundViewModelTest {

    private lateinit var beatBox: BeatBox
    private lateinit var sound: Sound
    private lateinit var subject: SoundViewModel

    @Before // Вместо того чтобы писать одинаковый код для всех тестов, JUnit предоставляет аннотацию @Before. Код, содержащийся в функции с пометкой @Before, будет выполнен один раз перед выполнением каждого теста. По действующим соглашениям большинство классов модульных тестов содержит одну функцию setUp() с пометкой @Before
    fun setUp() {
        beatBox = mock(BeatBox::class.java) // Функция которая создаёт фиктивный объект BeatBox, нужно в неё передать класс который хотим сымитировать
        sound = Sound("assetPath")
        subject = SoundViewModel(beatBox) // Именем subject - обозначается тестируемый объект, в нашем случае SoundViewModel()
        subject.sound = sound
    }

    @Test // Тест представляет собой функцию в тестовом классе, помеченную аннотацией @Test
    fun exposesSoundNameAsTitle() {
        assertThat(subject.title, `is`(sound.name)) // Функция читается как: "Предположим, что свойство title объекта SoundViewModel будет таким же, что и свойство name объекта Sound". Если две функции возвращают разные значения, тест не пройдет
    }

    @Test
    fun callsBeatBoxPlayOnButtonClicked() {
        subject.onButtonClicked() // Вызываем функцию onButtonClicked() класса BeatBox и проверяем делает ли она то, что ей сказано: вызывает BeatBox.play(Sound)
        verify(beatBox).play(sound) // Вызов verify(beatBox) означает: «Я хочу проверить, что для beatBox была вызвана функция». Следующий вызов функции интерпретируется так: «Проверить, что эта функция был вызвана именно так». Таким образом, вызов verify(...) означает: «Проверить, что функция play(...) была вызвана для beatBox с передачей sound в качестве параметра».
    }
}