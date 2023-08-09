package com.example.gyg.Community

import java.io.Serializable

class Community_MyComment(PostKey: String, ReplyKey: String, Comment: String, RepleWriter: String, Date: String, Good: Int): Serializable {
    private var postKey: String
    private var replyKey: String
    private var comment : String
    private var repleWriter : String
    private var date: String
    private var good : Int

    init {
        this.postKey = PostKey
        this.replyKey = ReplyKey
        this.comment = Comment
        this.repleWriter = RepleWriter
        this.date = Date
        this.good = Good
    }

    fun getRepleKey(): String {
        return this.replyKey
    }

    fun getCommet(): String {
        return this.comment
    }
}