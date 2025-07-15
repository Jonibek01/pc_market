//package uz.app.pc_market.exceptions;
//
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import uz.app.companycrud.dto.ResponseMessage;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
//@ControllerAdvice
////@ResponseBody
//public class GlobalHandler {
//
//    @ExceptionHandler(ItemNotFoundException.class)
//    public ResponseEntity<?> handleItemnotFoundException(ItemNotFoundException e) {
//        ResponseMessage build = ResponseMessage
//                .builder()
//                .status(false)
//                .message(e.getMessage())
//                .build();
//        return ResponseEntity.status(404).body(build);
//    }
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleValidationErrors(MethodArgumentNotValidException ex) {
//        Map<String, String> errors = new HashMap<>();
//        ex.getBindingResult().getFieldErrors()
//                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
//        return ResponseEntity.badRequest().body(errors);
//    }
//    @ExceptionHandler(UserBadRequestException.class)
//    public ResponseEntity<?> handleUserBadRequestException(ItemNotFoundException e) {
//        ResponseMessage build = ResponseMessage
//                .builder()
//                .status(false)
//                .message(e.getMessage())
//                .build();
//        return ResponseEntity.status(400).body(build);
//    }
//
//    @ExceptionHandler
//    public ResponseEntity<?> handleException(Exception e) {
//        ResponseMessage build = ResponseMessage
//                .builder()
//                .status(false)
//                .message(e.getMessage())
//                .build();
//
//        return ResponseEntity.status(400).body(build);
//    }
//}
