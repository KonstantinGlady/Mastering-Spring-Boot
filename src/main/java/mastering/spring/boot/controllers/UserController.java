package mastering.spring.boot.controllers;

import mastering.spring.boot.exceptions.NonUniqueEmailException;
import mastering.spring.boot.models.User;
import mastering.spring.boot.repositores.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger log = LoggerFactory.getLogger(UserController.class);
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        long start = System.currentTimeMillis();

        return userRepository.findAll()
                .doOnSubscribe(subscription ->
                        log.debug("Subscribed to user stream"))
                .doOnNext(user ->
                        log.debug("Process user {} in {} ms", user,
                                System.currentTimeMillis() - start))
                .doOnComplete(() ->
                        log.info("Finished streaming users for getAllUsers in {} ms",
                                System.currentTimeMillis() - start));
    }

    @GetMapping("/{id}")
    public Mono<User> findById(@PathVariable String id) {
        return userRepository.findById(id)
                .doOnSuccess(user ->
                        log.info("User find {}", user))
                .doOnError(error ->
                        log.error("User not found {}", error.getMessage()));
    }

    @DeleteMapping("/{id}")
    public Mono<Void> deleteById(@PathVariable String id) {
        return userRepository.deleteById(id);
    }

    @PostMapping
    public Mono<ResponseEntity<User>> createUser(@RequestBody User user) {
        return userRepository.findByEmail(user.email())
                .flatMap(emailExists ->
                        Mono.error(new NonUniqueEmailException("Email already exists!")))
                .then(userRepository.save(user))
                .map(ResponseEntity::ok)
                .doOnNext(createdUser ->
                        System.out.println("Created user " + createdUser))
                .onErrorResume(e -> {
                    System.out.println("Exception has occurred " + e.getMessage());

                    var status = (e instanceof NonUniqueEmailException)
                            ? CONFLICT : INTERNAL_SERVER_ERROR;

                    return Mono.just(
                            ResponseEntity
                                    .status(status)
                                    .build());
                });
    }

    @GetMapping("/stream")
    public Flux<User> streamUsers() {
        long start = System.currentTimeMillis();
        return userRepository.findAll()
                .onBackpressureBuffer()
                .doOnNext(user ->
                        log.debug("Process user {} in {} ms ", user,
                                System.currentTimeMillis() - start))
                .doOnError(error ->
                        log.error("Error streaming user {}", error))
                .doOnComplete(() ->
                        log.info("Finished streaming users for streamUsers in {} ms",
                                System.currentTimeMillis() - start));
    }
}
