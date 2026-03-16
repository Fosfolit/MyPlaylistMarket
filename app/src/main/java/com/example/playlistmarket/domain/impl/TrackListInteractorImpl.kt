package com.example.playlistmarket.domain.lmpl

import com.example.playlistmarket.domain.DataMusic

import com.example.playlistmarket.domain.api.trackList.TrackListInteractor
import com.example.playlistmarket.domain.api.trackList.TrackListRepository
import java.util.LinkedList

class TrackListInteractorImpl(private val repository: TrackListRepository): TrackListInteractor {
    private val maxListSize :Int = 10
    override fun addItem(track: DataMusic) {
        val t = Thread {
            val list : LinkedList<DataMusic> = repository.loadListTrack()
            list.remove(track)

            if (list.size >= maxListSize) {
                list.removeLast()
            }

            list.push(track)
            saveListTrack(list)
        }
        t.start()


    }

    override fun saveListTrack(list: LinkedList<DataMusic>) {
        val t = Thread {
            repository.saveListTrack(list)
        }
        t.start()
    }

    override fun loadListTrack(consume: TrackListInteractor.LoadTrackList) {
        val t = Thread {
            consume.consume(repository.loadListTrack())
        }
        t.start()
    }


}