package com.zoho.parking_system.repo;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zoho.parking_system.model.DiscountCoupon;

public interface DiscountCouponRepository extends PagingAndSortingRepository<DiscountCoupon, String> {

	Optional<DiscountCoupon> findByCoupon(String output);

	Page<DiscountCoupon> findAll(Specification<DiscountCoupon> spec, Pageable paging);

}
