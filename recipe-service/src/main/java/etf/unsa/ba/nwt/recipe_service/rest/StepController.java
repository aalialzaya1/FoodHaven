package etf.unsa.ba.nwt.recipe_service.rest;

import etf.unsa.ba.nwt.recipe_service.model.StepDTO;
import etf.unsa.ba.nwt.recipe_service.service.StepService;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/steps", produces = MediaType.APPLICATION_JSON_VALUE)
public class StepController {

    private final StepService stepService;

    public StepController(final StepService stepService) {
        this.stepService = stepService;
    }

    @GetMapping
    public ResponseEntity<List<StepDTO>> getAllSteps() {
        return ResponseEntity.ok(stepService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StepDTO> getStep(@PathVariable final Integer id) {
        return ResponseEntity.ok(stepService.get(id));
    }

    @PostMapping
    public ResponseEntity<Integer> createStep(@RequestBody @Valid final StepDTO stepDTO) {
        return new ResponseEntity<>(stepService.create(stepDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateStep(@PathVariable final Integer id,
            @RequestBody @Valid final StepDTO stepDTO) {
        stepService.update(id, stepDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStep(@PathVariable final Integer id) {
        stepService.delete(id);
        return ResponseEntity.noContent().build();
    }

}