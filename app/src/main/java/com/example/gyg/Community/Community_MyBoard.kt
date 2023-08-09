package com.example.gyg.Community
import java.io.Serializable

class Community_MyBoard(Key: String, Title: String, Content: String
                        , Writer: String, Date: String, Good: Int):Serializable {

 /*   private var writerId: String
    private var id: String*/
    private var key: String
    private var title : String
    private var content : String
    private var writer : String
    private var date: String
    private var good : Int
 /*   private var likeList: ArrayList<String> // 좋아요 수
    private var imageList: ArrayList<String> // 이미지 url 링크*/

    init {
        /*this.id = Id
        this.writerId = WriterId*/
        this.key = Key
        this.title = Title
        this.content = Content
        this.writer = Writer
        this.date = Date
        this.good = Good
        /*this.likeList = LikeList
        this.imageList = ImageList*/
    }

    /*fun getId(): String{
       return this.id
    }

    fun getWriterId(): String {
        return this.writerId
    }*/
    fun getKey(): String{
        return this.key
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
   /* fun getLikeList(): ArrayList<String> {
        return this.likeList
    }

    fun getImageList() :ArrayList<String> {
        return this.imageList
    }

    fun setId(id: String) {
        this.id = id
    }
    fun setWriterId(writerId: String) {
        this.writerId = writerId
    }*/
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
   // likeList, imageList

}