package fr.fullstack.shopapp.service;

import fr.fullstack.shopapp.model.Shop;
import fr.fullstack.shopapp.repository.ShopSearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShopSearchService {
    @Autowired
    private ShopSearchRepository shopSearchRepository;

    public List<Shop> searchShops(
            Boolean inVacations,
            LocalDate startDate,
            LocalDate endDate,
            String name
    ) {
        return shopSearchRepository.searchShops(
                inVacations, startDate, endDate, name
        );
    }
}