package br.com.lojavirtual.controller;

import br.com.lojavirtual.dto.PageResponse;
import br.com.lojavirtual.dto.user.UserPatchRequest;
import br.com.lojavirtual.dto.user.UserRequest;
import br.com.lojavirtual.dto.user.UserResponse;
import br.com.lojavirtual.dto.user.UserUpdateRequest;
import br.com.lojavirtual.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/users")
public class UserController {
    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(id));
    }

    @GetMapping
    public ResponseEntity<PageResponse<UserResponse>> list(
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "cpf", required = false) String cpf,
            @RequestParam(value = "email", required = false) String email,
            @RequestParam(value = "active", required = false, defaultValue = "true") Boolean active,
            @RequestParam(value = "pageNumber",  required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(value = "pageSize",  required = false, defaultValue = "8") Integer pageSize
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.list(name, cpf, email, active, pageNumber, pageSize));
    }

    @PostMapping
    public ResponseEntity<UserResponse> insert(@Valid  @RequestBody UserRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.insert(request));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.update(id, request));
    }

    @PatchMapping(value = "/{id}")
    public ResponseEntity<UserResponse> updatePartial(@PathVariable Long id, @Valid @RequestBody UserPatchRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.updatePartial(id, request));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        userService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
