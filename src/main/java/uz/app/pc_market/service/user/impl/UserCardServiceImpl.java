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
                    .message("User not found")
                    .data(null)
                    .build();
        }
        List<Card> cards = userCardRepository.findByCardHolderId(userId);
        return ResponseMessage.builder()
                .success(true)
                .message("Cards retrieved successfully")
                .data(cards)
                .build();
    }

    @Override
    public ResponseMessage addUserCard(Long userId, Card card) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("User not found")
                    .data(null)
                    .build();
        }
        if (card.getNumber() == null || card.getNumber().isEmpty() ||
                card.getPassword() == null || card.getPassword().isEmpty() ||
                card.getCardStatus() == null) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Card number, password, and status are required")
                    .data(null)
                    .build();
        }
        User currentUser = userOptional.get();
        card.setCardHolder(currentUser);
        Card savedCard = userCardRepository.save(card);
        return ResponseMessage.builder()
                .success(true)
                .message("Card added successfully")
                .data(savedCard)
                .build();
    }

    @Override
    public ResponseMessage updateUserCard(Long cardId, Double balance) {
        Optional<Card> cardOptional = userCardRepository.findById(cardId);
        if (cardOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Card not found")
                    .data(null)
                    .build();
        }
        Card currentCard = cardOptional.get();
        currentCard.setAmount(balance);
        Card updatedCard = userCardRepository.save(currentCard);
        return ResponseMessage.builder()
                .success(true)
                .message("Card updated successfully")
                .data(updatedCard)
                .build();
    }

    @Override
    public ResponseMessage deleteUserCard(Long cardId, Long userId) {
        Optional<Card> cardOptional = userCardRepository.findById(cardId);
        if (cardOptional.isEmpty()) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("Card not found")
                    .data(null)
                    .build();
        }
        Card card = cardOptional.get();
        if (!card.getCardHolder().getId().equals(userId)) {
            return ResponseMessage.builder()
                    .success(false)
                    .message("You cannot delete this card")
                    .data(null)
                    .build();
        }
        userCardRepository.delete(card);
        return ResponseMessage.builder()
                .success(true)
                .message("Card deleted successfully")
                .data(null)
                .build();
    }
}
