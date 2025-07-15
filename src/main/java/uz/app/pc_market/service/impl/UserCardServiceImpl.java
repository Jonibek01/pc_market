package uz.app.pc_market.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.ResponseMessage;
import uz.app.pc_market.entity.Card;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.repository.userrepo.UserCardRepository;
import uz.app.pc_market.repository.userrepo.UserRepository;
import uz.app.pc_market.service.UserCardService;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserCardServiceImpl implements UserCardService {
    private final UserCardRepository userCardRepository;
    private final UserRepository userRepository;

    @Override
    public ResponseMessage getAllUserCards(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("user not found")
                    .data(null)
                    .build();
        }
        List<Card> cards = userCardRepository.findByCardHolderId(userId);
        return ResponseMessage.builder()
                .success(true)
                .data(cards)
                .message("cards retrieved successfully")
                .build();
    }

    @Override
    public ResponseMessage addUserCard(Long userId, Card card) {
        return null;
    }

    @Override
    public ResponseMessage updateUserCard(Long cardId, Double balance) {
        return null;
    }

    @Override
    public ResponseMessage deleteUserCard(Long cardId, Long userId) {
        return null;
    }
}
