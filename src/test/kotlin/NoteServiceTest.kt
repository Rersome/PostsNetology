import Wall.Exceptions.CommentNotFoundException
import Wall.Exceptions.NoteNotFoundException
import Wall.Note.*
import Wall.Note.NoteService.getNoteById
import Wall.Note.NoteService.getNotes
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import kotlin.test.assertNotEquals
import kotlin.test.assertNull

class NoteWallServiceTest {
    @Before
    fun clearBeforeTest() {
        NoteService.clear()
    }

    @Test
    fun testAddNote(){
        val note = NoteService.addNote("TestTitle", "TestDescription")

        assertEquals(note.title, "TestTitle")
    }

    @Test
    fun testReturnNotes(){
        val note1 = NoteService.addNote("TestTitle1", "TestDescription1")
        val note2 = NoteService.addNote("TestTitle2", "TestDescription2")

        val notes = getNotes()
        assertEquals(notes.size, 2)
    }

    @Test
    fun testReturnNoteById(){
        val note = NoteService.addNote("TestTitle", "TestDescription")

        val notes = getNoteById(note.id)
        assertNotNull(notes)
    }

    @Test
    fun testDeleteNotePositive(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.deleteNote(note.id)
        val deletedNote = getNoteById(note.id)
        assertNull(deletedNote)
    }

    @Test(expected = NoteNotFoundException::class)
    fun testDeleteNoteNegative(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.deleteNote(3)
    }

    @Test(expected = NoteNotFoundException::class)
    fun testEditNoteNegative(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.editNote(3, "TestTitle", "TestDescription")
    }

    @Test
    fun testEditNotePositive(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.editNote(note.id, title = "TestTitle1", content = "TestDescription1")

        assertEquals(note.title, "TestTitle1")
    }

    @Test(expected = NoteNotFoundException::class)
    fun testCreateCommentNegative(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.createComment(3, "TestComment")
    }

    @Test
    fun testCreateCommentPositive(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        val comment = NoteService.createComment(note.id, "TestComment")

        assertEquals(comment.content, "TestComment")
    }

    @Test(expected = NoteNotFoundException::class)
    fun testGetCommentNegativeByException(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.createComment(note.id, "TestComment1")
        NoteService.createComment(note.id, "TestComment2")
        val comments = NoteService.getComments(4)
    }

    @Test
    fun testGetCommentNegative(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.createComment(note.id, "TestComment1")
        NoteService.createComment(note.id, "TestComment2")
        val comments = NoteService.getComments(note.id)

        assertNotEquals(3, comments.size)
    }

    @Test
    fun testGetCommentPositive(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.createComment(note.id, "TestComment1")
        NoteService.createComment(note.id, "TestComment2")
        val comments = NoteService.getComments(note.id)

        assertEquals(2, comments.size)
    }

    @Test(expected = NoteNotFoundException::class)
    fun testDeleteCommentNegativeByNoteException(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.createComment(note.id, "TestComment1")
        NoteService.deleteComment(3, 2)
    }

    @Test(expected = CommentNotFoundException::class)
    fun testDeleteCommentNegativeByCommentException(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.createComment(note.id, "TestComment1")
        NoteService.deleteComment(1, 2)
    }

    @Test
    fun testDeleteCommentPositive(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        val comment = NoteService.createComment(note.id, "TestComment1")

        assertEquals(NoteService.deleteComment(note.id, comment.id), true)
    }

    @Test(expected = NoteNotFoundException::class)
    fun testEditCommentNegativeByNoteException(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.createComment(note.id, "TestComment1")
        NoteService.editComment(3, 2, "TestComment2")
    }

    @Test(expected = CommentNotFoundException::class)
    fun testEditCommentNegativeByCommentException(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.createComment(note.id, "TestComment1")
        NoteService.editComment(1, 2, "TestComment2")
    }

    @Test
    fun testEditCommentPositive(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        val comment = NoteService.createComment(note.id, "TestComment1")

        assertEquals(NoteService.editComment(note.id, comment.id, "TestComment2"), true)
    }

    @Test(expected = NoteNotFoundException::class)
    fun testRestoreCommentNegativeByNoteException(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.createComment(note.id, "TestComment1")
        NoteService.restoreComment(3, 2)
    }

    @Test(expected = CommentNotFoundException::class)
    fun testRestoreCommentNegativeByCommentException(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        NoteService.createComment(note.id, "TestComment1")
        NoteService.restoreComment(1, 2)
    }

    @Test
    fun testRestoreCommentPositive(){
        val note = NoteService.addNote("TestTitle", "TestDescription")
        val comment = NoteService.createComment(note.id, "TestComment1")
        NoteService.deleteComment(note.id, comment.id)
        val restoredComment = NoteService.restoreComment(note.id, comment.id)

        assertEquals(restoredComment.isDeleted, false)
    }
}