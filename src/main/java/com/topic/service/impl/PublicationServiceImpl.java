package com.topic.service.impl;

import com.topic.entity.main.Board;
import com.topic.entity.main.Publication;
import com.topic.entity.main.User;
import com.topic.repository.BoardRepository;
import com.topic.repository.PublicationRepository;
import com.topic.repository.UserRepository;
import com.topic.service.PublicationService;
import com.topic.service.dto.CreatePublicationDto;
import com.topic.service.dto.PublicationDto;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;

    private final UserRepository userRepository;

    private final BoardRepository boardRepository;

    @Autowired
    public PublicationServiceImpl(
            PublicationRepository publicationRepository,
            UserRepository userRepository,
            BoardRepository boardRepository
    ) {
        this.publicationRepository = publicationRepository;
        this.userRepository = userRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public PublicationDto createPublication(CreatePublicationDto data) {

        Publication publication = new Publication();

        publication.setContent(data.content());

        // TODO: используется тестовый пользователь как автор ЛЮБЫХ досок
        Optional<User> author = userRepository.findById(1L);
        Optional<Board> board = boardRepository.findById(data.boardId());

        if (author.isEmpty() || board.isEmpty()){
            throw new RuntimeException("");
        }

        publication.setAuthor(author.get());
        publication.setBoard(board.get());

        var res = publicationRepository.save(publication);
        return PublicationServiceUtil.mapToPublicationDto(res);
    }

    @Override
    public PublicationDto getPublication(Long publicationId) throws EntityExistsException {
        Optional<Publication> publication = publicationRepository.findById(publicationId);
        if (publication.isEmpty()) {
            throw new EntityExistsException();
        }
        return PublicationServiceUtil.mapToPublicationDto(publication.get());
    }
}


class PublicationServiceUtil {
    public static PublicationDto mapToPublicationDto(Publication data) {
        return new PublicationDto(data.getId(), data.getAuthor().getUsername(), data.getContent());
    }
}