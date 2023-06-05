package com.example.gyg.Community
import java.io.Serializable

class Community_MyBoard(Title: String,Content: String, Writer: String, Date: String, Good: Int):Serializable {
    private var title : String
    private var content : String
    private var writer : String
    private var date: String
    private var good : Int

    init {
        this.title = Title
        this.content = Content
        this.writer = Writer
        this.date = Date
        this.good = Good
    }

    fun getTitle(): String {
        return this.title
    }

    fun getContent(): String {
        return this.content
    }

    fun getWriter(): String {
        return this.writer
    }

    fun getDate(): String {
        return this.date
    }
    fun getGood(): Int {
        return this.good
    }

    fun setTitle(title: String) {
        this.title = title
    }

    fun setContent(content: String) {
        this.content = content
    }

    fun setWriter(writer: String) {
        this.writer = writer
    }

    fun setDate(date: String) {
        this.date = date
    }

    fun setGood(good: Int) {
        this.good = good
    }

}