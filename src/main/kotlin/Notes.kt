package ru.netology

data class Note (
    val id: Int,
    var title: String,
    var text: String,
    var isDeleted: Boolean = false,
    val comments: MutableList<CommentNote> = mutableListOf()
)

data class CommentNote(
    val id: Int,
    var text: String,
    var isDeleted: Boolean = false
)

class NoteNotFoundException(message: String): RuntimeException(message)
class CommentNotFoundException(message: String): RuntimeException(message)
class CommentNotDeletedException(message: String): RuntimeException(message)

object NoteService {
    private val notes = mutableListOf<Note>()
    private var nextNoteId = 1
    private var nextCommentId = 1

    fun add(title: String, text: String ): Note {
        val note = Note(nextNoteId, title, text)
        notes.add(note)
        nextNoteId += 1
        return note
    }

    fun getNoteById(noteId: Int): Note {
        return notes.find { it.id == noteId } ?: throw NoteNotFoundException("Note with id $noteId not found or deleted")
    }

    fun delete(idNote: Int) {
        val note = getNoteById(idNote)
        note.isDeleted = true
        note.comments.forEach { it.isDeleted = true }
    }

    fun update(noteId: Int, title: String, text: String) {
        val note = getNoteById(noteId)
        note.title = title
        note.text = text
    }

    fun addComment(noteId: Int, commentText: String): CommentNote {
        val note = getNoteById(noteId)
        val comment = CommentNote(nextCommentId, commentText)
        note.comments.add(comment)
        nextCommentId = nextCommentId++
        return comment
    }

    fun deleteComment(noteId: Int, commentId: Int) {
        val comment = getComment(noteId, commentId)
        comment.isDeleted = true
    }

   fun restoreComment(noteId: Int, commentId: Int) {
       val comment = getComment(noteId, commentId)
       if(!comment.isDeleted) throw CommentNotDeletedException("Comment with commentID $commentId not deleted")
       comment.isDeleted = false
   }

    private fun getComment(noteId: Int, commentId: Int): CommentNote {
        val note = getNoteById(noteId)
        return note.comments.find { it.id == commentId } ?: throw CommentNotFoundException("Comment with id $commentId not found")
    }

    fun editComment(noteId: Int, commentId: Int, text: String) {
        val comment = getComment(noteId, commentId)
        if(comment.isDeleted) throw CommentNotFoundException("Comment with id $commentId is deleted")
        comment.text = text
    }

    fun getAllNotes(): List<Note> {
        return notes.filter { !it.isDeleted }
    }

    fun getAllComents(noteId: Int): List<CommentNote> {
        val note = getNoteById(noteId)
        return note.comments.filter { !it.isDeleted }
    }

    fun clearAll() {
        notes.clear()
        nextNoteId = 1
        nextCommentId = 1
    }

    fun getLastNoteId(): Int {
        return nextNoteId - 1
    }

}