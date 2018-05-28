package restController;

import domain.Cursa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repo.CurseJdbcRepo;

import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping(value = "/curse")
public class CursaController {
    private Properties props = PropertiesConfiguration.getInstance().getProps();
    private CurseJdbcRepo repo = new CurseJdbcRepo(props);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable String id) {
        Cursa cursa = repo.findOne(Integer.parseInt(id));
        if (cursa == null) {
            return new ResponseEntity<>("cursa nu a fost gasita", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(cursa, HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Cursa> getAll() {
        return repo.findAll();
    }

    @RequestMapping(method = RequestMethod.POST)
    public Cursa save(@RequestBody Cursa cursa) {
        repo.save(cursa);
        System.out.println("saved");
        return cursa;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Cursa update(@RequestBody Cursa cursa) {
        repo.update(cursa);
        System.out.println("updated");
        return cursa;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable String id) {
        try {
            repo.delete(Integer.parseInt(id));
            return new ResponseEntity<Cursa>(HttpStatus.OK);
        } catch (Exception ex) {
            System.out.println("error at delete " + ex);
            ex.printStackTrace();
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
