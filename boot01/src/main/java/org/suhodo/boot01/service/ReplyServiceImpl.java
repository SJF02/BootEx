package org.suhodo.boot01.service;

import java.lang.foreign.Linker.Option;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.suhodo.boot01.domain.Reply;
import org.suhodo.boot01.dto.PageRequestDTO;
import org.suhodo.boot01.dto.PageResponseDTO;
import org.suhodo.boot01.dto.ReplyDTO;
import org.suhodo.boot01.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class ReplyServiceImpl implements ReplyService{

    /* main에서는 bean을 주입할 때 생성자 주입 방식을 사용한다.
     * 
     * @RequiredArgsConstructor + private final
     */
    private final ReplyRepository replyRepository;

    private final ModelMapper modelMapper;

    @Override
    public Long register(ReplyDTO replyDTO) {

        Reply reply = modelMapper.map(replyDTO, Reply.class);

        Long rno = replyRepository.save(reply).getRno();

        return rno;
    }

    @Override
    public ReplyDTO read(Long rno) {
        Optional<Reply> replyOptional = replyRepository.findById(rno);
        Reply reply = replyOptional.orElseThrow();
        return modelMapper.map(reply, ReplyDTO.class);
    }

    @Override
    public void modify(ReplyDTO replyDTO) {
        Optional<Reply> replyOptional = replyRepository.findById(replyDTO.getRno());
        Reply reply = replyOptional.orElseThrow();
        reply.changeText(replyDTO.getReplyText());
        replyRepository.save(reply);
    }

    @Override
    public void remove(Long rno) {
        replyRepository.deleteById(rno);
    }

    @Override
    public PageResponseDTO<ReplyDTO> getListOfBoard(Long bno, PageRequestDTO pageRequestDTO) {
        PageRequest pageable = PageRequest.of(pageRequestDTO.getPage() <= 0 ? 0 :
                                                pageRequestDTO.getPage() - 1,
                                                pageRequestDTO.getSize(),
                                                Sort.by("rno").ascending());

        return null;
    }

}
