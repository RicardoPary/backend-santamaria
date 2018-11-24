package com.mycompany.myapp.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.mycompany.myapp.domain.Supply;
import com.mycompany.myapp.domain.*; // for static metamodels
import com.mycompany.myapp.repository.SupplyRepository;
import com.mycompany.myapp.service.dto.SupplyCriteria;

/**
 * Service for executing complex queries for Supply entities in the database.
 * The main input is a {@link SupplyCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Supply} or a {@link Page} of {@link Supply} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SupplyQueryService extends QueryService<Supply> {

    private final Logger log = LoggerFactory.getLogger(SupplyQueryService.class);

    private final SupplyRepository supplyRepository;

    public SupplyQueryService(SupplyRepository supplyRepository) {
        this.supplyRepository = supplyRepository;
    }

    /**
     * Return a {@link List} of {@link Supply} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Supply> findByCriteria(SupplyCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Supply> specification = createSpecification(criteria);
        return supplyRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Supply} which matches the criteria from the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Supply> findByCriteria(SupplyCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Supply> specification = createSpecification(criteria);
        return supplyRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SupplyCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Supply> specification = createSpecification(criteria);
        return supplyRepository.count(specification);
    }

    /**
     * Function to convert SupplyCriteria to a {@link Specification}
     */
    private Specification<Supply> createSpecification(SupplyCriteria criteria) {
        Specification<Supply> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Supply_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Supply_.name));
            }
            if (criteria.getType() != null) {
                specification = specification.and(buildStringSpecification(criteria.getType(), Supply_.type));
            }
            if (criteria.getStock() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getStock(), Supply_.stock));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), Supply_.description));
            }
            if (criteria.getSalePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getSalePrice(), Supply_.salePrice));
            }
            if (criteria.getWholesalePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWholesalePrice(), Supply_.wholesalePrice));
            }
            if (criteria.getPurchasePrice() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPurchasePrice(), Supply_.purchasePrice));
            }
            if (criteria.getMarke() != null) {
                specification = specification.and(buildStringSpecification(criteria.getMarke(), Supply_.marke));
            }
            if (criteria.getDiscount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDiscount(), Supply_.discount));
            }
            if (criteria.getBarcode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBarcode(), Supply_.barcode));
            }
            if (criteria.getUrlImage() != null) {
                specification = specification.and(buildStringSpecification(criteria.getUrlImage(), Supply_.urlImage));
            }
            if (criteria.getImageName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageName(), Supply_.imageName));
            }
            if (criteria.getImageCache() != null) {
                specification = specification.and(buildStringSpecification(criteria.getImageCache(), Supply_.imageCache));
            }
            if (criteria.getInventory() != null) {
                specification = specification.and(buildSpecification(criteria.getInventory(), Supply_.inventory));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildSpecification(criteria.getCategoryId(),
                    root -> root.join(Supply_.category, JoinType.LEFT).get(Category_.id)));
            }
        }
        return specification;
    }
}
