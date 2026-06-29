package com.productservice.microservices.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResponseDto<T> {

	List<T> data;
	
	Integer currentPageNumber;
	Integer sizeOfDataOnload;

	Long totalProducts;
	Integer totalPages;

	Boolean firstPage;
	Boolean lastPage;

}
