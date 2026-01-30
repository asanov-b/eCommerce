package com.ecommerce.ecommerce.modules.product.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class ProductSearchDTO {
    private String name;
    private String description;
    private String category;

    @Min(0)
    private BigDecimal minPrice;
    @Min(0)
    private BigDecimal maxPrice;

    private Boolean inStock;

    @Schema(
            description = "Sort field",
            allowableValues = {"createdAt", "price", "name"}
    )
    private String sort = "createdAt";

    @Schema(
            description = "Sort direction",
            allowableValues = {"ASC", "DESC"}
    )
    private Sort.Direction direction = Sort.Direction.ASC;

    @Min(0)
    private Integer page = 0;
    @Min(1)
    @Max(100)
    private Integer size = 10;

    @JsonIgnore
    @AssertTrue(message = "minPrice must be <= maxPrice")
    public boolean isPriceRangeValid() {
        if (minPrice == null || maxPrice == null) return true;
        return minPrice.compareTo(maxPrice) <= 0;
    }

    @JsonIgnore
    @AssertTrue(message = "sort must be by createdAt or price or name")
    public boolean isSortValid() {
        Set<String> sortFields = Set.of("createdAt", "price", "name");
        return sort==null || sortFields.contains(sort);
    }
}
