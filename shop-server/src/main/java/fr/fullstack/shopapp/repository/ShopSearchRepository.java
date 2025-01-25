package fr.fullstack.shopapp.repository;

import fr.fullstack.shopapp.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShopSearchRepository extends JpaRepository<Shop, Long> {
    @Query(nativeQuery = true, value =
            "SELECT * FROM search_shops(:inVacations, :startDate, :endDate, :name)")
    List<Shop> searchShops(
            @Param("inVacations") Boolean inVacations,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("name") String name
    );
}