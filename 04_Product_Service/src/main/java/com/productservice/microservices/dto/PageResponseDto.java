package com.productservice.microservices.dto;

import java.util.List;

public class PageResponseDto<T> {

	List<T> data;
	Integer currentPageNumber;
	Integer sizeOfDataOnload;

	Integer totalProducts;
	Integer totalPages;

	Boolean firstPage;
	Boolean lastPage;

}
