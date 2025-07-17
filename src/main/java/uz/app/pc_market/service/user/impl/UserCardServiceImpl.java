package uz.app.pc_market.service.user.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.userdto.ResponseMessage;
import uz.app.pc_market.entity.Card;
import uz.app.pc_market.entity.User;
import uz.app.pc_market.repository.userrepo.UserCardRepository;
import uz.app.pc_market.repository.userrepo.UserRepository;
import uz.app.pc_market.service.user.UserCardService;

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
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("user not found")
                    .data(null)
                    .build();
        }
        User currentUser = userOptional.get();
        card.setCardHolder(currentUser);
        Card savedCard = userCardRepository.save(card);
        return ResponseMessage.builder()
                .success(true)
                .message("card added successfully")
                .data(savedCard)
                .build();
    }

    @Override
    public ResponseMessage updateUserCard(Long cardId, Double balance) {
        Optional<Card> cardOptional = userCardRepository.findById(cardId);
        if (cardOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("card not found")
                    .data(null)
                    .build();
        }
        Card currentCard = cardOptional.get();
        currentCard.setAmount(currentCard.getAmount() + balance);
        Card updatedCard = userCardRepository.save(currentCard);
        return ResponseMessage.builder()
                .success(true)
                .message("card updated successfully")
                .data(updatedCard)
                .build();
    }

    @Override
    public ResponseMessage deleteUserCard(Long cardId, Long userId) {
        Optional<Card> cardOptional = userCardRepository.findById(cardId);
    if (cardOptional.isPresent()) {
        Card card = cardOptional.get();
        if (card.getCardHolder().getId().equals(userId)) {
            userCardRepository.delete(card);
            return ResponseMessage.builder()
                    .success(true)
                    .message("card deleted successfully")
                    .data(null)
                    .build();
        } else {
            return ResponseMessage.builder()
                    .success(false)
                    .message("you cannot delete this card")
                    .data(null)
                    .build();
        }
    }
    else {
        return ResponseMessage.builder()
                .success(false)
                .message("card not found")
                .data(null)
                .build();
    }
    }
}
