package gov.nasa;

import com.google.gson.JsonObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class NotesServlet extends HttpServlet {

    private static final String PATH = "/webapp/Notes.jsp";
    private static final String PATH2 = "/webapp/NoteReq.jsp";
    private static JsonObject note;
    private NotesRepository notesRepository = new NotesRepository(1000);


    public NotesServlet() {
    }

    public NotesServlet(NotesRepository notesRepository) {
        this.notesRepository = notesRepository;
    }

    @Override       //создание
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        PrintWriter responseWriter = resp.getWriter();
        responseWriter.append("enter your note: ");
        responseWriter.flush();
        String noteText = req.getParameter("noteText") + " ";
        String id = notesRepository.createNote(noteText);
        responseWriter.append("Your id: " + id);
        responseWriter.flush();
        resp.setStatus(201);
        req.setAttribute("noteitself", noteText);
        req.getRequestDispatcher(PATH2).forward(req, resp);
    }

    @Override       //запрос заметки
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter responseWriter = resp.getWriter();
        responseWriter.append("enter your note ID: ");
        responseWriter.flush();
        try {
            final String id = req.getParameter("id");
            req.setAttribute("noteid", id);
            note = notesRepository.getNote(id);
            req.setAttribute("noteitself", note.get("Note").toString());
            req.setAttribute("date", note.get("Date").toString());
            resp.setStatus(200);
            req.getRequestDispatcher(PATH).forward(req, resp);
        } catch (Throwable error) {
            resp.setStatus(404);
        }
        note = null;

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseWriter = resp.getWriter();
        responseWriter.append("enter your note ID: ");
        responseWriter.flush();
        try {
            final String currId = req.getParameter("id");
            String newText = req.getParameter("text");
            JsonObject editnote = new JsonObject();
            editnote.addProperty("Note", newText);
            editnote.addProperty("Date", System.currentTimeMillis());
            editnote.addProperty("ID", currId);
            notesRepository.editNote(editnote, currId);
            resp.setStatus(200);
        } catch (Throwable error) {
            resp.setStatus(404);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter responseWriter = resp.getWriter();
        responseWriter.append("enter note id to delete");
        responseWriter.flush();
        try {
            final String currId = req.getParameter("id");
            notesRepository.deleteNote(currId);
            resp.setStatus(200);
        } catch (Throwable error) {
            resp.setStatus(404);
        }
    }
}
