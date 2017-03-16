package gov.nasa;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@ApplicationPath("/")
public class App extends Application{

    private Set<Class<?>> classes = new HashSet<>();
    private Set<Object> singletons = new HashSet<>();

    public App(){
        classes.add(NotesServlet.class);
        classes.add(NotesRepository.class);

    }

    @Override
    public Set<Class<?>> getClasses() { return classes; }

    @Override
    public Set<Object> getSingletons() {return singletons; }


}
