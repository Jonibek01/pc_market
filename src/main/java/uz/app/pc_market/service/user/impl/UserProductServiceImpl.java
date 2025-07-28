package uz.app.pc_market.service.user.impl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.app.pc_market.dto.userdto.ProductFilterDTO;
import uz.app.pc_market.entity.Product;
import uz.app.pc_market.repository.userrepo.UserProductRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import uz.app.pc_market.service.user.UserProductService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProductServiceImpl implements UserProductService {
    private final UserProductRepository productRepository;
    private final EntityManager entityManager;

    @Override
    public List<Product> filterProducts(ProductFilterDTO filterDto) {
        StringBuilder jpql = new StringBuilder("SELECT p FROM Product p WHERE 1=1");
        List<Object> parameters = new ArrayList<>();
        int paramIndex = 1;

        if (filterDto.getCategoryId() != null) {
            jpql.append(" AND p.subCategory.category.id = ?").append(paramIndex++);
            parameters.add(filterDto.getCategoryId());
        }

        if (filterDto.getSubCategoryId() != null) {
            jpql.append(" AND p.subCategory.id = ?").append(paramIndex++);
            parameters.add(filterDto.getSubCategoryId());
        }

        if (filterDto.getMinPrice() != null) {
            jpql.append(" AND p.price >= ?").append(paramIndex++);
            parameters.add(filterDto.getMinPrice());
        }

        if (filterDto.getMaxPrice() != null) {
            jpql.append(" AND p.price <= ?").append(paramIndex++);
            parameters.add(filterDto.getMaxPrice());
        }

        if (filterDto.getCharacteristicIds() != null && !filterDto.getCharacteristicIds().isEmpty()) {
            for (int i = 0; i < filterDto.getCharacteristicIds().size(); i++) {
                Long charId = filterDto.getCharacteristicIds().get(i);
                if (charId != null) {
                    jpql.append(" AND EXISTS (SELECT pc FROM ProductCharacteristic pc WHERE pc.product = p" +
                            " AND pc.characteristic.id = ?").append(paramIndex++).append(")");
                    parameters.add(charId);
                }
            }
        }

        TypedQuery<Product> query = entityManager.createQuery(jpql.toString(), Product.class);
        for (int i = 0; i < parameters.size(); i++) {
            query.setParameter(i + 1, parameters.get(i));
        }

        return query.getResultList();
    }
}
