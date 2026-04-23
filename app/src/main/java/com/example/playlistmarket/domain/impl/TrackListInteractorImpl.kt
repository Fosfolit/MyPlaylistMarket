package com.example.playlistmarket.domain.lmpl

import com.example.playlistmarket.domain.DataMusic
import com.example.playlistmarket.domain.TrackList

import com.example.playlistmarket.domain.api.interactor.TrackListInteractor
import com.example.playlistmarket.domain.api.repository.TrackListRepository
import java.util.LinkedList

class TrackListInteractorImpl (
    private val repository: TrackListRepository,
    private val maxListSize: Int
): TrackListInteractor {

    override fun addItem(track: DataMusic) {
        val t = Thread {
            val trackList : TrackList = repository.loadListTrack()
            trackList.list.remove(track)

            if (trackList.list.size >= maxListSize) {
                trackList.list.removeLast()
            }

            trackList.list.push(track)
            saveListTrack(trackList)
        }
        t.start()


    }

    override fun saveListTrack(list: TrackList) {
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

    override fun clearListTrack(){
        val t = Thread {
            repository.saveListTrack(TrackList(LinkedList<DataMusic>()))
        }
        t.start()
    }


}