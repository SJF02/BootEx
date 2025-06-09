package org.suhodo.boot01.controller;

import java.util.Map;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.suhodo.boot01.dto.ReplyDTO;
import org.suhodo.boot01.service.ReplyService;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/replies")
@Log4j2
@RequiredArgsConstructor
public class ReplyController {

    private final ReplyService replyService;
    
    /* 
    @Operation(description="Replies POST")
    @PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Long>> register(@Valid @RequestBody ReplyDTO replyDTO){

        log.info(replyDTO);

        Map<String, Long> resultMap = Map.of("rno", 111L);

        return ResponseEntity.ok(resultMap);
    }
    */

    @Operation(description="Replies POST")
    @PostMapping(value="/", consumes=MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> register(@Valid @RequestBody ReplyDTO replyDTO,
                                    BindingResult bindingResult) throws BindException{

        log.info(replyDTO);

        if(bindingResult.hasErrors()){
            throw new BindException(bindingResult);
        }

        Map<String, Long> resultMap = Map.of("rno", 111L);

        Long rno = replyService.register(replyDTO);

        resultMap.put("rno", rno);

        return resultMap;
    }
}
