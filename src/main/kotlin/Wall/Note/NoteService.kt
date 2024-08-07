package Wall.Note

import Wall.Exceptions.CommentNotFoundException
import Wall.Exceptions.NoteNotFoundException

data class Note(
    val id: Int = 1,
    var title: String = "TestTitle",
    var content: String = "TestContent",
    val comments: MutableList<Comment> = mutableListOf()
)

data class Comment(
    val id: Int = 1,
    val noteId: Int = 1,
    var content: String = "TestContent",
    var isDeleted: Boolean = false
)

object NoteService {
    private var notes: MutableList<Note> = mutableListOf()
    private var nextNoteId = 1
    private var nextCommentId = 1

    fun addNote(title: String, content: String): Note {
        val note = Note(nextNoteId++)
        notes.add(note)
        return note
    }

    fun getNotes(): List<Note> {
        return notes
    }

    fun getNoteById(id: Int): Note? {
        return notes.find { it.id == id }
    }

    fun deleteNote(id: Int): Boolean {
        val note = getNoteById(id) ?: throw NoteNotFoundException("Заметка не найдена")
        notes.remove(note)
        return true
    }

    fun editNote(id: Int, content: String, title: String): Boolean {
        val note = getNoteById(id) ?: throw NoteNotFoundException(title)
        title.let { note.title = title }
        content.let { note.content = content }
        return true
    }

    fun createComment(noteId: Int, content: String): Comment {
        val note = getNoteById(noteId) ?: throw NoteNotFoundException("Заметка не найдена")
        val comment = Comment(nextCommentId++, content = content)
        note.comments.add(comment)
        return comment
    }

    fun getComments(noteId: Int): List<Comment> {
        val note = getNoteById(noteId) ?: throw NoteNotFoundException("Заметка не найдена")
        return note.comments.filter { !it.isDeleted }
    }

    fun deleteComment(noteId: Int, commentId: Int): Boolean {
        val note = getNoteById(noteId) ?: throw NoteNotFoundException("Заметка не найдена")
        val comment = note.comments.find { it.id == commentId && !it.isDeleted }
            ?: throw CommentNotFoundException("Комментарий не найден")
        comment.isDeleted = true
        return true
    }

    fun editComment(noteId: Int, commentId: Int, content: String): Boolean {
        val note = getNoteById(noteId) ?: throw NoteNotFoundException("Заметка не найдена")
        val comment = note.comments.find { it.id == commentId && !it.isDeleted }
            ?: throw CommentNotFoundException("Комментарий не найден")
        comment.content = content
        return true
    }

    fun restoreComment(noteId: Int, commentId: Int): Comment {
        val note = getNoteById(noteId) ?: throw NoteNotFoundException("Заметка не найдена")
        val comment = note.comments.find { it.id == commentId}
            ?: throw CommentNotFoundException("Комментарий не найден")
        if (!comment.isDeleted) {
            throw CommentNotFoundException("Комментарий не удален")
        }
        comment.isDeleted = false
        return comment
    }

    fun clear() {
        notes = mutableListOf()
        nextNoteId = 1
        nextCommentId = 1
    }
}