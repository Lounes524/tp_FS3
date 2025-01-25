package fr.fullstack.shopapp.repository;

import fr.fullstack.shopapp.model.Shop;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

@Repository
public class ShopSearchRepository {
    @Autowired
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    public List<Shop> searchShops(
            Boolean inVacations,
            LocalDate startDate,
            LocalDate endDate,
            String name
    ) {
        SearchSession searchSession = Search.session(entityManager);

        if (name == null || name.isEmpty()) {
            return searchSession.search(Shop.class)
                    .where(f -> {
                        var predicateBuilder = f.bool();

                        if (inVacations != null) {
                            predicateBuilder.must(
                                    f.match().field("inVacations").matching(inVacations)
                            );
                        }

                        if (startDate != null && endDate != null) {
                            predicateBuilder.must(
                                    f.range().field("createdAt").between(startDate, endDate)
                            );
                        } else if (startDate != null) {
                            predicateBuilder.must(
                                    f.range().field("createdAt").atLeast(startDate)
                            );
                        } else if (endDate != null) {
                            predicateBuilder.must(
                                    f.range().field("createdAt").atMost(endDate)
                            );
                        }

                        return predicateBuilder;
                    })
                    .fetchHits(1000);
        } else {
            return searchSession.search(Shop.class)
                    .where(f -> {
                        var predicateBuilder = f.bool();

                        predicateBuilder.must(
                                f.wildcard()
                                        .field("name")
                                        .matching("*" + name + "*")
                        );

                        if (inVacations != null) {
                            predicateBuilder.must(
                                    f.match().field("inVacations").matching(inVacations)
                            );
                        }

                        if (startDate != null && endDate != null) {
                            predicateBuilder.must(
                                    f.range().field("createdAt").between(startDate, endDate)
                            );
                        } else if (startDate != null) {
                            predicateBuilder.must(
                                    f.range().field("createdAt").atLeast(startDate)
                            );
                        } else if (endDate != null) {
                            predicateBuilder.must(
                                    f.range().field("createdAt").atMost(endDate)
                            );
                        }

                        return predicateBuilder;
                    })
                    .fetchHits(1000);
        }
    }
}