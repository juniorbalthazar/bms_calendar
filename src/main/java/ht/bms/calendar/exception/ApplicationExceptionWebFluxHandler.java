package ht.bms.calendar.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.function.Consumer;

@Service
public class ApplicationExceptionWebFluxHandler {
    public Mono<ServerResponse> handleException(HttpStatus status, Exception exception, ServerRequest request, Consumer<ProblemDetail> consumer){
        var problem = ProblemDetail.forStatusAndDetail(status, exception.getMessage());
        consumer.accept(problem);
        problem.setInstance(URI.create(request.uri().toString()));
        return ServerResponse.status(status).bodyValue(problem);
    }

}
