package gov.nasa;

import com.google.gson.JsonObject;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class NotesRepository {

    private Integer Capacity;
    private final ConcurrentHashMap<String, JsonObject> NotesMap = new ConcurrentHashMap<>(Capacity);

    public NotesRepository(Integer capacity) {
        Capacity = capacity;
    }

    public String createNote(String text){
            String id = UUID.randomUUID().toString();
            JsonObject currNote = new JsonObject();
            currNote.addProperty("Note", text);
            currNote.addProperty("Date", System.currentTimeMillis());
            currNote.addProperty("ID", id);
            NotesMap.put(id, currNote);
            return id;
    }

    public JsonObject getNote(String id){
        if (!NotesMap.isEmpty() && NotesMap.contains(id)) {
            return NotesMap.get(id);
        } else {
            System.out.println("No note or Map is empty");
            return null;
        }
    }

    public void editNote(JsonObject text, String id){
        if (!NotesMap.isEmpty() && NotesMap.contains(id)) {
            NotesMap.replace(id, text);
        } else {
            System.out.println("No note or Map is empty or you fucked up");
        }
    }

    public void deleteNote(String id){
        if (!NotesMap.isEmpty() && NotesMap.contains(id)) {
            NotesMap.remove(id);
        } else {
            System.out.println("No note or Map is empty");
        }
    }

}
