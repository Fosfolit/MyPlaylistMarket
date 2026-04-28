package com.example.playlistmarket.domain.lmpl

import android.os.Handler
import com.example.playlistmarket.domain.api.interactor.MusicInteractor
import com.example.playlistmarket.domain.api.repository.MusicRepository
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException


class MusicInteractImpl (
    private val repository: MusicRepository,
    private val handler : Handler
) : MusicInteractor {
    private var isClickAllowed = true
    private var lastSearchRunnable: Runnable? = null
    override fun searchMusic(expression: String, consumer: MusicInteractor.MusicConsumer) {

        val newSearchRunnable = Runnable{
            try {
                val result = repository.searchMusic(expression)
                consumer.consume(Result.success(result))
            } catch (e: ConnectException) {
                consumer.consume(Result.failure(IOException("Не удается подключиться к серверу")))
            } catch (e: NullPointerException) {
                consumer.consume(Result.failure(IOException("Ошибка данных")))
            } catch (e: SSLHandshakeException) {
                consumer.consume(Result.failure(IOException("Ошибка безопасности соединения")))
            } catch (e: SocketTimeoutException) {
                consumer.consume(Result.failure(IOException("Сервер не отвечает. Попробуйте позже")))
            } catch (e: UnknownHostException) {
                consumer.consume(Result.failure(IOException("Неизвестное исключение серверф")))
            } catch (e: ClassCastException) {
                consumer.consume(Result.failure(IOException("Ошибка формата данных")))
            } catch (e: Exception) {
                consumer.consume(Result.failure(IOException("Неизвестная ошибка: ${e.message}")))
            }
        }
        val t = Thread {
            if (clickDebounce() || (newSearchRunnable != lastSearchRunnable)) {
                lastSearchRunnable?.let { handler.removeCallbacks(it) }
                lastSearchRunnable = newSearchRunnable
                searchDebounce(lastSearchRunnable!!)
            }
        }
        t.start()
    }
    override fun clickDebounce(consume: MusicInteractor.BoolMusicConsumer) {
        val t = Thread {
            consume.consume( clickDebounce())
        }
        t.start()
    }

    fun clickDebounce() : Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, 2000L)
        }
        return current
    }

    private fun searchDebounce(run:Runnable) {
        handler.removeCallbacks(run)
        handler.postDelayed(run, 2000L)
    }
}
