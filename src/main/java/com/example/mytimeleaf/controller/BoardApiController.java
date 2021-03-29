package com.example.mytimeleaf.controller;

import java.util.List;

import com.example.mytimeleaf.model.Board;
import com.example.mytimeleaf.repository.BoardRepository;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
class BoardApiController {

    private final BoardRepository repository;

    BoardApiController(BoardRepository repository) {
        this.repository = repository;
    }


    // Aggregate root
    // tag::get-aggregate-root[]
    @GetMapping("/board")
    List<Board> all() {
        return repository.findAll();
    }
    // end::get-aggregate-root[]

    @PostMapping("/board")
    Board newBoard(@RequestBody Board newBoard) {
        return repository.save(newBoard);
    }

    // Single item

    @GetMapping("/board/{id}")
    Board one(@PathVariable Long id) {

        return repository.findById(id).orElse(null);
    }

    @PutMapping("/board/{id}")
    Board replaceEmployee(@RequestBody Board newBoard, @PathVariable Long id) {

        return repository.findById(id)
                .map(board -> {
                    board.setTitle(newBoard.getTitle());
                    board.setContent(newBoard.getContent());
                    return repository.save(board);
                })
                .orElseGet(() -> {
                    newBoard.setId(id);
                    return repository.save(newBoard);
                });
    }

    @DeleteMapping("/board/{id}")
    void deleteBoard(@PathVariable Long id) {
        repository.deleteById(id);
    }
}