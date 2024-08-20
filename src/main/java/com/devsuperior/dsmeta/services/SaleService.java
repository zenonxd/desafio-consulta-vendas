package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> salesReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable) {

		Page<SaleMinDTO> sales = repository.findByDateBetweenAndSellerName(minDate, maxDate, name, pageable);

		return sales;


	}


	public List<SellerMinDTO> salesSummary(LocalDate minDate, LocalDate maxDate) {

		List<SellerMinDTO> sales = repository.searchBySeller(minDate, maxDate);

		return sales;
	}
}
