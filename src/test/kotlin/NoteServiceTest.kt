import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import ru.netology.CommentNotDeletedException
import ru.netology.CommentNotFoundException
import ru.netology.NoteNotFoundException
import ru.netology.NoteService

class NoteServiceTest {

    private fun addDataForTesting() {
        NoteService.add("test title", "Test body")
        NoteService.add("test title 2", "Test body 2")
        NoteService.add("test title 3", "Test body 3")
    }

    @Before
    fun `clear Notes and Comments`() {
        NoteService.clearAll()
    }

    @Test
    fun `add note positive`() {
        val note = NoteService.add("test title positive", "Test body positive")
        assertNotEquals(/* expected = */ "test title positive", /* actual = */ note.title)
    }

    @Test
    fun `add note negative`() {
        val note = NoteService.add("test title", "Test body")
        assertNotEquals(-1, note.id)
    }

    @Test
    fun `delete note positive`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId() - 1
        NoteService.delete(noteId)
    }

    @Test(expected = NoteNotFoundException::class)
    fun `delete note negative`() {
        addDataForTesting()
        NoteService.delete(99)
    }

    @Test
    fun `update note positive`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId() - 1
        NoteService.update(noteId, "test title update positive", "test body updete positive")
        val note = NoteService.getNoteById(noteId)
        assertEquals("test body updete positive", note.text)
    }

    @Test
    fun `update note negative`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId() - 1
        NoteService.update(noteId, "test title update positive", "test body updete positive")
        val note = NoteService.getNoteById(noteId)
        assertNotEquals("test", note.text)
    }

    @Test
    fun `add comment for note positive`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId()
        val comment = NoteService.addComment(noteId, "some trash")
        assertEquals("some trash", comment.text)
    }

    @Test
    fun `add comment for note negative`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId()
        val comment = NoteService.addComment(noteId, "some trash")
        assertNotEquals("good note", comment.text)
    }

    @Test
    fun `delete comment positive`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId()
        val comment = NoteService.addComment(noteId, "some trash")
        NoteService.deleteComment(noteId, comment.id)
    }

    @Test(expected = CommentNotFoundException::class)
    fun `delete comment negative`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId()
        NoteService.addComment(noteId, "some trash")
        NoteService.deleteComment(noteId, -1)
    }

    @Test
    fun `restore comment positive`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId()
        NoteService.addComment(noteId, "some trash")
        val commentsList = NoteService.getAllComents(noteId)
        val lastComment = commentsList.last { !it.isDeleted }
        NoteService.deleteComment(noteId, lastComment.id)
        NoteService.restoreComment(noteId,lastComment.id)
    }

    @Test(expected = CommentNotFoundException::class)
    fun `restore comment negative wrong ID`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId()
        val comment = NoteService.addComment(noteId, "some trash")
        val commentId = comment.id
        NoteService.deleteComment(noteId, comment.id)
        NoteService.restoreComment(noteId,commentId + 1)
    }

    @Test(expected = CommentNotDeletedException::class)
    fun `restore comment negative not deleted`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId()
        val comment = NoteService.addComment(noteId, "some trash")
        val commentId = comment.id
        NoteService.restoreComment(noteId,commentId)
    }

    @Test
    fun `edit comment positive`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId()
        val comment = NoteService.addComment(noteId, "some trash")
        val commentId = comment.id
        NoteService.editComment(noteId, commentId,"good post")
        assertEquals("good post", comment.text)
    }

    @Test
    fun `edit comment negative not equals`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId()
        val comment = NoteService.addComment(noteId, "some trash")
        val commentId = comment.id
        NoteService.editComment(noteId, commentId,"good post")
        assertNotEquals("Not good post", comment.text)
    }

    @Test(expected = CommentNotFoundException::class)
    fun `edit comment negative not found`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId()
        val comment = NoteService.addComment(noteId, "some trash")
        val commentId = comment.id
        NoteService.editComment(noteId, commentId + 10,"good post")
    }

    @Test (expected = CommentNotFoundException::class)
    fun `edit comment negative deleted`() {
        addDataForTesting()
        val noteId = NoteService.getLastNoteId()
        val comment = NoteService.addComment(noteId, "some trash")
        NoteService.deleteComment(noteId, comment.id)
        NoteService.editComment(noteId, comment.id,"good post")
    }

}