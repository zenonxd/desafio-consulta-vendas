package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(
			@RequestParam(name = "minDate", required = false) String dataInicialStr,
			@RequestParam(name = "maxDate", required = false) String dataFinalStr,
			@RequestParam(name = "name", defaultValue = "") String sellerName,
			Pageable pageable
	) {
		// TODO
		LocalDate dataFinal;
		LocalDate dataInicial;
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		//data final
		if (dataFinalStr == null || dataFinalStr.isEmpty()) {
			dataFinal = today;
		} else {
			dataFinal = LocalDate.parse(dataFinalStr);
		}

		//data inicial

		if (dataInicialStr == null || dataFinalStr.isEmpty()) {
			dataInicial = dataFinal.minusYears(1l);
		} else {
			dataInicial = LocalDate.parse(dataInicialStr);
		}


		Page<SaleMinDTO> dto = service.salesReport(dataInicial, dataFinal, sellerName, pageable);
		return ResponseEntity.ok(dto);
	}


	@GetMapping(value = "/summary")
	public ResponseEntity<List<SellerMinDTO>> getSummary(
			@RequestParam(name = "minDate", required = false) String minDateStr,
			@RequestParam(name = "maxDate", required = false) String maxDateStr) {
		// TODO

		//minDate
		LocalDate initialDate;

		//maxDate
		LocalDate finalDate;


		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		if (maxDateStr == null || maxDateStr.isEmpty()) {
			finalDate = today;
		} else {
			finalDate = LocalDate.parse(maxDateStr);
		}

		if (minDateStr == null || minDateStr.isEmpty()) {
			initialDate = finalDate.minusYears(1L);
		} else {
			initialDate = LocalDate.parse(minDateStr);
		}

		List<SellerMinDTO> dto = service.salesSummary(initialDate, finalDate);

		return ResponseEntity.ok(dto);





	}
}
