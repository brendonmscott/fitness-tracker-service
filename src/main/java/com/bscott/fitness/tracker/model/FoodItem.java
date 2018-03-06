package com.bscott.fitness.tracker.model;

import com.bscott.fitness.tracker.Constants;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Document(collection = Constants.FOODS_COLLECTION)
public class FoodItem {

    @Id
    private String id;
    @NotEmpty
    private String name;
    @NotNull
    private Integer calories;
    @NotNull
    private BigDecimal fatGrams;
    @NotNull
    private BigDecimal proteinGrams;
    @NotNull
    private BigDecimal carbGrams;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCalories() {
        return calories;
    }

    public void setCalories(Integer calories) {
        this.calories = calories;
    }

    public BigDecimal getFatGrams() {
        return fatGrams;
    }

    public void setFatGrams(BigDecimal fatGrams) {
        this.fatGrams = fatGrams.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getProteinGrams() {
        return proteinGrams;
    }

    public void setProteinGrams(BigDecimal proteinGrams) {
        this.proteinGrams = proteinGrams.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public BigDecimal getCarbGrams() {
        return carbGrams;
    }

    public void setCarbGrams(BigDecimal carbGrams) {
        this.carbGrams = carbGrams.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
