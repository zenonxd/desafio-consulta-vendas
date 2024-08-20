package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SellerMinDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {


    @Query("SELECT new com.devsuperior.dsmeta.dto.SaleMinDTO(obj.id, obj.date, obj.amount, obj.seller.name) "
    + "FROM Sale obj JOIN obj.seller s "
    + "WHERE obj.date BETWEEN :minDate AND :maxDate AND UPPER(s.name) LIKE UPPER(concat('%', :sellerName, '%'))")
    Page<SaleMinDTO> findByDateBetweenAndSellerName(LocalDate minDate, LocalDate maxDate, String sellerName, Pageable pageable);


    @Query("SELECT new com.devsuperior.dsmeta.dto.SellerMinDTO(s.seller.name AS sellerName, SUM(s.amount) AS totalAmount) "
    + "FROM Sale s "
    + "WHERE s.date between :minDate and :maxDate "
    + "GROUP BY s.seller.id, s.seller.name")
    List<SellerMinDTO> searchBySeller(@Param("minDate") LocalDate minDate, @Param("maxDate") LocalDate maxDate);
}
