package com.kakaopay.project.api.product.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@AllArgsConstructor
@Getter
public class ProductModel {

  @Setter
  private Long productId;

  @NonNull
  private String title;

  @NonNull
  private ProductType productType;

  @NonNull
  private Long totalInvestingAmount;

  @NonNull
  private String startedAt;

  @NonNull
  private String finishedAt;

  @Setter
  private String createdTime;

  @Setter
  private String modifiedTime;

  @NonNull
  private Long registrantId;

}
