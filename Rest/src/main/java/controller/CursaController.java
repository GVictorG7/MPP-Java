package controller;

import domain.Cursa;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repo.CurseJdbcRepo;

import java.util.List;
import java.util.Properties;

@RestController
@RequestMapping(value = "/curse")
public class CursaController {
    private final Properties props = PropertiesConfiguration.getInstance().getProps();
    private final CurseJdbcRepo repo = new CurseJdbcRepo(props);

    @GetMapping(value = "/{id}")
    public ResponseEntity getById(@PathVariable String id) {
        Cursa cursa = repo.findOne(Integer.parseInt(id));
        if (cursa == null) {
            return new ResponseEntity<>("cursa nu a fost gasita", HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(cursa, HttpStatus.OK);
        }
    }

    @GetMapping
    public List<Cursa> getAll() {
        return repo.findAll();
    }

    @PostMapping
    public Cursa save(@RequestBody Cursa cursa) {
        repo.save(cursa);
        System.out.println("saved");
        return cursa;
    }

    @PutMapping(value = "/{id}")
    public Cursa update(@RequestBody Cursa cursa) {
        repo.update(cursa);
        System.out.println("updated");
        return cursa;
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity delete(@PathVariable String id) {
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
